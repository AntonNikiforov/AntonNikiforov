package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.service.BookService;
import by.training.library.service.exception.DeleteException;
import by.training.library.service.exception.NoSuchBookException;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBook implements Command {

    public static final String BOOK_ID = "id";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            return Page.START_PAGE;
        }

        try {
            Integer bookId = Integer.valueOf(request.getParameter(BOOK_ID));

            //CustomDao dao = new CustomDao();
            BookService service = BookService.getInstance();
            service.deleteBook(bookId);

            return Command.BOOKS;

        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.BOOKS;
        } catch (NoSuchBookException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKS;
        } catch (DeleteException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOK;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

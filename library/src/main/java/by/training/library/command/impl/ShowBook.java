package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Book;
import by.training.library.service.BookService;
import by.training.library.service.exception.NoSuchBookException;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowBook implements Command {

    public static final String BOOK_ID = "id";
    public static final String BOOK = "book";
    public static final String NUM = "num";
    public static final String COMMENTS = "comments";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if (SessionScope.getUserId(request) == null) return Page.START_PAGE;

        try {
            Integer bookId = Integer.valueOf(request.getParameter(BOOK_ID));

            //CustomDao dao = new CustomDao();
            BookService service = BookService.getInstance();
            Book book = service.readBook(bookId);

            //CommentDao cDao = new CommentDao();
            //List<Comment> list = cDao.getCommentsByBook(book);
            //request.setAttribute(COMMENTS, list);

            int numInLibNow = book.getNum() - service.getAllOpenBookings(bookId).size();

            request.setAttribute(BOOK, book);
            request.setAttribute(NUM, numInLibNow);
            //return "/book.jsp";
            return Page.BOOK_PAGE;
        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.BOOKS;
        } catch (NoSuchBookException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

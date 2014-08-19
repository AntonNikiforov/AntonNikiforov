package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Book;
import by.training.library.service.BookService;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBook implements Command {

    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";
    public static final String NUM = "num";
    public static final String GENRE = "genre_id";

    public static final String MESSAGE = "msg";
    public static final String GENRE_LIST = "genre_list";
    public static final String BOOK = "book";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            return Page.START_PAGE;
        }

        String name = request.getParameter(NAME);
        String author = request.getParameter(AUTHOR);
        String year = request.getParameter(YEAR);
        String numOfBooks = request.getParameter(NUM);
        String genreId = request.getParameter(GENRE);

        if (name != null && author != null && year != null && numOfBooks != null && genreId != null) {
            if ("".equals(name) || "".equals(author) || "".equals(year) || "".equals(numOfBooks)) {
                request.setAttribute(MESSAGE, "empty fields");
            } else {

                try {
                    BookService service = BookService.getInstance();
                    Book book = service.createBook(name, author,
                            Integer.parseInt(year), Integer.parseInt(numOfBooks), Integer.parseInt(genreId));

                    //return "/book?id=" + id;
                    request.setAttribute(BOOK, book);
                    return Page.BOOK_PAGE;

                } catch (ServiceException e) {
                    throw new CommandException(e);
                } catch (IllegalArgumentException e) {
                    request.setAttribute(MESSAGE, "wrong request");
                }
            }
        }

        try {
            BookService service = BookService.getInstance();
            request.setAttribute(GENRE_LIST, service.getAllGenres());

            return Page.ADD_BOOK_PAGE;

        } catch (ServiceException e) {
            throw new CommandException(e);
        }

    }
}

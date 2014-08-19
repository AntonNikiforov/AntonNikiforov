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

public class EditBook implements Command {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";
    public static final String NUM = "num";
    public static final String GENRE = "genre_id";

    public static final String BOOK = "book";
    public static final String GENRE_LIST = "genre_list";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);

        if (admin == null || !admin) {
            return Command.BOOKS;
        }

        String name = request.getParameter(NAME);
        String author = request.getParameter(AUTHOR);
        String year = request.getParameter(YEAR);
        String num = request.getParameter(NUM);
        String genreId = request.getParameter(GENRE);

        try {
            Integer bookId = Integer.parseInt(request.getParameter(ID));

            //CustomDao dao = new CustomDao();
            BookService service = BookService.getInstance();

            if (name != null && !name.equals("")) {
                service.changeName(bookId, name);
            }
            if (author != null && !author.equals("")) {
                service.changeAuthor(bookId, author);
            }
            if (year != null && !year.equals("")) {
                service.changeYear(bookId, Integer.parseInt(year));
            }
            if (num != null && !num.equals("")) {
                service.changeNum(bookId, Integer.parseInt(num));
            }
            if (genreId != null) {
                service.changeGenre(bookId, Integer.parseInt(genreId));
            }

            Book book = service.readBook(bookId);

            int numInLibNow = book.getNum() - service.getAllOpenBookings(bookId).size();

            request.setAttribute(BOOK, book);
            request.setAttribute(NUM, numInLibNow);
            request.setAttribute(GENRE_LIST, service.getAllGenres());

            //return "/edit_book.jsp";
            return Page.EDIT_BOOK_PAGE;

        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.EDIT_BOOK;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Book;
import by.training.library.entity.Genre;

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
        String num = request.getParameter(NUM);
        String genreId = request.getParameter(GENRE);

        if (name == null || author == null || year == null || num == null || genreId == null) {
            try {
                CustomDao dao = new CustomDao();
                request.setAttribute(GENRE_LIST, dao.getAllGenres());
                //return "/add_book.jsp";
                return Page.ADD_BOOK_PAGE;

            } catch (DaoException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }

        if (name.equals("") || author.equals("") || year.equals("")) {
            request.setAttribute(MESSAGE, "empty fields");
            //return "/add_book.jsp";
            return Page.ADD_BOOK_PAGE;
        }

        try {
            CustomDao dao = new CustomDao();
            Book book = new Book();

            book.setName(name);
            book.setAuthor(author);
            book.setYear(Integer.parseInt(year));
            int numInLib = Integer.parseInt(num);
            book.setNumInLibAll(numInLib);
            book.setNumInLibNow(numInLib);

            Genre genre = dao.getGenreById(Integer.parseInt(genreId));
            if (genre == null) throw new CommandException("genre is null");
            book.setGenre(genre);

            int id = dao.createBook(book).getId();

            //return "/book?id=" + id;
            request.setAttribute(BOOK, book);
            return Page.BOOK_PAGE;

        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (Exception e) {
            request.setAttribute(MESSAGE, "illegal arguments");
            return "/add_book.jsp";
        }
    }
}

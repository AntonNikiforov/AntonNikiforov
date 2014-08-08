package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Book;
import by.training.library.entity.Genre;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBook implements Command {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String YEAR = "year";
    public static final String NUM_IN_LIB_ALL = "num_in_lib_all";
    public static final String NUM_IN_LIB_NOW = "num_in_lib_now";
    public static final String GENRE = "genre_id";

    public static final String BOOK = "book";
    public static final String GENRE_LIST = "genre_list";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);

        if (admin == null || !admin) {
            return Command.BOOKS;
        }

        if (request.getParameter(ID) == null) {
            throw new CommandException("missing book id");
        }
        Integer bookId = Integer.parseInt(request.getParameter(ID));

        String name = request.getParameter(NAME);
        String author = request.getParameter(AUTHOR);
        String year = request.getParameter(YEAR);
        String numInLibAll = request.getParameter(NUM_IN_LIB_ALL);
        String numInLibNow = request.getParameter(NUM_IN_LIB_NOW);
        String genreId = request.getParameter(GENRE);

        try {
            CustomDao dao = new CustomDao();

            Book book = dao.readBookById(bookId);

            if (name != null && !name.equals("")) {
                dao.changeBookName(bookId, name);
                book.setName(name);
            }
            if (author != null && !author.equals("")) {
                dao.changeBookAuthor(bookId, author);
                book.setAuthor(author);
            }
            if (year != null && !year.equals("")) {
                Integer yearInt = Integer.parseInt(year);
                dao.changeBookYaer(bookId, yearInt);
                book.setYear(yearInt);
            }
            if (numInLibAll != null && !numInLibAll.equals("")) {
                Integer num = Integer.parseInt(numInLibAll);
                dao.changeBookNumInLibAll(bookId, num);
                book.setNumInLibAll(num);
            }
            if (numInLibNow != null && !numInLibNow.equals("")) {
                Integer num = Integer.parseInt(numInLibNow);
                dao.changeBookNumInLibNow(bookId, num);
                book.setNumInLibNow(num);
            }
            if (genreId != null) {
                Genre genre = dao.getGenreById(Integer.parseInt(genreId));
                if (!book.getGenre().equals(genre)) {
                    dao.changeBookGenre(bookId, genre);
                    book.setGenre(genre);
                }
            }

            request.setAttribute(BOOK, book);
            request.setAttribute(GENRE_LIST, dao.getAllGenres());

            //return "/edit_book.jsp";
            return Page.EDIT_BOOK_PAGE;

        } catch (DaoException e) {
            e.printStackTrace();
            throw new CommandException(e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommandException(e.toString(), e);
        }
    }
}

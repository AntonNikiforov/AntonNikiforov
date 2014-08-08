package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CommentDao;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Book;
import by.training.library.entity.Comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowBook implements Command {

    public static final String BOOK_ID = "id";
    public static final String BOOK = "book";
    public static final String COMMENTS = "comments";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if (SessionScope.getUserId(request) == null) return Page.START_PAGE;

        String bookId = request.getParameter(BOOK_ID);
        if (bookId == null) {
            //return "/books";
            return Command.BOOKS;
        }

        Integer id = Integer.parseInt(bookId);

        try {
            CustomDao dao = new CustomDao();
            Book book = dao.readBookById(id);

            if (book == null) {
                //return "/books";
                return Command.BOOKS;
            }

            CommentDao cDao = new CommentDao();
            List<Comment> list = cDao.getCommentsByBook(book);
            request.setAttribute(COMMENTS, list);

            request.setAttribute(BOOK, book);
            //return "/book.jsp";
            return Page.BOOK_PAGE;
        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBook implements Command {

    public static final String ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            return Page.START_PAGE;
        }

        if (request.getParameter(ID) == null) {
            throw new CommandException("missing user id");
        }
        Integer bookId = Integer.parseInt(request.getParameter(ID));

        try {
            CustomDao dao = new CustomDao();
            dao.deleteBookById(bookId);

            return Command.BOOKS;

        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

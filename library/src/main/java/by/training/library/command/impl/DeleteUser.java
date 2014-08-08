package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser implements Command {

    public static final String ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        if (request.getParameter(ID) == null) {
            throw new CommandException("missing user id");
        }
        Integer userId = Integer.parseInt(request.getParameter(ID));

        if (!currentUserId.equals(userId) && !admin) {
            return Page.USER_PAGE;
        }

        try {
            CustomDao dao = new CustomDao();
            dao.deleteUserById(userId);

            if (!admin) {
                return Command.SIGN_OUT;
            }

            return Command.USERS;
        } catch (DaoException e) {
            throw new CommandException(e.toString(), e);
        }
    }
}

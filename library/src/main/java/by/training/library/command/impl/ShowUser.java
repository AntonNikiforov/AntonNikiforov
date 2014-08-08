package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUser implements Command {

    public static final String USER_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Object currentUserId = request.getSession().getAttribute(SessionScope.USER_ID);
        if (currentUserId == null) {
            return Page.START_PAGE;
        }

        String userId = request.getParameter(USER_ID);

        try {
            CustomDao dao = new CustomDao();
            User user;


            if (userId == null) {
                user = dao.readUserById((Integer) currentUserId);

            } else {
                user = dao.readUserById(Integer.parseInt(userId));
            }
            request.setAttribute("user", user);
            //return "/user.jsp";
            return Page.USER_PAGE;
        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

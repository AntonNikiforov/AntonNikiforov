package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.User;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class Login implements Command {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if (SessionScope.getUserId(request) != null) return Page.START_PAGE;

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (email == null ||  password == null) {
            return Page.SIGN_IN_PAGE;

        }
        if (email.equals("") || password.equals("")) {
            request.setAttribute(MESSAGE, "empty fields");
            return Page.SIGN_IN_PAGE;
        }

        try {
            //UserDao dao = new MySqlDaoFactory().getDao(UserDao.class);
            CustomDao dao = new CustomDao();

            User user = dao.login(email, Security.getHashCode(password));

            if (user == null) {
                ResourceBundle bundle = ResourceBundle.getBundle("message");
                request.setAttribute(MESSAGE, bundle.getString("check_email_password"));
                return Page.SIGN_IN_PAGE;

            // success
            } else {
                request.getSession().setAttribute(SessionScope.USER_ID, user.getId());
                request.getSession().setAttribute(SessionScope.LOCALE, user.getLang().getName());
                request.getSession().setAttribute(SessionScope.ADMIN, user.isAdmin());

                return Command.USER;
            }
        } catch (DaoException e) {
            throw new CommandException(e.toString(), e);
        }
    }
}

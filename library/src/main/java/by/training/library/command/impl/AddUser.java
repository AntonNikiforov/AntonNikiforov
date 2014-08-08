package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Lang;
import by.training.library.entity.Role;
import by.training.library.entity.User;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser implements Command {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);

        if (admin != null && !admin) {
            return Page.START_PAGE;
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);

        if (email == null || password == null ||
                name == null || surname == null) {

            return Page.SIGN_UP_PAGE;

        } else if (email.equals("") || password.equals("") ||
                name.equals("") || surname.equals("")) {

            request.setAttribute(MESSAGE, "empty fields");
            return Page.SIGN_UP_PAGE;

        } else {

            try {
                CustomDao dao = new CustomDao();
                Role role = dao.getRoleByName(Role.CLIENT);

                String locale = (String) request.getSession().getAttribute(SessionScope.LOCALE);
                if (locale == null) locale = Lang.RU;
                Lang lang = dao.getLangByName(locale);

                User user = new User();
                user.setEmail(email);
                user.setPassword(Security.getHashCode(password));
                user.setName(name);
                user.setSurname(surname);
                user.setRole(role);
                user.setLang(lang);

                int id = dao.createUser(user).getId();

                if (admin == null) {
                    request.getSession().setAttribute(SessionScope.USER_ID, user.getId());
                    request.getSession().setAttribute(SessionScope.ADMIN, user.isAdmin());
                }

                //return Command.USER + "?id=" + id;
                request.setAttribute("user", user);
                return Page.USER_PAGE;
            } catch (DaoException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }
    }
}

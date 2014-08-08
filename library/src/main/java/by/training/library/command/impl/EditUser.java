package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.SessionScope;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Lang;
import by.training.library.entity.Role;
import by.training.library.entity.User;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser implements Command {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String ROLE = "role_id";
    public static final String LANG = "lang_id";

    public static final String USER = "user";
    public static final String LANG_LIST = "lang_list";
    public static final String ROLE_LIST = "role_list";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        if (request.getParameter(ID) == null) {
            throw new CommandException("missing user id");
        }
        Integer userId = Integer.parseInt(request.getParameter(ID));

        if (!currentUserId.equals(userId) && !admin) {
            userId = currentUserId;
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String langId = request.getParameter(LANG);
        String roleId = request.getParameter(ROLE);

        try {
            CustomDao dao = new CustomDao();

            User user = dao.readUserById(userId);

            if (email != null && !email.equals("")) {
                dao.changeUserEmail(userId, email);
                user.setEmail(email);
            }
            if (password != null && !password.equals("")) {
                dao.changeUserPassword(userId, Security.getHashCode(password));
                user.setPassword(password);
            }
            if (name != null && !name.equals("")) {
                dao.changeUserName(userId, name);
                user.setName(name);
            }
            if (surname != null && !surname.equals("")) {
                dao.changeUserSurname(userId, surname);
                user.setSurname(surname);
            }
            if (roleId != null && admin) {
                Role role = dao.getRoleById(Integer.parseInt(roleId));
                if (!user.getRole().equals(role)) {
                    dao.changeUserRole(userId, role);
                    user.setRole(role);

                    if (currentUserId.equals(userId)) {
                        user.setRole(role);
                        SessionScope.setAdmin(request, user.isAdmin());
                    }
                }
            }
            if (langId != null) {
                Lang lang = dao.getLangById(Integer.parseInt(langId));
                if (!user.getLang().equals(lang)) {
                    dao.changeUserLang(userId, lang);
                    user.setLang(lang);

                    if (currentUserId.equals(userId)) {
                        SessionScope.setLocale(request, lang.getName());
                    }
                }
            }

            // hide secret info
            user.setEmail(Security.hideEmail(user.getEmail()));
            user.setPassword(Security.hidePassword());

            request.setAttribute(USER, user);
            request.setAttribute(LANG_LIST, dao.getAllLangs());
            request.setAttribute(ROLE_LIST, dao.getAllRoles());

            return "/WEB-INF/jsp/settings.jsp";

        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        } catch (Exception e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}

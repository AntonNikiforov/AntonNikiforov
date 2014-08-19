package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Lang;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.UserService;

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
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        if (currentUserId == null) {
            return Page.START_PAGE;
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String langId = request.getParameter(LANG);
        String roleId = request.getParameter(ROLE);

        Integer userId;
        try {
            userId = Integer.parseInt(request.getParameter(ID));
        } catch (NumberFormatException e) {
            userId = currentUserId;
        }

        if (!currentUserId.equals(userId) && !admin) {
            userId = currentUserId;
        }

        try {
            //CustomDao dao = new CustomDao();
            //UserDao dao = DaoFactory.getInstance().getUserDao();
            UserService service = UserService.getInstance();

            //User user = service.readUser(userId);//dao.readUserById(userId);

            if (email != null && !email.equals("")) {
                //dao.changeUserEmail(userId, email);
                //user.setEmail(email);
                service.changeEmail(userId, email);
            }
            if (password != null && !password.equals("")) {
                service.changePassword(userId, password);
            }
            if (name != null && !name.equals("")) {
                service.changeName(userId, name);
            }
            if (surname != null && !surname.equals("")) {
                service.changeSurname(userId, surname);
            }
            if (roleId != null && admin) {
                service.changeRole(userId, Integer.parseInt(roleId));
            }
            if (langId != null) {
                service.changeLanguage(userId, Integer.parseInt(langId));

                if (currentUserId.equals(userId)) {
                    Lang lang = service.readLang(Integer.parseInt(langId));
                    SessionScope.setLocale(request, lang.getName());
                }
            }

        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, e.getMessage());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        try {
            UserService service = UserService.getInstance();

            request.setAttribute(USER, service.readUser(userId));
            request.setAttribute(LANG_LIST, service.getAllLangs());
            request.setAttribute(ROLE_LIST, service.getAllRoles());

            return "/WEB-INF/jsp/settings.jsp";
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

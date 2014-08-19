package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.User;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.exception.UserAlreadyExistsException;
import by.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser implements Command {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";

    public static final String MESSAGE = "msg";
    public static final String USER = "user";

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

        if (email != null && password != null && name != null && surname != null) {
            if ("".equals(email) || "".equals(password) || "".equals(name) || "".equals(surname)) {
                request.setAttribute(MESSAGE, "empty fields");
            }

            try {
                UserService service = UserService.getInstance();
                User user = service.createUser(email, password, name, surname);

                if (admin == null) {
                    request.getSession().setAttribute(SessionScope.USER_ID, user.getId());
                    request.getSession().setAttribute(SessionScope.LOCALE, user.getLang().getName());
                    request.getSession().setAttribute(SessionScope.ADMIN, user.isAdmin());
                }

                request.setAttribute(USER, user);
                return Page.USER_PAGE;

            } catch (UserAlreadyExistsException e) {
                request.setAttribute(MESSAGE, e.getMessage());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return Page.SIGN_UP_PAGE;
    }
}

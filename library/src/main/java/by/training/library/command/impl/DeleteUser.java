package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.service.exception.DeleteException;
import by.training.library.service.exception.NoSuchUserException;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser implements Command {

    public static final String USER_ID = "id";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        // anon
        if (currentUserId == null) {
            return Page.START_PAGE;
        }

        try {
            Integer userId = Integer.valueOf(request.getParameter(USER_ID));

            if (!currentUserId.equals(userId) && !admin) {
                return Page.USER_PAGE;
            }
            //CustomDao dao = new CustomDao();
            UserService service = UserService.getInstance();
            service.deleteUser(userId);

            if (!admin) {
                return Command.SIGN_OUT;
            }

            return Command.USERS;
        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.USERS;
        } catch (NoSuchUserException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.USERS;
        } catch (DeleteException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.USER;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

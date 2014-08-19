package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.User;
import by.training.library.service.exception.NoSuchUserException;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUser implements Command {

    public static final String USER_ID = "id";
    public static final String USER = "user";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        if (currentUserId == null) {
            return Page.START_PAGE;
        }

        String userId = request.getParameter(USER_ID);

        try {
           // CustomDao dao = new CustomDao();
            //UserDao dao = DaoFactory.getInstance().getUserDao();
            UserService service = UserService.getInstance();
            User user;


            if (userId == null) {
                user = service.readUser(currentUserId);

            } else {
                user = service.readUser(Integer.parseInt(userId));
            }
            request.setAttribute(USER, user);
            //return "/user.jsp";
            return Page.USER_PAGE;
        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKINGS;
        } catch (NoSuchUserException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKINGS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

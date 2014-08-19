package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Booking;
import by.training.library.service.BookingService;
import by.training.library.service.exception.NoMoreBooksException;
import by.training.library.service.exception.NoSuchBookException;
import by.training.library.service.exception.NoSuchUserException;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddBooking implements Command {

    public static final String USER_ID = "user_id";
    public static final String BOOK_ID = "book_id";
    public static final String TYPE_ID = "type_id";

    public static final String TYPE_LIST = "type_list";
    public static final String MESSAGE = "msg";
    public static final String BOOKING = "booking";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            return Page.START_PAGE;
        }

        String userParameter = request.getParameter(USER_ID);
        String bookParameter = request.getParameter(BOOK_ID);
        String typeParameter = request.getParameter(TYPE_ID);

        if (userParameter != null && bookParameter != null && typeParameter != null) {

            try {
                Integer userId = Integer.parseInt(userParameter);
                Integer bookId = Integer.parseInt(bookParameter);
                Integer typeID = Integer.parseInt(typeParameter);

                BookingService service = BookingService.getInstance();

                Booking booking = service.createBooking(userId, bookId, typeID);

                request.setAttribute(BOOKING, booking);
                return Page.BOOKING_PAGE;

            } catch (NoSuchUserException e) {
                request.setAttribute(MESSAGE, e.getMessage());
            } catch (NoSuchBookException e) {
                request.setAttribute(MESSAGE, e.getMessage());
            } catch (NoMoreBooksException e) {
                request.setAttribute(MESSAGE, e.getMessage());
            } catch (ServiceException e) {
                throw new CommandException(e);
            } catch (IllegalArgumentException e) {
                request.setAttribute(MESSAGE, "wrong request");
            }
        }

        try {
            BookingService service = BookingService.getInstance();
            request.setAttribute(TYPE_LIST, service.getAllTypes());

            request.setAttribute(USER_ID, userParameter);
            request.setAttribute(BOOK_ID, bookParameter);

            return Page.ADD_BOOKING_PAGE;
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (NumberFormatException e) {
            throw new CommandException(e);
        }
    }
}

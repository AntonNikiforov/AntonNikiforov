package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Booking;
import by.training.library.service.BookingService;
import by.training.library.service.exception.NoSuchBookingException;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBooking implements Command {

    public static final String BOOKING_ID = "id";
    public static final String BOOKING = "booking";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        try {
            Integer bookingId = Integer.parseInt(request.getParameter(BOOKING_ID));

            //BookingDao dao = new BookingDao();
            BookingService service = BookingService.getInstance();
            Booking booking = service.readBooking(bookingId);

            if (booking == null || (currentUserId != booking.getUser().getId() && !admin)) {
                //return "/bookings?user_id=" + booking.getUser().getId();
                return Command.BOOKINGS;
            }

            request.setAttribute(BOOKING, booking);

            return Page.BOOKING_PAGE;

        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.BOOKINGS;
        } catch (NoSuchBookingException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKINGS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

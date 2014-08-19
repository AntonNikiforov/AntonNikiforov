package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.SessionScope;
import by.training.library.service.BookingService;
import by.training.library.service.exception.NoSuchBookingException;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBooking implements Command {

    public static final String BOOKING_ID = "id";
    public static final String MESSAGE = "msg";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            //return "/bookings";
            return Command.BOOKINGS;
        }

        try {
            Integer bookingId = Integer.valueOf(request.getParameter(BOOKING_ID));

            BookingService service = BookingService.getInstance();
            service.deleteBooking(bookingId);

            return Command.BOOKINGS;
        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, "wrong request");
            return Command.BOOKING;
        } catch (NoSuchBookingException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.BOOKING;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

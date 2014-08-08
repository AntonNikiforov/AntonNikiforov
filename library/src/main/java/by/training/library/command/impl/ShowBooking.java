package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.BookingDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Booking;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBooking implements Command {

    public static final String BOOKING_ID = "id";
    public static final String BOOKING = "booking";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Integer currentUserId = SessionScope.getUserId(request);
        Boolean admin = SessionScope.isAdmin(request);

        if (request.getParameter(BOOKING_ID) == null) {
            throw new CommandException("missing user id");
        }
        Integer bookingId = Integer.parseInt(request.getParameter(BOOKING_ID));

        try {
            BookingDao dao = new BookingDao();
            Booking booking = dao.readBookingById(bookingId);

            if (currentUserId != booking.getUser().getId() && !admin) {
                //return "/bookings?user_id=" + booking.getUser().getId();
                return Command.BOOKINGS;
            }

            booking.getUser().setEmail(Security.hideEmail(booking.getUser().getEmail()));
            booking.getUser().setPassword(Security.hidePassword());
            request.setAttribute(BOOKING, booking);

            //System.out.println(booking);

            //return "/booking.jsp";
            return Page.BOOKING_PAGE;

        } catch (DaoException e) {
            throw new CommandException("ShowBooking: " + e.getMessage());
        }
    }
}

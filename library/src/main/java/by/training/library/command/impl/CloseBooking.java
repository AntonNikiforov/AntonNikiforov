package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.BookingDao;
import by.training.library.dao.DaoException;
import by.training.library.util.DateHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CloseBooking implements Command {

    public static final String BOOKING_ID = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        Boolean admin = SessionScope.isAdmin(request);
        if (admin == null || !admin) {
            //return "/bookings";
            return Command.BOOKINGS;
        }

        if (request.getParameter(BOOKING_ID) == null) {
            throw new CommandException("missing booking id");
        }
        Integer bookingId = Integer.parseInt(request.getParameter(BOOKING_ID));

        try {
            BookingDao dao = new BookingDao();
            dao.changeDateOfReturn(bookingId, DateHelper.getCurrentDate());
            dao.changeReturn(bookingId, true);

            return "/booking?id=" + bookingId;
        } catch (DaoException e) {
            throw new CommandException("CloseBooking: " + e.getMessage());
        }
    }
}

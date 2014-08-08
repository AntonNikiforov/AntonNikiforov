package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.BookingDao;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Book;
import by.training.library.entity.Booking;
import by.training.library.entity.User;
import by.training.library.util.DateHelper;
import by.training.library.util.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

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

        Spring page;

        if (userParameter != null && bookParameter != null && typeParameter != null) {
            Integer userId = Integer.parseInt(userParameter);
            Integer bookId = Integer.parseInt(bookParameter);
            Integer typeID = Integer.parseInt(typeParameter);

            try {
                Booking booking = new Booking();

                CustomDao cDao = new CustomDao();
                BookingDao dao = new BookingDao();

                User user = cDao.readUserById(userId);
                Book book = cDao.readBookById(bookId);

                if (user == null || book == null) {
                    if (user == null) request.setAttribute(MESSAGE, "no such user");
                    else request.setAttribute(MESSAGE, "no such book");

                } else {
                    booking.setUser(user);
                    booking.setBook(book);
                    booking.setDateOfIssue(DateHelper.getCurrentDate());
                    //booking.setDateOfReturn(null);
                    //booking.setReturned(false);
                    booking.setType(dao.getBookingTypeById(typeID));

                    int id = dao.createBooking(booking).getId();

                    //return "/booking?id=" + id;
                    booking.getUser().setEmail(Security.hideEmail(booking.getUser().getEmail()));
                    booking.getUser().setPassword(Security.hidePassword());
                    request.setAttribute(BOOKING, booking);
                    return Page.BOOKING_PAGE;
                }

            } catch (DaoException e) {
                throw new CommandException(getClass() + ": " + e.getMessage(), e);
            }
        }
        request.setAttribute(USER_ID, userParameter);
        request.setAttribute(BOOK_ID, bookParameter);
        try {
            BookingDao dao = new BookingDao();
            request.setAttribute(TYPE_LIST, dao.getAllBookingTypes());

            //return "/add_booking.jsp";
            return Page.ADD_BOOKING_PAGE;
        } catch (DaoException e) {
            throw new CommandException("AddBooking: " + e.getMessage(), e);
        }
    }
}

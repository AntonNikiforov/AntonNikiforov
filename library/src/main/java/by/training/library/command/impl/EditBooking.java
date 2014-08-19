package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.entity.Booking;
import by.training.library.service.BookingService;
import by.training.library.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class EditBooking implements Command {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String BOOK_ID = "book_id";
    public static final String TYPE_ID = "type_id";
    public static final String DATE_OF_ISSUE = "date_of_issue";
    public static final String DATE_OF_RETURN = "date_of_return";
    public static final String RETURNED = "returned";

    public static final String TYPE_LIST = "type_list";
    public static final String MESSAGE = "msg";
    public static final String BOOKING = "booking";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Boolean admin = SessionScope.isAdmin(request);

        if (admin == null || !admin) {
            return Command.BOOKS;
        }

        String userId = request.getParameter(USER_ID);
        String bookId = request.getParameter(BOOK_ID);
        String typeId = request.getParameter(TYPE_ID);
        String dateOfIssue = request.getParameter(DATE_OF_ISSUE);
        String dateOfReturn = request.getParameter(DATE_OF_RETURN);

        try {
            Integer bookingId = Integer.parseInt(request.getParameter(ID));

            BookingService service = BookingService.getInstance();

            if (userId != null && !userId.equals("")) {
                service.changeUser(bookingId, Integer.parseInt(userId));
            }
            if (bookId != null && !bookId.equals("")) {
                service.changeBook(bookingId, Integer.parseInt(bookId));
            }
            if (typeId != null && !typeId.equals("")) {
                service.changeType(bookingId, Integer.parseInt(typeId));
            }
            if (dateOfIssue != null && !dateOfIssue.equals("")) {
                service.changeDateOfIssue(bookingId, Date.valueOf(dateOfIssue));
            }
            if (dateOfReturn != null && !dateOfReturn.equals("")) {
                service.changeDateOfReturn(bookingId, Date.valueOf(dateOfReturn));
            }

            Booking booking = service.readBooking(bookingId);

            request.setAttribute(BOOKING, booking);
            request.setAttribute(TYPE_LIST, service.getAllTypes());

            //return "/edit_book.jsp";
            return Page.EDIT_BOOKING_PAGE;

        } catch (IllegalArgumentException e) {
            request.setAttribute(MESSAGE, e.getMessage());
            return Command.EDIT_BOOKING;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

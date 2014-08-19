package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.service.BookService;
import by.training.library.service.BookingService;
import by.training.library.service.exception.ServiceException;
import by.training.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Search implements Command {

    public static final String QUERY = "q";
    public static final String TYPE = "type";
    public static final String PAGE = "page";

    public static final String RESULT_LIST = "list";
    public static final String NUM = "num";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if (SessionScope.getUserId(request) == null) return Page.START_PAGE;

        String query = request.getParameter(QUERY);
        String type = request.getParameter(TYPE);
        String page = request.getParameter(PAGE);

        int numOfPart = 1;
        int numOfBooks = 10;

        if (page != null) numOfPart = Integer.parseInt(page);
        if (numOfPart <= 0) numOfPart = 1;

        if (type == null) type = "book";
        if (query == null) query = "";

        List resultList = null;
        int num = 0;

        try {
            //CustomDao dao = new CustomDao();

            if (type.equals("book")) {
                BookService service = BookService.getInstance();
                resultList = service.searchByName(query, numOfPart, numOfBooks);
                num = service.searchByName(query).size();
            }

            if (type.equals("user")) {
                UserService service = UserService.getInstance();
                resultList = service.getAllUsers(numOfPart, numOfBooks);
                num = service.getNumOfUsers();
            }

            if (type.equals("booking")) {
                BookingService service = BookingService.getInstance();
                resultList = service.getAllBookings(numOfPart, numOfBooks);
                num = service.getNumOfBooking();

                //for (Booking booking : resultList) System.out.println(booking);
            }

            request.setAttribute(RESULT_LIST, resultList);
            request.setAttribute(NUM, num);

            request.setAttribute(PAGE, numOfPart);
            request.setAttribute(TYPE, type);
            request.setAttribute(QUERY, query);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return Page.SEARCH_PAGE;
    }


}

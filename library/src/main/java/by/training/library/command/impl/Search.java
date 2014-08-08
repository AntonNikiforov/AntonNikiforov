package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;
import by.training.library.dao.BookingDao;
import by.training.library.dao.CustomDao;
import by.training.library.dao.DaoException;
import by.training.library.entity.Booking;

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
            CustomDao dao = new CustomDao();

            if (type.equals("book")) {
                resultList = dao.searchBookByName(query, numOfPart, numOfBooks);
                num = dao.getNumOfBooks(query);
            }

            if (type.equals("user")) {
                resultList = dao.getPartOfUsers(numOfPart, numOfBooks);
                num = dao.getNumOfUsers();
            }

            if (type.equals("booking")) {
                BookingDao bDao = new BookingDao();
                resultList = bDao.getPartOfBookings(numOfPart, numOfBooks);
                num = bDao.getNumOfBookings();

                System.out.println(num);
                System.out.println(resultList.isEmpty());
                //for (Booking booking : resultList) System.out.println(booking);
            }

            request.setAttribute(RESULT_LIST, resultList);
            request.setAttribute(NUM, num);

            request.setAttribute(PAGE, numOfPart);
            request.setAttribute(TYPE, type);
            request.setAttribute(QUERY, query);

            System.out.println("/search.jsp?q=" + query + "$type=" + type + "&page=" + numOfPart);

        } catch (DaoException e) {
            throw new CommandException(e.getMessage(), e);
        }

        return Page.SEARCH_PAGE;
    }


}

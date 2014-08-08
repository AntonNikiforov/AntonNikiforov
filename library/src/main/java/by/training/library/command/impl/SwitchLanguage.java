package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SwitchLanguage implements Command {

    public static final String LOCALE = "locale";
    public static final String PAGE = "page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String locale = request.getParameter(LOCALE);
        //String page = request.getParameter(PAGE);
        String page = (String) request.getSession().getAttribute(SessionScope.PAGE);
        if (page == null) {
            page = "/settings";
        }
        if (locale == null) {
            return Page.PREFIX + page;
        }

        request.getSession().setAttribute(SessionScope.LOCALE, locale);
        return  page;
    }
}

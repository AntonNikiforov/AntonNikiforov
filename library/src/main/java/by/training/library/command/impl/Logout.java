package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;
import by.training.library.controller.Page;
import by.training.library.controller.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        request.getSession().removeAttribute(SessionScope.ADMIN);
        request.getSession().removeAttribute(SessionScope.USER_ID);
        return Page.START_PAGE;
    }
}

package by.training.webxml.command.impl;

import by.training.webxml.command.Command;
import by.training.webxml.command.CommandException;
import by.training.webxml.controller.Page;

import javax.servlet.http.HttpServletRequest;

public class NoSuchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return Page.ERROR_PAGE;
    }
}

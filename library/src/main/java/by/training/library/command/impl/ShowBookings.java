package by.training.library.command.impl;

import by.training.library.command.Command;
import by.training.library.command.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBookings implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return "/search?type=booking";
    }
}

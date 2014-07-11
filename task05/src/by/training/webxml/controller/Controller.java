package by.training.webxml.controller;

import by.training.webxml.command.Command;
import by.training.webxml.command.CommandException;
import by.training.webxml.command.CommandHelper;
import by.training.webxml.command.CommandName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Enumeration;

public class Controller extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        perform(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        perform(request, response);
    }

    public void perform(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Command command = CommandHelper.getInstance().getCommand(CommandName.PARSE);
        String page = null;
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            request.setAttribute("error", e.toString());
            page = Page.ERROR_PAGE;
        }

        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            response.setContentType("text/html");
            response.getWriter().print(e.toString());
        }
    }
}

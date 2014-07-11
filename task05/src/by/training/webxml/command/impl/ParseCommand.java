package by.training.webxml.command.impl;

import by.training.webxml.command.Command;
import by.training.webxml.command.CommandException;
import by.training.webxml.controller.Page;
import by.training.webxml.dao.XmlDao;
import by.training.webxml.dao.XmlDaoException;
import by.training.webxml.dao.XmlDaoFactory;
import by.training.webxml.entity.OldCard;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ParseCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        XmlDao dao;

        String parser = request.getParameter("parser");
        String filename = request.getParameter("file");

        try {
            dao = XmlDaoFactory.getInstance().getXmlDao(parser);
            List<OldCard> cards = dao.parse(filename);

            request.setAttribute("cards", cards);

            page = Page.XML_PAGE;
        } catch (XmlDaoException e) {
            throw new CommandException(e.getHiddenException().toString());
        }
        return page;
    }
}

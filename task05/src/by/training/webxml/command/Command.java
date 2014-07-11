package by.training.webxml.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface Command {

    public String execute(HttpServletRequest request) throws CommandException;
}

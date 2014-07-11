package by.training.webxml.command;

import by.training.webxml.exception.ProjectException;

public class CommandException extends ProjectException {

    public CommandException(String msg) {
        super(msg);
    }

    public CommandException(String msg, Exception ex) {
        super(msg, ex);
    }
}

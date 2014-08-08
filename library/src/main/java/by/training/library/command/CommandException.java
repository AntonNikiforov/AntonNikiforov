package by.training.library.command;

import by.training.library.exception.ProjectException;

public class CommandException extends ProjectException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception e) {
        super(message, e);
    }
}

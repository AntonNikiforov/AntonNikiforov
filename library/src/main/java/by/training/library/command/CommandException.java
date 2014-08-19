package by.training.library.command;

import by.training.library.exception.ProjectException;

public class CommandException extends ProjectException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Exception e) {
        super(e);
    }
}

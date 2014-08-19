package by.training.library.service.exception;

import by.training.library.exception.ProjectException;

public class ServiceException extends ProjectException {

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String msg) {
        super(msg);
    }
}

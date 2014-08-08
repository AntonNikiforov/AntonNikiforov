package by.training.library.dao;

import by.training.library.exception.ProjectException;

public class DaoException extends ProjectException {

    public DaoException(String msg, Exception e) {
        super(msg, e);
    }

    public DaoException(String msg) {
        super(msg);
    }
}

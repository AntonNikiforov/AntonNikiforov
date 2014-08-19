package by.training.library.dao;

import by.training.library.exception.ProjectException;

public class DaoException extends ProjectException {

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String msg) {
        super(msg);
    }
}

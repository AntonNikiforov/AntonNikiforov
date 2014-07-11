package by.training.webxml.dao;

import by.training.webxml.exception.ProjectException;

public class XmlDaoException extends ProjectException {

    public XmlDaoException(String msg) {
        super(msg);
    }

    public XmlDaoException(String msg, Exception ex) {
        super(msg, ex);
    }
}

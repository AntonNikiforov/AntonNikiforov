package by.training.library.dao.pool;

import by.training.library.exception.ProjectException;

public class ConnectionPoolException extends ProjectException {

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }
}
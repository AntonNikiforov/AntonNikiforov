package by.training.webxml.exception;

public class ProjectException extends Exception {

    private Exception hiddenException;

    public ProjectException(String msg) {
        super(msg);
    }

    public ProjectException(String msg, Exception ex) {
        super(msg);
        hiddenException = ex;
    }

    public Exception getHiddenException() {
        return hiddenException;
    }
}

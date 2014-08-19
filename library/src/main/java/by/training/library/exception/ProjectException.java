package by.training.library.exception;

public class ProjectException extends Exception {

    private Exception hiddenException;

    public ProjectException(Exception e) {
        hiddenException = e;
    }

    public ProjectException(String msg) {
        super(msg);
    }

    public Exception getHiddenException() {
        return hiddenException;
    }
}

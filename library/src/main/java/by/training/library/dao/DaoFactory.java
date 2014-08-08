package by.training.library.dao;

public class DaoFactory {

    private static final DaoFactory instance = new DaoFactory();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance;
    }

}

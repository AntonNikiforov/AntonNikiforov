package by.training.library.dao;

public interface Dao<Entity> {

    int create(Entity entity) throws DaoException;
    Entity read(int id) throws DaoException;
    void update(Entity entity) throws DaoException;
    void delete(int id) throws DaoException;
}

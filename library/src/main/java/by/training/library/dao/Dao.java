package by.training.library.dao;

import java.util.List;

public interface Dao<Entity> {

    Entity create(Entity entity) throws DaoException;
    Entity read(int id) throws DaoException;
    void update(Entity entity) throws DaoException;
    void delete(int id) throws DaoException;
    List<Entity> getAll() throws DaoException;
}

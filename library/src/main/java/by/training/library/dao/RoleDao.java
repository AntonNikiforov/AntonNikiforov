package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RoleDao implements Dao<Role> {

    public static final String TABLE_NAME = "role";
    private static final String BY_ID = " where id = ?";
    public static final String GET_LAST_ID_QUERY = "select max(id) from " + TABLE_NAME;
    public static final String INSERT_QUERY = "insert into " +
            TABLE_NAME + " values (?, ?)";
    public static final String UPDATE_QUERY = "update " + TABLE_NAME + " set " +
            "name = ?" +
            BY_ID;
    public static final String DELETE_QUERY = "delete from " + TABLE_NAME + BY_ID;
    public static final String SELECT_QUERY = "select * from " + TABLE_NAME;


    private final static RoleDao instance = new RoleDao();

    private RoleDao() {}

    public static RoleDao getInstance() {
        return instance;
    }

    private ConnectionPool pool = ConnectionPool.getInstance();

    public int getLastId() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;

            connection = pool.getConnection();
            statement = connection.prepareStatement(GET_LAST_ID_QUERY);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public Role create(Role role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            int id = getLastId() + 1;
            role.setId(id);
            statement.setInt(1, id);
            statement.setString(2, role.getName());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return role;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public Role read(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY + BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));

                return role;
            }

            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public void update(Role role) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);


            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());

            int res = statement.executeUpdate();
            if (res != 1) throw new DaoException("smth wrong");
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            int res = statement.executeUpdate();
            if (res != 1) throw new DaoException("smth wrong");
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public List<Role> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY);

            resultSet = statement.executeQuery();

            List<Role> list = new LinkedList<Role>();

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));

                list.add(role);
            }

            return list;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private void close(ResultSet resultSet, PreparedStatement statement, Connection connection) throws DaoException {
        if (resultSet != null) try {
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        if (statement != null) try {
            statement.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        try {
            ConnectionPool.getInstance().returnConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }
}

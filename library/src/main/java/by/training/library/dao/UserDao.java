package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.Lang;
import by.training.library.entity.Role;
import by.training.library.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDao implements Dao<User> {

    public static final String TABLE_NAME = "user";
    private static final String BY_ID = " where id = ?";
    public static final String GET_LAST_ID_QUERY = "select max(id) from " + TABLE_NAME;
    public static final String INSERT_QUERY = "insert into " +
            TABLE_NAME + " values (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_QUERY = "update " + TABLE_NAME + " set " +
            "email = ?, password = ?, name = ?, surname = ?, role_id = ?, lang_id = ? " +
            BY_ID;
    public static final String DELETE_QUERY = "delete from " + TABLE_NAME + BY_ID;
    public static final String SELECT_QUERY = "select * from " + TABLE_NAME;


    private final static UserDao instance = new UserDao();

    private UserDao() {}

    public static UserDao getInstance() {
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
    public User create(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            int id = getLastId() + 1;
            user.setId(id);
            statement.setInt(1, id);
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setInt(6, user.getRole().getId());
            statement.setInt(7, user.getLang().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public User read(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY + BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setSurname(resultSet.getString(5));

                Dao<Role> roleDao = RoleDao.getInstance();
                Role role = roleDao.read(resultSet.getInt(6));
                user.setRole(role);

                Dao<Lang> langDao = LangDao.getInstance();
                Lang lang = langDao.read(resultSet.getInt(7));
                user.setLang(lang);

                return user;
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
    public void update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);


            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setInt(5, user.getRole().getId());
            statement.setInt(6, user.getLang().getId());
            statement.setInt(7, user.getId());

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
    public List<User> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY);

            resultSet = statement.executeQuery();

            List<User> list = new LinkedList<User>();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setSurname(resultSet.getString(5));

                Dao<Role> roleDao = RoleDao.getInstance();
                Role role = roleDao.read(resultSet.getInt(6));
                user.setRole(role);

                Dao<Lang> langDao = LangDao.getInstance();
                Lang lang = langDao.read(resultSet.getInt(7));
                user.setLang(lang);

                list.add(user);
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

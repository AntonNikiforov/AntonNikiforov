package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BookingDao implements Dao<Booking> {

    public static final String TABLE_NAME = "booking";
    private static final String BY_ID = " where id = ?";
    public static final String GET_LAST_ID_QUERY = "select max(id) from " + TABLE_NAME;
    public static final String INSERT_QUERY = "insert into " +
            TABLE_NAME + " values (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_QUERY = "update " + TABLE_NAME + " set " +
            "user_id = ?, book_id = ?, dateOfIssue = ?, dateOfReturn = ?, type_id = ? " +
            BY_ID;
    public static final String DELETE_QUERY = "delete from " + TABLE_NAME + BY_ID;
    public static final String SELECT_QUERY = "select * from " + TABLE_NAME;


    private final static BookingDao instance = new BookingDao();

    private BookingDao() {}

    public static BookingDao getInstance() {
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
    public Booking create(Booking booking) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            int id = getLastId() + 1;
            booking.setId(id);
            statement.setInt(1, id);
            statement.setInt(2, booking.getUser().getId());
            statement.setInt(3, booking.getBook().getId());
            statement.setDate(4, booking.getDateOfIssue());
            statement.setDate(5, booking.getDateOfReturn());
            statement.setInt(6, booking.getType().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return booking;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public Booking read(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY + BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt(1));

                Dao<User> userDao = UserDao.getInstance();
                User user = userDao.read(resultSet.getInt(2));
                booking.setUser(user);

                Dao<Book> bookDao = BookDao.getInstance();
                Book book = bookDao.read(resultSet.getInt(3));
                booking.setBook(book);

                booking.setDateOfIssue(resultSet.getDate(4));
                booking.setDateOfReturn(resultSet.getDate(5));

                Dao<BookingType> dao = BookingTypeDao.getInstance();
                BookingType type = dao.read(resultSet.getInt(6));
                booking.setType(type);

                return booking;
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
    public void update(Booking booking) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);


            statement.setInt(1, booking.getUser().getId());
            statement.setInt(2, booking.getBook().getId());
            statement.setDate(3, booking.getDateOfIssue());
            statement.setDate(4, booking.getDateOfReturn());
            statement.setInt(5, booking.getType().getId());
            statement.setInt(6, booking.getId());

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
    public List<Booking> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY);

            resultSet = statement.executeQuery();

            List<Booking> list = new LinkedList<Booking>();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt(1));

                Dao<User> userDao = UserDao.getInstance();
                User user = userDao.read(resultSet.getInt(2));
                booking.setUser(user);

                Dao<Book> bookDao = BookDao.getInstance();
                Book book = bookDao.read(resultSet.getInt(3));
                booking.setBook(book);

                booking.setDateOfIssue(resultSet.getDate(4));
                booking.setDateOfReturn(resultSet.getDate(5));

                Dao<BookingType> dao = BookingTypeDao.getInstance();
                BookingType type = dao.read(resultSet.getInt(6));
                booking.setType(type);

                list.add(booking);
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

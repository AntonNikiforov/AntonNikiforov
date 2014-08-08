package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.Booking;
import by.training.library.entity.BookingType;
import by.training.library.util.DateHelper;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookingDao {

    public static void main(String[] args) throws ConnectionPoolException, DaoException {
        ConnectionPool.getInstance().open();

        BookingDao dao = new BookingDao();
        CustomDao cDao = new CustomDao();

        Booking booking = new Booking();
        booking.setUser(cDao.readUserById(1));
        booking.setBook(cDao.readBookById(1));
        booking.setDateOfIssue(DateHelper.getCurrentDate());
        booking.setDateOfReturn(Date.valueOf("2014-03-05"));
        booking.setReturned(true);

        Booking booking1 = dao.createBooking(booking);

        ConnectionPool.getInstance().close();
    }

    private ConnectionPool pool = ConnectionPool.getInstance();

    public BookingType getBookingTypeById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking_type where id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            List<BookingType> list = parseRSForBookingTypes(resultSet);

            if (list.size() > 0) return list.get(0);
            return null;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public BookingType getBookingTypeByName(String name) throws DaoException {
        if (name == null) return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking_type where name = ?");
            statement.setString(1, name);

            resultSet = statement.executeQuery();
            List<BookingType> list = parseRSForBookingTypes(resultSet);

            if (list.size() > 0) return list.get(0);
            return null;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<BookingType> getAllBookingTypes() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking_type");

            resultSet = statement.executeQuery();
            List<BookingType> list = parseRSForBookingTypes(resultSet);

            return list;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<BookingType> parseRSForBookingTypes(ResultSet resultSet) throws DaoException {
        List<BookingType> list = new LinkedList<BookingType>();
        try {
            while (resultSet.next()) {
                BookingType type = new BookingType();
                type.setId(resultSet.getInt(1));
                type.setName(resultSet.getString(2));
                list.add(type);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public int getLastBookingId() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;

            connection = pool.getConnection();
            statement = connection.prepareStatement("select max(id) from booking");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            return id;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public Booking createBooking(Booking booking) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("insert into "
                    + "booking" + " values (?, ?, ?, ?, ?, ?, ?)");

            int id = getLastBookingId() + 1;
            booking.setId(id);
            statement.setInt(1, id);
            statement.setInt(2, booking.getUser().getId());
            statement.setInt(3, booking.getBook().getId());
            statement.setDate(4, booking.getDateOfIssue());
            statement.setDate(5, booking.getDateOfReturn());
            statement.setBoolean(6, booking.isReturned());
            statement.setInt(7, booking.getType().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return booking;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public Booking readBookingById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<Booking> list = parseRSForBooking(resultSet);
            if (list.isEmpty()) return null;
            return list.get(0);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public void deleteBookingById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("delete from booking where id = ?");
            statement.setInt(1, id);
            int res = statement.executeUpdate();
            if (res != 1) throw new DaoException("smth wrong");
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private void updateBooking(int id, String columnName, Object value) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("update booking set " + columnName + " = ? where id = ?");

            statement.setObject(1, value);
            statement.setInt(2, id);
            int res = statement.executeUpdate();
            if (res != 1) throw new DaoException("smth wrong");
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public void changeUserId(int id, int userId) throws DaoException {
        updateBooking(id, "user_id", userId);
    }

    public void changeBookId(int id, int bookId) throws DaoException {
        updateBooking(id, "book_id", bookId);
    }

    public void changeDateOfIssue(int id, Date date) throws DaoException {
        updateBooking(id, "dateOfIssue", date);
    }

    public void changeDateOfReturn(int id, Date date) throws DaoException {
        updateBooking(id, "dateOfReturn", date);
    }

    public void changeReturn(int id, boolean returned) throws DaoException {
        updateBooking(id, "returned", returned);
    }

    public void changeBookigType(int id, BookingType bookingType) throws DaoException {
        updateBooking(id, "booking_type", bookingType);
    }

    public List<Booking> getAllBookings() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking");

            resultSet = statement.executeQuery();

            return parseRSForBooking(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public int getNumOfBookings() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select count(*) from booking");

            resultSet = statement.executeQuery();

            int num = 0;
            if (resultSet.next()) {
                num = resultSet.getInt(1);
            }

            return num;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<Booking> getPartOfBookings(int numOfPart, int numOfBooks) throws DaoException {
        if (numOfPart <= 0 || numOfBooks <= 0) {
            throw new DaoException("illegal arguments");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking limit ?, ?");

            statement.setInt(1, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(2, numOfBooks);

            resultSet = statement.executeQuery();

            return parseRSForBooking(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<Booking> getPartOfBookingsByUserId(int userId, int numOfPart, int numOfBooks) throws DaoException {
        if (numOfPart <= 0 || numOfBooks <= 0) {
            throw new DaoException("illegal arguments");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking where user_id = ? limit ?, ?");

            statement.setInt(1, userId);
            statement.setInt(2, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(3, numOfBooks);

            resultSet = statement.executeQuery();

            return parseRSForBooking(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<Booking> getPartOfBookingsByBookId(int userId, int numOfPart, int numOfBooks) throws DaoException {
        if (numOfPart <= 0 || numOfBooks <= 0) {
            throw new DaoException("illegal arguments");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from booking where book_id = ? limit ?, ?");

            statement.setInt(1, userId);
            statement.setInt(2, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(3, numOfBooks);

            resultSet = statement.executeQuery();

            return parseRSForBooking(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Booking> parseRSForBooking(ResultSet resultSet) throws DaoException {
        List<Booking> list = new LinkedList<Booking>();
        try {
            CustomDao dao = new CustomDao();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt(1));
                booking.setUser(dao.readUserById(resultSet.getInt(2)));
                booking.setBook(dao.readBookById(resultSet.getInt(3)));
                booking.setDateOfIssue(resultSet.getDate(4));
                booking.setDateOfReturn(resultSet.getDate(5));
                booking.setReturned(resultSet.getBoolean(6));
                booking.setType(getBookingTypeById(resultSet.getInt(7)));
                list.add(booking);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void close(ResultSet resultSet, PreparedStatement statement, Connection connection) throws DaoException {
        if (resultSet != null) try {
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        if (statement != null) try {
            statement.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        try {
            ConnectionPool.getInstance().returnConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}

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

public class BookDao implements Dao<Book> {

    public static final String TABLE_NAME = "book";
    private static final String BY_ID = " where id = ?";
    public static final String GET_LAST_ID_QUERY = "select max(id) from " + TABLE_NAME;
    public static final String INSERT_QUERY = "insert into " +
            TABLE_NAME + " values (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_QUERY = "update " + TABLE_NAME + " set " +
            "name = ?, author = ?, year = ?, num = ?, genre_id = ? " +
            BY_ID;
    public static final String DELETE_QUERY = "delete from " + TABLE_NAME + BY_ID;
    public static final String SELECT_QUERY = "select * from " + TABLE_NAME;


    private final static BookDao instance = new BookDao();

    private BookDao() {}

    public static BookDao getInstance() {
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
    public Book create(Book book) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(INSERT_QUERY);

            int id = getLastId() + 1;
            book.setId(id);
            statement.setInt(1, id);
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getNum());
            statement.setInt(6, book.getGenre().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return book;
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    @Override
    public Book read(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY + BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setYear(resultSet.getInt(4));
                book.setNum(resultSet.getInt(5));

                Dao<Genre> dao = GenreDao.getInstance();
                Genre genre = dao.read(resultSet.getInt(6));
                book.setGenre(genre);

                return book;
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
    public void update(Book book) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(UPDATE_QUERY);

            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getYear());
            statement.setInt(4, book.getNum());
            statement.setInt(5, book.getGenre().getId());
            statement.setInt(6, book.getId());

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
    public List<Book> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(SELECT_QUERY);

            resultSet = statement.executeQuery();

            List<Book> list = new LinkedList<Book>();

            while (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setYear(resultSet.getInt(4));
                book.setNum(resultSet.getInt(5));

                Dao<Genre> dao = GenreDao.getInstance();
                Genre genre = dao.read(resultSet.getInt(6));
                book.setGenre(genre);

                list.add(book);
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

package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.*;
import by.training.library.util.SearchHelper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
public class CustomDao {

    public static void main(String[] args) throws ConnectionPoolException, DaoException {
        ConnectionPool.getInstance().open();

        CustomDao dao = new CustomDao();
        for (Book book : dao.searchBookByName(" ", 1, 20)) {
            System.out.println(book);
        }

        ConnectionPool.getInstance().close();
    }

    ConnectionPool pool = ConnectionPool.getInstance();

// ROLE
    public Role getRoleById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
       try {
           connection = pool.getConnection();
           statement = connection.prepareStatement("select * from role where id = ?");
           statement.setInt(1, id);

           resultSet = statement.executeQuery();
           List<Role> list = parseRSForRole(resultSet);

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

    public Role getRoleByName(String name) throws DaoException {
        if (name == null) return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from role where name = ?");
            statement.setString(1, name);

            resultSet = statement.executeQuery();
            List<Role> list = parseRSForRole(resultSet);

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

    public List<Role> getAllRoles() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from role");

            resultSet = statement.executeQuery();
            List<Role> list = parseRSForRole(resultSet);

            return list;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Role> parseRSForRole(ResultSet resultSet) throws DaoException {
        List<Role> list = new LinkedList<Role>();
        try {
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(1));
                role.setName(resultSet.getString(2));
                list.add(role);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

// LANG
    public Lang getLangById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from lang where id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            List<Lang> list = parseRSForLang(resultSet);

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

    public Lang getLangByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from lang where name = ?");
            statement.setString(1, name);

            resultSet = statement.executeQuery();
            List<Lang> list = parseRSForLang(resultSet);

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

    public List<Lang> getAllLangs() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from lang");

            resultSet = statement.executeQuery();

            return parseRSForLang(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Lang> parseRSForLang(ResultSet resultSet) throws DaoException {
        List<Lang> list = new LinkedList<Lang>();
        try {
            while (resultSet.next()) {
                Lang lang = new Lang();
                lang.setId(resultSet.getInt(1));
                lang.setName(resultSet.getString(2));
                list.add(lang);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

// USER
    public int getLastUserId() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;

            connection = pool.getConnection();
            statement = connection.prepareStatement("select max(id) from user");
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

    public User createUser(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("insert into "
                    + "user" + " values (?, ?, ?, ?, ?, ?, ?)");

            int id = getLastUserId() + 1;
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
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public User readUserById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from user where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<User> list = parseRSForUser(resultSet);
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

    public void deleteUserById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("delete from user where id = ?");
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

    private void updateUser(int id, String columnName, Object value) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("update user set " + columnName + " = ? where id = ?");

            statement.setObject(1, value);
            statement.setInt(2, id);
            int res = statement.executeUpdate();
            System.out.println(id);
            System.out.println(res);
            if (res != 1) throw new DaoException("smth wrong");
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public void changeUserEmail(int id, String email) throws DaoException {
        updateUser(id, "email", email);
    }

    public void changeUserPassword(int id, String password) throws DaoException {
        updateUser(id, "password", password);
    }

    public void changeUserName(int id, String name) throws DaoException {
        updateUser(id, "name", name);
    }

    public void changeUserSurname(int id, String surname) throws DaoException {
        updateUser(id, "surname", surname);
    }

    public void changeUserRole(int id, Role role) throws DaoException {
        updateUser(id, "role_id", role.getId());
    }

    public void changeUserLang(int id, Lang lang) throws DaoException {
        updateUser(id, "lang_id", lang.getId());
    }

    public List<User> getAllUsers() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from user");

            resultSet = statement.executeQuery();

            return parseRSForUser(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public int getNumOfUsers() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select count(*) from user");

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

    public User login(String email, String password) throws DaoException {
        if (email == null || password == null) return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from user where email = ? and password = ?");

            statement.setString(1, email);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            List<User> list = parseRSForUser(resultSet);

            if (list.isEmpty()) return null;
            return list.get(0);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<User> getPartOfUsers(int numOfPart, int numOfBooks) throws DaoException {
        if (numOfPart <= 0 || numOfBooks <= 0) {
            throw new DaoException("illegal arguments");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from user limit ?, ?");

            statement.setInt(1, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(2, numOfBooks);

            resultSet = statement.executeQuery();

            return parseRSForUser(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<User> parseRSForUser(ResultSet resultSet) throws DaoException {
        List<User> list = new LinkedList<User>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setSurname(resultSet.getString(5));
                user.setRole(getRoleById(resultSet.getInt(6)));
                user.setLang(getLangById(resultSet.getInt(7)));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public Genre getGenreById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from genre where id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();
            List<Genre> list = parseRSForGenre(resultSet);

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

    public Genre getGenreByName(String name) throws DaoException {
        if (name == null) return null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from genre where name = ?");
            statement.setString(1, name);

            resultSet = statement.executeQuery();
            List<Genre> list = parseRSForGenre(resultSet);

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

    public List<Genre> getAllGenres() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from genre");

            resultSet = statement.executeQuery();
            List<Genre> list = parseRSForGenre(resultSet);

            return list;

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Genre> parseRSForGenre(ResultSet resultSet) throws DaoException {
        List<Genre> list = new LinkedList<Genre>();
        try {
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(1));
                genre.setName(resultSet.getString(2));
                list.add(genre);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public int getLastBookId() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;

            connection = pool.getConnection();
            statement = connection.prepareStatement("select max(id) from book");
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

    public Book createBook(Book book) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("insert into "
                    + "book" + " values (?, ?, ?, ?, ?, ?, ?)");

            int id = getLastBookId() + 1;
            book.setId(id);

            statement.setInt(1, id);
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getYear());
            statement.setInt(5, book.getNumInLibAll());
            statement.setInt(6, book.getNumInLibNow());
            statement.setInt(7, book.getGenre().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return book;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public Book readBookById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from book where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<Book> list = parseRSForBook(resultSet);
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

    public void deleteBookById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("delete from book where id = ?");
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

    private void updateBook(int id, String columnName, Object value) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("update book set " + columnName + " = ? where id = ?");

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

    public void changeBookName(int id, String name) throws DaoException {
        updateBook(id, "name", name);
    }

    public void changeBookAuthor(int id, String author) throws DaoException {
        updateBook(id, "author", author);
    }

    public void changeBookYaer(int id, int year) throws DaoException {
        updateBook(id, "year", year);
    }

    public void changeBookNumInLibAll(int id, int num) throws DaoException {
        updateBook(id, "numInLibAll", num);
    }

    public void changeBookNumInLibNow(int id, int num) throws DaoException {
        updateBook(id, "numInLibNow", num);
    }

    public void changeBookGenre(int id, Genre genre) throws DaoException {
        updateBook(id, "genre_id", genre.getId());
    }

    public List<Book> getAllBooks() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from book");

            resultSet = statement.executeQuery();

            return parseRSForBook(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public List<Book> searchBookByName(String name, int numOfPart, int numOfBooks) throws DaoException {
        if (name == null) throw new DaoException("illegal argument");

        String regexp = SearchHelper.getRegex(name);

        String query = "select * from book where lower(name) regexp '" + regexp + "' limit ?, ?";

        System.out.println(query);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(2, numOfBooks);
            resultSet = statement.executeQuery();

            return parseRSForBook(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public int getNumOfBooks(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String regexp = SearchHelper.getRegex(name);
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select count(*) from book where name regexp '" + regexp + "'");

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

    public int getNumOfBooks() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select count(*) from book");

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

    public List<Book> getPartOfBooks(int numOfPart, int numOfBooks) throws DaoException {
        if (numOfPart <= 0 || numOfBooks <= 0) {
            throw new DaoException("illegal arguments");
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from book limit ?, ?");

            statement.setInt(1, numOfBooks * numOfPart - numOfBooks);
            statement.setInt(2, numOfBooks);

            resultSet = statement.executeQuery();

            return parseRSForBook(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Book> parseRSForBook(ResultSet resultSet) throws DaoException {
        List<Book> list = new LinkedList<Book>();
        try {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setYear(resultSet.getInt(4));
                book.setNumInLibAll(resultSet.getInt(5));
                book.setNumInLibNow(resultSet.getInt(6));
                book.setGenre(getGenreById(resultSet.getInt(7)));
                list.add(book);
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

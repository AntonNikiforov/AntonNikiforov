package by.training.library.dao;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;
import by.training.library.entity.Book;
import by.training.library.entity.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CommentDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    public int getLastCommentId() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            int id = 0;

            connection = pool.getConnection();
            statement = connection.prepareStatement("select max(id) from comment");
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

    public Comment createComment(Comment comment) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("insert into "
                    + "comment" + " values (?, ?, ?, ?, ?)");

            int id = getLastCommentId() + 1;
            comment.setId(id);

            statement.setInt(1, id);
            statement.setString(2, comment.getText());
            statement.setDate(3, comment.getDate());
            statement.setInt(4, comment.getUser().getId());
            statement.setInt(5, comment.getBook().getId());

            int res = statement.executeUpdate();

            if (res != 1) throw new DaoException("smth wrorg");

            return comment;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    public Comment readCommentById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from comment where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<Comment> list = parseRSForComment(resultSet);
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

    public void deleteCommentById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("delete from comment where id = ?");
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

    private void updateComment(int id, String columnName, Object value) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("update comment set " + columnName + " = ? where id = ?");

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

    public List<Comment> getAllComments() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from comment");

            resultSet = statement.executeQuery();

            return parseRSForComment(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
        }
    }

    private List<Comment> parseRSForComment(ResultSet resultSet) throws DaoException {
        List<Comment> list = new LinkedList<Comment>();
        try {
            CustomDao dao = new CustomDao();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setText(resultSet.getString(2));
                comment.setDate(resultSet.getDate(3));
                comment.setUser(dao.readUserById(resultSet.getInt(4)));
                comment.setBook(dao.readBookById(resultSet.getInt(5)));
                list.add(comment);
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public List<Comment> getCommentsByBook(Book book) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            statement = connection.prepareStatement("select * from comment where book_id = ?");
            statement.setInt(1, book.getId());

            resultSet = statement.executeQuery();

            return parseRSForComment(resultSet);

        } catch (ConnectionPoolException e) {
            throw new DaoException(e.getMessage(), e);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(resultSet, statement, connection);
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

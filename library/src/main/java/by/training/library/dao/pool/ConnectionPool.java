package by.training.library.dao.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {

    private final static String CONFIG = "jdbc";
    private final static String DRIVER = "db_driver";
    private final static String URL = "db_url";
    private final static String USER = "db_user";
    private final static String PASSWORD = "db_password";
    private final static String NUM = "db_numOfConnections";

    private BlockingQueue<Connection> free;
    private List<Connection> busy;

    private final static ConnectionPool instance = new ConnectionPool();
    private boolean open;

    private ConnectionPool() {
        open = false;
        busy = new LinkedList<Connection>();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }


    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;

        try {
            connection = free.take();
            busy.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.toString(), e);
        }

        return connection;
    }

    public void returnConnection(Connection con) throws ConnectionPoolException {
        if (con == null) throw new ConnectionPoolException("null pointer");

        if (busy.remove(con)) {
            try {
                free.put(con);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException(e.toString(), e);
            }
        }
    }

    public void open() throws ConnectionPoolException {
        if (!open) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(CONFIG);

                String driver = bundle.getString(DRIVER);
                String url = bundle.getString(URL);
                String user = bundle.getString(USER);
                String password = bundle.getString(PASSWORD);
                int num = Integer.parseInt(bundle.getString(NUM));

                free = new LinkedBlockingQueue<Connection>(num);

                Class.forName(driver);

                for (int i = 0; i < num; ++i) {
                    free.put(DriverManager.getConnection(url, user, password));
                }
                open = true;
            } catch (ClassNotFoundException e) {
                throw new ConnectionPoolException(e.toString(), e);
            } catch (SQLException e) {
                throw new ConnectionPoolException(e.toString(), e);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException(e.toString(), e);
            }
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void close() throws ConnectionPoolException {
        if (open) {
            try {
                for (Connection con : busy) {
                    con.close();
                }
                busy = null;
            } catch (SQLException e) {
                throw new ConnectionPoolException(e.toString(), e);
            }
            try {
                for (Connection con : free) {
                    con.close();
                }
                free = null;

            } catch (SQLException e) {
                throw new ConnectionPoolException(e.toString(), e);
            }


            Enumeration<Driver> drivers = DriverManager.getDrivers();

            Driver driver = null;

            // clear drivers
            try {
                while (drivers.hasMoreElements()) {

                    driver = drivers.nextElement();
                    DriverManager.deregisterDriver(driver);

                    open = false;
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException(e.toString(), e);
            }
        }
    }
}

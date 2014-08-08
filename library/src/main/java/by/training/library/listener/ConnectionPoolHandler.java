package by.training.library.listener;

import by.training.library.dao.pool.ConnectionPool;
import by.training.library.dao.pool.ConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolHandler implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().open();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
            servletContextEvent.getServletContext().setAttribute("error", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().close();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}

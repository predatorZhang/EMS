package com.casic.accessControl.core.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * mock dataSource.
 * 
 * @author Lingo
 */
public class MockDataSource implements DataSource {
    /**
     * get connection.
     * 
     * @return Connection
     * @throws java.sql.SQLException
     *             sql exception
     */
    public Connection getConnection() throws SQLException {
        return null;
    }

    /**
     * get connection.
     *
     * @param username
     *            String
     * @param password
     *            String
     * @return Connection
     * @throws java.sql.SQLException
     *             sql exception
     */
    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

    /**
     * get log writer.
     *
     * @return PrintWriter
     * @throws java.sql.SQLException
     *             sql exception
     */
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /**
     * set log writer.
     *
     * @param out
     *            PrintWriter
     * @throws java.sql.SQLException
     *             sql exception
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    /**
     * set login timeout.
     *
     * @param seconds
     *            int
     * @throws java.sql.SQLException
     *             sql exception
     */
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    /**
     * get login timeout.
     *
     * @return login timeout
     * @throws java.sql.SQLException
     *             sql exception
     */
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    // JDK 6
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    // JDK 6
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    // jdk7
    public Logger getParentLogger() {
        return null;
    }
}

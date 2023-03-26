package com.yty.godbatis.datasource;

import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Unpooled data source: without connection pool
 * @since 1.0
 * @version 1.0
 * @author yty
 */
public class UnPooledDataSource implements javax.sql.DataSource{
    private String driver;
    private String url;
    private String username;
    private String password;

    public UnPooledDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(this.url, this.username, this.password);
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}

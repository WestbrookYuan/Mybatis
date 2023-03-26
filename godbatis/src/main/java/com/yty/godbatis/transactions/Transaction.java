package com.yty.godbatis.transactions;

import java.sql.Connection;

/**
 * transaction interface
 * JDBC and MANAGED should follow this
 * @author yty
 * @since 1.0
 * @version 1.0
 */
public interface Transaction {
    void commit();
    void rollback();
    void close();

    void openConnection();

    Connection getConnection();
}

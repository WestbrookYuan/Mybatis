package com.yty.godbatis.transactions.impl;

import com.yty.godbatis.transactions.Transaction;

import java.sql.Connection;

public class ManagedTransaction implements Transaction {
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}

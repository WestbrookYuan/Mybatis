package com.yty.godbatis.core;


import com.yty.godbatis.pojo.MappedStatement;
import com.yty.godbatis.transactions.Transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author yty
 * @since 1.0
 * @version 1.0
 */
public class SqlSessionFactory {
/**
 * transaction manager
 *
 *
 * */
    private Transaction transaction;

/**
 * data source can be moved to Transaction: so removed from sqlSessionFactory class
 *
 * */

/**
 * a map to storage sql statement
 * key is the combination of namespace and id
 * value is the Mapped statement object
 */
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public SqlSessionFactory(){}

    public SqlSessionFactory(Transaction transaction, Map<String, MappedStatement> mappedStatements) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatements;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setMappedStatements(Map<String, MappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }


    /**
     * get session object
     * @return Session
     */
    public SqlSession openSession(){
        transaction.openConnection();
        SqlSession sqlSession = new SqlSession(this);
        return sqlSession;
    }
}

package com.yty.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class SqlSessionUtil {
    private SqlSessionUtil(){};
    private static SqlSessionFactory sessionFactory;
    // create sql session factory
    static {
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static SqlSession openSession(){
//        SqlSession sqlSession = null;
//        try {
//            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
//            sqlSession = sqlSessionFactory.openSession();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return sqlSession;
//    }

    //get session object
    public static SqlSession openSession(){
        return sessionFactory.openSession();
    }
}

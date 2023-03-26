package com.yty.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;


public class MyBatisCompleteTest {
    public static void main(String[] args) {

        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            // do query
            int count = sqlSession.insert("insertCar");
            // if no exceptions, commit transactions
            sqlSession.commit();

            System.out.println("number of car: ");
        } catch (IOException e) {

            if(sqlSession != null){
                sqlSession.rollback();
            }

            e.printStackTrace();
        }
        finally {
            if (sqlSession != null){
                sqlSession.close();
            }

        }

    }
}

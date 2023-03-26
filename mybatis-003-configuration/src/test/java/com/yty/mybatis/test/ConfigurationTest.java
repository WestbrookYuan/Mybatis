package com.yty.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationTest {


    @Test
    public void testEnvironment(){
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory development = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            SqlSessionFactory mybatisdb = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatisdb");

            SqlSession developmentSession = development.openSession();
            SqlSession mybatisdbSession = mybatisdb.openSession();
            developmentSession.insert("insertCar");
            developmentSession.commit();
            developmentSession.close();

            mybatisdbSession.insert("insertCar");
            mybatisdbSession.commit();
            mybatisdbSession.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDataSource(){

        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory development = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            SqlSession sqlSession1 = development.openSession();


            sqlSession1.insert("insertCar");
            sqlSession1.commit();
            sqlSession1.close();

            SqlSession sqlSession2 = development.openSession();
            sqlSession2.insert("insertCar");
            sqlSession2.commit();
            sqlSession2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

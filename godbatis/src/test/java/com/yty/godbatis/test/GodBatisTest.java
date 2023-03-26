package com.yty.godbatis.test;

import com.yty.godbatis.core.SqlSession;
import com.yty.godbatis.core.SqlSessionFactory;
import com.yty.godbatis.core.SqlSessionFactoryBuilder;
import com.yty.godbatis.pojo.User;
import com.yty.godbatis.utils.Resources;
import org.junit.Test;

public class GodBatisTest {
    @Test
    public void testSessionFactory(){
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        System.out.println(sqlSessionFactory);
    }

    @Test
    public void testInsert(){
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User("1", "yyt", "24");
        int count = sqlSession.insert("testEnvironment.insertCar", user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectOne(){
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = (User) sqlSession.selectOne("testEnvironment.selectById", "2");
        sqlSession.close();
        System.out.println(user.toString());
    }

}

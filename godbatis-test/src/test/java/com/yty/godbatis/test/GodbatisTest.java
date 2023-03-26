package com.yty.godbatis.test;

import com.yty.godbatis.core.SqlSession;
import com.yty.godbatis.core.SqlSessionFactory;
import com.yty.godbatis.core.SqlSessionFactoryBuilder;
import com.yty.godbatis.pojo.User;
import com.yty.godbatis.utils.Resources;
import org.junit.Test;

public class GodbatisTest {
    @Test
    public void testInsertUser(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = new User("3", "jushen", "24");
        int insert = sqlSession.insert("userMapper.insertUser", user);
        System.out.println(insert);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testSelectByid(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = (User) sqlSession.selectOne("userMapper.selectById", "3");
        System.out.println(user);
        sqlSession.close();

    }
}

package com.yty.mybatis.test;

import com.mysql.cj.Session;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class TestCarMapper {
    @Test
    public void testInsertCar(){
        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            // do query
            int count = sqlSession.insert("insertCar");
            // if no exceptions, commit transactions
            sqlSession.commit();

            System.out.println("number of car: " + count);
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

    @Test
    public void testInsertCarByUtil(){
        SqlSession session = SqlSessionUtil.openSession();
        int count = session.insert("insertCar");
        System.out.println(count);
        session.commit();
        session.close();
    }
}

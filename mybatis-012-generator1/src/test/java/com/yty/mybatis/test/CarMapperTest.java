package com.yty.mybatis.test;

import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectAll() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }
    @Test
    public void testInsert() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.insert(new Car(null, "1234", "Audi A5 Sportback", BigDecimal.valueOf(35.0), "2022-10-11", "gas"));
        sqlSession.commit();
        sqlSession.close();
    }
}

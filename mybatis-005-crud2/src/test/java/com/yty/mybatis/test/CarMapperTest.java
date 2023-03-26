package com.yty.mybatis.test;

import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsert(){
        Car car = new Car(null, 1, "Audi RS6", 50.5, "2022-10-11", "gas");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        int i = mapper.insertCar(car);
        System.out.println(i);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.deleteById(48L);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testUpdateById(){
        Car car = new Car(47L, 1, "Audi RS6", 50.5, "2022-10-11", "gas");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        mapper.updateCar(car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(1L);
        System.out.println(car.toString());
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectAll();
        cars.forEach(System.out::println);
    }
}

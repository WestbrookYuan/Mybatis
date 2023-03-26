package com.yty.mybatis.test;

import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class CarMapperTest {
    @Test
    public void testSelectCarsByCarType(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByCarType("'ele'");
        cars.forEach(car -> {
            System.out.println(car);
        });

    }

    @Test
    public void testSortCarsByPrice(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> carsSorted = mapper.sortByPrice("asc");
        carsSorted.forEach(car -> {
            System.out.println(car);
        });
    }

    @Test
    public void testSelectByBrand(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByBrand("Audi");
        cars.forEach(car -> {
            System.out.println(car);
        });
    }

    @Test
    public void testInsertCarUseGenerateId(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, 1111, "Nissan GTR", 55.0, "2022-10-11", "gas");
        int count = mapper.insertCarUseGenerateId(car);
        System.out.println(car);
        sqlSession.commit();
        sqlSession.close();
    }
}

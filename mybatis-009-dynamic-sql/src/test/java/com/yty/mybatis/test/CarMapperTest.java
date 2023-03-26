package com.yty.mybatis.test;

import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CarMapperTest {
    @Test
    public void testSelectByMultiCondition(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiCondition("Nissan",
                                                    null,
                                                        "");
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithWhere(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiConditionWithWhere("Audi",
                null,
                "");
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }

    @Test
    public void testselectByMultiConditionWithTrim(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiConditionWithTrim("sportback", 20.0, "");
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }

    @Test
    public void testSelectByChoose(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByChoose("", null, "");
        cars.forEach(car -> {
            System.out.println(car);
        });
    }
    @Test
    public void testUpdateById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car updateCar = new Car(37L, 500, "Audi A5 sportback", 55.0, "2022-11-14", "gas");
        int count = mapper.updateById(updateCar);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateBySet(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car updateCar = new Car(37L, null, "Audi A7 sportback", 55.0, "2022-11-14", "gas");
        int count = mapper.updateBySet(updateCar);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteByIds(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Long[] ids = new Long[]{47L, 50L, 51L};
        int count = mapper.deleteByIds(ids);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testDeleteByIds2(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Long[] ids = new Long[]{63L, 64L};
        int count = mapper.deleteByIds2(ids);
        sqlSession.commit();
        sqlSession.close();

    }


    @Test
    public void testInsertBatchCars(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(null, 1299, "Audi S6", 100.0, "2023-02-01", "gas"));
        cars.add(new Car(null, 1230, "Audi S7", 105.0, "2023-02-02", "gas"));
        cars.add(new Car(null, 1231, "Audi Q2L E-tron", 100.0, "2023-02-04", "ele"));
        int count = mapper.insertBatchCars(cars);
        sqlSession.commit();
        sqlSession.close();
    }
}

package com.yty.mybatis.test;

import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMapperTest {

    @Test
    public void testInsertCar(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Map<String, Object> map = new HashMap<>();
        map.put("carnum", 400);
        map.put("brand", "BYD Han");
        map.put("guide_price", 10.00);
        map.put("produce_time","2022-02-11");
        map.put("car_type", "ele");
        int count = sqlSession.insert("insertCar", map);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertCarByClass(){
        Car car = new Car(null, 400, "Audi A4L", 45.00, "2022-10-11", "gas");
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int insertCar = sqlSession.insert("insertCar", car);
        System.out.println(insertCar);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Car car = new Car(31L, 400, "Audi A4L", 45.00, "2022-10-11", "gas");
        int deleteById = sqlSession.delete("deleteById", 32);
        System.out.println(deleteById);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testUpdateCarById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Car car = new Car(33L, 450, "Audi A6L", 65.00, "2022-11-11", "gas");
        sqlSession.update("updateCarById", car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Object car = sqlSession.selectOne("selectCarById", 1);
        System.out.println(car);
        sqlSession.close();
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("selectAllCars");
        for (Object car : cars) {
            System.out.println(car);
        }
        sqlSession.close();
    }

    @Test
    public void testNamespace(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        List<Object> cars = sqlSession.selectList("userMapper.selectAllCars");
        cars.forEach(car -> System.out.println(car));

        sqlSession.close();

    }
}

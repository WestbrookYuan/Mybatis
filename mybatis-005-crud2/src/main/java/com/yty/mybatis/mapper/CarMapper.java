package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {
    int insertCar(Car car);
    int deleteById(Long id);

    int updateCar(Car car);
    Car selectById(Long id);

    List<Car> selectAll();

}

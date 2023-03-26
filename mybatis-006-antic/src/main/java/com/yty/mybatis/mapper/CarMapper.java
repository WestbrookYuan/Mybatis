package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {
    List<Car> selectByCarType(String carType);
    List<Car> sortByPrice(String sortBy);
    List<Car> selectByBrand(String brand);
    int insertCarUseGenerateId(Car car);
}

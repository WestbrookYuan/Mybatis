package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;
import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Car row);

    Car selectByPrimaryKey(Integer id);

    List<Car> selectAll();

    int updateByPrimaryKey(Car row);
}
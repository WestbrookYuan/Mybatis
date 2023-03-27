package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;

public interface CarMapper {
    Car selectById(Long id);

    Car selectById2(Long id);
}

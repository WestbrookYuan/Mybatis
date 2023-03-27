package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    List<Car> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * 使用pageHelper查询
     * @return
     */
    List<Car> selectAll();
}

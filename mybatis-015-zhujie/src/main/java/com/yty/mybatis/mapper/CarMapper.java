package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CarMapper {
    @Insert("insert into t_car " +
            "values(null, " +
            "#{carnum}, " +
            "#{brand}, " +
            "#{guidePrice}, " +
            "#{produceTime}, " +
            "#{carType})")
   int insert(Car car);

    @Delete("delete from t_car where id=#{id}")
    int delete(@Param("id") Integer id);

    @Update("update t_car " +
            "set carnum=#{carnum}, " +
            "brand=#{brand}, " +
            "guide_price=#{guidePrice}," +
            "produce_time=#{produceTime}," +
            "car_type=#{carType}" +
            "where id=#{id}")
    int update(Car car);

    @Select("select * from t_car where id= #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "carnum", column = "carnum"),
            @Result(property = "brand", column = "brand"),
            @Result(property = "produceTime", column = "produce_time"),
            @Result(property = "carType", column = "car_type"),
            @Result(property = "guidePrice", column = "guide_price")
    })
    Car selectById(Long id);
}

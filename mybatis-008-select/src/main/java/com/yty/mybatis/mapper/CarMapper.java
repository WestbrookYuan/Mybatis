package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface CarMapper {
    /**
     *
     * @param id
     * @return
     * 根据id查车辆讯息
     */
    Car selectById(Long id);
    List<Car> selectAll();

    /**
     * 使用一个pojo对象接收可能多个返回结果
     * @param brand
     * @return
     */
    Car selectByBrandLike(String brand);
    Map<String, Object> selectByIdRetMap(Long id);

    List<Map<String, Object>> selectAllRetList();

    /**
     * map集合的key是主键值
     * value是每条记录
     * @return
     */
    @MapKey("id")
    Map<Long, Map<String, Object>> selectAllRetMap();

    /**
     * 使用resultmap查询所有车
     * @return
     */

    List<Car> selectAllByResultMap();
    List<Car> selectAllBymapUnderscoreToCamelCase();
    Long selectTotal();
}

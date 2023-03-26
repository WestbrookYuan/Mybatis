package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    /**
     * 使用if标签实现动态sql
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiCondition(@Param("brand") String brand,
                                     @Param("guidePrice") Double guidePrice,
                                     @Param("carType") String carType);

    /**
     * 使用where标签使sql语句更加智能
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionWithWhere(@Param("brand") String brand,
                                     @Param("guidePrice") Double guidePrice,
                                     @Param("carType") String carType);
    /**
     * 使用trim标签使添加前缀、后缀
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiConditionWithTrim(@Param("brand") String brand,
                                             @Param("guidePrice") Double guidePrice,
                                             @Param("carType") String carType);

    /**
     * 根据choose when otherwise标签实现单条件查询
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByChoose(@Param("brand") String brand,
                             @Param("guidePrice") Double guidePrice,
                             @Param("carType") String carType);
    int updateById(Car car);
    int updateBySet(Car car);

    /**
     * 使用foreach进行批量删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") Long[] ids);
    int deleteByIds2(@Param("ids") Long[] ids);

    int insertBatchCars(@Param("cars") List<Car> cars);

}

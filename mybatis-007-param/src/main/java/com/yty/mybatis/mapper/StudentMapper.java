package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface StudentMapper {

    /**
     *
     * @param map
     * @return
     * 使用简单数据类型，但是用的是Map
     */
    int insertStudentByMap(Map<String, Object> map);
    /**
     *
     * @param student
     * @return
     * 使用pojo类
     */
    int insertStudentByPojo(Student student);

    /**
     *
     * @param name
     * @param sex
     * @return
     * 根据name 和 sex 多参数
     */
    List<Student> selectByNameAndSex(String name, Character sex);

    /**
     *
     * @param name
     * @param sex
     * @return
     * 使用Param注解
     */
    List<Student> selectByNameAndSex2(@Param("name") String name,@Param("sex") Character sex);
    List<Student> selectById(Long id);
    List<Student> selectByName(String id);
    List<Student> selectByBirth(Date birth);
    List<Student> selectBySex(Character sex);


}

package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 根据id查询学生信息，同时获取班级信息
     * @param sid
     * @return
     */
    Student selectById(Integer sid);

    /**
     * association
     * @param sid
     * @return
     */
    Student selectByIdAssociation(Integer sid);

    /**
     * 分步查询第一步：先查询学生的相关信息
     * @param sid
     * @return
     */
    Student selectByIdStep1(Integer sid);

    List<Student> selectByCid(Integer cid);
}

package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Clazz;
import com.yty.mybatis.pojo.Student;

import java.util.List;

public interface ClazzMapper {
    Clazz selectByIdStep2(Integer cid);

    /**
     * 使用collection查询班级信息
     * @param cid
     * @return
     */
    Clazz selectByCollection(Integer cid);

    Clazz selectByStep1(Integer cid);
}

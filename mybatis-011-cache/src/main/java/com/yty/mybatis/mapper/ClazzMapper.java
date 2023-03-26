package com.yty.mybatis.mapper;

import com.yty.mybatis.pojo.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {
    int insertClazz(@Param("cid") Integer cid,@Param(("cname")) String cname);
    Clazz selectByCid(Integer cid);
}

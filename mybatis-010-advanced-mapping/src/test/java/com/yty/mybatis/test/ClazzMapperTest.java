package com.yty.mybatis.test;

import com.yty.mybatis.mapper.ClazzMapper;
import com.yty.mybatis.pojo.Clazz;
import com.yty.mybatis.pojo.Student;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ClazzMapperTest {

    @Test
    public void testSelectByCollection(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCollection(1000);
        System.out.println(clazz);
        List<Student> students = clazz.getStudents();
        System.out.println(students);
        sqlSession.close();
    }

    @Test
    public void testSelectByStep1(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByStep1(1000);
        System.out.println(clazz.getCname());
        List<Student> students = clazz.getStudents();
        System.out.println(students);
        sqlSession.close();
    }
}

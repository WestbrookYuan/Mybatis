package com.yty.mybatis.test;

import com.yty.mybatis.mapper.StudentMapper;
import com.yty.mybatis.pojo.Student;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParametersTest {

    @Test
    public void testSelectById(){
        Long id = 1l;
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.selectById(id);
        studentList.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectByName(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByName("zhangsan");
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectBySex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectBySex('m');
        students.forEach(System.out::println);
        sqlSession.close();
    }
    @Test
    public void testSelectByBirth() throws Exception{
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("1980-11-06");
        List<Student> students = mapper.selectByBirth(date);
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testInsertStudentByMap() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap<>();
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date =  sdf.parse("1999-10-11");
        map.put("name", "wangwu");
        map.put("age", 25);
        map.put("height", 177.0);
        map.put("birth", date);
        map.put("sex", Character.valueOf('f'));
        int count = mapper.insertStudentByMap(map);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testInsertStudentByPojo() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date =  sdf.parse("1988-10-11");
        Student student = new Student(null, "zhang jiu", 27, 191.1, date, 'm');
        int count = mapper.insertStudentByPojo(student);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testSelectByNameAndSex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex("zhangsan",'m');
        students.forEach(System.out::println);
        sqlSession.close();
    }

    @Test
    public void testSelectByNameAndSex2(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectByNameAndSex2("zhangsan",'m');
        students.forEach(System.out::println);
        sqlSession.close();
    }
}

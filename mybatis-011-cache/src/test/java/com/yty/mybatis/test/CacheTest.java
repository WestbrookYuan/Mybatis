package com.yty.mybatis.test;

import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.mapper.ClazzMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.pojo.Clazz;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;

public class CacheTest {

    @Test
    public void testSelectByIdInSameSession(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(65L);
        System.out.println(car);

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(65L);
        System.out.println(car1);
        sqlSession.close();
    }

    @Test
    public void testSelectByIdInDiffSession() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        System.out.println(mapper1.selectById(65L));

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);
        System.out.println(mapper2.selectById(65L));
        sqlSession1.close();
        sqlSession2.close();
    }


    @Test
    public void testClearCacheCommand(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(65L);
        System.out.println(car);
        sqlSession.clearCache();

        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        Car car1 = mapper1.selectById(65L);
        System.out.println(car1);
        sqlSession.close();
    }

    @Test
    public void testClearCacheInSameTable(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCid(1000);
        System.out.println(clazz);
        int count = mapper.insertClazz(1002, "NZ173");
        sqlSession.commit();
        Clazz clazz1 = mapper.selectByCid(1000);
        System.out.println(clazz1);
        sqlSession.close();
    }
    @Test
    public void testClearCacheInDiffTable(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
        System.out.println(mapper1.selectById(65L));
        int count = mapper.insertClazz(1003, "NZ 174");
        System.out.println(mapper1.selectById(65L));
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testCacheInSqlFactoryWithoutClose() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);
        //the car has been cached in sqlSession1 (一级缓存)
        System.out.println(mapper1.selectById(65L));

        //if sqlSession not been closed, the car will not be cached into SqlSessionFactory

        //the car has been cached in sqlSession1 (一级缓存)
        System.out.println(mapper2.selectById(65L));

        //the car has been cached in SqlSessionFactory (二级缓存) from sqlSession1
        sqlSession1.close();
        //the car has been cached in SqlSessionFactory (二级缓存) from sqlSession2
        sqlSession2.close();
    }

    @Test
    public void testCacheInSqlFactoryWithClose() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        CarMapper mapper1 = sqlSession1.getMapper(CarMapper.class);
        CarMapper mapper2 = sqlSession2.getMapper(CarMapper.class);
        //the car has been cached in sqlSession1 (一级缓存)
        System.out.println(mapper1.selectById(65L));

        //if sqlSession not been closed, the car will not be cached into SqlSessionFactory
        //the car has been cached in SqlSessionFactory (二级缓存) from sqlSession1
        sqlSession1.close();

        //the car has been cached in sqlSession1 (一级缓存)
        System.out.println(mapper2.selectById(65L));

        //the car has been cached in SqlSessionFactory (二级缓存) from sqlSession2
        sqlSession2.close();
    }

}

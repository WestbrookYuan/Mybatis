package com.yty.mybatis.test;

import com.mysql.cj.CacheAdapter;
import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.pojo.CarExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CarMapperTest {

    //CarExample类负责封装查询条件
    @Test
    public void testSelect() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        //查询一个
        Car car = mapper.selectByPrimaryKey(65);
        System.out.println(car);
        //查询所有
        List<Car> cars = mapper.selectByExample(null);
        cars.forEach(car1 -> {
            System.out.println(car1);
        });
        //按照条件进行查询

        CarExample carExample = new CarExample();
        //调用carExample.createCriteria()创建条件
        carExample.createCriteria()
                .andBrandLike("% Audi %");
        List<Car> cars2 = mapper.selectByExample(carExample);
        cars2.forEach(car1 -> {
            System.out.println(car1);
        });

    }
}

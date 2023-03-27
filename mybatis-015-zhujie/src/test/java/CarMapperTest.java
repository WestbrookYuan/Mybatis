import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testInsert(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, 1235, "Audi S4 Avant", 70.0, "2023-03-03", "gas");
        mapper.insert(car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDelete(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(null, 1235, "Audi S4 Avant", 70.0, "2023-03-03", "gas");
        mapper.delete(72);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = new Car(68L, 1235, "Audi S4 Avant", 70.0, "2023-03-03", "gas");
        mapper.update(car);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelect(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        Car car = mapper.selectById(68L);
        System.out.println(car);
        sqlSession.close();
    }
}

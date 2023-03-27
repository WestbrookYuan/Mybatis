import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yty.mybatis.mapper.CarMapper;
import com.yty.mybatis.pojo.Car;
import com.yty.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectByPage(){
        int pageNumber = 1;
        int pageSize = 3;
        int startIndex = (pageNumber - 1) *pageSize;
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByPage(startIndex, pageSize);
        cars.forEach(car -> {
            System.out.println(car);
        });
        sqlSession.close();
    }

    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        //在执行分页插件之前，开启功能
        int pageNumber = 1;
        int pageSize = 1;
        PageHelper.startPage(1, 3);
        List<Car> cars = mapper.selectAll();
        cars.forEach(car -> {
            System.out.println(car);
        });
        PageInfo<Car> pageInfo = new PageInfo<>(cars, 5);
        System.out.println(pageInfo);
        sqlSession.close();
    }
}

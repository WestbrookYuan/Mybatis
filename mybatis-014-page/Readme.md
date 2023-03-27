r### Mybatis 分页插件



### 分页的原理

1. 页码Page Number

2. 每页的条数Page Size

3. 实际上每次执行分页，都需要传送这两个值
   1. url?pageNumber=3&pageSize=5
   2. Request.getParameters取得这两个值

4. MySQL limit关键字

   1. limit 开始下标，记录条数

   2. limit startindex, pagenumber

      ```sql
      +----+--------+-----------------+-------------+--------------+----------+
      | id | carnum | brand           | guide_price | produce_time | car_type |
      +----+--------+-----------------+-------------+--------------+----------+
      | 65 | 1231   | Audi Q2L E-tron |      100.00 | 2023-02-04   | ele      |
      | 66 | 1299   | Audi S6         |      100.00 | 2023-02-01   | gas      |
      | 67 | 1230   | Audi S7         |      105.00 | 2023-02-02   | gas      |
      +----+--------+-----------------+-------------+--------------+----------+
      3 rows in set (0.03 sec)
      
      mysql> select * from t_car limit 3, 3;
      +----+--------+-------------------+-------------+--------------+----------+
      | id | carnum | brand             | guide_price | produce_time | car_type |
      +----+--------+-------------------+-------------+--------------+----------+
      | 68 | 1231   | Audi Q2L E-tron   |      100.00 | 2023-02-04   | ele      |
      | 69 | 1234   | Audi A5 Sportback |       35.00 | 2022-10-11   | gas      |
      +----+--------+-------------------+-------------+--------------+----------+
      2 rows in set (0.00 sec)
      ```

5. 假设每页显示三条记录：

   1. 0 1 2
   2. 3 4 5
   3. 6 7 8
   4. startIndex = (pageNumber - 1) * pageNumber 

6. PageHelper配置

   ```xml
           <!--分页插件依赖-->
           <dependency>
               <groupId>com.github.pagehelper</groupId>
               <artifactId>pagehelper</artifactId>
               <version>5.3.1</version>
           </dependency>
   ```

   ```xml
       <typeAliases>
           <package name="com.yty.mybatis.pojo"/>
       </typeAliases>
       <!--分页拦截器配置 -->
       <plugins>
           <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
       </plugins>
   ```

7. 使用：

   ```java
       @Test
       public void testSelectAll(){
           SqlSession sqlSession = SqlSessionUtil.openSession();
           CarMapper mapper = sqlSession.getMapper(CarMapper.class);
           //在执行分页插件之前，开启功能
           int pageNumber = 1;
           int pageSize = 3;
           PageHelper.startPage(1, 3);
           List<Car> cars = mapper.selectAll();
           cars.forEach(car -> {
               System.out.println(car);
           });
           sqlSession.close();
       }
   ```

8. 获取PageInfo（导航栏）：

   ```java
   PageInfo<Car> pageInfo = new PageInfo<>(cars, 5);
   //PageInfo{pageNum=1, pageSize=3, size=3, startRow=1, endRow=3, total=5, pages=2, list=Page{count=true, pageNum=1, pageSize=3, startRow=0, endRow=3, total=5, pages=2, reasonable=false, pageSizeZero=false}[Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}, Car{id=66, carnum=1299, brand='Audi S6', guidePrice=100.0, produceTime='2023-02-01', carType='gas'}, Car{id=67, carnum=1230, brand='Audi S7', guidePrice=105.0, produceTime='2023-02-02', carType='gas'}], prePage=0, nextPage=2, isFirstPage=true, isLastPage=false, hasPreviousPage=false, hasNextPage=true, navigatePages=5, navigateFirstPage=1, navigateLastPage=2, navigatepageNums=[1, 2]}
   
   ```

   
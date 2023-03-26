# MyBatis CRUD

## 什么是CRUD

1. crud

   1. C:create
   2. R:retrive
   3. U:update
   4. D:Delete

2. ```xml
       <insert id="insertCar">
           insert into t_car(id, carnum, brand, guide_price, produce_time, car_type)
           values (null, 300, 'Toyota 4Runner', 30.00, '2019-10-11', 'gas');
       </insert>
   ```

   1. 在实际开发中是不可能存在的，值写死
   2. 应该是前段form传数据再交由后端处理

3. JDBC vs MyBatis

4. 1. 如果是JDBC：

      ```java
      String sql = "insert into t_car(id, brand) values(1, 'benz')";
      PrepareStatement ps = Conn.prepareStatement(sql);
      ps.setString(1, xxx);
      ps.SetString(2, xxx);
      ps.executeQuery();
      ```

   2. 在MyBatis中，等效的占位符是**#{}**

      1. 和JDBC中的**？**等效
      2. 可以使用Map的key给数据库语句传值
         1. **#{}**写map集合的key，如果没有就是null
         2. 起名要见名知意

      ```java
      public class CarMapperTest {
      
          @Test
          public void insertCar(){
              SqlSession sqlSession = SqlSessionUtil.openSession();
              Map<String, Object> map = new HashMap<>();
              map.put("carnum", 400);
              map.put("brand", "BYD Han");
              map.put("guide_price", 10.00);
              map.put("produce_time","2022-02-11");
              map.put("car_type", "ele");
              int count = sqlSession.insert("insertCar", map);
              System.out.println(count);
              sqlSession.commit();
              sqlSession.close();
          }
      }
      ```

      ```xml
          <insert id="insertCar">
              insert into t_car(id, carnum, brand, guide_price, produce_time, car_type)
              values (null, #{carnum}, #{brand}, #{guide_price}, #{produce_time}, #{car_type});
          </insert>
      ```

   3. 可以使用类的属性给数据库语句传值

      ```java
      public class Car{
          private Long id;
          private Integer carnum;
          private String brand;
          private Double guidePrice;
          private String produceTime;
          private String carType;
      
          public Car(){};
      
          public Car(Long id, Integer carnum, String brand, Double guidePrice, String produceTime, String carType) {
              this.id = id;
              this.carnum = carnum;
              this.brand = brand;
              this.guidePrice = guidePrice;
              this.produceTime = produceTime;
              this.carType = carType;
          }
      }
      ```

      ```xml
      <insert id="insertCar">
              insert into t_car(id, carnum, brand, guide_price, produce_time, car_type)
              values (null, #{carnum}, #{brand}, #{guidePrice}, #{produceTime}, #{carType});
          </insert>
      ```

      1. 本质上是属性的get方法的方法名去掉get
      2. 如果使用Pojo类传值的话，本质上传递的是Pojo类的get方法去掉get之后小写：反射+拼接

## MyBatis 核心配置文件



```xml
 <environments default="development"> <!--默认环境-->

        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/Mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="12345678"/>
            </dataSource>
        </environment>

        <environment id="mybatisdb">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/learn"/>
                <property name="username" value="root"/>
                <property name="password" value="12345678"/>
            </dataSource>
        </environment>
    </environments>
```

1. 一个数据库对应一个SqlSessionFactory对象，一个环境environment会对应一个SqlSessionFactory对象

2. Transaction实现类

   1. JDBCTransaction
   2. ManagedTransaction
   3. 实例化对应的对象

3. DataSource

   ```xml
   <dataSource type="POOLED">
       <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
       <property name="url" value="jdbc:mysql://localhost:3306/Mybatis"/>
       <property name="username" value="root"/>
       <property name="password" value="12345678"/>
   </dataSource>
   
   ```

   1. 数据源：为数据库提供连接对象
   2. 实际上是一套规范
   3. 数据库连接池提供连接对象，所以也是连接池
   4. 常见的数据库连接池：
      1. 阿里：德鲁伊连接池druid
   5. 用来制定数据源的类型：
      1. type：POOLED，UNPOOLED，JNDI
         1. UNPOOLED：不使用数据库连接池：创建新的数据库连接对象
         2. POOLED：Mybatis自己的数据库连接池
         3. JNDI：集成其他第三方的数据库连接池
            1. Java命名接口
            2. 是一个规范：实现者：tomcat、
      2. 

4. 

   


































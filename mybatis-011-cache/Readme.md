Mybatis缓存机制

### 缓存：

![截屏2023-03-26 上午11.42.39](截屏2023-03-26 上午11.42.39.png)



### Mybatis提供的缓存机制

#### 一级缓存

1. 存放在SqlSession中

2. 使用不同的mapper，只查询了一次

   ```java
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
   
   ```

   ```txt
   2023-03-26 12:16:03.158 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
   2023-03-26 12:16:03.172 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
   2023-03-26 12:16:03.184 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
   Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
   Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
   ```

3. 如果使用不同的SqlSession，会查询多次，验证了一级缓存存放在SqlSession中

   ```java
       @Test
       public void testSelectById() throws IOException {
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
   ```

   ```txt
   2023-03-26 12:16:55.937 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
   2023-03-26 12:16:55.951 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
   2023-03-26 12:16:55.964 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
   Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
   2023-03-26 12:16:55.969 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
   2023-03-26 12:16:55.982 [main] DEBUG o.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 259630944.
   2023-03-26 12:16:55.983 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@f79a760]
   2023-03-26 12:16:55.983 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
   2023-03-26 12:16:55.983 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
   2023-03-26 12:16:55.984 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
   Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
   
   ```

4. 不使用一级缓存的情况：

   1. 不同的SqlSession对象
   2. 查询条件不同

5. 缓存失效：

   1. 查询之间执行了两类行为会清空一级缓存：

      1. 执行了SqlSession的ClearCache方法

         ```java
             @Test
             public void testSelectByIdInSameSession(){
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
         ```

         ```
         2023-03-26 12:24:06.368 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
         2023-03-26 12:24:06.382 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
         2023-03-26 12:24:06.395 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
         Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
         2023-03-26 12:24:06.400 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
         2023-03-26 12:24:06.400 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
         2023-03-26 12:24:06.401 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
         Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
         ```

      2. 执行了Insert、Delete或Update语句，而且和表没有关系

         ```java
             @Test
             public void testCacheInDiffTable(){
                 SqlSession sqlSession = SqlSessionUtil.openSession();
                 ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
                 CarMapper mapper1 = sqlSession.getMapper(CarMapper.class);
                 System.out.println(mapper1.selectById(65L));
                 int count = mapper.insertClazz(1003, "NZ 174");
                 System.out.println(mapper1.selectById(65L));
                 sqlSession.commit();
                 sqlSession.close();
             }
         ```

         ```txt
         2023-03-26 12:43:35.673 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
         2023-03-26 12:43:35.689 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
         2023-03-26 12:43:35.702 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
         Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
         2023-03-26 12:43:35.707 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.insertClazz - ==>  Preparing: insert into t_clazz (cid, cname) values (?, ?);
         2023-03-26 12:43:35.708 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.insertClazz - ==> Parameters: 1003(Integer), NZ 174(String)
         2023-03-26 12:43:35.709 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.insertClazz - <==    Updates: 1
         2023-03-26 12:43:35.709 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
         2023-03-26 12:43:35.710 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
         2023-03-26 12:43:35.711 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
         Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
         ```

         

#### 二级缓存

1. 存放在SqlSessionFactory中

2. 使用二级缓存的要求：

   1. 全局设置中打开（默认已经开启）

   2. sqlMapper.xml加入配置

      ```xml
      <mapper namespace="com.yty.mybatis.mapper.CarMapper">
          <cache/>
          <select id="selectById" resultType="car">
              select * from t_car where id= #{id};
          </select>
      </mapper>
      ```

   3. Pojo类必须实现序列化接口

      ```java
      public class Car implements Serializable
      ```

   4. SqlSession对象关闭或提交之后，一级缓存中的数据才会被写入到二级缓存当中。此时二级缓存才可用

      ```java
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
      ```

      ```txt
      2023-03-26 13:06:00.540 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
      2023-03-26 13:06:00.555 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
      2023-03-26 13:06:00.577 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
      Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
      2023-03-26 13:06:00.581 [main] DEBUG com.yty.mybatis.mapper.CarMapper - Cache Hit Ratio [com.yty.mybatis.mapper.CarMapper]: 0.0
      2023-03-26 13:06:00.581 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
      2023-03-26 13:06:00.592 [main] DEBUG o.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 203819996.
      2023-03-26 13:06:00.592 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@c260bdc]
      2023-03-26 13:06:00.592 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById2 - ==>  Preparing: select * from t_car where id= ?;
      2023-03-26 13:06:00.592 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById2 - ==> Parameters: 65(Long)
      2023-03-26 13:06:00.593 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById2 - <==      Total: 1
      Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
      ```

      ```java
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
      ```

      ```txt
      2023-03-26 13:15:09.481 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==>  Preparing: select * from t_car where id= ?;
      2023-03-26 13:15:09.498 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - ==> Parameters: 65(Long)
      2023-03-26 13:15:09.514 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectById - <==      Total: 1
      Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
      2023-03-26 13:15:09.523 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@14d14731]
      2023-03-26 13:15:09.524 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@14d14731]
      2023-03-26 13:15:09.524 [main] DEBUG o.apache.ibatis.datasource.pooled.PooledDataSource - Returned connection 349259569 to pool.
      2023-03-26 13:15:09.525 [main] WARN  org.apache.ibatis.io.SerialFilterChecker - As you are using functionality that deserializes object streams, it is recommended to define the JEP-290 serial filter. Please refer to https://docs.oracle.com/pls/topic/lookup?ctx=javase15&id=GUID-8296D8E8-2B93-4B9A-856E-0A65AF9B8C66
      2023-03-26 13:15:09.527 [main] DEBUG com.yty.mybatis.mapper.CarMapper - Cache Hit Ratio [com.yty.mybatis.mapper.CarMapper]: 0.5
      Car{id=65, carnum=1231, brand='Audi Q2L E-tron', guidePrice=100.0, produceTime='2023-02-04', carType='ele'}
      
      ```

      1. [Cache Hit Ratio [com.yty.mybatis.mapper.CarMapper\]: 0.5]: 

   5. 先从一级缓存中取，再从二级缓存中取

   6. 二级缓存失效：

      1. 两次查询之间执行了增删改

   7. 二级缓存的配置：

      1. eviction:驱逐不常用的对象
         1. LRU
         2. FIFO
         3. SOFT
         4. WEAK
      2. flushinterval:刷新间隔，2000：
         1. 2000毫秒刷新一次
      3. size：存储对象的上限，默认1024
      4. readOnly:
         1. true:多个语句共享一个对象，可能存在线程安全问题
         2. false:调用clone方法

#### 其他缓存工具

1. 可以集成EhCache
   1. 替代mybatis的二级缓存，一级缓存是无法替代的

```xml
<!--mybatis集成ehcache的组件-->
<dependency>
  <groupId>org.mybatis.caches</groupId>
  <artifactId>mybatis-ehcache</artifactId>
  <version>1.2.2</version>
</dependency>

```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--磁盘存储:将缓存中暂时不使用的对象,转移到硬盘,类似于Windows系统的虚拟内存-->
    <diskStore path="e:/ehcache"/>
  
    <!--defaultCache：默认的管理策略-->
    <!--eternal：设定缓存的elements是否永远不过期。如果为true，则缓存的数据始终有效，如果为false那么还要根据timeToIdleSeconds，timeToLiveSeconds判断-->
    <!--maxElementsInMemory：在内存中缓存的element的最大数目-->
    <!--overflowToDisk：如果内存中数据超过内存限制，是否要缓存到磁盘上-->
    <!--diskPersistent：是否在磁盘上持久化。指重启jvm后，数据是否有效。默认为false-->
    <!--timeToIdleSeconds：对象空闲时间(单位：秒)，指对象在多长时间没有被访问就会失效。只对eternal为false的有效。默认值0，表示一直可以访问-->
    <!--timeToLiveSeconds：对象存活时间(单位：秒)，指对象从创建到失效所需要的时间。只对eternal为false的有效。默认值0，表示一直可以访问-->
    <!--memoryStoreEvictionPolicy：缓存的3 种清空策略-->
    <!--FIFO：first in first out (先进先出)-->
    <!--LFU：Less Frequently Used (最少使用).意思是一直以来最少被使用的。缓存的元素有一个hit 属性，hit 值最小的将会被清出缓存-->
    <!--LRU：Least Recently Used(最近最少使用). (ehcache 默认值).缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存-->
    <defaultCache eternal="false" maxElementsInMemory="1000" overflowToDisk="false" diskPersistent="false"
                  timeToIdleSeconds="0" timeToLiveSeconds="600" memoryStoreEvictionPolicy="LRU"/>

</ehcache>
```

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/> <!--In carMapper.xml-->
```


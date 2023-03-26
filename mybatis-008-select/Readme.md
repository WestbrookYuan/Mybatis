# Return 类型

### 单一pojo类

### 多个pojo类

### 返回list

### 返回map

1. 返回map时可以指定数据库表中的列名当作key，使用@MapKey注解

```java
    @MapKey("id")
    Map<Long, Map<String, Object>> selectAllRetMap();

```

```xml
    <select id="selectAllRetMap" resultType="map">
        select
            id,
            carnum,
            brand,
            guide_price as guidePrice,
            produce_time as produceTime,
            car_type as carType
        from t_car;
    </select>
```

```java
    @Test
    public void testSelectAllRetMap(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
       Map<Long, Map<String, Object>> cars = mapper.selectAllRetMap();
        System.out.println(cars);
        sqlSession.close();
    }
   
```



### 使用resultmap标签

```xml
<!--    定义结果映射 指定数据库的字段名和类的属性名-->
<!--    type 用来指定pojo类的雷鸣-->
<!--    id指定resultmap的唯一标识-->
    <resultMap id="carResultMap" type="Car">
<!--        property是类的属性名 coloum是表的列名-->
<!--        一般都有主键，符合数据库第一范式-->
<!--        如果有主键，id列提高查询效率-->
        <id property="id" column="id"/>
        <result property="carnum" column="carnum"/>
        <result property="guidePrice" column="guide_price"/>
        <result property="produceTime" column="produce_time"/>
        <result property="carType" column="car_type"/>
    </resultMap>
    
    <!--    resultMap的值是指定的resultMap的id-->
    <select id="selectAllByResultMap" resultMap="carResultMap" >
        select
            *
        from t_car;
    </select>
```



### 驼峰命名自动映射

1. 前提

   1. SQL命名规范：全部小写，单词之间用下划线隔开
   2. Java Pojo类命名规范：首字母小写，其余的每个单词都大写

2. 在mybatis-config中进行配置

   ```xml
   <!--    在properties后边-->
       <settings>
           <setting name="logImpl" value="SLF4J"/>
           <setting name="mapUnderscoreToCamelCase" value="true"/>
       </settings>
   ```
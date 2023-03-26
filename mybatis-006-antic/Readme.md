# Mybatis 小技巧

### #{} 和 ${}

#{}:

```
2023-03-06 11:34:35.531 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectByCarType - ==>  Preparing: select id, carnum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car where car_type = ?;
2023-03-06 11:34:35.544 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectByCarType - ==> Parameters: ele(String)
2023-03-06 11:34:35.556 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectByCarType - <==      Total: 2
```



${}

```
2023-03-06 11:40:42.773 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectByCarType - ==>  Preparing: select id, carnum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType from t_car where car_type = ele;
2023-03-06 11:40:42.786 [main] DEBUG com.yty.mybatis.mapper.CarMapper.selectByCarType - ==> Parameters: 
### Cause: java.sql.SQLSyntaxErrorException: Unknown column 'ele' in 'where clause'
```



1. #{}：
   1. 底层使用prepareStatement：进行字符串占位符
2. ${}：
   1. 底层使用Statement：进行字符串拼接，有注入的风险
3. 必须使用${}的情况
   1. 需要数据库拼接：比如升序查询
   2. 拼接表名
   3. 批量删除
   4. 模糊查询：
      1. %${brand}%
      2. Concat('%', #{brand}, '%')
      3. Concat('%', '${brand}', '%')
      4. "%"#{brand}"%"

### 别名：typeAlias

```xml
    <typeAliases>
        <typeAlias type="com.yty.mybatis.pojo.Car" alias="Car"/>
        <typeAlias type="com.yty.mybatis.pojo.Car" /> # = alias="Car"
        <package name="com.yty.mybatis.pojo"/>
    </typeAliases>
```

1. alias 不区分大小写
2. namespace没有别名，必须写全限定包名
3. alias有默认值
4. 默认值是类的简名
5. 如果使用package标签，包下所有的类都会启用别名



### Mappers

1. resource：放在类路径下寻找文件

2. url：找寻根路径

3. class：全限定接口名

   ```xml
           <mapper class="com.yty.mybatis.mapper.CarMapper"/>
   ```

   

   1. 如果用这种方式:mybatis会去resources/com/yty.mybatis/mapper，寻找CarMapper.xml文件
   2. xml文件和接口名必须一致
   3. 可以使用package标签，指定文件夹下所有xml文件（最常用）



### 使用自增主键插入数据

```xml
    <insert id="insertCarUseGenerateId" useGeneratedKeys="true" keyProperty="id">
        insert into t_car
        values (null,
                #{carnum},
                #{brand},
                #{guidePrice},
                #{produceTime},
                #{carType});
    </insert>
```

1. userGeneratedKey:true时使用自增主键机制
2. keyProperty:回写给对象的哪个属性
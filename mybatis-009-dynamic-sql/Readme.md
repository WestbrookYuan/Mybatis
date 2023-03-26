# 动态sql

条件筛选的业务需求时使用动态sql帮助查询

### if标签

```xml
<if test="brand != null and brand != ''">
            and brand like "%"#{brand}"%"
        </if>
```

1. test属性是必须的，其条件表达式的结果必须是一个布尔值

2. 如果条件表达式需要或、与等操作，**不能**使用&& 或 ||，会与xml语法冲突，要使用and 或 or（mybatis提供的）

3. 条件表达式的属性名：

   1. arg0, arg1(如果未注解)
   2. param1, param2
   3. 如果注解了，使用注解名
   4. 如果使用pojo类传参，需要使用pojo类的属性名

   ```java
       List<Car> selectByMultiCondition(@Param("brand") String brand,
                                        @Param("guidePrice") Double guidePrice,
                                        @Param("carType") String carType);
   ```



### where标签

```xml
<select id="selectByMultiConditionWithWhere" resultType="car">
        select * from t_car
        <!--负责where子句动态生成的-->
        <where>
            <if test="brand != null and brand != ''">
                brand like "%"#{brand}"%" and
            </if>
            <if test="guidePrice != null">
                guide_price > #{guidePrice} and
            </if>

            <if test="carType != null and carType != ''">
                car_type = #{carType};
            </if>
        </where>
    </select>
```

1. 当所输条件全为空时，where语句保证不会在select语句输出where condition
2. 根据输入条件自动去除表达式**前面**的**and**或**or**

### Trim标签

```xml
    <select id="selectByMultiConditionWithTrim" resultType="car">
        select *
        from t_car
        <!-- 前缀动态加where 后缀动态去掉and-->
        <trim prefix="where" suffix="" prefixOverrides="" suffixOverrides="and|or">
            <if test="brand != null and brand != ''">
                brand like "%"#{brand}"%" and
            </if>
            <if test="guidePrice != null">
                guide_price > #{guidePrice} and
            </if>

            <if test="carType != null and carType != ''">
                car_type = #{carType};
            </if>
        </trim>
    </select>
```

1. 属性名：
   1. prefix：加前缀
      1. 在所有语句的前边添加前缀
   2. suffix：加后缀
   3. prefixOverrides：去掉前缀
   4. suffixOverrides：去掉后缀
      1. 在所有语句中去掉后缀



### Set标签

```xml
    <update id="updateBySet">
        update t_car
        <set>
            <if test="carnum != null ">
                carnum = #{carnum},
            </if>
            <if test="brand != null and brand != ''">
                brand = #{brand},
            </if>
            <if test="guidePrice != null">
                guide_price = #{guidePrice},
            </if>
            <if test="produceTime != null and produceTime != ''">
                produce_time = #{produceTime},
            </if>
            <if test="carType != null and carType != ''">
                car_type = #{carType}
            </if>
        </set>
        where id=#{id}
    </update>
```



1. 用在update语句中，生成set语句，去掉最后多余的","
2. 比如我们只更新提交的不为空的字段，如果提交的数据是空或者""，那么这个字段我们将不更新



### Choose标签

```xml
<choose>
  <when></when>
  <when></when>
  <when></when>
  <otherwise></otherwise>
</choose>
```



1. choose与when、otherwise一同使用：
   1. 等同与if... else if... else
   2. 只有一个分支会被执行
   3. 只有第一个符合条件的分支会被执行



### ForEach标签

```java
    /**
     * 使用foreach进行批量删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") Long[] ids);
```



```xml
    <delete id="deleteByIds" >
        delete from t_car where id in (
            <foreach collection="ids" item="id" separator=",">
                #{id}
            </foreach>
            )
    </delete>
```



1. 属性：
   1. collection：指定数组或集合
      1. 如果没有使用param注解：**Available parameters are [array, arg0]**
   2. item：数组中的元素
   3. seprator：循环之间的分隔符
   4. open：前缀
   5. close：后缀
2. where id in **小括号可以不写**：使用open 和 close标签



### SQL和Include标签



```xml
    <sql id="carColumnName">
        id,
        carnum,
        brand,
        guide_price as guidePrice,
        produce_time as produceTime,
        car_type as carType
    </sql>
    
        <select id="selectById" resultType="car">
        select
            <include refid="carColumnName"/>
        from t_car where id=#{id};
    </select>
```



定义全局的sql标签

使用include调用

提高代码复用性
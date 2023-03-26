# Mybatis advanced mapping

### 

### 多对一关系映射

1. 给定情形：有多个学生在不同的班级

   1. 多：学生
   2. 一：班级
   3. 哪个在前边那个是主表：
      1. 多对一：多是主表
      2. 一对多：一是主表

2. 主表应当映射为JVM中的主对象：都是student

   1. student对象中应当有一个clazz属性，承载clazz对象

3. 实现方式：

   1. 使用一条语句（级联属性映射）

      ```java
      public class Student {
          private Integer sid;
          private String sname;
          private Clazz clazz;
      }
      
      public interface StudentMapper {
      
          /**
           * 根据id查询学生信息，同时获取班级信息
           * @param id
           * @return
           */
          Student selectById(Integer sid);
      }
      ```

      ```xml
          <resultMap id="studentResultMap" type="Student">
              <id property="sid" column="sid"/>
              <result property="sname" column="sname"/>
              <result property="clazz.cid" column="cid"/>
              <result property="clazz.cname" column="cname"/>
          </resultMap>
      <!--由于student对象中有另一个类的对象作为属性，绑定时需要使用student类中clazz对应的属性名和clazz的属性名绑定进resultMap-->
          <select id="selectById" resultType="studentResultMap">
              select s.sid, s.sname, c.cid, c.cname
              from t_student  s
              left join t_clazz c on c.cid = s.cid
              where s.sid=#{sid};
          </select>
      ```

   2. 使用一条语句（association）

      ```xml
          <resultMap id="studentResultMapAssociation" type="Student">
              <id property="sid" column="sid"/>
              <result property="sname" column="sname"/>
              <association property="" javaType="">
                  
              </association>
          </resultMap>
      ```

      1. association：将student关联一个clazz对象
         1. properity:提供pojo类的属性名
         2. javaType：要映射的java类型

   3. 分步查询，使用两条语句（常用）

      ```xml
          <!--student mapper-->
          <select id="selectByIdStep1" resultMap="studentResultMapByStep">
              select s.sid, s.sname, s.cid
              from t_student s where s.sid = #{sid}
          </select>
          
          
          <!--两条sql语句，完成多对一的结果查询-->
          <!--step1: 根据学生的id查询信息-->
          <!--step2: 在association中指定下一步sql语句的id,使用association中的select属性-->
          <!--延迟加载 -->
          <resultMap id="studentResultMapByStep" type="Student">
              <id property="sid" column="sid"/>
              <result property="sname" column="sname"/>
              <association property="clazz"
                           select="com.yty.mybatis.mapper.ClazzMapper.selectByIdStep2"
                           column="cid"
                           fetchType="lazy"/>
          </resultMap>
          
          
          <!--clazz mapper-->
          <select id="selectByIdStep2" resultType="clazz">
              select * from t_clazz where cid=#{cid};
          </select>
      ```

      ```txt
      2023-03-26 00:23:26.904 [main] DEBUG c.yty.mybatis.mapper.StudentMapper.selectByIdStep1 - ==>  Preparing: select s.sid, s.sname, s.cid from t_student s where s.sid = ?
      2023-03-26 00:23:26.919 [main] DEBUG c.yty.mybatis.mapper.StudentMapper.selectByIdStep1 - ==> Parameters: 3(Integer)
      2023-03-26 00:23:26.943 [main] DEBUG c.yty.mybatis.mapper.StudentMapper.selectByIdStep1 - <==      Total: 1
      wangwu
      2023-03-26 00:23:26.944 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.selectByIdStep2 - ==>  Preparing: select * from t_clazz where cid=?;
      2023-03-26 00:23:26.944 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.selectByIdStep2 - ==> Parameters: 1000(Integer)
      2023-03-26 00:23:26.946 [main] DEBUG com.yty.mybatis.mapper.ClazzMapper.selectByIdStep2 - <==      Total: 1
      Clazz{cid=1000, cname='NZ 171'}
      ```

      

      1. 优点：

         1. 可复用：分成多个组件，可以分别使用

         2. 懒加载（延迟加载）：用到的时候查询，不用的时候不查询，提高效率

            1. 只需要学生姓名，不需要班级
            2. association中的属性为：**fetchType="lazy"**

            ```xml
            <setting name="lazyLoadingEnabled" value="true"/>
            ```

            3. 推荐在全局设置中设置延迟加载机制，有特殊需求需要全局加载：**fetchType="eager"**



### 一对多关系映射

1. 一个班级对应多个学生：一个班级应该返回一个学生集合，即一个班的所有学生

2. 一在前：一是主表

3. 实现方式：

   1. Collection：

      ```xml
          <select id="selectByCollection" resultMap="">
              select c.cid, c.cname, s.sid, s.sname from t_clazz c left join t_student s on c.cid = s.cid where c.cid=#{cid}
          </select>
          
          <resultMap id="clazzResultMap" type="Clazz">
              <id property="cid" column="cid"/>
              <result property="cname" column="cname"/>
              <!--一对多使用collection-->
              <collection property="students" ofType="Student">
                  <id property="sid" column="sid"/>
                  <result property="sname" column="sname"/>
              </collection>
          </resultMap>
      ```

      ```java
          private List<Student> students;
      ```

      1. 使用collection标签：
         1. property存放Clazz类中的集合名称
         2. **ofType**存放集合的元素类型

   2. 分步查询（延迟加载）


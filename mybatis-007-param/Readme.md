# My batis parameter

### 单一Parameter类型

```xml
    <select id="selectById" resultType="Student" parameterType="java.lang.Long">
        select id, name, age, height, birth, sex from t_student where id=#{id};
    </select>
```

1. parameterType这个参数可以识别输入的数据类型
   1. mybatis有自己的类型推断机制，所以一般不用写
2. 将数据传到预先编译的sql语句的问号上，并决定jdbc的Set函数
   1. ps.setString() ...
   2. 取决于mybatis的parameter Type
3. parameterType内置别名



### Map数据类型

```java
    @Test
    public void testInsertStudentByMap() throws Exception {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> map = new HashMap<>();
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date =  sdf.parse("1999-10-11");
        map.put("name", "wangwu");
        map.put("age", 25);
        map.put("height", 177.0);
        map.put("birth", date);
        map.put("sex", Character.valueOf('f'));
        mapper.insertStudentByMap(map);
        sqlSession.commit();
        sqlSession.close();

    }
```

```xml
    <insert id="insertStudentByMap" parameterType="map">
        insert into t_student values (null, #{name}, #{age}, #{height}, #{birth}, #{sex});
    </insert>
```

1. map中的key值要和mapper.xml语句中的#{}的值一致



### 实体类

```java
    @Test
    public void testInsertStudentByPojo() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date =  sdf.parse("1988-10-11");
        Student student = new Student(null, "zhang jiu", 27, 191.1, date, 'm');
        int count = mapper.insertStudentByPojo(student);
        sqlSession.commit();
        sqlSession.close();
    }
```

1. 对象中的属性值要和mapper.xml语句中的#{}的值一致



### 多参数

```java
    List<Student> selectByNameAndSex(String name, Character sex);
```

1. mybatis会自动创建一个map
   1. map的存储顺序:
   2. map.put("arg0", name);  map.put("arg1", sex);
2. 如果直接用value：
   1. **Parameter 'name' not found. Available parameters are [arg1, arg0, param1, param2]**
3. 用arg0, 1或param1, 2



### 注解方式多参数

如果使用注解

```java
    List<Student> selectByNameAndSex2(@Param("name") String name,@Param("sex") Character sex);
```

1. map.put("**name**", name);  map.put("**sex**", sex);

2. param1 param2还在

3. 源码分析

   ```java
           SqlSession sqlSession = SqlSessionUtil.openSession();
           StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
           List<Student> students = mapper.selectByNameAndSex2("zhangsan",'m');
           students.forEach(System.out::println);
           sqlSession.close();
   ```

   此时mapper是代理对象，是StudentMapper的代理，selectByNameAndSex2是代理方法。

   代理方法和目标方法：**代理方法**：租房子的中介，**目标方法**：我找房子

4. 代理模式解析

   ```java
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           try {
               return Object.class.equals(method.getDeclaringClass()) ? method.invoke(this, args) : this.cachedInvoker(method).invoke(proxy, method, args, this.sqlSession);
           } catch (Throwable var5) {
               throw ExceptionUtil.unwrapThrowable(var5);
           }
       }
   ```

   1. mapper中的姓名和性别，赋给了args数组：args = {"zhangsan", 'm'}
   2. 底层有一个**sortedMap**，存储注解中每一个的信息：
      1. 0 - > name
      2. 1 -> sex
   3. 如果mybatis发现有注解，将会创建一个**paramMap**：
      1. paramMap的key：sortedMap的value，paramMap的value：args[i]
      2. "name" -> "zhangsan"

5. 
My first Mybatis program
----
1. resources: 资源文件、配置文件，等同于放到了类的根路径下

2. 开发步骤：
   1. 打包方式:jar
   2. 引入依赖:mvn repo
   3. 编写Mybatis核心XML：mybatis-config.xml
      1. 约定俗成
      2. 位置在resource
      3. 修改连接数据库信息
   4. 编写XxxMapper.xml
   5. 在Mybatis-config.xml中指定Mapper.xml的路径
   6. 编写Mybatis程序，使用指定类库
      1. 执行sql语句：sqlSession，是一个Java程序和数据库之间的一次会话
      2. 需要先获取sqlSessionFactory对象，进而通过sqlSession工厂获取Session对象
      3. 如何获取sqlSession Factory
         1. 先获取sqlSessionFactoryBuilder对象，再调用sqlSessionFactoryBuilder的build对象
      4. MyBatis的核心对象：sqlSessionFactoryBuilder --> sqlSessionFactory --> sqlSession

3. 从XML构建SqlSessionFactory
   1. 重要对象
   2. 需要使用XML
   3. XML:配置文件

4. 两个主要配置文件：

   1. Mybatis-config.xml：连接数据库的信息等（一个）
   2. XxxMapper.xml:编写SQL语句的配置文件（一个表一个）

5. 第一个程序的细节：

   1. sql语句结尾分号可以省略

   2. Resource.getResourceAsStream

      ```java
      InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
      ```

      

      1. 大部分是从根路径下开始查找
      2. 移植性很强
      3. 

   3. 如果自己new一个Stream：

      ```java
      InputStream is = new FileInputStream("/Users/yuantengyan/IdeaProjects/mybatis/mybatis-001-introduction/src/main/resources/mybatis-config.xml");
      
      ```

      可移植性太差：程序不够健壮，可能移植到其他的操作系统中

   4. **通过系统的类加载器**：

      ```java
      InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
      ```

      1. 从类路径加载配置文件
      2. 实际上就是Resources.getResourceAsStream的**底层实现**

   5. 已验证

      1. 核心配置文件名字可以自定义
      2. 不一定在根路径下，最好放到类路径下

   6. mapper

      1. ```xml
         <mapper resource="CarMapper.xml"/>
         ```

      2. ```xml
         <mapper url="file:///Users/yuantengyan/IdeaProjects/mybatis/mybatis-001-introduction/src/main/resources/CarMapper.xml" />
         
         ```

6. MyBatis事务管理机制

   1. ```xml
      <transactionManager type="JDBC"/>
      ```

   2. JDBC

      1. mybatis自己管理事务，使用jdbc代码管理事务

      2. ```java
         conn.setAutoCommit(false)
           ...
         conn.commit()
         ```

      3. 底层创建的是JDBC的事务管理器对象

      4. 

   3. MANAGED

      1. ```xml
         <transactionManager type="MANAGED"/>
         ```

      2. mybatis不负责任务管理，交给其他部分，如果没人管，表示事务没有开启

   4. JDBC如果不设置自动提交为false，就会自动提交

   5. 只要设置autoCommit，就是没有开启事务

7. MyBatis集成日志组件

   1. 常见的Mybatis支持的日志组件：

      1. SLF4j
      2. log4j
      3. log4j2
      4. STDOUT_LOGGING

   2. ```xml
      <settings>
      	<setting name="logImpl" value="STDOUT_LOGGING"/>
      </settings>	
      ```

   3. 集成logback日志

      1. SLF4j标准实现

      2. 引入logback依赖

         ```xml
         <dependency>
         	<groupId>ch.qos.logback</groupId>
         	<artifactId>logback-classic</artifactId>
         	<version>1.4.5</version>
         	<scope>test</scope>
         </dependency>
         ```

         

      3. 引入logback配置文件

         1. 必须叫**logback.xml**或**logback-test.xml**
         2. 必须在根目录下
# Mybatis配置文件

### environments的default标签

1. 默认加载的sql数据库环境

2. 想要切换

   ```java
               SqlSessionFactory mybatisdb = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "mybatisdb");
   
   ```

   

### DataSource

1. datasource可以借助property配置动态的数据库设置

   ```xml
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="${jdbc.driver}"/>
                   <property name="url" value="${jdbc.url}"/>
                   <property name="username" value="${jdbc.username}"/>
                   <property name="password" value="${jdbc.password}"/>
               </dataSource>
           </environment>
   ```

   ```xml
       <properties>
           <property name="jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
           <property name="jdbc.url" value="jdbc:mysql://localhost:3306/Mybatis"/>
           <property name="jdbc.username" value="root"/>
           <property name="jdbc.password" value="12345678"/>
       </properties>
       <properties resource="jdbc.properties" />
   ```

### 针对mapper.xml

1. 使用时记得**namespace.id**
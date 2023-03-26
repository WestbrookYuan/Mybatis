package com.yty.godbatis.core;

import com.yty.godbatis.pojo.MappedStatement;
import com.yty.godbatis.transactions.Transaction;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Map;

/**
 * @author yty
 * @version 1.0
 * @since 1.0
 */
public class SqlSession {
    private SqlSessionFactory sqlSessionFactory;

    public SqlSession() {
    }
    public SqlSession(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public int insert(String sqlid, Object pojo){

        int count = 0;
        try {
            Connection connection = sqlSessionFactory.getTransaction().getConnection();
            String godbatisSql = sqlSessionFactory.getMappedStatements().get(sqlid).getSql();
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println(sql);
            int fromindex = 0;
            int index = 1;
            while (true){
                int sharpIndex =  godbatisSql.indexOf("#", fromindex);
                if (sharpIndex < 0){
                    break;
                }

                int youkuohao = godbatisSql.indexOf("}", fromindex);
                String propertyName = godbatisSql.substring(sharpIndex + 2, youkuohao).trim();
                fromindex = youkuohao + 1;
                String getMethodName = "get"+propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                Method getMethod = pojo.getClass().getDeclaredMethod(getMethodName);
                Object propertyValue = getMethod.invoke(pojo);
                ps.setString(index, propertyValue.toString());
                index ++;
            }
            count = ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return count;
    }

    public Object selectOne(String sqlid, Object parameter){
        Object obj = null;
        try {
            Connection connection = sqlSessionFactory.getTransaction().getConnection();
            String godbatisSql = sqlSessionFactory.getMappedStatements().get(sqlid).getSql();
            String resultType = sqlSessionFactory.getMappedStatements().get(sqlid).getResultType();
            System.out.println(resultType);
            String sql = godbatisSql.replaceAll("#\\{[a-zA-Z0-9_$]*}", "?");
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, parameter.toString());
            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                Class<?> resultTypeClass = Class.forName(resultType);
                obj = resultTypeClass.newInstance();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String propertyName = rsmd.getColumnName(i+1);
                    String setMethodName = "set"+ propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
                    Method setMethod = resultTypeClass.getDeclaredMethod(setMethodName, String.class);
                    setMethod.invoke(obj, rs.getString(propertyName));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
    public void commit(){
        sqlSessionFactory.getTransaction().commit();
    }

    public void rollback(){
        sqlSessionFactory.getTransaction().rollback();
    }

    public void close(){
        sqlSessionFactory.getTransaction().close();
    }


}

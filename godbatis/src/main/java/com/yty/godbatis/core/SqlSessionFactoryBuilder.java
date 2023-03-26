package com.yty.godbatis.core;


import com.yty.godbatis.consts.Const;
import com.yty.godbatis.datasource.JndiDataSource;
import com.yty.godbatis.datasource.PooledDataSource;
import com.yty.godbatis.datasource.UnPooledDataSource;
import com.yty.godbatis.pojo.MappedStatement;
import com.yty.godbatis.transactions.Transaction;
import com.yty.godbatis.transactions.impl.JdbcTransaction;
import com.yty.godbatis.transactions.impl.ManagedTransaction;
import com.yty.godbatis.utils.Resources;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yty
 * @since 1.0
 * @version 1.0
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactoryBuilder(){}
    public SqlSessionFactory build(InputStream in) {
        SqlSessionFactory factory = null;
        try {

            SAXReader reader = new SAXReader();
            Document document = reader.read(in);

            Element environments = (Element) document.selectSingleNode("/configuration/environments");
            String defaultId = environments.attributeValue("default");
            Element environment = (Element) document.selectSingleNode("/configuration/environments/environment[@id='" + defaultId + "']");


            Element transactionManagerElt = environment.element("transactionManager");
            Element dataSourceElt = environment.element("dataSource");

            List<Node> nodes = document.selectNodes("//mapper");
            List<String> sqlMapperXMLPathList = new ArrayList<>();

            nodes.forEach(node -> {
                Element mapper = (Element) node;
                String resource = mapper.attributeValue("resource");
                sqlMapperXMLPathList.add(resource);
            });



            DataSource dataSource = getDataSource(dataSourceElt);
            Transaction transaction = getTransaction(transactionManagerElt, dataSource);
            Map<String, MappedStatement> mappedStatements = getMappedStatements(sqlMapperXMLPathList);


            factory = new SqlSessionFactory(transaction, mappedStatements);
        } catch (Exception e) {
            e.printStackTrace();
        }




        return factory;
    }


    /**
     *
     * @param sqlMapperXMLPathList
     * @return mapped Statements
     */
    private Map<String, MappedStatement> getMappedStatements(List<String> sqlMapperXMLPathList) {
        Map<String, MappedStatement> mappedStatements = new HashMap<>();
        sqlMapperXMLPathList.forEach(sqlMapperXMLPath ->{

            try {
                SAXReader reader = new SAXReader();
                System.out.println(sqlMapperXMLPath);
                Document document = reader.read(Resources.getResourceAsStream(sqlMapperXMLPath));
                Element mapper = (Element) document.selectSingleNode("mapper");
                String namespace = mapper.attributeValue("namespace");
                List<Element> elements = mapper.elements();
                elements.forEach(element ->{
                    String id = element.attributeValue("id");
                    String sqlid = namespace + "." + id;
                    String resultType = element.attributeValue("resultType");
                    String sql = element.getText().trim();
                    MappedStatement mappedStatement = new MappedStatement(sql, resultType);
                    mappedStatements.put(sqlid, mappedStatement);
                });

            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });
        return mappedStatements;
    }

    /**
     * 获取事务管理器
     * @param transactionManagerElt
     * @param dataSource
     * @return Transaction
     */
    private Transaction getTransaction(Element transactionManagerElt, DataSource dataSource) {
        String type = transactionManagerElt.attributeValue("type").trim().toLowerCase();
        Transaction transaction = null;
        if (type.equals(Const.JDBC_TRANSACTION)){
            transaction = new JdbcTransaction(dataSource, false);
        }
        if (type.equals(Const.MANAGED_TRANSACTION)){
            transaction = new ManagedTransaction();
        }
        return transaction;
    }

    /**
     * 获取数据源
     * @param dataSourceElt
     * @return dataSource
     */
    private DataSource getDataSource(Element dataSourceElt) {
        Map<String, String> map = new HashMap<>();
        String type = dataSourceElt.attributeValue("type").trim().toLowerCase();
        DataSource dataSource = null;
        List<Element> propertyElts = dataSourceElt.elements("property");
        propertyElts.forEach(propertyElt ->{
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            map.put(name, value);
        });
        if (type.equals(Const.UN_POOLED_DATASOURCE)) {
            dataSource = new UnPooledDataSource(map.get("driver"), map.get("url"), map.get("username"), map.get("password"));
        }

        if (type.equals(Const.POOLED_DATASOURCE)) {
            dataSource = new PooledDataSource();
        }

        if(type.equals(Const.JNDI_DATASOURCE)){
            dataSource = new JndiDataSource();
        }
        return dataSource;
    }

}

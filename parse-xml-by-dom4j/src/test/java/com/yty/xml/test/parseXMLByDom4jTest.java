package com.yty.xml.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class parseXMLByDom4jTest {
    @Test
    public void testParseMyBatisConfigXML() throws Exception{
        SAXReader reader = new SAXReader();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        Document document = reader.read(is);

        // get Environments element
        String xPath = "/configuration/environments";
        Element environments = (Element) document.selectSingleNode(xPath);
        String defaultEnvironmentId = environments.attributeValue("default");

        // get Environment element
        xPath = "/configuration/environments/environment[@id='"+defaultEnvironmentId+"']";
        Element environment = (Element) document.selectSingleNode(xPath);

        // get transactionManager element
        Element transactionManager = environment.element("transactionManager");
        String transactionManagerType = transactionManager.attributeValue("type");
        System.out.println("transaction manager type: " + transactionManagerType);

        // get datasource element
        Element dataSource = environment.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println("data source type: " + dataSourceType);

        // get all child elements of the data source
        List<Element> elements = dataSource.elements();
        elements.forEach(element -> {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            System.out.println(name + ": " + value);
        });

        // get all mappers
        xPath = "//mapper";
        List<Node> mappers = document.selectNodes(xPath);
        mappers.forEach(mapper -> {
            Element mapperElement = (Element) mapper;
            String resource = mapperElement.attributeValue("resource");
            System.out.println(resource);
        });

    }


    @Test
    public void testSqlMapper() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("CarMapper.xml");
        Document document = reader.read(is);

        String xPath = "/mapper";
        Element mapper = (Element) document.selectSingleNode(xPath);
        String namespace = mapper.attributeValue("namespace");
        System.out.println(namespace);
        List<Element> elements = mapper.elements();
        elements.forEach(element -> {

            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            System.out.println(id);
            System.out.println(resultType);


            String batisQuery = element.getText().strip();
            String jdbcQuery = batisQuery.replaceAll("#\\{[0-9A-Za-z$]*}", "?");
            System.out.println(jdbcQuery);
        });
    }
}

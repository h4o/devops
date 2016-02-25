package com.mnt2.xmlAnalyzer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLAnalyzer {
    public static void main(String[] args) {
        System.out.println("Running Parser");
        String DIR_PATH = System.getProperty("user.dir");
        try {
            /*Class c = Class.forName("org.apache.xerces.parsers.SAXParser");
            XMLReader reader = (XMLReader) c.newInstance();*/
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            System.out.println("Parsing "+DIR_PATH + "/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml");
            parser.parse(DIR_PATH + "/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml", handler);
            System.out.println("File parsed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

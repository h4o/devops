package com.mnt2.xmlAnalyzer;

import org.apache.commons.io.FileUtils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XMLAnalyzer {

    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        String DIR_PATH = System.getProperty("user.dir");
        parser.parse(DIR_PATH+"/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml");
    }

}

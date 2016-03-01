package com.mnt2.xmlAnalyzer;

import org.apache.commons.io.FileUtils;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class XMLAnalyzer {

    public static void main(String[] args) {

        System.out.println("Running Parser");
        String DIR_PATH = System.getProperty("user.dir");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            System.out.println("Parsing "+DIR_PATH + "/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml");
            parser.parse(DIR_PATH + "/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml", handler);

        } catch (Exception e) {
            System.out.println(e);
        }
        String HTML_PATH_NEW = System.getProperty("user.dir")+"/HTMLGenerated/index_gen.html";



        File htmlTemplateFile = new File(HTML_PATH_NEW);
        String htmlString = null;
        try {
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace
        String nbMutantKilled = "" + ((NbMutants.nbFailed() / NbMutants.getNbMutants()) * 100);
        htmlString = htmlString.replace("$NBMUTANTS", nbMutantKilled);
        htmlString = htmlString.replace("$TABLEBODY", "");
        File newHtmlFile = new File(HTML_PATH_NEW);

        // Rewrite new html
        try {
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package com.mnt2.xmlAnalyzer;

public class XMLAnalyzer {
    public static void main(String[] args) {

        String DIR_PATH = System.getProperty("user.dir");
        String HTML_PATH = DIR_PATH+"/HTMLGenerated/";

        IParseTest parser = new XMLParser();
        IGenerator generator = new GeneratorHTML(parser.parse(DIR_PATH+"/sample/output/tests/TEST-0.xml"),HTML_PATH);
        generator.generateMutant(parser.parse(DIR_PATH+"/sample/output/tests/TEST-1.xml"));
        generator.generateMutant(parser.parse(DIR_PATH+"/sample/output/tests/TEST-0.xml"));
        generator.generateMutant(parser.parse(DIR_PATH+"/sample/output/tests/TEST-2.xml"));
        generator.generateReport();
    }
}
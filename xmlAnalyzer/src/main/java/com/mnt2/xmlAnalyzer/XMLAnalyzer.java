package com.mnt2.xmlAnalyzer;

public class XMLAnalyzer {
    public static void main(String[] args) {
        IParseTest parser = new XMLParser();
        String DIR_PATH = System.getProperty("user.dir");
        String HTML_PATH = DIR_PATH+"/HTMLGenerated/";
        IGenerator generator = new GeneratorHTML(parser.parse(DIR_PATH+"/sample/TEST-0.xml"),HTML_PATH);
        generator.generateMutant(parser.parse(DIR_PATH+"/sample/TEST-1.xml"));

    }
}
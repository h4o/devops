package com.mnt2.xmlAnalyzer;

public class XMLAnalyzer {
    public static void main(String[] args) {
        IParseTest parser = new XMLParser();
        String DIR_PATH = System.getProperty("user.dir");
        String HTML_PATH = DIR_PATH+"/HTMLGenerated/";
        /*IGenerator generator = new GeneratorHTML(HTML_PATH);
        parser.parse(DIR_PATH+"/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml");
        generator.generateMutant(parser.parse(DIR_PATH+"/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml"),
             );
*/
    }
}

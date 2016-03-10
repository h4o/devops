package com.mnt2.xmlAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLAnalyzer {
    public static void main(String[] args) {

        String DIR_PATH = System.getProperty("user.dir");
        String HTML_PATH = DIR_PATH+"/HTMLGenerated/";

        IParseTest parser = new XMLParser();

        // Get content of a folder and store each file name
        File f = new File(DIR_PATH+"/sample/output/tests/");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));

        // On parse le rapport du code sans mutation (mutateur vide appliqué)
        IGenerator generator = new GeneratorHTML(parser.parse(DIR_PATH+"/sample/output/tests/TEST-0.xml"),HTML_PATH);
        // On enlève le fichier de la liste
        names.remove("TEST-0.xml");

        // Pour chaque fichier on le parse et on prépare le rapport HTML
        for (String testReportFile : names) {
            generator.generateMutant(parser.parse(DIR_PATH+"/sample/output/tests/"+testReportFile));
        }

        // On finalise le rapport
        generator.generateReport();
    }
}
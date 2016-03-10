package com.mnt2.xmlAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLAnalyzer {
    public static void main(String[] args) {

        String DIR_PATH = System.getProperty("user.dir");
        String HTML_PATH = DIR_PATH+"/HTMLGenerated/";
        String test = "TEST-fr.unice.polytech.ogl.islbd.map.BiomeTest.xml";

        IParseTest parser = new XMLParser();

        // Get content of a folder and store each folder name
        File f = new File(DIR_PATH+"/sample/output/tests/");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));

        // On parse le rapport du code sans mutation (mutateur vide appliqué)
        IGenerator generator = new GeneratorHTML(parser.parseFolderXML(DIR_PATH+"/sample/output/tests/mutant-0/"),HTML_PATH);
        // On enlève le fichier de la liste
        names.remove("mutant-0");

        // Pour chaque fichier on le parse et on prépare le rapport HTML
        for (String mutantFolder : names) {
            generator.generateMutant(parser.parseFolderXML(DIR_PATH+"/sample/output/tests/"+mutantFolder));
        }

        // On finalise le rapport
        generator.generateReport();
    }
}
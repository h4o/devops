package com.mnt2.xmlAnalyzer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class XMLAnalyzer2 {
    public static void main(String[] args) {
        System.out.println("Running Parser");
        SAXBuilder builder = new SAXBuilder();
        String DIR_PATH = System.getProperty("user.dir");

        try {
            File xmlFile = new File(DIR_PATH + "/sample/target/surefire-reports/TEST-com.mnt2.sample.TestClassTest.xml");
            Document document = builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List tests = rootNode.getChildren("testcase");
            Iterator i = tests.iterator();
            while(i.hasNext())
            {
                //On recrée l'Element courant à chaque tour de boucle afin de
                //pouvoir utiliser les méthodes propres aux Element comme :
                //sélectionner un nœud fils, modifier du texte, etc...
                Element courant = (Element)i.next();
                //On affiche le nom de l’élément courant
                System.out.println("Test "+i.toString());
                System.out.println(courant.getChild("failure").getText());
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
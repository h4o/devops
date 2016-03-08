package com.mnt2.xmlAnalyzer;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class XMLParser implements IParse {
    private int nbSuccess = 0;
    private int nbMutant= 0;

    public XMLParser() {

    }

    /**
     * Parse un fichier
     * @param filePath chemin du fichier du rapport XML à Parse
     */
    public List<TestReport> parse(String filePath) {
        List<TestReport> reports = new List<>();
        String localHTML = new String();
        File inputFile = new File(filePath);
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(inputFile);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element classElement = document.getRootElement();
        List<Element> testcases = classElement.getChildren("testcase");
        System.out.println("-----------");
        for (int temp = 0; temp < testcases.size(); temp++) {
            Element testcase = testcases.get(temp);
            System.out.println("\nCurrent testcase :"
                    + testcase.getName());
            Attribute attribute =  testcase.getAttribute("classname");
            System.out.println("Classname : "
                    + attribute.getValue() );
            Attribute attributeName =  testcase.getAttribute("name");
            System.out.println("testname : "
                    + attributeName.getValue() );
        }
    }

    /**
     * parse tout les fichiers associés aux strings contenues par la list passée en paramètre
     * @param list renvoie un HashMap associant un mutant à une list de rapport de tests
     */
    public HashMap<Integer, List<TestReport>> parse(List<String> list) {

        return null;
    }


    public void newRowHTML(String in, String classe, String statut, String classeTest, String testName) {
        in+="<tr class=\""+classe+"\"><td class=\""+statut+"</td><td>"+classeTest+"</td><td>"+testName+"</td></tr>";
    }

    public void newMutantHTML(String in, ) {
        in+="<h2 class=\"page-header"></h2><table class="table table-striped"><thead><tr><th>Statut</th><th>#Mutant</th><th>Classe de Test</th><th>Test</th></tr></thead><tbody>
    }
}

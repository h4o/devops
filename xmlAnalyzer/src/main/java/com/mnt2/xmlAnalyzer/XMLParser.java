package com.mnt2.xmlAnalyzer;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLParser implements IParseTest {
    private int nbSuccess = 0;
    private int nbMutant= 0;

    public XMLParser() {

    }

    /**
     * Parse un fichier
     * @param filePath chemin du fichier du rapport XML à Parse
     */
    public List<TestReport> parse(String filePath) {
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
        List<TestReport> testList = new ArrayList<>();
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
            Element failures = testcase.getChild("failure");
            String content = new String();
            if (failures != null) {
                content = failures.getContent().toString();
                testList.add(new TestReport(TestStatusEnum.FAILED,attribute.getValue(), attributeName.getValue(), content));
            }
            testList.add(new TestReport(TestStatusEnum.SUCCEED,attribute.getValue(), attributeName.getValue(), content));
        }
        return null;
    }

}
package com.mnt2.xmlAnalyzer;

import org.apache.commons.io.FileUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;

/**
 * Classe utilisée pour gérer les évènements émis par SAX lors du traitement de fichiers XML
 */
class XMLHandler extends DefaultHandler
{
    private static final String HTML_PATH_INIT = System.getProperty("user.dir")+"/HTMLGenerated/index.html";
    private static final String HTML_PATH_NEW = System.getProperty("user.dir")+"/HTMLGenerated/index_gen.html";
    private String tagCourant = "";
    private String content = "";
    private int numMutants = 0;
    private boolean fail = false;
    private String testName = "";
    /**
     * Actions à réaliser lors de la détection d'un nouvel élément.
     */
    public void startElement(String nameSpace, String localName,
                             String qName, Attributes attr) throws SAXException  {
        tagCourant = localName;
        if (tagCourant.equals("testcase")) {
            testName = "<td>"+numMutants+"</td><td>"+attr.getValue("classname")+"</td><td>"+attr.getValue("name")+"</td>";
        }
        if (tagCourant.equals("failure")) {
            if (!fail) {
                fail = true;
                NbMutants.incFailedMutants(numMutants);
            }
            content += "<tr class=\"danger\"><td class=\"echec_txt\">Echec</td>"+testName+"</tr>";
        }
    }

    /**
     * Actions à réaliser lors de la détection de la fin d'un élément.
     */
    public void endElement(String nameSpace, String localName,
                           String qName) throws SAXException {
        if (tagCourant.equals("failure") ){
        }
        if (tagCourant.equals("testcase") ){
            if(!fail) {
                content+="<tr class=\"success\"><td>Réussit</td>"+testName+"</tr>";
            }
        }
        tagCourant = "";

    }

    /**
     * Actions à réaliser au début du document.
     */
    public void startDocument() {
        numMutants = NbMutants.incMutants();
        content = "<h2 class=\"page-header\">Mutant #"+numMutants+"</h2>";
        content += "<table class=\"table table-striped\"><thead><tr><th>Statut</th><th>#Mutant</th><th>Classe de Test</th><th>Test</th></tr></thead><tbody>";
        System.out.println("Start Document");
    }

    /**
     * Actions à réaliser lors de la fin du document XML.
     */
    public void endDocument() {
        // Get template file
        content+="</tbody></table>";
        File htmlTemplateFile = new File(HTML_PATH_INIT);
        String htmlString = null;
        try {
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace
        htmlString = htmlString.replace("$TABLEBODY", content+"$TABLEBODY");
        File newHtmlFile = new File(HTML_PATH_NEW);

        // Rewrite new html
        try {
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
    }

    /**
     * Actions à réaliser sur les données
     */
    public void characters(char[] caracteres, int debut,
                           int longueur) throws SAXException {
        String donnees = new String(caracteres, debut, longueur);

        if (tagCourant.equals("failure")) {
            if(!Character.isISOControl(caracteres[debut])) {
                //content+= donnees;
            }
        }
    }
}

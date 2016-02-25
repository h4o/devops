package com.mnt2.xmlAnalyzer;

import java.util.*;

import com.sun.org.apache.xpath.internal.SourceTreeManager;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Classe utilisée pour gérer les évènements émis par SAX lors du traitement de fichiers XML
 */
public class XMLHandler extends DefaultHandler {
    private String currentTag = "";

    /**
     * Action à réaliser lors de la détection d'un nouvel élément
     */
    public XMLHandler() {
        super();
    }

    public void startElement(String namespace, String localName, String qName, Attributes attr) throws SAXException {

    }
    public void endElement(String namespace, String localName, String qName) throws SAXException {
        currentTag = "";
        System.out.println("End Tag "+localName);
    }
}

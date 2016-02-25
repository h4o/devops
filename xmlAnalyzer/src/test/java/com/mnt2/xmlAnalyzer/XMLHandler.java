package com.mnt2.xmlAnalyzer;

import java.util.*;

import com.sun.org.apache.xpath.internal.SourceTreeManager;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Classe utilisée pour gérer les évènements émis par SAX lors du traitement de fichiers XML
 */
class TestSAX2Handler extends DefaultHandler
{
    private String tagCourant = "";

    /**
     * Actions à réaliser lors de la détection d'un nouvel élément.
     */
    public void startElement(String nameSpace, String localName,
                             String qName, Attributes attr) throws SAXException  {
        tagCourant = localName;
        System.out.println("debut tag : " + localName);
    }

    /**
     * Actions à réaliser lors de la détection de la fin d'un élément.
     */
    public void endElement(String nameSpace, String localName,
                           String qName) throws SAXException {
        tagCourant = "";
        System.out.println("Fin tag " + localName);
    }

    /**
     * Actions à réaliser au début du document.
     */
    public void startDocument() {
        System.out.println("Debut du document");
    }

    /**
     * Actions à réaliser lors de la fin du document XML.
     */
    public void endDocument() {
        System.out.println("Fin du document");
    }

    /**
     * Actions à réaliser sur les données
     */
    public void characters(char[] caracteres, int debut,
                           int longueur) throws SAXException {
        String donnees = new String(caracteres, debut, longueur);

        if (!tagCourant.equals("")) {
            if(!Character.isISOControl(caracteres[debut])) {
                System.out.println("   Element " + tagCourant +",
                        valeur = *" + donnees + "*");
            }
        }
    }
}

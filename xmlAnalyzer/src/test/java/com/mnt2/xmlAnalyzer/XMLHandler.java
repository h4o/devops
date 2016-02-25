package com.mnt2.xmlAnalyzer;

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Classe utilisée pour gérer les évènements émis par SAX lors du traitement de fichiers XML
 */
class XMLHandler extends DefaultHandler
{
    private String tagCourant = "";

    /**
     * Actions à réaliser lors de la détection d'un nouvel élément.
     */
    public void startElement(String nameSpace, String localName,
                             String qName, Attributes attr) throws SAXException  {
        tagCourant = localName;
        if (tagCourant.equals("testcase")) {
            System.out.println("{ " +
                    "\n\tTag : " + localName +
                    "\n\tclassname : "+attr.getValue("classname")+
                    "\n\tname : " + attr.getValue("name") + "\n}");

        }
    }

    /**
     * Actions à réaliser lors de la détection de la fin d'un élément.
     */
    public void endElement(String nameSpace, String localName,
                           String qName) throws SAXException {
        tagCourant = "";
        if (tagCourant.equals("testcase") ){
            System.out.println("/Tag : " + localName);
        }

    }

    /**
     * Actions à réaliser au début du document.
     */
    public void startDocument() {
        System.out.println("Start Document");
    }

    /**
     * Actions à réaliser lors de la fin du document XML.
     */
    public void endDocument() {
        System.out.println("End of Document");
    }

    /**
     * Actions à réaliser sur les données
     */
    public void characters(char[] caracteres, int debut,
                           int longueur) throws SAXException {
        String donnees = new String(caracteres, debut, longueur);

        if (!tagCourant.equals("")) {
            if(!Character.isISOControl(caracteres[debut])) {
                System.out.println("   Element " + tagCourant +", valeur = *" + donnees + "*");
            }
        }
    }
}

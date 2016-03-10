package com.mnt2.mutationFramework;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 10/03/16.
 */
public class ConfigurationReader  {
    private SelectorType selectorType;
    private Selector selector;
    private HashMap<String, List<String>> modifiers;
    private String outputDir;

    public void readConfiguration(String filename,boolean readModifiers){
        modifiers = new HashMap<>();
        SAXBuilder builder = new SAXBuilder();
        try {
            File f = new File("./config/config.xml");
            Document document = builder.build(f);
            Element rootNode = document.getRootElement();

            selectorType = SelectorType.valueOf(rootNode.getAttributeValue("type"));
            switch (selectorType) {
                case RANDOM:
                    int chance = Integer.valueOf(rootNode.getAttribute("value").getValue());
                    selector = new RandomSelector(chance);
                    break;
                case ONESHOT:
                    selector = new OneShotSelector();
                    break;
                case ALWAYS:
                    selector = new AlwaysSelector();
                    break;
            }
            outputDir = rootNode.getAttributeValue("output");
            if(readModifiers) {
                List modifierList = rootNode.getChildren("modifier");
                Iterator i = modifierList.iterator();
                while (i.hasNext()) {

                    Element current = (Element) i.next();
                    Element before = current.getChild("before");
                    Element after = current.getChild("after");
                    List<String> afterValues = new ArrayList<>();

                    Iterator values = after.getChildren().iterator();
                    while (values.hasNext()) {
                        afterValues.add(((Element) values.next()).getText());
                    }
                    values = before.getChildren().iterator();
                    while (values.hasNext()) {
                        modifiers.put(((Element) values.next()).getText(), afterValues);
                    }
                    System.out.println(modifiers);
                }
            }

        } catch (IOException io){
            io.printStackTrace();
        } catch (JDOMException jde){
            jde.printStackTrace();
        }
    }

    public Selector getSelector(){
        return selector;
    }

    public List<String> getModifiers(String value){
        if(modifiers.containsKey(value))
            return modifiers.get(value);
        return null;
    }

    public String getOutputDir(){
        return outputDir;
    }


}

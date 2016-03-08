package com.mnt2.mutationFramework;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import spoon.processing.AbstractProcessor;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.jdom.input.SAXBuilder;
/**
 * Created by Fabien VICENTE on 25/02/16.
 */
public abstract class AbstractMutator  extends AbstractProcessor<CtElement> {
    private String report;
    protected Map<String,List<String>> modifiers;
    @Override
    public  void init(){
        modifiers = new HashMap<>();
        parseConfig();
        System.out.println("init");
        report = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        report = "<processor name=\""+this.getClass().getName()+"\"";
        super.init();
    }


    private void parseConfig(){
        SAXBuilder builder = new SAXBuilder();
        try {
            File f = new File("./config/config.xml");
            Document document = builder.build(f);
            Element rootNode = document.getRootElement();
            List modifierList = rootNode.getChildren("modifier");
            Iterator i = modifierList.iterator();
            while(i.hasNext()){

                Element current = (Element)i.next();
                Element before = current.getChild("before");
                Element after = current.getChild("after");
                //List<String> beforeValues = new ArrayList<>();
                List<String> afterValues = new ArrayList<>();

                Iterator values = after.getChildren().iterator();
                while(values.hasNext()){
                    afterValues.add(((Element)values.next()).getText());
                }
                values = before.getChildren().iterator();
                while(values.hasNext()){
                    modifiers.put(((Element)values.next()).getText(),afterValues);
                }
                System.out.println(modifiers);
            }


        } catch (IOException io){
            io.printStackTrace();
        } catch (JDOMException jde){
            jde.printStackTrace();
        }
    }

    @Override
    public void processingDone(){
        report +="</processor>";
        saveReport();
        super.processingDone();
    }








    public void addModification(String before, String after, SourcePosition pos){
        if(before.equals(after))
            return;
        try {
            report += "<modification file=\"" + pos.getFile().getCanonicalPath() +
                    "\" line=\""+pos.getLine()+"\" column=\""+pos.getColumn()+"\">";
            report += "<before>"+before+"</before>";
            report += "<after>"+after+"</after>";
            report += "</modification>";
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void saveReport(){
        try {
            File f = new File("./target/mutationframework");
            f.mkdirs();
            PrintWriter writer = new PrintWriter("./target/mutationframework/"+this.getClass().getName()+".xml",
                    "UTF-8");
            writer.println(report);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}


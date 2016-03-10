package com.mnt2.mutationFramework;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import spoon.processing.AbstractProcessor;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

import java.io.*;
import java.util.*;
/**
 * Created by Fabien VICENTE on 25/02/16.
 */
public abstract class AbstractMutator  extends AbstractProcessor<CtElement> {

    private String report;
    protected Map<String,List<String>> modifiers;
    protected int chance = 0;
    private  Selector selector;
    protected Random random;
    private int hashCodeToChange = -1;
    private List<Integer> changedHashCode;

    @Override
    public  void init(){
        random = new Random();
        modifiers = new HashMap<>();
        parseConfig();
        System.out.println("init");
        report = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        report = "<processor name=\""+this.getClass().getName()+"\">";

        super.init();
    }
    @Override
    public boolean isToBeProcessed(CtElement element){
        if(selector == Selector.RANDOM){
            return random.nextInt(100) > chance;
        } else if(selector == Selector.ONESHOT){
            CtClass classToChange = element.getParent(CtClass.class);
            if(classToChange != null){
                System.out.println("class hashcode:"+classToChange.hashCode());
                if(hashCodeToChange == -1 && !changedHashCode.contains(classToChange.hashCode())){
                    hashCodeToChange = classToChange.hashCode();
                    changedHashCode.add(classToChange.hashCode());
                    writeHashCodes();

                } else if(classToChange.hashCode() == hashCodeToChange) {
                    return true;
                }
                return false;
            }
           // System.out.println(element.getParent(CtClass.class).hashCode());
        }
        return false;
    }

    private void writeHashCodes(){
        ObjectOutputStream oos = null;
        File file;
        try{
            file =  new File(".tmp") ;
             oos =  new ObjectOutputStream(new FileOutputStream(file)) ;
            oos.writeObject(changedHashCode) ;


        } catch (IOException io){

        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHashCodes(){
        ObjectInputStream oos = null;
        File file;
        try{
            file =  new File(".tmp") ;
            oos =  new ObjectInputStream(new FileInputStream(file)) ;
            changedHashCode = (List<Integer>)oos.readObject() ;

        } catch (IOException io){
            changedHashCode = new ArrayList<>();
        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        finally {
            if(oos !=null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseConfig(){
        SAXBuilder builder = new SAXBuilder();
        try {
            File f = new File("./config/config.xml");
            Document document = builder.build(f);
            Element rootNode = document.getRootElement();

            String type = rootNode.getAttribute("type").getValue();
            if(type.equalsIgnoreCase("random")) {
                chance = Integer.valueOf(rootNode.getAttribute("value").getValue());
                selector = Selector.RANDOM;
            } else{
                selector = Selector.ONESHOT;
                readHashCodes();
            }
            List modifierList = rootNode.getChildren("modifier");
            Iterator i = modifierList.iterator();
            while(i.hasNext()){

                Element current = (Element)i.next();
                Element before = current.getChild("before");
                Element after = current.getChild("after");
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


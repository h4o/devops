package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Fabien VICENTE on 25/02/16.
 */
public abstract class AbstractMutator  extends AbstractProcessor<CtElement> {
    private String report;

    @Override
    public  void init(){
        System.out.println("init");
        report = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        report = "<processor name=\""+this.getClass().getName()+"\"";
        super.init();
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


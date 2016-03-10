package com.mnt2.mutationFramework;

import spoon.reflect.cu.SourcePosition;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by user on 10/03/16.
 */
public class Reporter {
    private String report;
    private String filename;
    private String folder;
    public Reporter(String position,String name){
        filename = name;
        folder = position;
        report = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        report += "<processor name=\""+this.getClass().getName()+"\">";


    }

    public void report(String before, String after, SourcePosition pos){
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


    public void saveReport(){
        report += "</processor>";
        try {
            File f = new File(folder);
            f.mkdirs();
            PrintWriter writer = new PrintWriter(folder+filename, "UTF-8");
            writer.println(report);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

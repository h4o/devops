package com.mnt2.mutationFramework;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/03/16.
 */
public class OneShotSelector implements Selector {
    private List<Integer> changedHashCode;
    private int hashCodeToChange;

    public OneShotSelector(){
        readHashCodes();
    }
    @Override
    public boolean isToBeProcessed(CtElement element) {

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
}

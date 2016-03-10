package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.declaration.CtClassImpl;

import java.util.Random;

/**
 * Created by user on 10/03/16.
 */
public class CRTInterfaceMutator extends AbstractProcessor<CtMethod> {

    private Random random;
    private ConfigurationReader reader;
    private Selector selector;
    private Reporter reporter;

    @Override
    public void init(){
        reader = new ConfigurationReader();
        reader.readConfiguration("./config/config.xml",true);
        selector = reader.getSelector();
        random = new Random();
        reporter = new Reporter(reader.getOutputDir(),this.getClass().getCanonicalName()+".xml");
        super.init();
    }

    @Override
    public void process(CtMethod ctMethod) {

        /* Replace the declared type by one of its interface */
        // Find the declaration of the objects belonging to the project package
        ctMethod.getElements(new TypeFilter<>(CtVariableReference.class)).stream().filter(ctVariableReference -> ctVariableReference.getDeclaration() != null
                && ctVariableReference.getDeclaration().getType() != null
                && ctVariableReference.getDeclaration().getType().getPackage() != null
                && ctVariableReference.getParent(CtClassImpl.class) != null
                && ctVariableReference.getDeclaration().getType().getPackage().toString().contains(ctVariableReference.getParent(CtClassImpl.class).getPackage().toString())).forEach(ctVariableReference -> {

            // Check if the found object implents an interface
            if (!ctVariableReference.getDeclaration().getType().getSuperInterfaces().isEmpty()) {

                // Retrieve the first interface found
                Object[] ref = ctVariableReference.getDeclaration().getType().getSuperInterfaces().toArray();

                // Replace the declaration type with the interface
                ctVariableReference.getDeclaration().setType((CtTypeReference) ref[0]);
            }
        });

    }

    @Override
    public void processingDone(){
        reporter.saveReport();
    }
}

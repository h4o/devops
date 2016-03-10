package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.declaration.CtClassImpl;

import java.util.Random;
import java.util.Set;

/**
 * Created by user on 10/03/16.
 */
public class ANDMutator extends AbstractProcessor<CtMethod> {

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

        // Check if the method has a parent class implemented (useful to know the package)
        if(ctMethod.getParent(CtClassImpl.class) != null) {

            // Loop through the CtInvocation elements of the method
            for (CtInvocation ctInvocation : ctMethod.getElements(new TypeFilter<>(CtInvocation.class))
                    ) {

                // Check the referenced types of the called method
                for (CtTypeReference ctTypeReference:ctInvocation.getReferencedTypes()
                     ) {

                    // If one of the referenced types is part of the package, it's a method created (its not the best way for sure...)
                    if(ctTypeReference.getPackage() != null && ctTypeReference.getPackage().toString().contains(ctMethod.getParent(CtClassImpl.class).getPackage().toString())){
                        // If the called method has arguments, the first one is removed
                        if(!ctInvocation.getArguments().isEmpty()){
                            ctInvocation.getArguments().remove(0);
                        }
                        break;
                    }
                }


            }
        }
    }

    @Override
    public void processingDone(){
        reporter.saveReport();
    }
}

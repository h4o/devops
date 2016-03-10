package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.Random;

/**
 * Created by user on 09/03/16.
 */
public class ABSMutator extends AbstractProcessor<CtClass> {

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
    public boolean isToBeProcessed(CtClass candidate) {
        return selector.isToBeProcessed(candidate);
    }

    @Override
    public void process(CtClass ctClass) {
        List<CtBinaryOperator> bops = ctClass.getElements(new TypeFilter<>(CtBinaryOperator.class));

        for (CtBinaryOperator cbo:bops
             ) {
            cbo.getElements(new TypeFilter<>(CtVariableAccess.class)).forEach(
                    ctVariableAccess -> ctVariableAccess.replace(
                            getFactory().Code().createCodeSnippetExpression(
                                    "Math.abs("+ctVariableAccess+")"
                            )));
        }

    }

    @Override
    public void processingDone(){
        reporter.saveReport();
    }
}

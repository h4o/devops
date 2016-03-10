package com.mnt2.mutationFramework;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtMethod;

import java.util.Random;

/**
 * Created by user on 08/03/16.
 */
public class OverrideMethodRemovalMutator extends AbstractAnnotationProcessor<Override, CtMethod> {

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
    public void process(Override override, CtMethod ctMethod) {
        ctMethod.delete();
    }

    @Override
    public void processingDone(){
        reporter.saveReport();
    }
}

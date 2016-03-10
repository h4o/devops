package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fabien VICENTE on 12/02/16.
 */
public class UnaryOperatorMutator extends AbstractProcessor<CtUnaryOperator> {
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
    public boolean isToBeProcessed(CtUnaryOperator candidate){
        return selector.isToBeProcessed(candidate);
    }

    @Override
    public void processingDone(){
        reporter.saveReport();
    }

    public void process(CtUnaryOperator op){
        List<String> modifiers = reader.getModifiers(op.getKind().toString());
        if(modifiers != null){
            List<String> kinds = new ArrayList<>(modifiers);
            kinds.remove(op.getKind());
            UnaryOperatorKind kind;
            kind = UnaryOperatorKind.valueOf(kinds.get(random.nextInt(kinds.size())));
            reporter.report(op.getKind().name(),kind.name(),op.getPosition());
            op.setKind(kind);
        }
    }





}

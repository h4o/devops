package com.mnt2.mutationFramework;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 25/02/16.
 */
public class LogicalOperatorMutator extends AbstractProcessor<CtBinaryOperator> {
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
    public boolean isToBeProcessed(CtBinaryOperator candidate){
        return selector.isToBeProcessed(candidate);
    }


    @Override
    public void process(CtBinaryOperator op) {

        List<String> kinds = new ArrayList<>(reader.getModifiers(op.getKind().toString()));
        if(kinds != null){
            kinds.remove(op.getKind());
            BinaryOperatorKind kind;
            kind = BinaryOperatorKind.valueOf(kinds.get(random.nextInt(kinds.size())));
            reporter.report(op.getKind().name(), kind.name(), op.getPosition());
            op.setKind(kind);
        }
    }


}

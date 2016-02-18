package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;

/**
 * Created by Fabien VICENTE on 12/02/16.
 */
public class UnaryOperatorMutator extends AbstractProcessor<CtElement> {
    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return candidate instanceof CtUnaryOperator;
    }



    public void process(CtElement candidate){
        if(!isToBeProcessed(candidate)){
            return;
        }
        CtUnaryOperator op = (CtUnaryOperator) candidate;
        op.setKind(UnaryOperatorKind.POSTDEC);
    }
}

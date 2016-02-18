package com.mnt2.mutationFramework;

import com.sun.xml.internal.stream.buffer.AbstractProcessor;
import spoon.reflect.declaration.CtElement;

/**
 * Created by user on 12/02/16.
 */
public class UnaryOperatorMutator extends AbstractProcessor<CtElement> {
    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return true;
    }


    @Override
    public void process(CtElement candidate){

    }
}

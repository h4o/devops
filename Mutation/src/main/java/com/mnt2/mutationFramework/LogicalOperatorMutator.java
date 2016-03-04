package com.mnt2.mutationFramework;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 25/02/16.
 */
public class LogicalOperatorMutator extends AbstractMutator{
    Random random;

    @Override
    public void init(){
        random = new Random();
        super.init();
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return candidate instanceof CtBinaryOperator;
    }


    @Override
    public void process(CtElement ctElement) {

        CtBinaryOperator op = (CtBinaryOperator) ctElement;
        if(modifiers.containsKey(op.getKind().toString())){
           List<String> kinds = modifiers.get(op.getKind().toString());
           kinds.remove(op.getKind());
           BinaryOperatorKind kind;
            kind = BinaryOperatorKind.valueOf(kinds.get(random.nextInt(kinds.size())));
           addModification(op.getKind().name(),kind.name(),op.getPosition());
           op.setKind(kind);
        }
    }


}

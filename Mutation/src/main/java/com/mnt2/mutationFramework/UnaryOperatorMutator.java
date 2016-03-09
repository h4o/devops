package com.mnt2.mutationFramework;

import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.declaration.CtElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fabien VICENTE on 12/02/16.
 */
public class UnaryOperatorMutator extends AbstractMutator {
    private Random random;

    @Override
    public void init(){
        random = new Random();
        super.init();
    }


    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return candidate instanceof CtUnaryOperator;
    }



    public void process(CtElement candidate){
        if(!isToBeProcessed(candidate)){
            return;
        }
       // candidate.getParent(CtClass.class).getSimpleName().equals("LE NOM DE MA CLASSE QUE JE VEUX MUTER");
        CtUnaryOperator op = (CtUnaryOperator) candidate;
        if(modifiers.containsKey(op.getKind().toString())){
            List<String> kinds = modifiers.get(op.getKind().toString());
            kinds.remove(op.getKind());
            UnaryOperatorKind kind;
            kind = UnaryOperatorKind.valueOf(kinds.get(random.nextInt(kinds.size())));
            addModification(op.getKind().name(),kind.name(),op.getPosition());
            op.setKind(kind);
        }
    }





}

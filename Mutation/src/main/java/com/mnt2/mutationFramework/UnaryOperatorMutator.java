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
        CtUnaryOperator op = (CtUnaryOperator) candidate;
        List<UnaryOperatorKind> operators = new ArrayList<>();
        operators.add(UnaryOperatorKind.POSTDEC);
        operators.add(UnaryOperatorKind.PREDEC);
        operators.add(UnaryOperatorKind.POSTINC);
        operators.add(UnaryOperatorKind.PREINC);
        operators.remove(op.getKind());
        UnaryOperatorKind kind = operators.get(random.nextInt(operators.size()));
        addModification(op.getKind().name(),kind.name(),op.getPosition());
        op.setKind(kind);


    }


}

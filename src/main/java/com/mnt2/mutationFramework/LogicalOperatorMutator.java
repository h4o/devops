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
    }

    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return candidate instanceof CtBinaryOperator;
    }


    @Override
    public void process(CtElement ctElement) {
        if(!isToBeProcessed(ctElement))
            return;
        CtBinaryOperator op = (CtBinaryOperator) ctElement;
        switch (op.getKind()){
            case AND:
            case OR:
                processBooleanOperator(op);
                break;
            case EQ:
            case GE:
            case GT:
            case LE:
            case LT:
            case NE:
                processComparisonOperator(op);
                break;
        }
    }

    private void processBooleanOperator(CtBinaryOperator op){
        BinaryOperatorKind kind = BinaryOperatorKind.AND;
        if(op.getKind().equals(kind)){
            kind = BinaryOperatorKind.OR;
        }
        addModification(op.getKind().name(),kind.name(),op.getPosition());
        op.setKind(kind);
    }

    private void processComparisonOperator(CtBinaryOperator op){
        List<BinaryOperatorKind> operatorLists = new ArrayList<>();
        operatorLists.add(BinaryOperatorKind.EQ);
        operatorLists.add(BinaryOperatorKind.GE);
        operatorLists.add(BinaryOperatorKind.GT);
        operatorLists.add(BinaryOperatorKind.LE);
        operatorLists.add(BinaryOperatorKind.LT);
        operatorLists.add(BinaryOperatorKind.NE);
        operatorLists.remove(op.getKind());
        BinaryOperatorKind kind = operatorLists.get(random.nextInt(operatorLists.size()));
        addModification(op.getKind().name(),kind.name(),op.getPosition());
        op.setKind(kind);
    }
}

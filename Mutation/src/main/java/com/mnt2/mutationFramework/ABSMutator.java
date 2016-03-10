package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

/**
 * Created by user on 09/03/16.
 */
public class ABSMutator extends AbstractProcessor<CtClass> {
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
}

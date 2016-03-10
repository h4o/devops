package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.ReferenceFilter;
import spoon.reflect.visitor.filter.InvocationFilter;
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

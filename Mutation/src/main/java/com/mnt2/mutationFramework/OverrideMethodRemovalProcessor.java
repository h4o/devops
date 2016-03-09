package com.mnt2.mutationFramework;

import org.apache.log4j.Level;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AnnotationFilter;
import spoon.support.reflect.declaration.CtElementImpl;

import java.util.List;

/**
 * Created by user on 08/03/16.
 */
public class OverrideMethodRemovalProcessor extends AbstractAnnotationProcessor<Override, CtMethod> {

    @Override
    public void process(Override override, CtMethod ctMethod) {
        ctMethod.delete();
    }
}

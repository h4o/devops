package com.mnt2.mutationFramework;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtMethod;

/**
 * Created by user on 08/03/16.
 */
public class OverrideMethodRemovalMutator extends AbstractAnnotationProcessor<Override, CtMethod> {

    @Override
    public void process(Override override, CtMethod ctMethod) {
        ctMethod.delete();
    }
}

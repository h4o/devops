package com.mnt2.mutationFramework;

import spoon.reflect.declaration.CtElement;

import spoon.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by cazala on 08/03/16.
 */
public class EmptyMutator extends AbstractProcessor<CtElement> {


    @Override
    public void process(CtElement ctElement) {

    }
}

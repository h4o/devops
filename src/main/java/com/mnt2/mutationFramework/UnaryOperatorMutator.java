package com.mnt2.mutationFramework;

import spoon.processing.AbstractProcessor;
import spoon.processing.ProcessorProperties;
import spoon.reflect.code.CtUnaryOperator;
import spoon.reflect.code.UnaryOperatorKind;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabien VICENTE on 12/02/16.
 */
public class UnaryOperatorMutator extends AbstractMutator {



    @Override
    public boolean isToBeProcessed(CtElement candidate){
        return candidate instanceof CtUnaryOperator;
    }



    public void process(CtElement candidate){
        if(!isToBeProcessed(candidate)){
            return;
        }
        CtUnaryOperator op = (CtUnaryOperator) candidate;
        addModification(op.getKind().name(),UnaryOperatorKind.POSTDEC.name(),op.getPosition());
        op.setKind(UnaryOperatorKind.POSTDEC);


    }


}

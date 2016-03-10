package com.mnt2.mutationFramework;

import spoon.reflect.declaration.CtElement;

/**
 * Created by user on 10/03/16.
 */
public interface Selector {
    boolean isToBeProcessed(CtElement element);
}

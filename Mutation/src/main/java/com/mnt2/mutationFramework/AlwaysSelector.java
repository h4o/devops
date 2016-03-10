package com.mnt2.mutationFramework;

import spoon.reflect.declaration.CtElement;

/**
 * Created by user on 10/03/16.
 */
public class AlwaysSelector implements Selector {
    @Override
    public boolean isToBeProcessed(CtElement element) {
        return true;
    }
}

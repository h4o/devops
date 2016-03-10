package com.mnt2.mutationFramework;

import spoon.reflect.declaration.CtElement;

import java.util.Random;

/**
 * Created by user on 10/03/16.
 */
public class RandomSelector implements Selector {
    private Random r;
    private int chance;
    public RandomSelector(int chance){
        r = new Random();
        this.chance = chance;
    }

    @Override
    public boolean isToBeProcessed(CtElement element) {
        return r.nextInt(100)>chance;
    }
}

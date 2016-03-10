package com.mnt2.sample;

/**
 * Created by user on 10/03/16.
 */
public class ExtendedTestClass extends TestClass {
    @Override
    public int increment(int i){
        i += 10;
        dumb(i);
        return i;
    }

    public int dumb(int i){
        return i;
    }
}

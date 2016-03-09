package com.mnt2.sample;

/**
 * Created by user on 08/03/16.
 */
public class ExtendedTestClass extends TestClass {
    @Override
    public int increment(int i){
        i += 10;
        return i;
    }

    public int dumb(int i){
        return i;
    }
}

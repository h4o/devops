package com.mnt2.sample;

/**
 * Created by user on 10/03/16.
 */
public class EvilPlan {

    public static void main(String [] args){
        MonsieurM m1 = new MonsieurM();
        m1.beEvil();
        m1.talk();

        MonsieurM m2 = new MonsieurM();
        m2.beEvil(66);

        MonsieurM m3 = new MonsieurM();
        m3.talk();

        MonsieurM m4 = new MonsieurM();
        m4.beEvil(57,4);
    }
}

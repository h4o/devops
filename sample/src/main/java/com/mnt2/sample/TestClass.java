package com.mnt2.sample;


public class TestClass {
    public int increment(int i){
        i++;
        return i;
    }
    public int decrement(int i) {
        i--;
        return i;
    }

    public boolean testOperateursLogique(int a, int b, int c){
        return a > b && c <= a;
    }

    public int testAutres(int a, int b){
        if(a < b){
            a++;
        } else {
            b++;
        }

        return Math.max(a,b);
    }
}

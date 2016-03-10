package com.mnt2.sample;

/**
 * Created by user on 10/03/16.
 */
public class MonsieurM extends Raptor implements Talking {
    @Override
    protected void beEvil() {
        System.out.println("Building a wall...");
    }

    @Override
    protected void beEvil(int percentage) {
        System.out.println("Percentage of desperation among students : "+percentage+"%");
    }

    @Override
    protected void beEvil(int nbKilled, int nbAlive) {
        System.out.println("Number of students killed : "+nbKilled);
        System.out.println("Number of students alive : "+nbAlive);
        System.out.println("Efficiency of the wall : "+(nbKilled/(nbKilled+nbAlive))*100);
    }

    @Override
    public void talk() {
        System.out.println("Êtes-vous tolérants face à la critique ?");
    }
}

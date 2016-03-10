package com.mnt2.sample;

/**
 * Created by user on 10/03/16.
 */
public class MonsieurM extends Raptor implements Talking {
    @Override
    protected String beEvil() {
        return "Building a wall...";
    }

    @Override
    protected String beEvil(int percentage) {
        return "Percentage of desperation among students : "+percentage+"%";
    }

    @Override
    protected String beEvil(int nbKilled, int nbAlive) {
        return "Number of students killed : "+nbKilled+" ; " +
                "Number of students alive : "+nbAlive+" ; " +
                "Efficiency of the wall : "+(nbKilled/(nbKilled+nbAlive))*100+"%";
    }

    @Override
    public String talk() {
        return "Etes vous tolerants face a la critique ?";
    }
}

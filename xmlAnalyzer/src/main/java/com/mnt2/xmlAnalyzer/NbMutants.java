package com.mnt2.xmlAnalyzer;

import java.util.ArrayList;
/**
 * Created by cazala on 25/02/16.
 */
public final class NbMutants {
    private static int nbMutants = 0;
    private static ArrayList<Integer> failed = new ArrayList<>();
    public static int incMutants () {
        return ++nbMutants;
    }
    public static void resetMutants() {
        nbMutants = 0;
        failed = new ArrayList<>();
    }
    public static int getNbMutants() {
        return nbMutants;
    }

    public static void incFailedMutants(int id) {
        failed.add(new Integer(id));
    }

    public static int nbFailed () {
        return failed.size();
    }
}

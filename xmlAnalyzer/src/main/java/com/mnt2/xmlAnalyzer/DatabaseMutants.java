package com.mnt2.xmlAnalyzer;

import java.util.ArrayList;
/**
 * Created by cazala on 25/02/16.
 */
public final class DatabaseMutants {
    private static DatabaseMutants ourInstance = new DatabaseMutants();

    public static DatabaseMutants getInstance() {
        return ourInstance;
    }

    private DatabaseMutants() {
    }

    private static int nbSuccess = 0;
    private static int nbFailed = 0;
    private static ArrayList<Integer> failed = new ArrayList<>();

    public static int incSucc () {
        nbSuccess++;
        return nbSuccess+nbFailed;
    }
    public static int incFailed() {
        nbFailed++;
        return nbSuccess+nbFailed;
    }
    public static int getNbMutants() {
        return nbFailed+nbSuccess;
    }
    public static int getNbSuccess() { return nbSuccess;}
    public static int getNbFailed() { return nbFailed;}
}

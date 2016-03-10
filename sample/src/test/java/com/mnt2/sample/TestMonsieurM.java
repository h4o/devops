package com.mnt2.sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 10/03/16.
 */
public class TestMonsieurM {
    private MonsieurM monsieurM;

    @Before
    public void setUp() throws Exception {
        monsieurM = new MonsieurM();
    }

    @Test
    public void testBeEvilNoParam() throws Exception {
        assertEquals("Building a wall...",monsieurM.beEvil());
    }

    @Test
    public void testBeEvilOneParam() throws Exception {
        assertEquals("Percentage of desperation among students : 10%",monsieurM.beEvil(10));
    }

    @Test
    public void testBeEvilTwoParams() throws Exception {
        assertEquals("Number of students killed : 10 ; " +
                "Number of students alive : 0 ; " +
                "Efficiency of the wall : 100%",monsieurM.beEvil(10,0));
    }

    @Test
    public void testTalk() throws Exception {
        assertEquals("Etes vous tolerants face a la critique ?",monsieurM.talk());
    }

}

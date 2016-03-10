package com.mnt2.sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 08/03/16.
 */
public class TestExtendedTestClass {
    private ExtendedTestClass etc;

    @Before
    public void setUp() throws Exception {
        etc = new ExtendedTestClass();
    }

    @Test
    public void testIncrement() throws Exception {
        assertEquals(14,etc.increment(4));
    }

    @Test
    public void testDumb() throws Exception {
        assertEquals(42, etc.dumb(42));
    }
}

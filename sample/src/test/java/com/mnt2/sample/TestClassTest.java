package com.mnt2.sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 18/02/16.
 */
public class TestClassTest {
    private TestClass testClass;

    @Before
    public void setUp() throws Exception {
        testClass = new TestClass();
    }

    @Test
    public void testIncrement() throws Exception {
        assertEquals(4,testClass.increment(3));
    }

    @Test
    public void testDecrement() throws Exception {
        assertEquals(2,testClass.decrement(3));
    }
}
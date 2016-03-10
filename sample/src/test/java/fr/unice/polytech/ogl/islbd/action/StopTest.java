package fr.unice.polytech.ogl.islbd.action;

import junit.framework.TestCase;

import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;

public class StopTest extends TestCase {

	public StopTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of a stop (toString should return a proper JSon string)
	 */
	@Test
	public void testStopInitialization() {
		Stop stop= new Stop();
		String expected= "{\"action\":\"stop\"}";
		
		assertEquals(expected, stop.toString());
	}
}
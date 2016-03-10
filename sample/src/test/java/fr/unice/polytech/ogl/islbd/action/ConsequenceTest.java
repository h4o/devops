package fr.unice.polytech.ogl.islbd.action;

import junit.framework.TestCase;
import org.junit.Test;

public class ConsequenceTest extends TestCase{

	public ConsequenceTest(String method) {
		super(method);
	}
	
	/**
	 * Test on hasSucceeded()
	 */
	@Test
	public void testHasSucceeded() {
		Consequence consequenceSuccess = new Consequence("{\"cost\":7,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"OK\"}");
		Consequence consequenceFail = new Consequence("{\"cost\":0,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"KO\"}");
		
		assertTrue(consequenceSuccess.hasSucceeded());
		assertFalse(consequenceFail.hasSucceeded());		
	}
	
	/**
	 * Test on GetCost()
	 */
	@Test
	public void testGetCost() {
		Consequence consequenceSuccess = new Consequence("{\"cost\":42,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"OK\"}");
		Consequence consequenceFail = new Consequence("{\"cost\":42,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"KO\"}");
		
		assertEquals(42, consequenceSuccess.getCost());
		assertEquals(0, consequenceFail.getCost());
	}


	
	

}

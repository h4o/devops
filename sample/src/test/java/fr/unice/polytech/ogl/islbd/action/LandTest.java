package fr.unice.polytech.ogl.islbd.action;

import junit.framework.TestCase;

import org.junit.Test;

public class LandTest extends TestCase {

	public LandTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of a land (toString should return a proper JSon string)
	 */
	@Test
	public void testLandInitialization() {
		Land land = new Land("creek0" , 30);
		String expected = "{\"action\":\"land\",\"parameters\":{\"creek\":\"creek0\",\"people\":30}}";
		assertEquals(expected, land.toString());
	}
	
	/**
	 * Test getCreek()
	 */
	@Test
	public void testGetCreek(){
		Land land = new Land("C1" , 30);
		assertEquals("C1", land.getCreek());
	}
	
	/**
	 * Test getPeople()
	 */
	@Test
	public void testGetPeople(){
		Land land = new Land("C1" , 30);
		assertEquals(30, land.getPeople());
	}
	
	/**
	 * Test land if the creek is null
	 */
	@Test
	public void testGetCreekNull(){
		Land land = new Land(null , 30);
		assertNull(land.getCreek());
	}

}

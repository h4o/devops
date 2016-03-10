package fr.unice.polytech.ogl.islbd.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.map.Biome;
import fr.unice.polytech.ogl.islbd.objective.Amount;
import fr.unice.polytech.ogl.islbd.objective.AmountCondPair;
import fr.unice.polytech.ogl.islbd.objective.Cond;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

public class GlimpseTest  extends TestCase {

	public GlimpseTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of a Glimpse (toString should return a proper JSon string)
	 */
	@Test
	public void testGlimpseInitialization() {
		Glimpse glimpse = new Glimpse(Direction.N, 2);
		
		String expected = "{\"action\":\"glimpse\",\"parameters\":{\"range\":2,\"direction\":\"N\"}}";
		assertEquals(expected, glimpse.toString());
	}
	
	/**
	 * Test of getDirection 
	 */
	@Test
	public void testGetDirection() {
		Glimpse glimpse = new Glimpse(Direction.E, 2);

		assertEquals(Direction.E, glimpse.getDirection());
	}
	
	/**
	 * Test of getRange
	 *
	 */
	@Test
	public void testGetRange() {
		Glimpse glimpse = new Glimpse(Direction.E, 1);
		int expected = 1;
		
		assertEquals(expected, glimpse.getRange());
	}
	
	/**
	 * Test of getDirection with null
	 */
	@Test
	public void testGetDirectionNull() {
		Glimpse glimpse = new Glimpse(null, 1);
		Direction expected= Direction.N;
		
		assertEquals(expected, glimpse.getDirection());
	}
	
	/**
	 * Test of getRange with range too big 
	 *
	 */
	@Test
	public void testGetRangeBig() {
		Glimpse glimpse = new Glimpse(Direction.E, 5);
		int expected = 4;
		
		assertEquals(expected, glimpse.getRange());
	}
	
	/**
	 * Test of getRange with range too small 
	 *
	 */
	@Test
	public void testGetRangeSmall() {
		Glimpse glimpse = new Glimpse(Direction.E, 0);
		int expected = 1;
		
		assertEquals(expected, glimpse.getRange());
	}
	
	/**
	 * Test of getBiomeInfo
	 *
	 */
	@Test
	public void testGetBiomeInfo() {
		Glimpse glimpse = new Glimpse(Direction.E, 4);
		glimpse.setConsequence(new Consequence("{ \"status\": \"OK\", \"cost\": 12, \"extras\": {\"asked_range\": 4,\"report\": [[[\"MANGROVE\", 80.0], [\"BEACH\", 20.0]], [[\"MANGROVE\", 40.0], [\"TROPICAL_RAIN_FOREST\", 40.0], [\"TROPICAL_SEASONAL_FOREST\", 20.0]], [\"TROPICAL_RAIN_FOREST\", \"TROPICAL_SEASONAL_FOREST\"], [\"TROPICAL_RAIN_FOREST\"]]}}"));
		
		List<Map<Biome, Double>> expectedInfos = new ArrayList<Map<Biome, Double>>();
        
		Map<Biome, Double> case1=new HashMap<>();
		case1.put(Biome.BEACH, 20.0);
		case1.put(Biome.MANGROVE, 80.0);
		expectedInfos.add(case1);
		
		Map<Biome, Double> case2=new HashMap<>();
		case2.put(Biome.TROPICAL_SEASONAL_FOREST, 20.0);
		case2.put(Biome.MANGROVE, 40.0);
		case2.put(Biome.TROPICAL_RAIN_FOREST, 40.0);
		expectedInfos.add(case2);
		
		Map<Biome, Double> case3=new HashMap<>();
		case3.put(Biome.TROPICAL_SEASONAL_FOREST, null);
		case3.put(Biome.TROPICAL_RAIN_FOREST, null);
		expectedInfos.add(case3);
		
		Map<Biome, Double> case4=new HashMap<>();
		case4.put(Biome.TROPICAL_RAIN_FOREST, null);
		expectedInfos.add(case4);
        
		assertEquals(expectedInfos, glimpse.getBiomeInfo());
	}
}

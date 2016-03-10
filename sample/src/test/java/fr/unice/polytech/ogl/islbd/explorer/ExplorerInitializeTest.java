package fr.unice.polytech.ogl.islbd.explorer;

import fr.unice.polytech.ogl.islbd.Explorer;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExplorerInitializeTest {

	private Explorer explorer;
	private String exptdCreek;
	private long exptdBudget;
	private long exptdMen;
	private Map<BasicResource, Long> exptdObjectives;

	public ExplorerInitializeTest() {
		exptdCreek = "creek_id";
		exptdBudget = 600;
		exptdMen =50;
	}
	
	@Before
	public void setup() {
		explorer = new Explorer();
		exptdObjectives = new HashMap<>();
	}

	/**
	 * Simple test that initialize the Memory with a creek, a budget, some men and an objective.
	 */
	@Test
	public void testSimpleInitialize() {
		String msg = "{ \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [ { \"resource\": \"WOOD\", \"amount\": 600 } ] }";
		
		exptdObjectives.put(BasicResource.WOOD, (long) 600);
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
	
	/**
	 * Test when there is no objective.
	 */
	@Test
	public void testNoObjectiveInitialize(){
		String msg = "{ \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [] }";
				
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
	
	/**
	 * Test when there is several objectives.
	 */
	@Test
	public void testMultipleObjectivesInitialize(){
		String msg = "{ \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [{ \"resource\": \"WOOD\", \"amount\": 600 },{ \"resource\": \"QUARTZ\", \"amount\": 15 }] }";
		
		exptdObjectives.put(BasicResource.WOOD, (long) 600);
		exptdObjectives.put(BasicResource.QUARTZ, (long) 15);
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}

	/**
	 * Test when there is an additional unknown parameter.
	 */
	@Test
	public void testUnknownParameterInitialize(){
		String msg = "{ \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [{ \"resource\": \"WOOD\", \"amount\": 600 }], \"unknown\":[12,13,-1] }";
		
		exptdObjectives.put(BasicResource.WOOD, (long) 600);
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
	
	/**
	 * Test when there is an unknown objective in the objectives list.
	 */
	@Test
	public void testUnknownObjectiveInitialize(){
		String msg = "{ \"creek\": \"creek_id\", \"budget\": 600, \"men\": 50, \"objective\": [{ \"resource\": \"WOOD\", \"amount\": 600 },{\"resource\":\"THING\", \"amount\": 60020}] }";
		
		exptdObjectives.put(BasicResource.WOOD, (long) 600);
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
	
	/**
	 * Test the parameters "budget", "men" and "objective" with a value of Long.MAX_VALUE
	 */
	@Ignore @Test
	public void testMaxValueInitialize(){
		long maxVal = Long.MAX_VALUE;
		String msg = "{ \"creek\": \"creek_id\", \"budget\":"+maxVal+", \"men\":"+maxVal+", \"objective\": [{ \"resource\": \"WOOD\", \"amount\":"+ maxVal +"}] }";
		
		exptdBudget = maxVal;
		exptdMen = maxVal;
		exptdObjectives.put(BasicResource.WOOD, maxVal);
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
	
	/**
	 * Test what happen if the Json is empty (even if it's almost impossible !)
	 */
	@Ignore @Test
	public void testEmptyJsonInitialize(){
		String msg = "{}";
		
		exptdBudget = 0;
		exptdCreek = "";
		exptdMen = 0;
		
		explorer.initialize(msg);

		Memory m = explorer.getMemory();

		assertEquals(exptdCreek, m.getInitialCreek());
		assertEquals(exptdBudget, m.getInitialBudget());
		assertEquals(exptdMen, m.getInitialMen());
		assertEquals(exptdObjectives, m.getResourceNeeded());
	}
}

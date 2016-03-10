package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.Explorer;
import fr.unice.polytech.ogl.islbd.action.Action;
import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.action.Move;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemoryTest {

	@Test
	public void rememberActionTest() {
		Memory mem = new Memory();
		Action a = new Move(Direction.N);
		mem.rememberAction(a);
		try {
			mem.rememberConsequences("{\"cost\": 10, \"extras\": {}, \"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(mem.getLastAction(),a);
		assertEquals(mem.getNumberAction(a.getName()), 1);
		Action b = new Move(Direction.N);
		mem.rememberAction(a);
		try {
			mem.rememberConsequences("{\"cost\": 10, \"extras\": {}, \"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(mem.getLastAction(),b);
		assertEquals(mem.getNumberAction(a.getName()), 2);
	}
	@Test
	public void avgCostToStopTest() {
		Explorer explorer = new Explorer();
		String initialize = "{\"creek\": \"f56c51f3-4bd8-4d44-a589-d6059ee5cd93\",\"men\": 25,\"budget\":100,\"objective\": [{\"amount\": 2,\"resource\": \"FLOWER\"},{\"amount\": 600,\"resource\": \"FUR\"},{\"amount\": 3000,\"resource\": \"SUGAR_CANE\"}]}";
		explorer.initialize(initialize);
		explorer.takeDecision();
		Action a = new Move(Direction.N);
		explorer.getMemory().rememberAction(a);
		String ackno = "{\"cost\": 10, \"extras\": {}, \"status\": \"OK\"}";
		explorer.acknowledgeResults(ackno);
		assertEquals(explorer.getMemory().getAverageCost("move_to"),10);
		explorer.getMemory().rememberAction(new Move(Direction.N));
		//assertEquals(explorer.getMemory().get)
		ackno = "{\"cost\": 50, \"extras\": {}, \"status\": \"OK\"}";
		explorer.acknowledgeResults(ackno);
		assertEquals(explorer.getMemory().getAverageCost("move_to"),30);
	}
	
	@Test
	public void rememberInitialDataTest() {
		Memory mem = new Memory();
		try {
			mem.rememberInitialData("{    \"creek\": \"ea30e70f-6e29-4088-a32f-4b60faddf543\",    \"men\": 50,    \"budget\": 600,    \"objective\": [{\"amount\": 600,\"resource\": \"WOOD\"}]}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(mem.getInitialCreek(),"ea30e70f-6e29-4088-a32f-4b60faddf543" );
		assertEquals(mem.getInitialBudget(),600);
		assertEquals(mem.getInitialMen(),50);
		Map<BasicResource, Long> resourceObj = new HashMap<BasicResource,Long>();
		resourceObj.put(BasicResource.WOOD,(long) 600);
		assertEquals(mem.getResourceNeeded(),resourceObj);
	}
	
	@Test
	
	public void getPercentCompletedTest() {
		Memory mem = new Memory();
		try {
			mem.rememberInitialData("{    \"creek\": \"ea30e70f-6e29-4088-a32f-4b60faddf543\",    \"men\": 50,    \"budget\": 600,    \"objective\": [{\"amount\": 1000,\"resource\": \"WOOD\"}]}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mem.rememberAction(new Exploit(BasicResource.WOOD));
		String ackno1 = "{\"cost\": 7,\"extras\": {\"amount\": 600},\"status\": \"OK\"}";
		try {
			mem.rememberConsequences(ackno1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertEquals(mem.getAmountCollected(BasicResource.WOOD),600);
		Map<BasicResource, Long> resourceObj = new HashMap<BasicResource,Long>();
		resourceObj.put(BasicResource.WOOD,(long) 1000);
		assertEquals(mem.getResourceNeeded(), resourceObj);
		assertTrue(mem.getResourceNeeded().containsKey(BasicResource.WOOD));
		assertEquals(mem.getResourceNeeded().get(BasicResource.WOOD),(long) 1000, 0.0);
		assertEquals((mem.getAmountCollected(BasicResource.WOOD) * 100) / mem.getResourceNeeded().get(BasicResource.WOOD).longValue() , 60.0,0.001);
		//assertEquals((mem.getPercentCompleted(Resource.WOOD)), 60.0, 0.0001);
		
		mem.rememberAction(new Exploit(BasicResource.WOOD));
		String ackno2 = "{\"cost\": 7,\"extras\": {\"amount\": 400},\"status\": \"OK\"}";
		try {
			mem.rememberConsequences(ackno2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double full = 60.0;
		//assertEquals(mem.getPercentCompleted(Resource.WOOD), full,0.01);
	}
}

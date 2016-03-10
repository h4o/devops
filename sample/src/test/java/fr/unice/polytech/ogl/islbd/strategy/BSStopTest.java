package fr.unice.polytech.ogl.islbd.strategy;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;

import fr.unice.polytech.ogl.islbd.Explorer;

public class BSStopTest extends TestCase {
	private int budgetActuel;
	
	public BSStopTest() {
		budgetActuel = 100;
	}

	@Test
	public void testNoBudget() {
		Explorer explorer = new Explorer();
		String initialize = "{\"creek\": \"f56c51f3-4bd8-4d44-a589-d6059ee5cd93\",\"men\": 25,\"budget\":"+budgetActuel+",\"objective\": [{\"amount\": 2,\"resource\": \"FLOWER\"},{\"amount\": 600,\"resource\": \"FUR\"},{\"amount\": 3000,\"resource\": \"SUGAR_CANE\"}]}";
		explorer.initialize(initialize);
		explorer.takeDecision();
		assertEquals(explorer.getMemory().getLastAction().getName(),"land");
		String ackno = "{\"cost\": 99, \"extras\": {}, \"status\": \"OK\"}";
		explorer.acknowledgeResults(ackno);
		explorer.takeDecision();
		assertEquals(explorer.getMemory().getLastAction().getName(),"stop");
	}
	/*
	@Test
	public void noRessourceToExploitTest() {
		Explorer explorer = new Explorer();
		String initialize = "{\"creek\": \"f56c51f3-4bd8-4d44-a589-d6059ee5cd93\",\"men\": 25,\"budget\":"+budgetActuel+",\"objective\": [{\"amount\": 2,\"resource\": \"FLOWER\"},{\"amount\": 600,\"resource\": \"FUR\"}]}";
		explorer.initialize(initialize);
		explorer.takeDecision(); // Land
		assertEquals(explorer.getMemory().getLastAction().getName(),"land");
		String ackno = "{\"cost\": 1, \"extras\": {}, \"status\": \"OK\"}";
		explorer.acknowledgeResults(ackno);
		explorer.takeDecision();
		assertNotEquals(explorer.getMemory().getLastAction().getName(),"stop");
		
	}*/

}

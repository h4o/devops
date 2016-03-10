package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.*;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Action;
import fr.unice.polytech.ogl.islbd.action.Consequence;
import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.action.Land;
import fr.unice.polytech.ogl.islbd.action.Move;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

public class BSShouldILand {
	Memory memory;
	BehaviourSimple behaviour;
	Consequence consequences;
	String cons;
	
	@Before
	public void setUp() {
		String iniData = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": [{\"amount\": 1000,\"resource\": \"WOOD\"},{\"amount\": 1000,\"resource\": \"FISH\"}]}";
		memory = new Memory();
		
		try {
			memory.rememberInitialData(iniData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		behaviour = new BehaviourSimple(memory);
		/*consequences= new Consequence("{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]] },\"status\"\"OK\"}");
		cons= "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]] },\"status\"\"OK\"}";
*/	}
	
	@Test
	public void testNotGoodAction(){
		assertNull(behaviour.shouldILand());	
		memory.rememberAction(new Move(Direction.N));
		assertNull(behaviour.shouldILand());	
	}
	
	@Test 
	public void testNoFish() {
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		assertNull(behaviour.shouldILand());	
	}
	
	@Test
	public void testNotEnoughFish() {
		memory.rememberAction(new Exploit(BasicResource.FISH));
		try {
			memory.rememberConsequences("{ \"cost\":4,\"extras\": {\"amount\": 100},\"status\": \"OK\"}");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertNull(behaviour.shouldILand());	
	}
	
	@Test
	public void testEnoughFish() {
		memory.rememberAction(new Exploit(BasicResource.FISH));
		try {
			memory.rememberConsequences("{ \"cost\":4,\"extras\": {\"amount\": 1000},\"status\": \"OK\"}");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals("land", behaviour.shouldILand().getName());
	}
}

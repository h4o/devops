package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.*;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.action.Land;
import fr.unice.polytech.ogl.islbd.action.Move;
import fr.unice.polytech.ogl.islbd.action.Scout;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.memory.Memory;

public class BSComputePonderation {

	private Memory memory;
	private BehaviourSimple behaviour;
	
	@Before
	public void setUp() throws Exception {
		String data = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": [{\"amount\": 3000,\"resource\": \"WOOD\"}]}";
		memory = new Memory();
		behaviour = new BehaviourSimple(memory);
		
		memory.rememberInitialData(data);
		memory.rememberAction(new Land(memory.getInitialCreek(), 1));
		memory.rememberConsequences("{\"cost\": 13,\"extras\": {},\"status\": \"OK\"}");		
	}

	@Test
	public void testComputePonderationWithWater() throws ParseException {
		
		String glimpseWaterBoth = "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",100]],[[\"OCEAN\",100]],"
				+ "[\"OCEAN\"],[\"OCEAN\"]] },\"status\"\"OK\"}";
		String glimpseWaterFirst = "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"SHRUBLAND\",100]],[[\"OCEAN\",100]],"
				+ "[\"SHRUBLAND\"],[\"SHRUBLAND\"]] },\"status\"\"OK\"}";
		String glimpseWaterSecond = "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"SHRUBLAND\",100]],[[\"SHRUBLAND\",100]],"
				+ "[\"OCEAN\"],[\"SHRUBLAND\"]] },\"status\"\"OK\"}";
		
		memory.rememberAction(new Glimpse(Direction.N, 3));
		memory.rememberConsequences(glimpseWaterBoth);
		
		memory.rememberAction(new Glimpse(Direction.E, 3));
		memory.rememberConsequences(glimpseWaterFirst);
		
		memory.rememberAction(new Glimpse(Direction.S, 3));
		memory.rememberConsequences(glimpseWaterSecond);
		
		// The two tiles up north contains the biome OCEAN, so weight = 4
		assertEquals(4, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.N));
		
		// The first tile on the east only contains the biome OCEAN, so weight = 4
		assertEquals(4, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.E));
		
		// The second tile on the south only contains the biome OCEAN, so weight = 4
		assertEquals(4, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.S));
		
	}
	
	@Test
	public void testComputePonderationNoWater() throws ParseException {
		
		String glimpseNotWater = "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"SHRUBLAND\",100]],[[\"SHRUBLAND\",100]],"
				+ "[\"SHRUBLAND\"],[\"SHRUBLAND\"]] },\"status\"\"OK\"}";
		String glimpseVoid = "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[] },\"status\"\"OK\"}";
		
		memory.rememberAction(new Glimpse(Direction.E, 3));
		memory.rememberConsequences(glimpseNotWater);
		
		memory.rememberAction(new Glimpse(Direction.S, 3));
		memory.rememberConsequences(glimpseNotWater);
		
		memory.rememberAction(new Glimpse(Direction.W, 3));
		memory.rememberConsequences(glimpseVoid);
		
		// The two tiles on the east does not contains the biome OCEAN and are not visited, so weight = 0
		assertEquals(0, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.E));
		
		// The first tile on the east is now visited, so weight = 2
		memory.getTile(Direction.E, 1).setVisited(true);
		assertEquals(2, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.E));
		
		// The second tile on the east is now visited, so weight = 3
		memory.getTile(Direction.E, 2).setVisited(true);
		assertEquals(3, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.E));
		
		// Only the second tile is visited, so weight = 1
		memory.getTile(Direction.S, 2).setVisited(true);
		assertEquals(1, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.S));
		
		// On the west there is no tile detected (out of the map), so weight = 0
		assertEquals(0, behaviour.computePonderation(memory.getTile(memory.getLocation()), Direction.W));
		
	}

}

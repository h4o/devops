package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.action.Land;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.memory.Memory;

public class BSFindOrientationTest {
	
	private Memory memory;
	private BehaviourSimple behaviour;

	@Before
	public void setUp() throws Exception {
		String data = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": []}";
		memory = new Memory();
		behaviour = new BehaviourSimple(memory);
		
		memory.rememberInitialData(data);
		memory.rememberAction(new Land(memory.getInitialCreek(), 1));
		memory.rememberConsequences("{\"cost\": 13,\"extras\": {},\"status\": \"OK\"}");
	}

	@Test
	public void testFindOrientation() {
		
		Coordinate destination = new Coordinate(0, 1);
		assertEquals(Direction.N, behaviour.findOrientation(destination));
		
		destination = new Coordinate(1, 0);
		assertEquals(Direction.E, behaviour.findOrientation(destination));
		
		destination = new Coordinate(0, -1);
		assertEquals(Direction.S, behaviour.findOrientation(destination));
		
		destination = new Coordinate(-1, 0);
		assertEquals(Direction.W, behaviour.findOrientation(destination));
		
		
		destination = new Coordinate(1, 1);
		assertEquals(Direction.E, behaviour.findOrientation(destination));
		
		destination = new Coordinate(1, -1);
		assertEquals(Direction.E, behaviour.findOrientation(destination));
		
		destination = new Coordinate(-1, -1);
		assertEquals(Direction.W, behaviour.findOrientation(destination));
		
		destination = new Coordinate(-1, 1);
		assertEquals(Direction.W, behaviour.findOrientation(destination));
		
		
		destination = new Coordinate(0, 0);
		assertNull(behaviour.findOrientation(destination));
		
	}

}

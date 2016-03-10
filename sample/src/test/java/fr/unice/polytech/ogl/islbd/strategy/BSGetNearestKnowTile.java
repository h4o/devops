package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.action.Explore;
import fr.unice.polytech.ogl.islbd.action.Move;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.IslandMap;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

public class BSGetNearestKnowTile  extends TestCase {
	private Memory memory;
	private IslandMap map;
	private BehaviourSimple behaviour;

	public BSGetNearestKnowTile(String method) {
		super(method);
	}

	@Before
	protected void setUp() throws Exception {
		memory = new Memory();
		map= new IslandMap();
		memory.setMap(map);
		behaviour = new BehaviourSimple(memory);
	}

	@Test
	public void test() throws ParseException {
		Coordinate c1= new Coordinate(0,2);
		Tile t1= new Tile(c1,false, map);
		Coordinate c2= new Coordinate(5,10);
		Tile t2= new Tile(c2,false, map);
		Map<Coordinate, Tile> liste= new HashMap<>();
		liste.put(c1, t1);
		liste.put(c2, t2);
		map.setLocation(new Coordinate(0,0));
		
		assertEquals(c1, behaviour.getNearestKnownTile(liste));
	}

}

package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Consequence;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.action.Move;
import fr.unice.polytech.ogl.islbd.action.Scout;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.IslandMap;
import fr.unice.polytech.ogl.islbd.memory.Memory;

public class BSRemoveUnreachable {
	private Memory memory;
	private BehaviourSimple behaviour;
	private Consequence consequences;
	private String cons;
	private IslandMap map;
	
	@Before
	public void setUp() {
		map = new IslandMap();
		
		memory=new Memory();
		memory.setMap(map);
		String iniData = "{ \"creek\": \"CoucouTuVeuxVoirMaCreek?\",\"men\": 15,\"budget\": 10000,\"objective\": [{\"amount\": 1000,\"resource\": \"WOOD\"},{\"amount\": 1000,\"resource\": \"FISH\"}]}";
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
	public void testTileNull() {
		List<Direction> directions = new ArrayList<Direction>();
		//map.addTile(new Coordinate(0, 1), false);
		map.addTile(null, false);
		directions.add(Direction.N);
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 0);
	}
	
	@Test
	public void testUnreachableScootOnly() {
		List<Direction> directions = new ArrayList<Direction>();
		map.addTile(new Coordinate(0, 1), false);
		directions.add(Direction.N);
		memory.rememberAction(new Scout(Direction.N));
		try {
			memory.rememberConsequences("{\"cost\": 6,\"extras\": {\"altitude\": 2,\"resources\": [\"SUGAR_CANE\"], \"unreachable\":true},\"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 0);
	}
	
	@Test
	public void testUnreachableGlimpseOnly() {
		List<Direction> directions = new ArrayList<Direction>();
		map.addTile(new Coordinate(0, 1), false);
		map.addTile(new Coordinate(0, 2), false);
		map.addTile(new Coordinate(0, 3), false);
		directions.add(Direction.N);
		memory.rememberAction(new Glimpse(Direction.N, 3));
		try {
			memory.rememberConsequences("{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[[[\"TROPICAL_RAIN_FOREST\",99.97],[\"TROPICAL_RAIN_FOREST\",0.03]],[[\"TROPICAL_RAIN_FOREST\",100]]] },\"status\"\"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 1);
		memory.rememberAction(new Move(Direction.N));
		try {
			memory.rememberConsequences("{\"cost\": 5,\"extras\": {},\"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 0);


	}
	
	@Test
	public void testUnreachableGlimpseAndScout() {
		List<Direction> directions = new ArrayList<Direction>();
		map.addTile(new Coordinate(0, 1), false);
		map.addTile(new Coordinate(0, 2), false);
		map.addTile(new Coordinate(0, 3), false);
		memory.rememberAction(new Glimpse(Direction.N, 3));
		try {
			memory.rememberConsequences("{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]] },\"status\"\"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.addTile(new Coordinate(0, -1), false);
		directions.add(Direction.S);
		memory.rememberAction(new Scout(Direction.S));
		try {
			memory.rememberConsequences("{\"cost\": 6,\"extras\": {\"altitude\": 2,\"resources\": [\"SUGAR_CANE\"], \"unreachable\":true},\"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 0);
	}
	
	@Test
	public void testGlimpseNotScout() {
		List<Direction> directions = new ArrayList<Direction>();
		map.addTile(new Coordinate(0, 1), false);
		map.addTile(new Coordinate(0, 2), false);
		map.addTile(new Coordinate(0, 3), false);
		directions.add(Direction.N);
		memory.rememberAction(new Glimpse(Direction.N, 3));
		try {
			memory.rememberConsequences("{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[[[\"TROPICAL_RAIN_FOREST\",99.97],[\"TROPICAL_RAIN_FOREST\",0.03]],[[\"TROPICAL_RAIN_FOREST\",100]]] },\"status\"\"OK\"}");
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.addTile(new Coordinate(0, -1), false);
		
		memory.rememberAction(new Scout(Direction.N));
		try {
			memory.rememberConsequences("{\"cost\": 6,\"extras\": {\"altitude\": 2,\"resources\": [\"SUGAR_CANE\"]},\"status\": \"OK\"}");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		behaviour.removeUnreachable(directions);
		assertEquals(directions.size(), 1);
	}

}

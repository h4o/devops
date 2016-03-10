package fr.unice.polytech.ogl.islbd.strategy;

import junit.framework.TestCase;




import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Action;
import fr.unice.polytech.ogl.islbd.action.Consequence;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.IslandMap;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.memory.GlimpseTileInfo;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import static org.junit.Assert.*;

public class BSGlimpseArroundTest extends TestCase {
	IslandMap map;
	Memory memory;
	BehaviourSimple behaviour;
	Consequence consequences;
	String cons;
	
	
	@Override
	public void setUp() throws Exception {
		map = new IslandMap();
		map.addTile(new Coordinate(0, 1), false);
		map.addTile(new Coordinate(0, 2), false);
		map.addTile(new Coordinate(0, 3), false);
		map.addTile(new Coordinate(0, 4), false);
		memory=new Memory();
		memory.setMap(map);
		behaviour = new BehaviourSimple(memory);
		consequences= new Consequence("{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]] },\"status\"\"OK\"}");
		cons= "{ \"cost\":4,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]] },\"status\"\"OK\"}";
	}
	
	/**
	 * If we didn't glimpse anywhere before
	 */
	@Test 
	public void testNoGlimpseBefore(){
		Glimpse g;
		int i=0;
		assertEquals(behaviour.glimpseAround(),  new Glimpse(Direction.N, 3));
	}
	
	/**
	 * If we glimpsed in one direction before (not in the order, only east)
	 * @throws ParseException 
	 */
	@Test
	public void testOneGlimpseBefore() throws ParseException{
		Action glimpse= new Glimpse(Direction.N, 3);
		memory.rememberAction(glimpse);
		memory.rememberConsequences(cons);
		if (glimpse.getConsequence().hasSucceeded()){
			assertNotEquals(Direction.N, ((Glimpse) behaviour.glimpseAround()).getDirection());
			
		}
	}
	
	/**
	 * If we glimpsed in three directions before
	 * @throws ParseException 
	 */
	@Test
	public void testThreeGlimpseBefore() throws ParseException{
		Action g1= new Glimpse(Direction.E, 3);
		Action g2= new Glimpse(Direction.N, 3);
		Action g3= new Glimpse(Direction.S, 3);
		memory.rememberAction(g1);
		memory.rememberConsequences(cons);
		memory.rememberAction(g2);
		memory.rememberConsequences(cons);
		memory.rememberAction(g3);
		memory.rememberConsequences(cons);
		if (g1.getConsequence().hasSucceeded() && g2.getConsequence().hasSucceeded() && g3.getConsequence().hasSucceeded()){
			memory.rememberAction(g1);
			memory.rememberAction(g2);
			memory.rememberAction(g3);
			assertEquals(new Glimpse(Direction.W, 3), behaviour.glimpseAround());
		}
	}
	
	
	/**
	 * If we glimpsed only two tiles away, we'll gimpse on the current tile and the one after
	 * @throws ParseException 
	 */
	@Test
	public void testTwoTilesAwayGlimpsed() throws ParseException{
		Tile tile = memory.getTile(0, 2);
		Glimpse glimpse = new Glimpse(Direction.N, 3);
		glimpse.setConsequence(consequences);
		tile.addActionInfo(new GlimpseTileInfo(3, glimpse));;
		assertEquals(new Glimpse(Direction.N, 2), behaviour.glimpseAround());	
		
	}
	
	/**
	 * If we glimpsed only three tiles away, we'll gimpse on the current tile and the two after
	 * @throws ParseException 
	 */
	@Test
	public void testThreeTilesAwayGlimpsed() throws ParseException{
		Tile tile = memory.getTile(0, 3);
		Glimpse glimpse = new Glimpse(Direction.N, 4);
		glimpse.setConsequence(consequences);
		tile.addActionInfo(new GlimpseTileInfo(4, glimpse));;
		assertEquals(new Glimpse(Direction.N, 3), behaviour.glimpseAround());	
		
	}

}

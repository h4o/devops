package fr.unice.polytech.ogl.islbd.map;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class IslandMapTest extends TestCase {

	public IslandMapTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of an IslandMap and a simple case of the method getVisitedTiles
	 */
	@Test
	public void testIslandMapTestInitialization() {
		IslandMap map = new IslandMap();
		List<Tile> expected = new ArrayList<>();
		expected.add(new Tile(new Coordinate(0, 0), true, map));
		
		assertEquals(expected,map.getVisitedTiles());
	}
	
	/**
	 * Test the add of a new tile
	 */
	@Test
	public void testAddTile() {
		IslandMap iMap = new IslandMap();
		iMap.addTile(new Coordinate(0,2), true);
		Tile expected= new Tile(new Coordinate(0,2), true, iMap);

		assertEquals(expected,iMap.getTile(new Coordinate(0,2)));
	}
	
	/**
	 * Test the add of a new tile with null coordinates
	 */
	@Test
	public void testAddTileNullCoordinate() {
		IslandMap iMap = new IslandMap();
		iMap.addTile(null, true);
	}
	
	/**
	 * Test the add of a tile that is already in the map
	 */
	@Test
	public void testAddTileAlreadyIn() {
		IslandMap iMap = new IslandMap();
		iMap.addTile(new Coordinate(0,2), true);
		iMap.addTile(new Coordinate(0,2), true);
		List<Tile> expected = new ArrayList<>();
		expected.add(new Tile(new Coordinate(0, 0), true, iMap));
		expected.add(new Tile(new Coordinate(0,2), true, iMap));

		assertEquals(expected,iMap.getVisitedTiles());
	}
	
	/**
	 * Test getLocation and setLocation
	 */
	@Test
	public void testLocation(){
		IslandMap iMap = new IslandMap();
		iMap.addTile(new Coordinate(0,2), true);
		iMap.setLocation(new Coordinate(0,2));
		Coordinate expected= new Coordinate(0,2);
		
		assertEquals(expected,iMap.getLocation());
	}
	
	/**
	 * Test getLocation and setLocation with null coordinates
	 */
	@Test
	public void testLocationNullCoordinate(){
		IslandMap iMap = new IslandMap();
		iMap.setLocation(null);
		Coordinate expected= new Coordinate(0,0);
		
		assertEquals(expected,iMap.getLocation());
	}
	
	
	/**
	 * Test getLocation and setLocation with null coordinates
	 */
	@Test
	public void testGetMap(){
		IslandMap iMap = new IslandMap();
		iMap.addTile(new Coordinate(20,30), true);
		iMap.addTile(new Coordinate(-4,8), true);
		Map<Coordinate, Tile> expected= new HashMap<>();
		expected.put(new Coordinate(0, 0),new Tile(new Coordinate(0, 0), true, iMap));		
		expected.put(new Coordinate(20,30),new Tile(new Coordinate(20,30), true, iMap));	
		expected.put(new Coordinate(-4,8),new Tile(new Coordinate(-4,8), true, iMap));	
		
		assertEquals(expected,iMap.getMap());
	}
	

}

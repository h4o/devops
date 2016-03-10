package fr.unice.polytech.ogl.islbd.map;

import java.util.ArrayList;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;

public class CoordinateTest extends TestCase {
	
	public CoordinateTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of a Coordinate and its getters getX and get Y
	 */
	@Test
	public void testCoordinateInitialization() {
		Coordinate c= new Coordinate(0, 0);
		int expectedX=0;
		int expectedY=0;
		
		assertEquals(expectedX,c.getX());
		assertEquals(expectedY,c.getY());
	}
	
	/**
	 * Test the method getNeighbour when we don't tell the distance
	 */
	@Test
	public void testGetNeighbourNoDistance() {
		Coordinate c= new Coordinate(10, 20);
		Coordinate expected= new Coordinate(10, 19);
		
		assertEquals(expected, c.getNeighbour(Direction.S));
	}
	
	/**
	 * Test the method getNeighbour when we give a negative distance (nonsense) 
	 */
	@Test
	public void testGetNeighbourNegativDistance() {
		Coordinate c= new Coordinate(10, 20);
		Coordinate expected= new Coordinate(10, 22);
		
		assertEquals(expected, c.getNeighbour(Direction.N, -2));
	}
	
	/**
	 * Test the method getNeighbour when the distance is 0
	 */
	@Test
	public void testGetNeighbourZeroDistance() {
		Coordinate c= new Coordinate(10, 20);
		Coordinate expected= new Coordinate(10, 20);
		
		assertEquals(expected, c.getNeighbour(Direction.E, 0));
	}

	/**
	 * Test the method getNeighbours 
	 */
	@Test
	public void testGetNeighbours() {
		Coordinate c= new Coordinate(0, 0);
		List<Coordinate> expected= new ArrayList<>();
		expected.add(new Coordinate(0,1));
		expected.add(new Coordinate(-1,0));
		expected.add(new Coordinate(1,0));
		expected.add(new Coordinate(0,-1));
		
		assertEquals(expected, c.getNeighbours());
	}
	
	/**
	 * Test the method DistanceFrom in a simple case
	 */
	@Test
	public void testDistanceFromSimpleCase() {
		Coordinate c1= new Coordinate(0, 0);
		Coordinate c2= new Coordinate(0, 4);
		float expected=4;
		
		assertEquals(expected, c1.DistanceFrom(c2));
	}
	
	/**
	 * Test the method DistanceFrom when asking the distance from a coordinate and itself  
	 */
	@Test
	public void testDistanceFromSameCoordinate() {
		Coordinate c1= new Coordinate(0, 0);
		float expected=0;
		
		assertEquals(expected, c1.DistanceFrom(c1));
	}
	
	/**
	 * Test the method DistanceFrom when asking the distance from a coordinate and a null one
	 */
	@Test
	public void testDistanceFromNullCoordinate() {
		Coordinate c1= new Coordinate(-5, 0);
		float expected=5;
		
		assertEquals(expected, c1.DistanceFrom(null));
	}
	
	/**
	 * Test the method equals in a simple case
	 */
	@Test
	public void testEqualsSimpleCase() {
		Coordinate c1= new Coordinate(-5, 0);
		Coordinate c2= new Coordinate(-5, 0);
		boolean expected=true;
		
		assertEquals(true, c1.equals(c2));
	}
	
	/**
	 * Test the method equals when one coordinate is null
	 */
	@Test
	public void testEqualsNullCoordinate() {
		Coordinate c1= new Coordinate(-5, 0);
		Coordinate c2= null;
		boolean expected=false;
		
		assertEquals(expected, c1.equals(c2));
	}
	
	/**
	 * Test the method hashCode
	 */
	@Test
	public void testHashCode() {
		Coordinate c= new Coordinate(-5, 21);
		int expected = 31*(-5)+21;
		
		assertEquals(expected, c.hashCode());
	}
	
	/**
	 * Test the method toString
	 */
	@Test
	public void testToString() {
		Coordinate c= new Coordinate(2,3);
		String expected= "[2;3]";
		
		assertEquals(expected, c.toString());
	}


}

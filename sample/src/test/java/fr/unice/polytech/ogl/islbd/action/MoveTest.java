package fr.unice.polytech.ogl.islbd.action;

import junit.framework.TestCase;

import org.junit.Test;

import fr.unice.polytech.ogl.islbd.Direction;

public class MoveTest extends TestCase {

	public MoveTest(String method) {
		super(method);
	}
	
	/**
	 * Test the creation of a move (toString should return a proper JSon string)
	 */
	@Test
	public void testMoveInitialization() {
		Move move= new Move(Direction.W);
		String expected= "{\"action\":\"move_to\",\"parameters\":{\"direction\":\"W\"}}";
		
		assertEquals(expected, move.toString());
	}
	
	/**
	 * Test getDirection
	 */
	@Test
	public void testgetDirection() {
		Move move= new Move(Direction.S);
		Direction expected= Direction.S;
		
		assertEquals(expected, move.getDirection());
	}
	
	/**
	 * Test getDirection when direction is null
	 */
	@Test
	public void testgetNullDirection() {
		Move move= new Move(null);
		Direction expected= Direction.N;
		
		assertEquals(expected, move.getDirection());
	}
}

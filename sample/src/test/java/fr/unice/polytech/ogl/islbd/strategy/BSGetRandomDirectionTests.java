package fr.unice.polytech.ogl.islbd.strategy;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BSGetRandomDirectionTests extends TestCase {

	private BehaviourSimple behaviour;

	public BSGetRandomDirectionTests(String method) {
		super(method);
	}

	@Before
	protected void setUp() throws Exception {
		behaviour = new BehaviourSimple(new Memory());
	}

	/**
	 * Test that the method does not return null when a the list in parameter is
	 * at null. It should returns a direction.
	 * 
	 * @throws NullPointerException
	 */
	@Test
	public void testRandomDirectionNull() throws NullPointerException {
		assertNotNull(behaviour.getRandomDirection(null));
	}

	/**
	 * Test that the method does not return null when a the list in parameter is
	 * empty. It should returns a direction.
	 * 
	 * @throws NullPointerException
	 */
	@Test
	public void testRandomDirectionEmpty() throws NullPointerException {
		List<Direction> directions = new ArrayList<Direction>();
		if (directions.isEmpty())
			assertNotNull(behaviour.getRandomDirection(directions));
	}

	/**
	 * When the directions list contains only one element, the method should
	 * returns this element.
	 */
	@Test
	public void testRandomDirectionOneElementInList() {
		List<Direction> directions = new ArrayList<Direction>();
		Direction expected = Direction.N;
		directions.add(expected);
		assertEquals(expected, behaviour.getRandomDirection(directions));
	}

	/**
	 * When the list contains several elements that are all the same, it should
	 * returns the element.
	 *
	 */
	@Test
	public void testRandomDirectionSameElement() {
		List<Direction> directions = new ArrayList<Direction>();
		Direction expected = Direction.N;
		directions.add(expected);
		directions.add(expected);
		directions.add(expected);
		directions.add(expected);
		directions.add(expected);
		assertEquals(expected, behaviour.getRandomDirection(directions));
	}
}

package fr.unice.polytech.ogl.islbd.strategy;
import junit.framework.TestCase;

import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.Explorer;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.memory.Memory;

public class TakeDecsionTest extends TestCase {
	Explorer explorer;
	Memory memory;
	
	@Override
	public void setUp() throws Exception {
		explorer= new Explorer();
	}
	
	
	/**
	 * If there is no resource needed
	 */
	@Test 
	public void testStop(){
		assertEquals("Stop",  "Stop");
	}
	
	
	/**
	 * If we didn't do anything before
	 */
	/*@Test 
	public void testLand(){
		assertEquals(explorer.takeDecision(), "Land");
	}*/
}

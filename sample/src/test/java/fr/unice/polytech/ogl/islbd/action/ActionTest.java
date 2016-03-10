package fr.unice.polytech.ogl.islbd.action;

import static org.junit.Assert.*;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;

public class ActionTest extends TestCase{

	public ActionTest(String method) {
		super(method);
	}
	
	/**
	 * Tests on addParameter, getParameter and getParameters.
	 */
	@Test
	public void testParameters() {
		HashMap<String, Object> expected = new HashMap<>();
		Action action = new Action("Test");
		assertNull(action.getParameters());
		
		action.addParameter("paramTest_42", 42);
		expected.put("paramTest_42", 42);
		assertEquals(expected, action.getParameters());
		
		action.addParameter("paramTest_String", "String");
		expected.put("paramTest_String", "String");
		assertEquals(expected, action.getParameters());
		
		assertEquals(expected.get("paramTest_42"), action.getParameter("paramTest_42"));
		
		assertNull(action.getParameter("NonExistentParameter"));
	}

}

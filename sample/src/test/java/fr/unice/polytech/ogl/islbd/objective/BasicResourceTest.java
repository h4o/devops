package fr.unice.polytech.ogl.islbd.objective;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BasicResourceTest {
	
	private Map<BasicResource, Float> expected;
	
	@Test
    public void testGetRecipe(){
		
    	for (BasicResource res : BasicResource.values()) {
    		
    		// We want 1 resource
    		expected = new HashMap<BasicResource, Float>();
    		expected.put(res, (float) 1);
			assertEquals(expected, res.getRecipe(1));
			
			// We want 0 resource
			expected = new HashMap<BasicResource, Float>();
    		expected.put(res, (float) 0);
    		assertEquals(expected, res.getRecipe(0));
    		
    		// We want -10 resource (should be equals to 0)
    		expected = new HashMap<BasicResource, Float>();
    		expected.put(res, (float) 0);
    		assertEquals(expected, res.getRecipe(-10));
    		
    		// We want 10 of the resource
    		expected = new HashMap<BasicResource, Float>();
    		expected.put(res, (float) 10);
			assertEquals(expected, res.getRecipe(10));
    		
		}
    }

}

package fr.unice.polytech.ogl.islbd.objective;

import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AdvResourceTest{

	private Map<BasicResource, Float> expected;
	
    @Test
    public void testHowMuchWith() throws Exception {
        assertEquals(AdvResource.INGOT.howMuchWith(new HashMap<BasicResource, Integer>() {
            {
                put(BasicResource.ORE, 5);
                put(BasicResource.WOOD, 5);
            }
        }), 1);

        assertEquals(AdvResource.GLASS.howMuchWith(new HashMap<BasicResource, Integer>() {
            {
                put(BasicResource.QUARTZ, 1);
                put(BasicResource.WOOD, 5);
            }
        }), 0);

        assertEquals(AdvResource.LEATHER.howMuchWith(new HashMap<BasicResource, Integer>() {
            {
                put(BasicResource.ORE, 5);
                put(BasicResource.WOOD, 5);
                put(BasicResource.QUARTZ, 5);
                put(BasicResource.FUR, 5);
            }
        }), 1);

        assertEquals(AdvResource.INGOT.howMuchWith(new HashMap<BasicResource, Integer>() {
            {
                put(BasicResource.ORE, 528);
                put(BasicResource.WOOD, 345);
            }
        }), 69);


        assertEquals(AdvResource.PLANK.howMuchWith(new HashMap<BasicResource, Integer>() {
            {
                put(BasicResource.WOOD, 345);
            }
        }), 1380);
    }
    
    @Test
    public void testGetRecipe(){
    	for (AdvResource advRes : AdvResource.values()) {
    		
    		// We want 1 resource
    		expected = new HashMap<>();
			for (BasicResource bRes : advRes.getRecipe().keySet()) {
				expected.put(bRes, advRes.getRecipe().get(bRes));
			}
			assertEquals(expected, advRes.getRecipe(1));
			
			// We want 0 resource
			expected = new HashMap<>();
			for (BasicResource bRes : advRes.getRecipe().keySet()) {
				expected.put(bRes, (float) 0);
			}
			assertEquals(expected, advRes.getRecipe(0));
			
			// We want -10 resource (should be equals to 0)
			expected = new HashMap<>();
			for (BasicResource bRes : advRes.getRecipe().keySet()) {
				expected.put(bRes, (float) 0);
			}
			assertEquals(expected, advRes.getRecipe(-10));
			
			// We want 10 of the resource
			expected = new HashMap<>();
			for (BasicResource bRes : advRes.getRecipe().keySet()) {
				expected.put(bRes, advRes.getRecipe().get(bRes) * 10);
			}
			assertEquals(expected, advRes.getRecipe(10));
		}        
    }
}
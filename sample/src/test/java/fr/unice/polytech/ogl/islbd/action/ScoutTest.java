package fr.unice.polytech.ogl.islbd.action;

import static org.junit.Assert.assertEquals;
import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.objective.Amount;
import fr.unice.polytech.ogl.islbd.objective.AmountCondPair;
import fr.unice.polytech.ogl.islbd.objective.Cond;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 08/04/2015.
 */
public class ScoutTest extends TestCase {
	
	public ScoutTest(String method) {
		super(method);
	}
	

	/**
	 * Test the creation of a scout (toString should return a proper JSon string)
	 */
	@Test
	public void testScoutInitialization() {
		Scout scout= new Scout(Direction.E);
		String expected= "{\"action\":\"scout\",\"parameters\":{\"direction\":\"E\"}}";
		
		assertEquals(expected, scout.toString());
	}
	
	/**
	 * Test getDirection
	 */
	@Test
	public void testgetDirection() {
		Scout scout= new Scout(Direction.E);
		Direction expected= Direction.E;
		
		assertEquals(expected, scout.getDirection());
	}
	
	/**
	 * Test if Explore can store one resource.
	 */
    @Test
    public void testGetResources() {
    	Scout scout= new Scout(Direction.E);
    	scout.setConsequence(new Consequence("{\"status\": \"OK\", \"cost\": 8,  \"extras\": {\"resources\": [\"WOOD\", \"FUR\", \"FLOWER\"], \"altitude\": -23 }}"));
    	List<BasicResource> expectedResources = new ArrayList<>();
        expectedResources.add(BasicResource.WOOD);
        expectedResources.add(BasicResource.FUR);
        expectedResources.add(BasicResource.FLOWER);
            
        Assert.assertEquals(expectedResources, scout.getResources());
    }
    
    /**
     * Test when there is no resources to store.
     */
    @Test
    public void testGetResourcesNoResources() {
    	Scout scout= new Scout(Direction.E);
    	scout.setConsequence(new Consequence("{\"status\": \"OK\", \"cost\": 8,  \"extras\": {\"resources\": [], \"altitude\": -23 }}"));
    	List<BasicResource> expectedResources = new ArrayList<>();
            
        Assert.assertEquals(expectedResources, scout.getResources());
    }
    
    /**
     * Test when there is several time the same resource
     */
    @Test
   public void testGetResourcesTwiceSame() {
    	Scout scout= new Scout(Direction.E);
    	scout.setConsequence(new Consequence("{\"status\": \"OK\", \"cost\": 8,  \"extras\": {\"resources\": [\"WOOD\",\"WOOD\"], \"altitude\": -23 }}"));
    	List<BasicResource> expectedResources = new ArrayList<>();
    	expectedResources.add(BasicResource.WOOD);
    	expectedResources.add(BasicResource.WOOD);
            
        Assert.assertEquals(expectedResources, scout.getResources());
    }
    
    /**
	 * Test if we can reach the place we want to scout
	 */
    @Test
    public void testIsNotReachableWhenIs() {
    	Scout scout= new Scout(Direction.E);
    	scout.setConsequence(new Consequence("{\"status\": \"OK\", \"cost\": 8,  \"extras\": {\"resources\": [\"WOOD\", \"FUR\", \"FLOWER\"], \"altitude\": -23 }}"));
    	Boolean expected=false;
            
        Assert.assertEquals(expected, scout.isNotReachable());
    }
    
    /**
     * Test when we can't reach the place we want to scout
     */
    @Test
    public void testIsNotReachableWhenIsNot() {
    	Scout scout= new Scout(Direction.E);
    	scout.setConsequence(new Consequence("{\"status\": \"OK\", \"cost\": 8,  \"extras\": {\"unreachable\": true, \"altitude\": -23 }}"));
    	Boolean expected= true;
            
        Assert.assertEquals(expected, scout.isNotReachable());
    }
}
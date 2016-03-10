package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.objective.Amount;
import fr.unice.polytech.ogl.islbd.objective.AmountCondPair;
import fr.unice.polytech.ogl.islbd.objective.Cond;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 08/04/2015.
 */
public class ExploreTest extends TestCase {
	
	public ExploreTest(String method) {
		super(method);
	}
	
	/**
	 * Test if Explore can store one resource.
	 */
    @Test
    public void testGetResources() {
        Explore explore = new Explore();
        explore.setConsequence(new Consequence("{\"cost\":7,\"extras\":{\"resources\":[{\"amount\":\"MEDIUM\",\"resource\":\"FUR\",\"cond\":\"FAIR\"}],\"pois\":[]},\"status\": \"OK\"}"));
        Map<BasicResource, AmountCondPair> expectedResources = new HashMap<>();
        expectedResources.put(BasicResource.FUR, new AmountCondPair(Amount.Medium, Cond.Fair));
        
        Map<BasicResource, AmountCondPair> resources = explore.getResources();       
        Assert.assertArrayEquals(resources.entrySet().toArray(), expectedResources.entrySet().toArray());
    }
    
    /**
     * Test when there is no resources to store.
     */
    @Test
    public void testGetResourcesNoResources() {
        Explore explore = new Explore();
        explore.setConsequence(new Consequence("{\"cost\":7,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"OK\"}"));       
        
        Map<BasicResource, AmountCondPair> expectedResources = new HashMap<>();
        
        Map<BasicResource, AmountCondPair> resources = explore.getResources();       
        Assert.assertArrayEquals(resources.entrySet().toArray(), expectedResources.entrySet().toArray());
    }
    
    /**
     * Test when there is several resources (all different)
     */
    @Test
    public void testGetResourcesMultipleDifferentResources() {
        Explore explore = new Explore();
        explore.setConsequence(new Consequence("{\"cost\":8,\"extras\":{\"resources\":[{\"amount\":\"MEDIUM\",\"resource\":\"FUR\",\"cond\":\"FAIR\"},{\"amount\":\"HIGH\",\"resource\":\"WOOD\",\"cond\":\"HARSH\"}],\"pois\":[]},\"status\": \"OK\"}"));
        Map<BasicResource, AmountCondPair> expectedResources = new HashMap<>();
        expectedResources.put(BasicResource.FUR, new AmountCondPair(Amount.Medium, Cond.Fair));
        expectedResources.put(BasicResource.WOOD, new AmountCondPair(Amount.High, Cond.Harsh));
        
        Map<BasicResource, AmountCondPair> resources = explore.getResources();       
        Assert.assertArrayEquals(resources.entrySet().toArray(), expectedResources.entrySet().toArray());
    }
    
    /**
     * Test when there is several resources (all same)
     */
    @Test
    public void testGetResourcesMultipleSameResource() {
        Explore explore = new Explore();
        explore.setConsequence(new Consequence("{\"cost\":7,\"extras\":{\"resources\":[{\"amount\":\"MEDIUM\",\"resource\":\"FUR\",\"cond\":\"FAIR\"},{\"amount\":\"MEDIUM\",\"resource\":\"FUR\",\"cond\":\"FAIR\"}],\"pois\":[]},\"status\": \"OK\"}"));
        Map<BasicResource, AmountCondPair> expectedResources = new HashMap<>();
        expectedResources.put(BasicResource.FUR, new AmountCondPair(Amount.Medium, Cond.Fair));
        expectedResources.put(BasicResource.FUR, new AmountCondPair(Amount.Medium, Cond.Fair));
        
        Map<BasicResource, AmountCondPair> resources = explore.getResources();       
        Assert.assertArrayEquals(resources.entrySet().toArray(), expectedResources.entrySet().toArray());
    }
}
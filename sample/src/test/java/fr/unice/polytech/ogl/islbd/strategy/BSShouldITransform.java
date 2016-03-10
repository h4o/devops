package fr.unice.polytech.ogl.islbd.strategy;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.action.Land;
import fr.unice.polytech.ogl.islbd.action.Transform;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

public class BSShouldITransform {
	
	private Memory memory;
	private BehaviourSimple behaviour;

	@Before
	public void setUp() throws Exception {
		memory = new Memory();
		behaviour = new BehaviourSimple(memory);	
	}

	@Test
	public void testShouldITransformMultipleAdvancedResources() throws ParseException {
		Map<BasicResource, Integer> expected = new HashMap<BasicResource, Integer>();
		
		String data = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": ["
				+ "{\"amount\": 10,\"resource\": \"GLASS\"},{\"amount\": 400,\"resource\": \"PLANK\"}]}";
		
		memory.rememberInitialData(data);
		memory.rememberAction(new Land(memory.getInitialCreek(), 1));
		memory.rememberConsequences("{\"cost\": 13,\"extras\": {},\"status\": \"OK\"}");
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 11},\"status\": \"OK\"}");
		
		// We want 44 PLANK > (400 PLANK * 0.1 = 40 PLANK) ; 44 PLANK = 11 WOOD
		expected.put(BasicResource.WOOD, 11);
		assertEquals(new Transform(expected), behaviour.shouldITransform());
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 10},\"status\": \"OK\"}");
		memory.rememberAction(new Exploit(BasicResource.QUARTZ));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 1000},\"status\": \"OK\"}");
		
		// We want 4 GLASS > (10 GLASS * 0.1 = 1 GLASS)
		expected.put(BasicResource.WOOD, 20);
		expected.put(BasicResource.QUARTZ, 40);
		assertEquals(new Transform(expected), behaviour.shouldITransform());
		
	}
	
	@Test
	public void testShouldITransformNoAdvancedResources() throws ParseException {
		String data = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": ["
				+ "{\"amount\": 15,\"resource\": \"WOOD\"},{\"amount\": 300,\"resource\": \"FUR\"}]}";
		
		memory.rememberInitialData(data);
		memory.rememberAction(new Land(memory.getInitialCreek(), 1));
		memory.rememberConsequences("{\"cost\": 13,\"extras\": {},\"status\": \"OK\"}");
		
		assertEquals(null, behaviour.shouldITransform());
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 1000},\"status\": \"OK\"}");
		
		memory.rememberAction(new Exploit(BasicResource.FUR));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 1000},\"status\": \"OK\"}");
		
		assertEquals(null, behaviour.shouldITransform());
		
	}
	
	@Test
	public void testShouldITransformCommonObjectives() throws ParseException{
		Map<BasicResource, Integer> expected = new HashMap<BasicResource, Integer>();
		
		String data = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": ["
				+ "{\"amount\": 15,\"resource\": \"WOOD\"},{\"amount\": 400,\"resource\": \"PLANK\"}]}";
		
		// Transform if amount of WOOD > (400/4)*0.1 = 10 WOOD (10 WOOD = 40 PLANK)
		
		memory.rememberInitialData(data);
		memory.rememberAction(new Land(memory.getInitialCreek(), 1));
		memory.rememberConsequences("{\"cost\": 13,\"extras\": {},\"status\": \"OK\"}");
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 5},\"status\": \"OK\"}");
		
		// 5 WOOD < 10
		assertEquals(null, behaviour.shouldITransform());
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 6},\"status\": \"OK\"}");
		
		// 11 WOOD > 10
		expected.put(BasicResource.WOOD, 11);
		assertEquals(new Transform(expected), behaviour.shouldITransform());
		
		memory.rememberAction(new Exploit(BasicResource.WOOD));
		memory.rememberConsequences("{\"cost\": 4,\"extras\": {\"amount\": 1000},\"status\": \"OK\"}");
		
		// 1011 WOOD > 10
		expected.put(BasicResource.WOOD, 1011);
		assertEquals(new Transform(expected), behaviour.shouldITransform());
		
	}

}

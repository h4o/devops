package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TransformTest {

    private Transform t;
    private Map<BasicResource, Integer> usedResources = new HashMap<BasicResource, Integer>() {
        {
            put(BasicResource.SUGAR_CANE, 100);
            put(BasicResource.FRUITS, 10);
        }
    };

    @Before
    public void initialize() {
        t = new Transform(usedResources);
    }

    @Test
    public void testToString() throws Exception {

        String oracle = "{\"action\":\"transform\",\"parameters\":{\"FRUITS\":10,\"SUGAR_CANE\":100}}";
        assertEquals(t.toString(), oracle);
    }

    @Test
    public void testGetKindAndProduction() {

        String cons = "{\"status\":\"OK\",\"cost\":12,\"extras\":{\"kind\":\"RUM\",\"production\":9}}";
        Consequence c = new Consequence(cons);
        t.setConsequence(c);
        assertEquals(t.getKind(), AdvResource.RUM);
        assertEquals(t.getProduction(), 9);
    }

    @Test
    public void testGetProducedResource() {
        assertEquals(t.usedResource(BasicResource.FRUITS), 10);
        assertEquals(t.usedResource(BasicResource.SUGAR_CANE), 100);
        assertEquals(t.usedResource(BasicResource.WOOD), 0);
    }

    @Test
    public void testUsedResource() throws Exception {
        assertEquals(usedResources, t.usedResources());
    }
}
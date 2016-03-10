package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.action.Consequence;
import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AveragePickingTest {

    private Exploit exploitWood30;

    private Exploit exploitWood60;

    @Before
    public void setup() {
        exploitWood30 = new Exploit(BasicResource.WOOD);
        exploitWood30.setConsequence(new Consequence("{\"cost\":6,\"extras\":{\"amount\": 30},\"status\": \"OK\"}"));

        exploitWood60 = new Exploit(BasicResource.WOOD);
        exploitWood60.setConsequence(new Consequence("{\"cost\":6,\"extras\":{\"amount\": 60},\"status\": \"OK\"}"));
    }
    @Test
    public void testAveragePicking() {
        Memory mem = new Memory();

        Map<BasicResource, Float> avg = mem.getAveragePicking();
        assertEquals(0, avg.size());

        mem.rememberAction(exploitWood30);

        avg = mem.getAveragePicking();
        assertEquals(1, avg.size());
        assertTrue(Math.abs(30 - avg.get(BasicResource.WOOD)) < 1e-15);

        mem.rememberAction(exploitWood30);
        avg = mem.getAveragePicking();
        assertTrue(Math.abs(30 - avg.get(BasicResource.WOOD)) < 1e-15);

        mem.rememberAction(exploitWood60);
        avg = mem.getAveragePicking();
        assertTrue(Math.abs(40 - avg.get(BasicResource.WOOD)) < 1e-15);

    }

}

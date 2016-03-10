package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
public class InventoryTest {

    @Test
    public void testAddAndRemoveResources() throws Exception {
        Inventory inventory = new Inventory();

        // Adding tests
        inventory.addResources(AdvResource.PLANK, 5);
        assertEquals(5, inventory.getPossessed(AdvResource.PLANK));
        assertEquals(0, inventory.getPossessed(AdvResource.GLASS));

        inventory.addResources(AdvResource.GLASS, 10);
        assertEquals(10, inventory.getPossessed(AdvResource.GLASS));
        assertNotEquals(11, inventory.getPossessed(AdvResource.GLASS));

        assertEquals(0, inventory.getPossessed(BasicResource.FRUITS));
        inventory.addResources(BasicResource.FRUITS, 1);
        assertEquals(1, inventory.getPossessed(BasicResource.FRUITS));

        inventory.addResources(BasicResource.FISH, -30);
        assertEquals(0, inventory.getPossessed(BasicResource.FISH));

        // Removing tests
        inventory.removeResources(AdvResource.PLANK, 2);
        assertEquals(3, inventory.getPossessed(AdvResource.PLANK));

        inventory.removeResources(AdvResource.GLASS, 500);
        assertEquals(0, inventory.getPossessed(AdvResource.GLASS));

        inventory.removeResources(AdvResource.PLANK, -2);
        assertEquals(3, inventory.getPossessed(AdvResource.PLANK));
    }
}
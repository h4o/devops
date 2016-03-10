package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import fr.unice.polytech.ogl.islbd.objective.IResource;

import java.util.*;

public class Inventory {
    private Map<IResource, Integer> inventory = new HashMap<>();

    /**
     * Add to the inventory an specified quantity of resource exploited or crafted
     * @param res the resource to add
     * @param quantity the quantity we retrieved
     */
    public void addResources(IResource res, int quantity) {
        // We cannot possess less than 0 of a resource
        quantity = Math.max(quantity, 0);
        int possessed = inventory.getOrDefault(res, 0);
        inventory.put(res, possessed + quantity);
    }

    public void removeResources(IResource res, int quantity) {
        quantity = Math.max(quantity, 0);
        int possessed = inventory.getOrDefault(res, 0);
        // We cannot possess less than 0 of a resource
        possessed = Math.max(possessed - quantity, 0);
        inventory.put(res, possessed);
    }

    public Map<IResource, Integer> getInventory() {
        return inventory;
    }

    public int getPossessed(IResource res) {
        return inventory.getOrDefault(res, 0);
    }

    public Map<BasicResource, Integer> getBasicResPossessed() {
        Map<BasicResource, Integer> basicResPossessed = new HashMap<>();
        Set<IResource> resources = new HashSet<>(inventory.keySet());
        Set<IResource> basicRes = new HashSet<IResource>(Arrays.asList(BasicResource.values()));
        resources.retainAll(basicRes);

        for (IResource res : resources) {
            basicResPossessed.put((BasicResource)res, inventory.get(res));
        }

        return basicResPossessed;
    }
}

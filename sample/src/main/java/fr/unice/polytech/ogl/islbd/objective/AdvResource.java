package fr.unice.polytech.ogl.islbd.objective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AdvResource implements IResource{
    GLASS(new HashMap<BasicResource, Float>() {
        {
            put(BasicResource.QUARTZ, 10f);
            put(BasicResource.WOOD, 5f);
        }
    }),
    INGOT(new HashMap<BasicResource, Float>() {
        {
            put(BasicResource.ORE, 5f);
            put(BasicResource.WOOD, 5f);
        }
    }),
    PLANK(new HashMap<BasicResource, Float>() {
        {
            put(BasicResource.WOOD, 0.25f);
        }
    }),
    LEATHER(new HashMap<BasicResource, Float>() {
        {
            put(BasicResource.FUR, 3f);
        }
    }),
    RUM(new HashMap<BasicResource, Float>() {
        {
            put(BasicResource.SUGAR_CANE, 10f);
            put(BasicResource.FRUITS, 1f);
        }
    });

    private Map<BasicResource, Float> recipe;

    AdvResource(Map<BasicResource, Float> recipe) {
        this.recipe = recipe;
    }

    public static AdvResource getResource(String res) {
        for (AdvResource r : AdvResource.values()) {
            if (res.toUpperCase().equals(r.toString())) {
                return r;
            }
        }
        return null;
    }


    public List<BasicResource> getResources() {
        return new ArrayList<>(recipe.keySet());
    }

    /**
     * Compute how much of an advanced resource you can do
     * /!\ This is an estimation /!\
     * @param material resources to use during the transformation process
     * @return the amount of resource retrievable
     */
    public int howMuchWith(Map<BasicResource, Integer> material) {
        int amount = Integer.MAX_VALUE;
        for (Map.Entry<BasicResource, Float> element : recipe.entrySet()) {
            BasicResource res = element.getKey();
            float exchangeRate = element.getValue();
            if (!material.containsKey(element.getKey())) {
                return 0;
            }
            int bound = (int) (material.get(res) / exchangeRate);
            amount = (bound < amount ? bound : amount);
        }
        return amount;
    }

    /**
     * Return the minimum amount of resources the bot need to create the maximum of the resource
     * @param collected
     * @return
     */
    public Map<BasicResource, Integer> filterUseless(Map<BasicResource, Integer> collected) {
        Map<BasicResource, Integer> useful = new HashMap<>();
        int howMuch = this.howMuchWith(collected);
        for (BasicResource resource : recipe.keySet()) {
            int amount = 0;
            if (collected.containsKey(resource)) {
                Float minQuantity = howMuch * recipe.get(resource);
                amount = minQuantity.intValue();
            }
            useful.put(resource, amount);
        }
        return useful;
    }


    @Override
    public Map<BasicResource, Float> getRecipe(){
    	return this.recipe;
    }
    
	@Override
	public Map<BasicResource, Float> getRecipe(int quantity) {
		Map<BasicResource, Float> calcRecipe = new HashMap<>();
		quantity = Math.max(quantity, 0);
		for (BasicResource res : this.recipe.keySet()) {
			calcRecipe.put(res, this.recipe.get(res) * quantity);
		}
		
		return calcRecipe;
	}

}

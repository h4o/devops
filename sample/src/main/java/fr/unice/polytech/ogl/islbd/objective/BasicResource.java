package fr.unice.polytech.ogl.islbd.objective;

import java.util.HashMap;
import java.util.Map;

/**
 * Enums for resources

 * @author Chloe Guglielmi
 */
public enum BasicResource implements IResource {
	FISH(), QUARTZ(), ORE(), WOOD(), FRUITS(), SUGAR_CANE(), FLOWER(), FUR();

	/**
	 * Returns the resource corresponding to the String
	 * 
	 * @param res: the name of the resource we're looking for
	 * @return the resource we're looking for
	 */
	public static BasicResource getResource(String res) {
		for (BasicResource r : BasicResource.values()) {
			if (res.toUpperCase().equals(r.toString())) {
				return r;
			}
		}
		return null;
	}
	
	@Override
	public Map<BasicResource, Float> getRecipe(){
		Map<BasicResource, Float> recipe = new HashMap<>();
		recipe.put(this, (float) 1);
		
    	return recipe;
    }

	@Override
	public Map<BasicResource, Float> getRecipe(int quantity) {
		Map<BasicResource, Float> recipe = new HashMap<>();
		quantity = Math.max(quantity, 0);
		recipe.put(this, (float) (quantity));
		
		return recipe;
	}
}

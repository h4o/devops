package fr.unice.polytech.ogl.islbd.objective;

import java.util.Map;

public interface IResource {
	
	public Map<BasicResource, Float> getRecipe();
	public Map<BasicResource, Float> getRecipe(int quantity);
}

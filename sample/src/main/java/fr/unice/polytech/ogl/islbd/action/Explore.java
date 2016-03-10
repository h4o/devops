package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.objective.Amount;
import fr.unice.polytech.ogl.islbd.objective.AmountCondPair;
import fr.unice.polytech.ogl.islbd.objective.Cond;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Explore class - Give extras informations about the tile we are.
 * 
 * @author user
 *
 */
public class Explore extends Action{
	
	/**
	 * An explore doesn't need parameter. So we just create it by the Action constructor (we need the type of the action which is explore).
	 */
	public Explore(){
		super("explore");
	}
	
	/**
	 * Return the resources exploited associated with their amounts
	 * @return
	 */
	public Map<BasicResource, AmountCondPair> getResources() {
		Map<BasicResource, AmountCondPair> resources = new HashMap<>();

		JSONObject extras = (JSONObject) this.getConsequence().get("extras");
		JSONArray jsResources = (JSONArray) extras.get("resources");
		for (Object r : jsResources) {
			JSONObject jsResource = (JSONObject) r;
			BasicResource resource = BasicResource.getResource((String) jsResource.get("resource"));
			Amount amount = Amount.getAmount((String) jsResource.get("amount"));
			Cond cond = Cond.getCond((String) jsResource.get("cond"));
			if (resource != null) {
				resources.put(resource, new AmountCondPair(amount, cond));
			}
		}

		return resources;
	}

}


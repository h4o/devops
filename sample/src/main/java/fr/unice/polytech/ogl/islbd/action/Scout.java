package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide extra informations about adjacent tiles. (So we scout in a direction) 
 * @author user
 *
 */
public class Scout  extends Action {

	/**
	 * Constructor - Call super (action) with name "scout" and add the direction in parameter
	 * @param direction
	 */
	public Scout(Direction direction){
		super("scout");
		addParameter("direction", direction.toString());
	}

	/**
	 * Return the direction
	 * @return
	 */
	public Direction getDirection(){
		return  Direction.getDirection(this.getParameter("direction").toString());
	}

	/**
	 * Return all the resources we found by scouting
	 * @return List<Resource>
	 */
	public List<BasicResource> getResources() {
		JSONObject extras = (JSONObject) this.getConsequence().get("extras");
		List<String> jsResources = (List<String>) extras.get("resources");
		List<BasicResource> resources = new ArrayList<>();
		for (String s : jsResources) {
			BasicResource r = BasicResource.getResource(s);
			if (r != null) {
				resources.add(r);
			}
		}
		return resources;
	}


    public boolean isNotReachable() {
        JSONObject extras = (JSONObject) this.getConsequence().get("extras");
        return extras.get("unreachable") != null && (boolean) extras.get("unreachable");
    }
}

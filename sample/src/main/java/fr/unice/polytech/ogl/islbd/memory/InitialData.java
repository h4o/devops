package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import fr.unice.polytech.ogl.islbd.objective.IResource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The first data that the island provided to us
 * @author user
 *
 */
public class InitialData extends JSONObject {

    private Map<IResource, Long> resourceObj = new HashMap<IResource,Long>();
    
    //TODO test
    public InitialData(JSONObject jsobj) {
        // "init" : { "creek": "creek_id", "budget": 600, "men": 50, "objective": [ { "resource": "WOOD", "amount": 600 } ] },
        this.putAll(jsobj);
        JSONArray objectives = (JSONArray) jsobj.get("objective");
        for (Object objective : objectives) {
            JSONObject jsobjective = (JSONObject) objective;
            long amount = (Long) jsobjective.get("amount");

            BasicResource neededRes = BasicResource.getResource((String) jsobjective.get("resource"));
            AdvResource advRes = AdvResource.getResource((String) jsobjective.get("resource"));
            if (neededRes != null) {
                resourceObj.put(neededRes, amount);
            }
            if (advRes != null) {
            	resourceObj.put(advRes, amount);
            }
        }
    }

    /**
     * Return the budget we have
     * @return int
     */
    public int getBudget() {
        long budget = (Long) this.get("budget");
        return (int) budget;
    }

    /**
     * Return the first creek we know
     * @return String (the creek)
     */
    public String getCreek() {
        return (String) this.get("creek");
    }

    /**
     * Return the number of men we have to fulfill our dream !
     * @return
     */
    public int getMen() {
        return (int) (long) this.get("men");
    }

    /**
     * Return our objectives (resources)
     * @return
     */
    public Map<IResource,Long> getResourceObjective() {
        return resourceObj;
    }

    /**
     * Set the objectives we need for this mission !
     * @param resourceObj
     */
    public void setResourceObjective(HashMap<IResource,Long> resourceObj) {
        this.resourceObj = resourceObj;
    }
}

package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.memory.Storable;
import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Classe Action - Create the JSON associated to the action's event in way to communicate with the server 
 * @author user
 *
 */
public class Action extends JSONObject implements Storable {


    private Consequence consequence = null;
    
    /**
     * Default constructor
     */
    public Action() {
        super();
    }
    
    /**
     * Create an action (fill the action type by name)
     * @param name the action's name
     */
    public Action(String name) {
        this.put("action", name);
    }
    
    /**
     * Create an action with his associated consequences (what happen)
     * @param action
     * @param consequence
     */
    public Action(JSONObject action, JSONObject consequence) {
        this.putAll(action);
        this.setConsequence(consequence);
    }
    
    
    public Action(Action other) {
        this.putAll(other);
        this.setConsequence(other.getConsequence());
    }

    /**
     * return the type (name) of the action
     * @return name
     */
    public String getName() {
        return (String) this.get("action");
    }
    
    /**
     * set the type (name) of the action
     * @param name
     */
    public void setName(String name) {
        this.put("action", name);
    }
    
    /**
     * Add a parameter to the action (JSON). For example, a move need a direction, so you will add a "direction" associated to the value "S" for example.
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value) {
        JSONObject parameter = (JSONObject) this.get("parameters");
        if (parameter == null) {
            parameter = new JSONObject();
        }
        parameter.put(name, value);
        this.put("parameters", parameter);
    }
    
    /**
     * Set consequences of an action
     * @param consequence
     */
    public void setConsequence(Consequence consequence) {
        this.consequence = consequence;
    }
    
    /**
     * Set the consequence of an action by a JSONObject
     * @param consequence
     */
    public void setConsequence(JSONObject consequence) {
        this.consequence.putAll(consequence);
    }
    
    /**
     * Get the consequence of the action
     * @return Consequence
     */
    public Consequence getConsequence() {
        return consequence;
    }
    
    /**
     * Return the state of the action (KO or OK (03/22/2015))
     * @return
     */
    public boolean hasSucceeded() {
        return consequence.hasSucceeded();
    }
    
    /**
     * Return the cost (how much it costs to do it)
     * @return
     */
    public int getCost() {
        return consequence.getCost();
    }
    
    // Not sure these methods are useful
    /**
     * Return the parameter in the action specified by the parameter (of the method)
     * @param name
     * @return
     */
    public Object getParameter(String name) {
        JSONObject parameters = (JSONObject) this.get("parameters");
        return (parameters == null ? null : parameters.get(name));
    }
    
    /**
     * Return all the parameters
     * @return
     */
    public HashMap<String, Object> getParameters() {
        return (HashMap<String, Object>) this.get("parameters");
    }

    
}

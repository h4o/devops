package fr.unice.polytech.ogl.islbd.action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Consequence extends JSONObject {

    public Consequence() {

    }

    // Example of Consequence : "{\"cost\":7,\"extras\":{\"resources\":[],\"pois\":[]},\"status\": \"OK\"}"
    
    public Consequence(String consequence) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsobj = null;
            jsobj = (JSONObject) parser.parse(consequence);
            this.putAll(jsobj);
        } catch (ParseException e) {e.printStackTrace();
        }
    }

    /**
	 * If the event associated to this consequence has succeeded or not
	 * @return boolean
	 */
    public boolean hasSucceeded() {
        String status = this.getStatus();

        if (status != null) {
            return (status.equals("OK"));
        }
        return false;
    }

    /**
     * Return the cost of the event
     * @return int
     */
    public int getCost() {
        if (hasSucceeded()) {
            long cost = (Long) this.get("cost");
            return (int) cost;
        }
        return 0;
    }

    /**
     * Return the status of the event (KO or OK for the moment (03/22/2015))
     * @return
     */
    private String getStatus() {
        return (String) this.get("status");
    }
}

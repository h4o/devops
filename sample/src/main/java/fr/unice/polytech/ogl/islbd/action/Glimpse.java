package fr.unice.polytech.ogl.islbd.action;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.map.Biome;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Glimpse class - This action allows you to get information about the biomes present in the current case and a number of boxes in a given direction.
 * @author user
 *
 */
public class Glimpse extends Action {
	/**
	 * Constructor - we need a direction to know where we will glimpse and a range. This range has a maximum of 4.
	 * @param direction
	 * @param range
	 */
    public Glimpse(Direction direction, int range){
        super("glimpse");
        if (direction!= null)
        	addParameter("direction", direction.toString());
        else if (direction == null)
        	addParameter("direction", "N");
        if (range > 4) 
        	range = 4;
        else if (range < 1)
        	range =1;
        addParameter("range", range);
    }

    /**
     * Return the direction we specified in the constructor
     * @return Direction
     */
    public Direction getDirection() {
        return  Direction.getDirection(this.getParameter("direction").toString());
    }
    
    /**
     * Return the range we specified when the object was created
     * @return
     */
    public int getRange() {
        return (int) this.getParameter("range");
    }


    /**
     * Return the info the glimpse discovered
     * @return the info the glimpse discovered
     */
    public List<Map<Biome, Double>> getBiomeInfo() {
        List<Map<Biome, Double>> l = new ArrayList<>();
        
        JSONObject extras = (JSONObject) this.getConsequence().get("extras");
        JSONArray jsCases = (JSONArray) extras.get("report");
        // For each range
        for (int r = 0; r < jsCases.size(); r++) {
            Map<Biome, Double> biomeSquare  = new HashMap<>();
            JSONArray biomes = (JSONArray) jsCases.get(r);

            //biomes
            for (Object b: biomes) {
                Biome biome;
                Double percentage = null;

                // If the range is near, we have an array with a percentage
                if (r < 2) {
                    JSONArray biomeInfo = (JSONArray) b;
                    biome = Biome.getBiome((String) biomeInfo.get(0));
                    percentage = Double.parseDouble(biomeInfo.get(1).toString());
                } else {
                    biome = Biome.getBiome((String) b);
                }
                // In case the biome is not in the Biome enum
                if (biome == null) {
                    biome = Biome.UNKNOWN;
                }
                biomeSquare.put(biome, percentage);
            }
            l.add(biomeSquare);
        }
        return l;
    }
}

	

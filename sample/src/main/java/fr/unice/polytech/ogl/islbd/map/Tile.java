package fr.unice.polytech.ogl.islbd.map;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Exploit;
import fr.unice.polytech.ogl.islbd.action.Explore;
import fr.unice.polytech.ogl.islbd.memory.GlimpseTileInfo;
import fr.unice.polytech.ogl.islbd.memory.ScoutTileInfo;
import fr.unice.polytech.ogl.islbd.memory.Storable;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create a tile.
 * @author user
 *
 */
public class Tile {
    private List<Storable> actionsInfo = new ArrayList<>();
    private int altitude;
    private Coordinate coordinate;
    private boolean visited;
    private IslandMap islandMap;

    public Tile(Coordinate coordinate, boolean visited, IslandMap islandMap) {
        this.coordinate = coordinate;
        this.visited = visited;
        this.islandMap = islandMap;
    }

    /**
     * Check whether the tile contains a specific biome. If we can't know, returns false.
     * @param biomeSearched
     * @return
     */
    public boolean containsBiome(Biome biomeSearched) {
        for (Biome b : this.getBiomes().keySet()) {
            if (b.equals(biomeSearched)) {
                return true;
            }
        }
        return false;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getAltitude() {
        return altitude;
    }

    /**
     * Removes from a copy of the resources in entry those which are not present anymore on the tile
     * Contract : the input must be resources whe know are present on the tile
     * @param resources
     * @return the list
     */
    public List<BasicResource> removeFullyExploited(List<BasicResource> resources) {
        List<BasicResource> copyRsc = new ArrayList<>(resources);
        for (int i = copyRsc.size(); i-- > 0; ) {
            BasicResource rsc = copyRsc.get(i);
            Exploit lastExploit = this.getLastExploit(rsc);
            if (lastExploit != null) {
                copyRsc.remove(i);
            }
        }
        return copyRsc;
    }

    /**
    * Return the resources the tile still produces among a specific list of resources
    * @param resources
    * @return the resources
    */
    public List<BasicResource> doContainResourceAmong(List<BasicResource> resources) {
        List<BasicResource> usedToContain = usedToContainResourceAmong(resources);
        return removeFullyExploited(usedToContain);
    }

    /**
     * Return the resources the tile once produced among a specific list of resources
     * @param resources
     * @return the resources
     */
    public List<BasicResource> usedToContainResourceAmong(List<BasicResource> resources) {
        List<BasicResource> toReturn = new ArrayList<>();

        // If the tile is not up to date no way to be SURE it containsBiome a resource
        if (producedResourcesUpToDate()) {
            List<BasicResource> resourceProduced = new ArrayList<>();
            ScoutTileInfo lastScoutInfo = (ScoutTileInfo) this.getLastActionInfo("scout_tile_info");
            if (lastScoutInfo != null) {
                resourceProduced = lastScoutInfo.getScout().getResources();
            } else {
                Explore explore = (Explore) this.getLastActionInfo("explore");
                if (explore != null) {
                    resourceProduced = new ArrayList<>(explore.getResources().keySet());
                }
            }

            for (BasicResource rsrcNeeded : resources) {

                Exploit lastExploit = this.getLastExploit(rsrcNeeded);
                //if (resourceProduced.contains(rsrcNeeded) && (lastExploit == null || lastExploit.getAmount() > 0)) {
                //if (resourceProduced.contains(rsrcNeeded) && lastExploit == null) {
                if (resourceProduced.contains(rsrcNeeded)) {
                    toReturn.add(rsrcNeeded);
                }
            }
        }
        return toReturn;
    }

    /**
     * If the tile is up to date, return what it used to contain.
     * Else returns the resources the biome may produce
     *
     * (This version of the method return systematically the resources it's biome may produce if the tile is not up to date)
     *
     * @param resources the resources
     * @return
     */
    public List<BasicResource> mayContainResourceAmong(List<BasicResource> resources) {
        List<BasicResource> resourcesIMayContain = new ArrayList<>();
        if (producedResourcesUpToDate()) {
            resourcesIMayContain = usedToContainResourceAmong(resources);
        } else {
            for (Biome biome : this.getBiomes().keySet()) {
                for (BasicResource resource : biome.getResources()) {
                    if (resources.contains(resource) && !resourcesIMayContain.contains(resource)) {
                        resourcesIMayContain.add(resource);
                    }
                }
            }
        }
        return resourcesIMayContain;
    }

    public Exploit getLastExploit(BasicResource resource) {
        for (int i = actionsInfo.size(); --i >= 0; ) {
            Storable s = actionsInfo.get(i);
            if (s.getName().equals("exploit")) {
                Exploit e = (Exploit) s;
                if (e.getResource().equals(resource)) {
                    return e;
                }
            }
        }
        return null;
    }

    public Storable getLastActionInfo(String actionName){

        for (int i = actionsInfo.size(); --i >= 0; ) {
            Storable actionInfo = actionsInfo.get(i);
            if (actionName.equals(actionInfo.getName())) {
                return actionInfo;
            }
        }

        return null;
    }

    public Storable getLastActionInfo() {
        return actionsInfo.get(actionsInfo.size()-1);
    }

    /**
     * Return a list of actionInfo
     * @param actionName the type of actionInfo to return
     * @return the list of actionInfo matching the type given in parameters
     */
    public List<Storable> getActionsInfo(String actionName) {
        List<Storable> matchingActionsInfo = new ArrayList<>();
        for (Storable actionInfo : actionsInfo) {
            if (actionInfo.getName().equals(actionName)) {
                matchingActionsInfo.add(actionInfo);
            }
        }
        return matchingActionsInfo;
    }

    public boolean nextToWater(Direction d) {
        Tile neighbour = islandMap.getTile(coordinate.getNeighbour(d));
        if (neighbour != null) {
            return neighbour.containsWater();
        }
        return false;
    }

    /**
     * True if we can exploit the resources safely
     * False if never scout or explore
     * @return whether or not the tile is up to date
     */
    //TODO test
    public boolean producedResourcesUpToDate() {
        ScoutTileInfo lastScoutInfo = (ScoutTileInfo) getLastActionInfo("scout_tile_info");
        Explore lastExplore = (Explore) getLastActionInfo("explore");

        return (lastScoutInfo != null || lastExplore != null);
    }

    /**
     * Return whether an action that could have reduce the amount of resources of a tile has been done after the last explore
     * @return
     */
    public boolean amountResourceUpToDate() {
        Explore lastExplore = (Explore) getLastActionInfo("explore");

        if (lastExplore == null) {
            return false;
        } else {
            Exploit lastExploit = (Exploit) getLastActionInfo("exploit");
            if (lastExploit != null) {
                int lastExploitIndex = actionsInfo.lastIndexOf(lastExploit);
                int lastExploreIndex = actionsInfo.lastIndexOf(lastExplore);
                if (lastExploitIndex > lastExploreIndex) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Return the biomes present on the tile
     * If a percetage is unknown, the map value will be null;
     * @return
     */
    public Map<Biome, Double> getBiomes() {
        Map<Biome, Double> biomes = new HashMap<>();
        List<Storable> glimpsesInfo = this.getActionsInfo("glimpse_tile_info");

        if (!glimpsesInfo.isEmpty() && this.getReachability().equals(Reachability.REACHABLE)) {
            // Selection of the most precise glimpse done on the tile
            GlimpseTileInfo mostPreciseGlimpse = (GlimpseTileInfo) glimpsesInfo.get(0);
            for (Storable gti : glimpsesInfo) {
                GlimpseTileInfo glimpseInfo = (GlimpseTileInfo) gti;
                if (glimpseInfo.getRange() > mostPreciseGlimpse.getRange()) {
                    mostPreciseGlimpse = glimpseInfo;
                }
            }
            biomes =  mostPreciseGlimpse.getBiomes();
        }
        return biomes;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    //TODO : Implement
    public boolean hasBeenVisited() {
        return visited;
    }

    public void addActionInfo(Storable actionInfo) {
        actionsInfo.add(actionInfo);
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }

    public Reachability getReachability() {
        // If it has already been visited, no problem
        if (visited) {
            return Reachability.REACHABLE;
        }

        ScoutTileInfo sti = (ScoutTileInfo) getLastActionInfo("scout_tile_info");
        GlimpseTileInfo gti = (GlimpseTileInfo) getLastActionInfo("glimpse_tile_info");
        if (sti == null && gti == null) {
            return Reachability.UNKNOWN;
        }
        if ((gti != null && gti.isNotReachable()) || (sti !=  null && sti.getScout().isNotReachable())) {
            return Reachability.UNREACHABLE;
        }
        return Reachability.REACHABLE;
    }
    
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (actionsInfo == null) {
			if (other.actionsInfo != null)
				return false;
		} else if (!actionsInfo.equals(other.actionsInfo))
			return false;
		if (altitude != other.altitude)
			return false;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (islandMap == null) {
			if (other.islandMap != null)
				return false;
		} else if (!islandMap.equals(other.islandMap))
			return false;
		if (visited != other.visited)
			return false;
		return true;
	}

    public boolean hasBeenGlimpsed() {
        return this.getLastActionInfo("glimpse_tile_info") != null;
    }

    public boolean containsWater() {
        return (this.containsBiome(Biome.OCEAN) ||  this.containsBiome(Biome.LAKE));
    }
}


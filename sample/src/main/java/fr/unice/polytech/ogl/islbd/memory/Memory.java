package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.*;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.IslandMap;
import fr.unice.polytech.ogl.islbd.map.Reachability;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import fr.unice.polytech.ogl.islbd.objective.IResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Memory {


    private InitialData initData = null;
    private List<Action> actions = new ArrayList<>();
    private IslandMap map = new IslandMap();
    private Inventory inventory = new Inventory();
    
    public void setMap(IslandMap m){
    	this.map=m;
    }

    /**
     * Store initalData in the memory
     * @param data the string containing the json of the data
     * @throws ParseException
     */
    public void rememberInitialData(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsobj = (JSONObject) parser.parse(data);
        initData = new InitialData(jsobj);
    }

    /**
     * Return the average cost of an action
     * @param actionName The action
     * @return The average
     */
    public int getAverageCost(String actionName) {
        int average = 0;
        List<Action> actionList = getListAction(actionName);
        for (Action move : actionList) {
            average += move.getCost();
        }
        if (actionList.size() == 0) {
            return 20;
        }
        return average / actionList.size();
    }

    /**
     * Return a mapping between a resource and the average quantity picked up with an Exploit
     * If a resource has never been exploited, it won't appear in the map
     * @return the map
     */
    public Map<BasicResource, Float> getAveragePicking() {
        Map<BasicResource, Float> averagePicking = new HashMap<>();
        Map<BasicResource, Integer> occurenceMap = new HashMap<>();

        List<Action> exploits = this.getListAction("exploit");
        for (Action action : exploits) {
            Exploit exploit = (Exploit) action;
            BasicResource bRes = exploit.getResource();
            int occurence = occurenceMap.getOrDefault(bRes, 0);
            float average = averagePicking.getOrDefault(bRes, 0f);
            average = (average * occurence + exploit.getAmount()) / (occurence + 1);
            ++occurence;

            averagePicking.put(bRes, average);
            occurenceMap.put(bRes, occurence);
        }

        return averagePicking;
    }

    public BasicResource getMostInterestingResource(List<BasicResource> reachableResources) {

        List<BasicResource> reachableBasicObjectives = getBasicResourcesNeeded();
        reachableBasicObjectives.retainAll(reachableResources);
        
        IResource betterObjective = null;
        BasicResource betterResource = null;
        
        float progressMax = Float.MAX_VALUE;

        Map<IResource, Integer> undoneObj = getUndoneObj();
        // For each undone objective
        for (Map.Entry<IResource, Integer> resEntry : undoneObj.entrySet()) {
            IResource res = resEntry.getKey();
            int lack = resEntry.getValue();
            
            Set<BasicResource> recipeRes = new HashSet<>(res.getRecipe().keySet());
            recipeRes.retainAll(reachableBasicObjectives);
            if(!recipeRes.isEmpty()){
            	//float progression = getProgression(new ArrayList<BasicResource>(res.getRecipe().keySet()));
                float progression = getProgression(res.getRecipe(lack));
            	
            	if(progression < progressMax){
            		progressMax = progression;
            		betterObjective = res;
            	}
            }
            
        }
        
        if(betterObjective != null){
	        Set<BasicResource> resCandidates = new HashSet<>(betterObjective.getRecipe().keySet());
	        resCandidates.retainAll(reachableBasicObjectives);
	        progressMax = Float.MAX_VALUE;

            int lackOfTheAdvRes = undoneObj.get(betterObjective);
            Map<BasicResource, Float> recipe = betterObjective.getRecipe(lackOfTheAdvRes);

	        for (BasicResource basicResource : resCandidates) {
	        	int lackOfComponent = 0;
	        	if(betterObjective.toString().equals(basicResource.toString()))
	        		lackOfComponent = (int) Math.max(recipe.get(basicResource), 0);
	        	else
	        		lackOfComponent = (int) Math.max(recipe.get(basicResource) - getAmountCollected(basicResource), 0);

				//float progression = getProgression(basicResource, lackOfComponent);
                Map<BasicResource, Float> temp = new HashMap<>();
                temp.put(basicResource, (float) lackOfComponent);
                float progression = getProgression(temp);
				if(progression != 0 && progression < progressMax){
	        		progressMax = progression;
	        		betterResource = basicResource;
	        	}
			}
        }
        
        return betterResource;
    }

    /**
     * Compute the progression of each element of a basicResource list and return the sum
     * @param resources
     * @return
     */
    //public float getProgression(List<BasicResource> resources) {
    public float getProgression(Map<BasicResource, Float> resources) {
        float progression = 0;
        Map<BasicResource, Float> averagePicking = getAveragePicking();
        Map<IResource, Long> resourceNeeded = this.getResourceNeeded();
        for (BasicResource resource : resources.keySet()) {
            //int lack = (int) Math.max(resourceNeeded.getOrDefault(resource, 0l) - inventory.getPossessed(resource), 0);
            float lack = resources.get(resource);
            progression += lack / averagePicking.getOrDefault(resource, 1f);
        }

        return progression;
    }

    /**
     * Compute the progression of each element of a basicResource list and return the sum
     * @param resource
     * @return
     */
    public float getProgression(BasicResource resource, int lack) {
        float progression = 0;
        Map<BasicResource, Float> averagePicking = getAveragePicking();
        Map<IResource, Long> resourceNeeded = this.getResourceNeeded();
        //int lack = (int) Math.max(resourceNeeded.getOrDefault(resource, 0l) - inventory.getPossessed(resource), 0);
        progression += lack / averagePicking.getOrDefault(resource, 1f);

        return progression;
    }

    /**
     * Return the number of the action which names are specified
     * @param actionName The name of the action
     * @return the number of the action which names are specified
     */
    public int getNumberAction(String actionName) {
        return getListAction(actionName).size();
    }

    public void rememberAction(Action action) {
        actions.add(action);
    }

    public Map<IResource, Integer> getUndoneObj() {
        Map<IResource, Integer> getUndoneObj = new HashMap<>();

        for (Map.Entry<IResource, Long> resAmount : initData.getResourceObjective().entrySet()) {
            IResource resource = resAmount.getKey();
            long amountNeeded = resAmount.getValue();
            long possessed = inventory.getPossessed(resource);

            if (possessed < amountNeeded) {
                getUndoneObj.put(resource, (int) (amountNeeded - possessed));
            }
        }

        return getUndoneObj;
    }

    /**
     * Remember the consequence of the last action the explorer has done
     * @param consequence the consequence
     * @throws ParseException
     */
    public void rememberConsequences(String consequence) throws ParseException {
        Consequence newConsequence = new Consequence(consequence);

        Action lastAction = this.getLastAction();
        lastAction.setConsequence(newConsequence);

        Tile tile = map.getTile(map.getLocation());
        tile.addActionInfo(lastAction);

        if (lastAction.hasSucceeded()) {
            switch (lastAction.getName()) {
                case "move_to":
                    manageMoveTo((Move) lastAction);
                    break;
                case "glimpse":
                    manageGlimpse((Glimpse) lastAction);
                    break;
                case "scout":
                    manageScout((Scout) lastAction);
                    break;
                /*case "explore":
                    manageExplore((Explore) lastAction);
                    break;*/
                case "exploit":
                    manageExploit((Exploit) lastAction);
                    break;
                case "transform":
                    manageTransform((Transform) lastAction);
                    break;
                case "land":
                    manageLand((Land) lastAction);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Return in percent how advanced the bot is regarding a resource
     * @param resource the resource concerned
     * @return the percentage
     */
    public double getPercentCompleted(BasicResource resource) {
        int collected = this.getAmountCollected(resource);
        Map<IResource, Long> objectives = this.getResourceNeeded();
        if (objectives.containsKey(resource)) {
            return collected / objectives.get(resource) * 100;
        }
        return 1.0;
    }

    /**
     * Delete the reached objectives
     * @param exploit
     */
    private void manageExploit(Exploit exploit) {
        inventory.addResources(exploit.getResource(), exploit.getAmount());
    }

    /**
     * Add to the inventory how much of the advanced resource we have created
     * and remove the resources we used to do so
     * @param lastAction the transform action
     */
    private void manageTransform(Transform lastAction) {
        inventory.addResources(lastAction.getKind(), lastAction.getProduction());
        Map<BasicResource, Integer> usedResources = lastAction.usedResources();
        for (Map.Entry<BasicResource, Integer> usedresource : usedResources.entrySet()) {
            inventory.removeResources(usedresource.getKey(), usedresource.getValue());
        }
    }

    /**
     * Move the player position in the model
     * @param moveTo
     */
    private void manageMoveTo(Move moveTo) {
        Direction moveDir = moveTo.getDirection();
        Coordinate targetTileCoordinate = map.getLocation().getNeighbour(moveDir);

        // If the map doesn't contain the tile we add it
        if (map.getTile(targetTileCoordinate) == null) {
            //TODO see if the second parameter can be removed
            map.addTile(targetTileCoordinate, false);
        }
        map.getTile(targetTileCoordinate).setVisited(true);

        map.setLocation(targetTileCoordinate);
    }

    /**
     * Move the player position in the model to the land creek
     * @param lastAction
     */
    private void manageLand(Land lastAction) {
        map.setLocation(new Coordinate(0, 0));
    }

    /**
     * Complete the map with the information of the glimpse
     * @param glimpse the glimpse action
     */
    private void manageGlimpse(Glimpse glimpse) {
        Map<Coordinate, Tile> tiles = map.getMap();
        Direction dirGlimpse = glimpse.getDirection();

        for (int range = 0; range < glimpse.getRange(); ++range) {
            Coordinate glimpsedCoordinate = map.getLocation().getNeighbour(dirGlimpse, range);

            // If the map doesn't contain the tile we add it
            if (!tiles.containsKey(glimpsedCoordinate)) {
                map.addTile(glimpsedCoordinate, false);
            }
            Tile tile = tiles.get(glimpsedCoordinate);
            tile.addActionInfo(new GlimpseTileInfo(range + 1, glimpse));
        }
    }
    /**
     * Complete the map with the information of the scout
     * @param scout the scout action
     */
    public void manageScout(Scout scout) {
        Map<Coordinate, Tile> maptiles = this.map.getMap();
        Direction dirScout = scout.getDirection();

        Coordinate scoutedCoordinate = this.map.getLocation().getNeighbour(dirScout);
        if (!maptiles.containsKey(scoutedCoordinate)) {
            this.map.addTile(scoutedCoordinate, false);
        }

        Tile scoutedTile = maptiles.get(scoutedCoordinate);
        Tile currentTile = map.getTile(map.getLocation());
        currentTile.addActionInfo(scout);
        scoutedTile.addActionInfo(new ScoutTileInfo(scout));
    }

    /**
     * Return tiles that may contain the resources. I.e the tiles of a correct biome that has not been fully exploited
     * @param resources The resources
     * @return tiles that may contain the resources.
     */
    public Map<Coordinate, Tile> getTilesContainingResources(List<BasicResource> resources) {
        Map<Coordinate, Tile> correctTiles = new HashMap<>();
        List<Coordinate> potentialTiles = new ArrayList<>();
        for (Tile tile : map.getMap().values()) {
            List<BasicResource> rscTMayContain = tile.mayContainResourceAmong(resources);
            //if (!tile.mayContainResourceAmong(resources).isEmpty()) {
            if (!rscTMayContain.isEmpty()) {
                if (!tile.removeFullyExploited(rscTMayContain).isEmpty()) {
                    correctTiles.put(tile.getCoordinate(), tile);
                }

                for (Direction dir : Direction.values()) {
                    Coordinate coorNeighbour = tile.getCoordinate().getNeighbour(dir);
                    Tile neighbour = getTile(coorNeighbour);

                    // If we don't know anything about what the tile may contain or not
                    if ((neighbour == null
                            || (!neighbour.hasBeenGlimpsed() && !neighbour.producedResourcesUpToDate()))
                            && !potentialTiles.contains(coorNeighbour)) {
                        potentialTiles.add(coorNeighbour);
                    }
                }
            }
        }
        for (Coordinate coorPTile : potentialTiles) {
            Tile pTile = getTile(coorPTile);
            if (pTile == null) {
                map.addTile(coorPTile, false);
            }
            correctTiles.put(coorPTile, getTile(coorPTile));
        }
        return correctTiles;
    }


    /**
     * Return the resources needed for this mission
     * @return
     */
    //TODO test
    public Map<IResource,Long> getResourceNeeded() {
        return initData.getResourceObjective();
    }
    
    public List<BasicResource> getBasicResourcesNeeded(){
    	Map<IResource, Integer> objectives = getUndoneObj();
    	Set<BasicResource> resources = new HashSet<>();
    	
    	for (IResource obj : objectives.keySet()) {
            resources.addAll(obj.getRecipe().keySet());
		}
    	
    	return new ArrayList<BasicResource>(resources);
    }

//    /**
//     * Includes the resources used for manufacturing
//     * @return
//     */
//    public Map<BasicResource, Long> getAllResourceNeeded() {
//        Map<BasicResource,Long> resources = getResourceNeeded();
//        for (BasicResource resource : getResUsedInInManufacturing()) {
//            if (!resources.containsKey(resource)) {
//                resources.put(resource, 0l);
//            }
//        }
//        return resources;
//    }
    
//    public Map<AdvResource,Long>  getAdvancedResourceNeeded() {
//        return initData.getAdvancedRes();
//    }

    /**
     * Return the budget we have (Points of Action)
     * @return
     */
    public int getInitialBudget() {
        return initData.getBudget();
    }

    /**
     * Return our actuel budget ((the initial) - (all the actions we've done)).
     * @return
     */
    public int getCurrentBudget() {
        int budget = this.getInitialBudget();
        for(Action action : actions) {
            budget -= action.getCost();
        }
        return budget;
    }
    
    /**
     * Return the first creek we know
     * @return String
     */
    public String getInitialCreek() {
        return initData.getCreek();
    }

    /**
     * Return the last action we've done
     * @return Action
     */
    public Action getLastAction() {
        if (actions.isEmpty()) {
            return null;
        }
        return actions.get(actions.size() - 1);
    }

    /**
     * Return the number of men actually exploring the island
     * @return int
     */
    public int getMenOnIsland() {
        Land lastLand = getLastLand();
        if (lastLand != null) {
            return lastLand.getPeople();
        }
        return 0;
    }
    
    /**
     * Return the last Creek we've used
     * @return String
     */
    public String getActualCreek() {
        Land lastLand = getLastLand();
        if (lastLand != null) {
            return lastLand.getCreek();
        }
        return getInitialCreek();
    }

    /**
     * Return how much men we had at the beginning
     * @return int
     */
    public int getInitialMen() {
        return initData.getMen();
    }
    
    /**
     * Return the last Land action we've done
     * @return Land
     */
    public Land getLastLand() {
        return (Land) getLastAction("land");
    }
    
    public Action getLastAction(String actionName){

        for (int i = actions.size(); --i >= 0; ) {
            Action action = actions.get(i);
            if (actionName.equals(action.getName())) {
                return action;
            }
        }
        
        return null;
    }

    /**
     * Return all actions we've done since the beginning.
     * @param actionName
     * @return
     */
    public List<Action> getListAction(String actionName) {
        List<Action> listAction = new ArrayList<>();
        for (Action action : actions) {
            if (action.getName().equals(actionName)) {
                listAction.add(action);
            }
        }
        return listAction;
    }

    /**
     * Return the Tile next to us in a direction (for example in south).
     * @param direction
     * @return Tile
     */
    public Tile getTile(Direction direction) {
        return map.getTile(direction);
    }


    public Tile getTile(Coordinate coordinate) {
        return getTile(coordinate.getX(), coordinate.getY());
    }

    /**
     * Return the Tile associated to these coordinates
     * @param x
     * @param y
     * @return Tile
     */
    public Tile getTile(int x, int y) {
        return map.getTile(x, y);
    }

    public Tile getTile (Direction d, int range) {
        Coordinate location = this.getLocation();
        return getTile(location.getNeighbour(d, range));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(initData);

        for (Action action : actions) {
            sb.append(action);
            sb.append(action.getConsequence());
        }
        return sb.toString();
    }

	public boolean hasBeenGlimpsed(Direction direction, int i) {
        Coordinate location = map.getLocation();
        Coordinate neighbour = location.getNeighbour(direction, i - 1);
        
		Tile tile = map.getTile(neighbour);

        if (tile == null || tile.getBiomes().isEmpty()) {
            return false;
        }
        
		return true;
	}


	public Coordinate getLocation() {
		return map.getLocation();
	}

    public int getAmountCollected(IResource resource) {
        return inventory.getPossessed(resource);
    }

    /*public int getAmountCollected(AdvResource resource) {
        List<Action> transforms = this.getListAction("transform");
        int amount = 0;
        for (Action actionTransform : transforms) {
            Transform transform = (Transform) actionTransform;
            if (transform.getKind().equals(resource)) {
                amount += transform.getProduction();
            }
        }
        return amount;
    }*/

    public Map<BasicResource, Integer> getCollected() {
        /*Map<BasicResource, Integer> collection = new HashMap<>();
        for (Action exploitAction : this.getListAction("exploit")) {
            Exploit exploit = (Exploit) exploitAction;
            if (!collection.containsKey(exploit.getResource())) {
                collection.put(exploit.getResource(), this.getAmountCollected(exploit.getResource()));
            }
        }
        return collection;*/
        return inventory.getBasicResPossessed();
    }

    public double getShortestDistanceTo(Coordinate coordinate) {
        return map.getShortestDistanceTo(coordinate);
    }


    public boolean mayBeReachable(Coordinate coordinate) {
        Tile tile = map.getTile(coordinate);
        return (tile == null || !tile.getReachability().equals(Reachability.UNREACHABLE));
    }

    public boolean mayBeReachable(Direction direction) {
        return mayBeReachable(getLocation().getNeighbour(direction));
    }
    
//    /**
//     * @return all the resources we didn't entirely find according to our objectives
//     */
//    public List<IResource> getUndoneObj(){
//    	List<IResource> undoneRes= new ArrayList<>();
//    	Map<IResource,Long> res= initData.getResourceObjective();
//    	Set set = res.entrySet();
//        Iterator i = set.iterator();
//        while(i.hasNext()) {
//            Map.Entry me = (Map.Entry)i.next();
//            if (this.getPercentCompleted((IResource)me.getKey()) < 100.0){
//            	undoneRes.add((IResource)me.getKey());
//            }
//        }
//        return undoneRes;
//    }

    public List<Tile> getMapTiles() {
        return new ArrayList<>(map.getMap().values());
    }

    /**
     * Return directions leading to reachable tiles taking into account the actual location
     * @return
     */
    public List<Direction> getReachableDirections() {
        List<Direction> dirs = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Tile neighbour = getTile(dir);
            if (neighbour != null && neighbour.getReachability().equals(Reachability.REACHABLE)) {
                dirs.add(dir);
            }
        }
        return dirs;
    }

    public Map<AdvResource, Long> getAdvancedResourceNeeded() {
        Map<AdvResource, Long> advResNeeded = new HashMap<>();

        Map<IResource, Integer> undoneObj = this.getUndoneObj();
        Set<IResource> undoneRes = new HashSet<>(undoneObj.keySet());
        Set<IResource> advancedResource = new HashSet<IResource>(Arrays.asList(AdvResource.values()));

        undoneRes.retainAll(advancedResource);

        for (IResource iResource : undoneRes) {
            advResNeeded.put((AdvResource) iResource, (long) undoneObj.get(iResource));
        }

        return advResNeeded;
    }
}


package fr.unice.polytech.ogl.islbd.strategy;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.*;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.Reachability;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import fr.unice.polytech.ogl.islbd.objective.IResource;

import java.util.*;

/**
 * Class BehaviourSimple : implements a strategy to search the resources from
 * the objectives and exploit them.
 *
 */
public class BehaviourSimple implements IBehaviourStrategy {
	private Memory memory;

	public BehaviourSimple(Memory memory) {
		this.memory = memory;
	}

	/**
	 * Intelligence of the bot. It's here he chooses what to do according to the
	 * context. This bot has 3 parts :
	 * 
	 * - The landing part : when the bot arrives on the Island for the first
	 * time, he chooses to Glimpse to the farthest point in each four
	 * directions.
	 * 
	 * - The research part : once the bot has land, he searches where to go
	 * according to the objectives. Then he tries to reach the tile that he
	 * chose.
	 * 
	 * - The exploit part : at this point, the bot has reached the tile he was
	 * looking for. So he must exploit the resources on it (according to the
	 * objectives). To do this, if he's sure that the tile containsBiome the
	 * resource, he exploit it, else he explores the tile to update the
	 * information in the memory.
	 */
	@Override
	public Action chooseNextAction() {
		Action decision = null;
		Coordinate destination = null;

		decision = shouldILand();
		if (decision == null) {
			decision = shouldITransform();
		}

		// If this initialization has been done already, we run the "normal"
		// behavior
		if (decision == null) {
			List<IResource> objectives = new ArrayList<>(memory
					.getResourceNeeded().keySet());

			// We retrieve the nearest tile which may containsBiome some
			// interesting resources
			// destination = getNearestKnownTileWithMostAdvancedResource();
			destination = getMostInterestingTile();

			// If we know where to find a resource
			if (destination != null) {
				// Tile destTile = tiles.get(destination);
				Tile destTile = memory.getTile(destination);

				// We try to reach the tile destTile
				decision = reachTile(destTile, destination);

				// If we stand on the tile, we exploit it
				if (decision == null) {
					decision = exploitTile(destTile);
				}

			} else {
				decision = glimpseAround();
				if (decision == null) {
					decision = searchResources();
				}
			}
		}

		return decision;
	}

	/**
	 * If there is enough BasicResources to transform into at least 10%
	 * AdvancedResources (present in the objectives), this method returns the
	 * corresponding action Transform.
	 * 
	 * @return The action Transform if there is enough resources to transform,
	 *         else null.
	 */
	public Action shouldITransform() {
		Map<IResource, Long> resourceNeeded = memory.getResourceNeeded();
		Map<BasicResource, Integer> collected = memory.getCollected();
		for (AdvResource res : memory.getAdvancedResourceNeeded().keySet()) {
			long amount = resourceNeeded.get(res);
			int estimatedProd = res.howMuchWith(collected);
			float plateau = (float) (0.1 * amount);
			if (estimatedProd > plateau) {
				Map<BasicResource, Integer> usefulResources = res
						.filterUseless(collected);
				return new Transform(usefulResources);
			}
		}
		return null;
	}

	public Action shouldILand() {
		Action lastAction = memory.getLastAction();
		if (lastAction != null && lastAction.getName().equals("exploit")) {
			if (((Exploit) lastAction).getResource().equals(BasicResource.FISH)
					&& !memory.getUndoneObj().keySet()
							.contains(BasicResource.FISH)) {
				return new Land(memory.getActualCreek(), 1);
			}
		}
		return null;
	}

	/**
	 * For every resource objective (the basics and those which are involved in
	 * the advanced) The method select the tiles that may contain the resource,
	 * then we pull out the nearest one and we add the pair (Resource,
	 * NearestTile) in a map.
	 *
	 * The objectives are sorted thanks to this map and other data and the
	 * method picked up the best one
	 *
	 * How doest the method sort : A resource that may be present where the bot
	 * stands has the priority. Then, a resource involved in the craft of
	 * advanced resources Then, the resource objective the most completed
	 *
	 * @return the most interesting coordinate to go, or null if none found.
	 */

	public Coordinate getMostInterestingTile() {
		Coordinate destination = null;
		List<BasicResource> basicObjectives = memory.getBasicResourcesNeeded();
		Map<BasicResource, Map<Coordinate, Tile>> mapResourcesTiles = new HashMap<BasicResource, Map<Coordinate, Tile>>();

		for (BasicResource basicResource : basicObjectives) {
			Map<Coordinate, Tile> tiles = memory
					.getTilesContainingResources(Arrays.asList(basicResource));
			if (!tiles.isEmpty())
				mapResourcesTiles.put(basicResource, tiles);

		}

		BasicResource interestingRes = memory
				.getMostInterestingResource(new ArrayList<BasicResource>(
						mapResourcesTiles.keySet()));
		if (interestingRes != null)
			destination = getNearestKnownTile(mapResourcesTiles
					.get(interestingRes));

		return destination;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Action searchResources() {
		Tile curentLocation = memory.getTile(memory.getLocation());
		Map<Direction, Integer> weightedDirections = new HashMap<>();
		List<Direction> destinations = new ArrayList<>();
		int minWeight = 5;

		for (Direction direction : Direction.values()) {
			int weight = computePonderation(curentLocation, direction);
			if (weight < minWeight) {
				minWeight = weight;
			}
			weightedDirections.put(direction, weight);
		}

		// If there is at least a direction with a weight < 3
		if (minWeight < 3) {
			for (Direction direction : weightedDirections.keySet()) {
				if (weightedDirections.get(direction).equals(minWeight))
					destinations.add(direction);
			}

		} else {
			List<Tile> nearestTiles = nearestKnownUnvisitedTiles(curentLocation);
			for (Tile tile : nearestTiles) {
				destinations.add(findOrientation(tile.getCoordinate()));
			}
		}

		removeUnreachable(destinations);
		if (destinations.isEmpty()) {
			destinations = memory.getReachableDirections();
		}

		Action decision = new Move(getRandomDirection(destinations));

		return decision;
	}

	public void removeUnreachable(List<Direction> directions) {
		if (directions != null) {
			for (int i = directions.size(); --i >= 0;) {
				Tile tile = memory.getTile(directions.get(i));
				if (tile == null
						|| !tile.getReachability().equals(
								Reachability.REACHABLE)) {
					directions.remove(i);
				}
			}
		}
	}

	/**
	 * Compute the ponderation this way : 4 if an ocean is present 1 if the
	 * adjacent tile has been visited 2 if the tile after the adjacent tile has
	 * been visited 3 if both 0 otherwise
	 * 
	 * @param location
	 *            the origin tile
	 * @param dir
	 *            the direction scoped
	 * @return the ponderation
	 */
	public int computePonderation(Tile location, Direction dir) {
		Coordinate adjCoor = location.getCoordinate().getNeighbour(dir, 1);
		Coordinate remoteCoor = location.getCoordinate().getNeighbour(dir, 2);

		Tile adjTile = memory.getTile(adjCoor);
		Tile remoteTile = memory.getTile(remoteCoor);
		int ponderation = 0;
		if ((adjTile != null && (adjTile.containsWater() || adjTile
				.nextToWater(dir)))
				|| (remoteTile != null && (remoteTile.containsWater() || remoteTile
						.nextToWater(dir)))) {
			ponderation = 4;
		} else {
			if (adjTile != null && adjTile.hasBeenVisited()) {
				ponderation += 2;
			}
			if (remoteTile != null && remoteTile.hasBeenVisited()) {
				ponderation += 1;
			}
		}
		return ponderation;
	}

	/**
	 * Never keep an ocean
	 * 
	 * @param location
	 * @return
	 */
	public List<Tile> nearestKnownUnvisitedTiles(Tile location) {

		Map<Tile, Double> interestingTiles = new HashMap<>();
		List<Tile> nearestInterestingTiles = new ArrayList<>();
		double nearestDistance = Double.MAX_VALUE;
		for (Tile tile : memory.getMapTiles()) {
			// Tiles that has been glimpsed, that does not contain water and
			// that has never been visited
			if (tile.hasBeenGlimpsed() && !tile.containsWater()
					&& !tile.hasBeenVisited()) {
				double distance = tile.getCoordinate().DistanceFrom(
						location.getCoordinate());
				interestingTiles.put(tile, distance);
				// I look for the minimum distance
				if (distance < nearestDistance) {
					nearestDistance = distance;
				}
			}
		}

		for (Map.Entry<Tile, Double> entry : interestingTiles.entrySet()) {
			if (entry.getValue() == nearestDistance) {
				nearestInterestingTiles.add(entry.getKey());
			}
		}

		return nearestInterestingTiles;
	}

	/**
	 * Allow the bot to check if the four directions are Glimpsed to a distance
	 * of 3. If not, choose to Glimpse to the distance of 3. If the third tile
	 * is known but not the second, choose to Glimpse the second tile.
	 *
	 * @return The Glimpse to do or null if the four directions are already
	 *         Glimpsed.
	 */
	public Action glimpseAround() {

		for (Direction direction : Direction.values()) {
			Tile t2 = memory.getTile(direction, 2);
			Tile t1 = memory.getTile(direction, 1);
			if (!memory.hasBeenGlimpsed(direction, 3)
					&& (t2 == null || !t2.getReachability().equals(
							Reachability.UNREACHABLE))) {
				return new Glimpse(direction, 3);
			}
			if (!memory.hasBeenGlimpsed(direction, 2)
					&& (t1 == null || !t1.getReachability().equals(
							Reachability.UNREACHABLE))) {
				return new Glimpse(direction, 2);
			}
		}
		return null;
	}

	/**
	 * Return the coordinates of the nearest tile from the current location
	 * among the list of tiles in parameter.
	 *
	 * @param tiles
	 *            The list of tiles we are searching.
	 * @return The coordinates of the nearest tile.
	 */
	public Coordinate getNearestKnownTile(Map<Coordinate, Tile> tiles) {
		Coordinate mycoord = memory.getLocation();
		Coordinate nearest = null;

		for (Coordinate coord : tiles.keySet()) {
			if (nearest == null
					|| mycoord.DistanceFrom(coord) < mycoord
							.DistanceFrom(nearest))
				nearest = coord;
		}

		return nearest;
	}

	/**
	 * Decide what to do to reach the given destination. It search where is the
	 * tile and how to reach it. Depending on where is the tile and what we know
	 * of it, the methods choose to Scout, to Move, to Glimpse or to do nothing
	 * (if the current location and the destination are the same).
	 *
	 * @param tile
	 *            The destination tile
	 * @param destination
	 *            The coordinates of the destination
	 * @return The Action to do to reach the tile
	 */
	private Action reachTile(Tile tile, Coordinate destination) {
		Action decision = null;
		Coordinate mycoord = memory.getLocation();
		Tile currentTile = memory.getTile(mycoord.getX(), mycoord.getY());

		Direction direction = findOrientation(destination);

		// If we haven't reached the tile yet
		if (direction != null) {
			// If the tile we are searching is adjacent
			if (destination.DistanceFrom(mycoord) == 1) {

				// List<BasicResource> objectives = new
				// ArrayList(memory.getResourceNeeded().keySet());
				List<BasicResource> objectives = memory
						.getBasicResourcesNeeded();

				// TODO : Temporary, to fix a nullPointerException. I don't know
				// if this is the best choice
				/*
				 * TODO Try to glimpse 4. It may be a waste of pa if we know the
				 * tile beyond the unknown tile, but i think most of the time we
				 * don't If we glimpse 2 instead, well maybe it's better to
				 * scout. I don't know
				 */
				// If the tile is unknown it means we're here because it's the
				// closest unknown tile. So we glimpse it !
				if (tile == null) {
					return new Scout(direction);
					// return new Glimpse(direction.toString(), 2);
				}

				// Return what we are sure that the tile containsBiome
				List<BasicResource> retrievableResources = tile
						.doContainResourceAmong(objectives);

				// If resources unknown, Scout
				if (retrievableResources.isEmpty()) {
					decision = new Scout(direction);

					// else move (if there is no resources, this tile will no
					// longer be searched)
				} else {
					// Don't test if the tile is reachable, because this move
					// happen when we try to reach a tile producing a resource,
					// which is a reachable tile, implicitly
					decision = new Move(direction);
				}

			} else {
				// If current tile unknown, Glimpse the tile
				if (currentTile == null) {
					decision = new Glimpse(Direction.N, 1);

					// Else move to direction
				} else {
					// This move happen if we try to get close to a resource
					// tile or to a tile we want to know about so we check if
					// it's reachable
					// If we don't know, we first glimpse it
					// If we know, then logically it's a resource tile so it's
					// reachable so for now we just check if we know
					// Coordinate tileToReach=
					// memory.getLocation().getNeighbour(direction);
					if (memory.getTile(direction) == null) {
						decision = new Glimpse(direction, 2);
					} else {
						decision = new Move(direction);
					}
				}

			}
		}

		return decision;
	}

	/**
	 * Take the decision to Exploit or Explore the given tile.
	 *
	 * @param tile
	 *            The tile to Exploit. The tile must be the tile of the current
	 *            location.
	 * @return The Action to do.
	 */
	public Action exploitTile(Tile tile) {
		// Being here means we stand on a tile that may contain a resource
		Action decision = null;

		if (tile.producedResourcesUpToDate()) {
			List<BasicResource> resources = tile.doContainResourceAmong(memory
					.getBasicResourcesNeeded());

			// Should never be empty
			if (!resources.isEmpty()) {
				decision = new Exploit(resources.get(0));
			}
		} else {
			decision = new Explore();
		}

		return decision;
	}

	/**
	 * Returns in which direction is the given destination according to the
	 * current location.
	 * 
	 * @param destination
	 *            The coordinate we want to reach
	 * @return The Direction to take to reach the destination. Null if we the
	 *         coordinate of the current location and the destination are the
	 *         same.
	 */
	public Direction findOrientation(Coordinate destination) {
		Coordinate mycoord = memory.getLocation();
		Direction direction = null;

		if (mycoord.getX() < destination.getX()
				&& memory.mayBeReachable(Direction.E)) {
			direction = Direction.E;
		} else if (mycoord.getX() > destination.getX()
				&& memory.mayBeReachable(Direction.W)) {
			direction = Direction.W;
		} else if (mycoord.getY() < destination.getY()
				&& memory.mayBeReachable(Direction.N)) {
			direction = Direction.N;
		} else if (mycoord.getY() > destination.getY()
				&& memory.mayBeReachable(Direction.S)) {
			direction = Direction.S;
		}

		return direction;
	}

	/**
	 * Return a random direction among the given list of directions. If
	 * directions is null, get a random direction among the four directions.
	 * 
	 * @param directions
	 *            The list of directions
	 * @return The random Direction
	 */
	public Direction getRandomDirection(List<Direction> directions) {
		Random rand = new Random();

		if (directions == null || directions.isEmpty())
			directions = new ArrayList<>(Arrays.asList(Direction.values()));

		Direction direction = directions.get(rand.nextInt(directions.size()));

		return direction;
	}

}

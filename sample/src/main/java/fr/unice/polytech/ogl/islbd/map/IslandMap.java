package fr.unice.polytech.ogl.islbd.map;

import fr.unice.polytech.ogl.islbd.Direction;

import java.util.*;

/**
 * Create a map which will be filled by informations we found. A map is composed by tiles
 * @author user
 *
 */
public class IslandMap {
    Coordinate location = new Coordinate(0, 0);
    Map<Coordinate, Tile> map = new HashMap<>();

    public IslandMap() {
        Tile firstTile = new Tile(location, true, this);
        firstTile.setAltitude(0);
        
        map.put(location, firstTile);
    }


    //TODO : TEST
    /*public Map<Coordinate, Tile> getTilesContainingBiome(List<Biome> biomes) {
        Map<Coordinate, Tile> tilesToReturn = new HashMap<>();
        for (Biome biome : biomes) {
            for (Entry<Coordinate, Tile> coordinateTileEntry : map.entrySet()) {
                if (coordinateTileEntry.getValue().containsBiome(biome)) {
                    tilesToReturn.put(coordinateTileEntry.getKey(), coordinateTileEntry.getValue());
                }
            }
        }
        return tilesToReturn;
    }*/

    /**
     * Add a new tile to the map
     * @param coordinate the coordinate
     */
    public void addTile(Coordinate coordinate, boolean visited) {
        Tile newTile = new Tile(coordinate, visited, this);
        newTile.setAltitude(0);
        map.put(coordinate, newTile);
    }

    public double getShortestDistanceTo(Tile t) {
        Set<Tile> vertices = new HashSet<>(this.getVisitedTiles());
        Map<Tile, Double> dist = new HashMap<>();
        Set<Tile> queue = new HashSet<>(vertices);

        for (Tile tile : vertices) {
            dist.put(tile, Double.MAX_VALUE);
        }

        dist.put(map.get(location), (double) 0);

        while (!queue.isEmpty()) {
            Tile u = null;
            for (Tile tile : queue) {
                if (u == null) {
                    u = tile;
                } else {
                    if (dist.get(tile) < dist.get(u)) {
                        u = tile;
                    }
                }
            }

            queue.remove(u);

            List<Coordinate> neighbours = new ArrayList<>();
            // U should never be null, but just in case...
            if (u != null) {
                for (Coordinate neighbour : u.getCoordinate().getNeighbours()) {
                    if (vertices.contains(map.get(neighbour))) {
                        neighbours.add(neighbour);
                    }
                }
            }
            double w = 1;
            for (Coordinate coorNeighbour : neighbours) {
                Tile neighbour = map.get(coorNeighbour);
                if (dist.get(neighbour) > dist.get(u) + w) {
                    dist.put(neighbour, dist.get(u) + w);
                }
            }
        }

        return dist.get(t);
    }

    public double getShortestDistanceTo(Coordinate c) {
        return getShortestDistanceTo(map.get(c));
    }

    public List<Tile> getVisitedTiles() {
        List<Tile> visitedTiles = new ArrayList<>();
        for (Tile tile : map.values()) {
            if (tile.hasBeenVisited()) {
                visitedTiles.add(tile);
            }
        }
        return visitedTiles;
    }

    public Tile getTile(Direction direction) {
        return map.get(location.getNeighbour(direction));
    }
    
    public Tile getTile(int x, int y) {
        return map.get(new Coordinate(x, y));
    }
    public Tile getTile(Coordinate coordinate) {
        return map.get(new Coordinate(coordinate.getX(), coordinate.getY()));
    }

	public Coordinate getLocation() {
		return location;
	}

    public Map<Coordinate, Tile> getMap() {
        return map;
    }

    public void setLocation(Coordinate location) {
    	if (location==null)
    		this.location=this.getTile(new Coordinate(0,0)).getCoordinate();
    	else
    		this.location = location;
    }
}

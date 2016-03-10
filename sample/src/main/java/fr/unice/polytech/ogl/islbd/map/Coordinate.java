package fr.unice.polytech.ogl.islbd.map;

import fr.unice.polytech.ogl.islbd.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinations of a tile
 * @author user
 *
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Simply create it in the 2D environment
     * @param x
     * @param y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the value in X-axis
     * @return
     */
    public int getX() {
        return x;
    }
    
    /**
     * Return the value of the Y-axis
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Return an adjacent coordinate
     * @param direction The direction of the neighbour researched
     * @return The coordinates found
     */
    public Coordinate getNeighbour(Direction direction) {
        return getNeighbour(direction, 1);
    }

    /**
     * Return the coordinate of a distant neighbour
     * For example third tile to the north
     * @param direction The direction we're looking toward
     * @param distance How far we're looking
     * @return The coordinates found
     */
    public Coordinate getNeighbour(Direction direction, int distance) {
        int xN = this.getX();
        int yN = this.getY();
        
        distance=Math.abs(distance);
        
        switch (direction) {
            case N :
                yN += distance; break;
            case S :
                yN -= distance; break;
            case E :
                xN += distance; break;
            case W :
                xN -= distance; break;
        }
        return new Coordinate(xN, yN);
    }

    /**
     * 
     * @param other : other coordinate
     * @return the distance between the current coordinate and the given one
     *
     */
    public float DistanceFrom(Coordinate other) {
    	if (other==null)
    		other=new Coordinate(0,0);
        return (float) Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y - other.getY(), 2));
    }

    /**
     * Two coordinates are equals if they point to same location (same X and Y)
     * @param o the other object
     * @return true whether the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "["+x+";"+y+"]";
    }

    public List<Coordinate> getNeighbours() {
        List<Coordinate> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            neighbours.add(this.getNeighbour(direction));
        }
        return neighbours;
    }
}

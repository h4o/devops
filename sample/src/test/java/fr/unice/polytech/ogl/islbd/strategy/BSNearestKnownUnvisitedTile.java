package fr.unice.polytech.ogl.islbd.strategy;

import fr.unice.polytech.ogl.islbd.Direction;
import fr.unice.polytech.ogl.islbd.action.Consequence;
import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.map.Coordinate;
import fr.unice.polytech.ogl.islbd.map.IslandMap;
import fr.unice.polytech.ogl.islbd.map.Tile;
import fr.unice.polytech.ogl.islbd.memory.Memory;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BSNearestKnownUnvisitedTile {

    private BehaviourSimple behaviour;
    private Memory memory;
    private IslandMap map;
    private Glimpse gOcean;
    private Consequence cOcean;

    private Glimpse gNotOcean;
    private Consequence cNotOcean;
    private Glimpse gNotOceanWest;
    private Glimpse gNotOceanWest3;
    private Consequence cNotWest3;

    @Before
    public void setup() throws ParseException {
        String iniData = "{ \"creek\": \"4672ce1b-3732-4731-bbcc-781f8f140a13\",\"men\": 15,\"budget\": 7000,\"objective\": [{\"amount\": 1000,\"resource\": \"WOOD\"},{\"amount\": 1000,\"resource\": \"FISH\"}]}";

        memory = new Memory();
        behaviour = new BehaviourSimple(memory);
        map = new IslandMap();
        memory.setMap(map);

        memory.rememberInitialData(iniData);

        gOcean = new Glimpse(Direction.N, 2);
        cOcean = new Consequence("{ \"cost\":2,\"extras\":{\"asked_range\":2,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]]] },\"status\"\"OK\"}");

        gNotOcean = new Glimpse(Direction.E, 2);
        cNotOcean = new Consequence("{ \"cost\":2,\"extras\":{\"asked_range\":2,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"BEACH\",11.11]]] },\"status\"\"OK\"}");

        gNotOceanWest = new Glimpse(Direction.W, 2);
        gNotOceanWest3 = new Glimpse(Direction.W, 3);
        cNotWest3 = new Consequence("{ \"cost\":2,\"extras\":{\"asked_range\":3,\"report\":[ [[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"BEACH\",11.11]], [\"BEACH\"]] },\"status\"\"OK\"}");
    }

    @Test
    public void testEmptyMap() {
        assertEquals(0, behaviour.nearestKnownUnvisitedTiles(null).size());
    }

    @Test
    public void testNearestKnownUnvisitedTile() throws ParseException {

        Tile origin = map.getTile(0, 0);

        // Because the origin has been visited
        assertEquals(0, behaviour.nearestKnownUnvisitedTiles(origin).size());

        map.addTile(new Coordinate(0, 1), false);

        // Because the tile has never been glimpsed
        assertEquals(0, behaviour.nearestKnownUnvisitedTiles(origin).size());

        memory.rememberAction(gOcean);
        memory.rememberConsequences(cOcean.toString());

        // Because the tile contains water (ocean or lake)
        assertEquals(0, behaviour.nearestKnownUnvisitedTiles(origin).size());

        map.addTile(new Coordinate(1, 0), true);

        memory.rememberAction(gNotOcean);
        memory.rememberConsequences(cNotOcean.toString());
        // Because the tile has been visited
        assertEquals(0, behaviour.nearestKnownUnvisitedTiles(origin).size());


        map.addTile(new Coordinate(-1, 0), false);
        memory.rememberAction(gNotOceanWest);
        memory.rememberConsequences(cNotOcean.toString());

        List<Tile> nkut = behaviour.nearestKnownUnvisitedTiles(origin);
        assertEquals(1, nkut.size());
        assertEquals(map.getTile(-1, 0), nkut.get(0));

        map.addTile(new Coordinate(-2, 0), false);
        memory.rememberAction(gNotOceanWest3);
        memory.rememberConsequences(cNotWest3.toString());
        assertEquals(1, nkut.size());
        assertEquals(map.getTile(-1, 0), nkut.get(0));
    }

}
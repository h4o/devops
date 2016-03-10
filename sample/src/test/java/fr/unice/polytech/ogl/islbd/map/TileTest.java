package fr.unice.polytech.ogl.islbd.map;

import fr.unice.polytech.ogl.islbd.Direction;

import fr.unice.polytech.ogl.islbd.action.*;
import fr.unice.polytech.ogl.islbd.memory.GlimpseTileInfo;
import fr.unice.polytech.ogl.islbd.memory.ScoutTileInfo;
import fr.unice.polytech.ogl.islbd.memory.Storable;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;

public class TileTest {

    private Tile tileOrigin;
    private Tile anotherTile;
    private String sGlimpse4Cons;
    private String sExploreConsFur;
    private String sExploreConsFish;
    private String sScoutCons;

    private Glimpse glimpseOceanShrub;
    private Explore exploreFur;
    private Explore exploreFish;
    private Scout scoutWood;
    private GlimpseTileInfo gtiOceanShrubTrop;

    @Before
    public void setup() {
        tileOrigin = new Tile(new Coordinate(0, 0), false, new IslandMap());
        anotherTile = new Tile(new Coordinate(2, 4), false, new IslandMap());
        glimpseOceanShrub = new Glimpse(Direction.N, 4);
        exploreFur = new Explore();
        exploreFish = new Explore();
        scoutWood = new Scout(Direction.N);
        gtiOceanShrubTrop = new GlimpseTileInfo(2, glimpseOceanShrub);
        sGlimpse4Cons = "{\"cost\":4,\"extras\":{\"asked_range\":4,\"report\":[[[\"OCEAN\",53.28],[\"SHRUBLAND\",43.22],[\"OCEAN\",3.51]],[[\"SHRUBLAND\",64.78],[\"TROPICAL_SEASONAL_FOREST\",24.12],[\"OCEAN\",11.11]],[\"SHRUBLAND\",\"TROPICAL_SEASONAL_FOREST\"],[\"TROPICAL_SEASONAL_FOREST\"]]},\"status\":\"OK\"}";
        sExploreConsFur = "{\"cost\":7,\"extras\":{\"resources\":[{\"amount\":\"MEDIUM\",\"resource\":\"FUR\",\"cond\":\"FAIR\"}],\"pois\":[]}}";
        sExploreConsFish = "{\"cost\":7,\"extras\":{\"resources\":[{\"amount\":\"MEDIUM\",\"resource\":\"FISH\",\"cond\":\"FAIR\"}],\"pois\":[]}}";
        sScoutCons = "{\"cost\":6,\"extras\":{\"altitude\":1,\"resources\":[\"WOOD\"]},\"status\":\"OK\"}";

        glimpseOceanShrub.setConsequence(new Consequence(sGlimpse4Cons));
        exploreFur.setConsequence(new Consequence(sExploreConsFur));
        exploreFish.setConsequence(new Consequence(sExploreConsFish));
        scoutWood.setConsequence(new Consequence(sScoutCons));
    }

    @Test
    public void testGetLastActionInfo() throws Exception {
        tileOrigin.addActionInfo(scoutWood);
        assertEquals(tileOrigin.getLastActionInfo(), scoutWood);

        tileOrigin.addActionInfo(glimpseOceanShrub);
        assertEquals(tileOrigin.getLastActionInfo(), glimpseOceanShrub);

        tileOrigin.addActionInfo(exploreFish);
        tileOrigin.addActionInfo(exploreFur);
        assertEquals(tileOrigin.getLastActionInfo(), exploreFur);
    }

    @Test
    public void testContainsBiome() throws Exception {
        tileOrigin.addActionInfo(gtiOceanShrubTrop);
        assertEquals(true, tileOrigin.containsBiome(Biome.OCEAN));
        assertEquals(true, tileOrigin.containsBiome(Biome.TROPICAL_SEASONAL_FOREST));
        assertEquals(true, tileOrigin.containsBiome(Biome.SHRUBLAND));

        assertEquals(false, tileOrigin.containsBiome(Biome.MANGROVE));
    }

    @Test
    public void testDoContainResourceAmong() throws Exception {
        List<BasicResource> resources = new ArrayList<>();
        // Because the resource list is empty
        assertEquals(tileOrigin.doContainResourceAmong(resources).size(), 0);

        resources.add(BasicResource.FISH);
        // Because we don't know if we can find a fish as the tile has not been scouted nor explored yet
        assertEquals(tileOrigin.doContainResourceAmong(resources).size(), 0);

        tileOrigin.addActionInfo(exploreFur);
        // Because fur has been found, but not fish
        assertEquals(tileOrigin.doContainResourceAmong(resources).size(), 0);

        tileOrigin.addActionInfo(exploreFish);
        List<BasicResource> resourcesAmong = tileOrigin.doContainResourceAmong(resources);
        // True because we just explored the tile and found some fish
        assertEquals(true, resourcesAmong.contains(BasicResource.FISH));

        resources.add(BasicResource.WOOD);
        resourcesAmong = tileOrigin.doContainResourceAmong(resources);
        // But no wood :(
        assertEquals(false, resourcesAmong.contains(BasicResource.WOOD));

        // Now let's scout some wood !
        tileOrigin.addActionInfo(new ScoutTileInfo(scoutWood));
        resourcesAmong = tileOrigin.doContainResourceAmong(resources);
        assertEquals(true, resourcesAmong.contains(BasicResource.WOOD));

        // Doesn't work with FISH anymore, because the last tileInfo is taken(here the scout which only say about wood)
        assertEquals(false, resourcesAmong.contains(BasicResource.FISH));

        // Just to be sure
        assertEquals(false, resourcesAmong.contains(BasicResource.QUARTZ));
    }

    @Test
    public void testMayContainResourceAmong() throws Exception {
        List<BasicResource> resources = new ArrayList<>();

        // Because no information yet
        assertEquals(0, tileOrigin.mayContainResourceAmong(resources).size());

        resources.add(BasicResource.FISH);
        // Still because no information
        assertEquals(0, tileOrigin.mayContainResourceAmong(resources).size());

        tileOrigin.addActionInfo(gtiOceanShrubTrop);
        List<BasicResource> resourceAmong = tileOrigin.mayContainResourceAmong(resources);
        assertEquals(true, resourceAmong.contains(BasicResource.FISH));
        // Because it was not told to look for wood
        assertEquals(false, resourceAmong.contains(BasicResource.WOOD));

        resources.add(BasicResource.WOOD);
        resourceAmong = tileOrigin.mayContainResourceAmong(resources);
        // Now it is
        assertEquals(true, resourceAmong.contains(BasicResource.WOOD));

        resources.add(BasicResource.QUARTZ);
        resourceAmong = tileOrigin.mayContainResourceAmong(resources);
        // No information about quartz
        assertEquals(false, resourceAmong.contains(BasicResource.QUARTZ));

        // Remember we used to think fish could be exploited here
        assertEquals(true, resourceAmong.contains(BasicResource.FISH));
        tileOrigin.addActionInfo(new ScoutTileInfo(scoutWood));
        resourceAmong = tileOrigin.mayContainResourceAmong(resources);
        // After a scout, the resources actually produced are up to date. It happens that the tile only produce WOOD
        assertEquals(false, resourceAmong.contains(BasicResource.FISH));
        assertEquals(true, resourceAmong.contains(BasicResource.WOOD));

        tileOrigin.addActionInfo(gtiOceanShrubTrop);
        resourceAmong = tileOrigin.mayContainResourceAmong(resources);
        // Nope, false, because the produced resources are still up to date
        assertEquals(false, resourceAmong.contains(BasicResource.FISH));
    }

    @Test
    public void testProducedResourcesUpToDate() throws Exception {
        assertTrue(!tileOrigin.producedResourcesUpToDate());
        tileOrigin.addActionInfo(new ScoutTileInfo(new Scout(Direction.E)));
        assertTrue(tileOrigin.producedResourcesUpToDate());
        tileOrigin.addActionInfo(new Exploit(BasicResource.WOOD));
        assertTrue(tileOrigin.producedResourcesUpToDate());

        anotherTile.addActionInfo(new Explore());
        assertTrue(anotherTile.producedResourcesUpToDate());
    }

    @Test
    public void testAmountResourceUpToDate() throws Exception {
        Tile tile = new Tile(new Coordinate(0, 0), false, new IslandMap());
        assertTrue(!tile.amountResourceUpToDate());
        tile.addActionInfo(new ScoutTileInfo(new Scout(Direction.E)));
        assertTrue(!tile.amountResourceUpToDate());
        tile.addActionInfo(new Explore());
        assertTrue(tile.amountResourceUpToDate());
        tile.addActionInfo(new Exploit(BasicResource.WOOD));
        assertTrue(!tile.amountResourceUpToDate());
    }

    @Test
    public void testGetBiomes() throws Exception {
        assertEquals(tileOrigin.getBiomes().size(), 0);

        tileOrigin.addActionInfo(new GlimpseTileInfo(1, glimpseOceanShrub));
        Map<Biome, Double> biomes = tileOrigin.getBiomes();
        Set<Biome> checkedBiomeSet = new HashSet<>();
        checkedBiomeSet.add(Biome.OCEAN);
        checkedBiomeSet.add(Biome.SHRUBLAND);

        assertArrayEquals(biomes.keySet().toArray(), checkedBiomeSet.toArray());
    }
}

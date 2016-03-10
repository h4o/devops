package fr.unice.polytech.ogl.islbd.memory;

import fr.unice.polytech.ogl.islbd.action.Glimpse;
import fr.unice.polytech.ogl.islbd.map.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class containsBiome the info of a glimpse relative to a tile
 */
public class GlimpseTileInfo implements Storable {
    int range;
    Glimpse glimpse;

    public GlimpseTileInfo(int range, Glimpse glimpse) {
        this.range = range;
        this.glimpse = glimpse;
    }

    /**
     * Test if the glimpse tell us the tile is unreachable
     * @return false if the glimpse tell us the tile is unreachable
     */
    public boolean isNotReachable() {
        return (range > glimpse.getBiomeInfo().size());
    }

    @Override
    public String getName() {
        return "glimpse_tile_info";
    }

    /**
     * return the range
     * @return the range
     */
    public int getRange() {
        return range;
    }

    public Glimpse getGlimpse() {
        return glimpse;
    }

    /**
     * Return the biomes of the range concerned by this glimpse info
     * @return
     */
    public Map<Biome, Double> getBiomes() {
        Map<Biome, Double> biomes = new HashMap<>();
        List<Map<Biome, Double>> biomeInfo = getGlimpse().getBiomeInfo();
        if (range-1 < biomeInfo.size()) {
            biomes = biomeInfo.get(range-1);
        }
        return biomes;
    }
}

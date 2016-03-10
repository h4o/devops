package fr.unice.polytech.ogl.islbd.map;

import fr.unice.polytech.ogl.islbd.objective.BasicResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describes all the biome possibles in the island
 * @author user
 *
 */
public enum Biome {
    OCEAN(new ArrayList<BasicResource>(Arrays.asList(BasicResource.FISH))),
    LAKE(new ArrayList<BasicResource>(Arrays.asList(BasicResource.FISH))),
    SHRUBLAND(new ArrayList<BasicResource>(Arrays.asList(BasicResource.FUR))),
    TROPICAL_SEASONAL_FOREST(new ArrayList<BasicResource>(Arrays.asList(BasicResource.WOOD,BasicResource.SUGAR_CANE,BasicResource.FRUITS))),
    TROPICAL_RAIN_FOREST(new ArrayList<BasicResource>(Arrays.asList(BasicResource.WOOD,BasicResource.SUGAR_CANE,BasicResource.FRUITS))),
    BEACH(new ArrayList<BasicResource>(Arrays.asList(BasicResource.FISH, BasicResource.QUARTZ))),
    MANGROVE(new ArrayList<BasicResource>(Arrays.asList(BasicResource.WOOD,BasicResource.FLOWER))),
	UNKNOWN(new ArrayList<BasicResource>());

    private List<BasicResource> resources; //Resources we can find in this biome

	Biome(ArrayList<BasicResource> r) {
		this.resources = r;
	}
	
	/**
	 * Returns the biome corresponding to the String
	 * 
	 * @param res: the name of the biome we're looking for
	 * @return the biome we're looking for
	 */
	public static Biome getBiome(String res) {
		for (Biome b : Biome.values()) {
			if (res.toUpperCase().equals(b.toString())) {
				return b;
			}
		}
		return null;
	}

	public static List<Biome> biomesConcerningResources(List<BasicResource> resources) {
		List<Biome> biomes = new ArrayList<>();

		for (BasicResource resource : resources) {
			for (Biome biome : Biome.values()) {
				if (biome.resources.contains(resource) && !biomes.contains(biome)) {
					biomes.add(biome);
				}
			}
		}
		
		return biomes;
	}

	public List<BasicResource> getResources() {
		return resources;
	}
}

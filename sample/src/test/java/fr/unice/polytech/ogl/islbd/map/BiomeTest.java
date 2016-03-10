package fr.unice.polytech.ogl.islbd.map;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.unice.polytech.ogl.islbd.objective.BasicResource;

@RunWith(value=Parameterized.class)
public class BiomeTest extends TestCase{
	
	private BasicResource[] resources;
	private Biome[] expected;
	
	@Parameters
	 public static Collection data() {
		 return Arrays.asList( new Object[][] {
							 { new BasicResource[] { BasicResource.FISH }, new Biome[]{ Biome.OCEAN, Biome.LAKE, Biome.BEACH } }, 
							 { new BasicResource[] { BasicResource.FLOWER }, new Biome[]{ Biome.MANGROVE } },
							 { new BasicResource[] { BasicResource.FRUITS }, new Biome[]{ Biome.TROPICAL_SEASONAL_FOREST, Biome.TROPICAL_RAIN_FOREST } },
							 { new BasicResource[] { BasicResource.FUR }, new Biome[]{ Biome.SHRUBLAND } },
							 { new BasicResource[] { BasicResource.ORE }, new Biome[]{} },
							 { new BasicResource[] { BasicResource.QUARTZ }, new Biome[]{ Biome.BEACH } },
							 { new BasicResource[] { BasicResource.SUGAR_CANE }, new Biome[] { Biome.TROPICAL_SEASONAL_FOREST, Biome.TROPICAL_RAIN_FOREST } },
							 { new BasicResource[] { BasicResource.WOOD }, new Biome[] { Biome.TROPICAL_SEASONAL_FOREST, Biome.TROPICAL_RAIN_FOREST, Biome.MANGROVE } },
							 { new BasicResource[] {}, new Biome[] {} },
							 { new BasicResource[] {BasicResource.FLOWER, BasicResource.QUARTZ}, new Biome[] { Biome.MANGROVE, Biome.BEACH } },
						 	});
	 } 
	
	public BiomeTest(BasicResource[] resources, Biome[] biomes) {		
		this.resources = resources;
		this.expected = biomes;
	}
	
	@Test
	public void testBiomesConcerningResources() {
		assertArrayEquals(expected, Biome.biomesConcerningResources(Arrays.asList(resources)).toArray());
	}

}

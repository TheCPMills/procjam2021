package Biomes;

import java.util.Random;

// Biome Info
/**
 * Tundra - snow and ice Taiga - snowy grass and boreal trees Mountain - stone
 * and gravel Grassland - grass and shrubs Forest - grass and oak trees Jungle -
 * mud, moss, and mahogany trees Savanna - tropical grass and acacia trees
 * Desert - sand, clay, and cacti Ocean - sand, water, and palm tees
 */
//private enum BiomeType {TUNDRA, MOUNTAIN, TAIGA, GRASSLANDS, FOREST, JUNGLE, SAVANNA, DESERT, OCEAN};
public class BiomeInfo {
    private Random RNG;

    public Biome primaryBiome = null;
    public float proportion; // 0 never happens, 1 is primary only
    public Biome secondaryBiome = null;

    public BiomeInfo(Random RNG) {
        this.RNG = RNG;
    }

    public void addBiome(Biome biome, float proportion) {
        if (primaryBiome == null) {
            this.primaryBiome = biome;
            this.proportion = proportion;
        } else if (secondaryBiome == null) {
            this.secondaryBiome = biome;
        }
    }

    public double getBiomeHeight(float baseHeight, float maxDeviation) {
        float primaryHeight = baseHeight;
        float secondaryHeight = baseHeight;

        primaryHeight += primaryBiome.getHeight() * maxDeviation;
        if (secondaryBiome != null) {
            secondaryHeight += secondaryBiome.getHeight() * maxDeviation;
        }
        // still 0 to 1, but smoothed
        float trigonometricProportion = -0.5f * (float) Math.cos(proportion * Math.PI) + 0.5f;

        return (primaryHeight - secondaryHeight) * trigonometricProportion + secondaryHeight;
    }

    public int getLushColor(){
        float randomVal = RNG.nextFloat();
        return primaryBiome.getLushColor(randomVal, proportion, secondaryBiome);
    }
}

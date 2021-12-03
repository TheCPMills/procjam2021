package util.biomes;
import java.util.Random;
import util.*;
import util.block.Block;

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

    public Block getLush() {
        float randomVal = (float) RNG.nextFloat();
        return primaryBiome.getLush(randomVal, proportion, secondaryBiome);
    }

    public Block getSoil() {
        float randomVal = (float) RNG.nextFloat();
        return primaryBiome.getSoil(randomVal, proportion, secondaryBiome);
    }
}
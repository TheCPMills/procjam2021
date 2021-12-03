package util.biomes;
import util.*;
import util.block.Block;

public abstract class Biome {

    public abstract Block getLush();

    public abstract Block getSoil();

    public Block getLush(float randomVal, float proportion, Biome secondaryBiome) {
        return getLush(randomVal < proportion, secondaryBiome);
    }

    public Block getLush(Boolean isPrimary, Biome secondaryBiome) {
        if (secondaryBiome == null || isPrimary) {
            return getLush();
        } else {
            return secondaryBiome.getLush();
        }
    }

    public Block getSoil(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoil(randomVal < proportion, secondaryBiome);
    }

    public Block getSoil(Boolean isPrimary, Biome secondaryBiome) {
        if (secondaryBiome == null || isPrimary) {
            return getSoil();
        } else {
            return secondaryBiome.getSoil();
        }
    }

    public float getHeight() {
        return 0;
    }
}
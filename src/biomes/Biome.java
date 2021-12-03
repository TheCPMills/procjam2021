package biomes;
public abstract class Biome {

    public abstract int getLush();

    public abstract int getSoil();

    public int getLush(float randomVal, float proportion, Biome secondaryBiome) {
        return getLush(randomVal < proportion, secondaryBiome);
    }

    public int getLush(Boolean isPrimary, Biome secondaryBiome) {
        if (secondaryBiome == null || isPrimary) {
            return getLush();
        } else {
            return secondaryBiome.getLush();
        }
    }

    public int getSoil(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoil(randomVal < proportion, secondaryBiome);
    }

    public int getSoil(Boolean isPrimary, Biome secondaryBiome) {
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
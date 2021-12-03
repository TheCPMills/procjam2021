package Biomes;

public abstract class Biome {
    
    public abstract int getLushColor();

    public abstract int getSoilColor();

    public int getLushColor(float randomVal, float proportion, Biome secondaryBiome) {
        return getLushColor(randomVal < proportion, secondaryBiome);
    }

    public int getLushColor(Boolean isPrimary, Biome secondaryBiome) {
        if (secondaryBiome == null || isPrimary) {
            return getLushColor();
        }
        else {
            return secondaryBiome.getLushColor();
        }
    }

    public int getSoilColor(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoilColor(randomVal < proportion, secondaryBiome);
    }

    public int getSoilColor(Boolean isPrimary, Biome secondaryBiome) {
        if (secondaryBiome == null || isPrimary) {
            return getSoilColor();
        } else {
            return secondaryBiome.getSoilColor();
        }
    }

    public float getHeight() {
        return 0;
    }
}

package Biomes;

public abstract class Biome {
    
    public abstract int getLushColor();

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

    public float getHeight() {
        return 0;
    }
}

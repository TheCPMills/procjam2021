package Biomes;

public final class Ocean extends Biome {
    private static Ocean INSTANCE;
    
    public static Ocean getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Ocean();
        }
        
        return INSTANCE;
    }

    private static int SAND = 0xffbeab5e;

    public int getLushColor() {
        return SAND;
    }

    public int getLushColor(float randomVal, float proportion, Biome secondaryBiome) {
        return getLushColor(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    @Override
    public float getHeight() {
        return 1;
    }
}

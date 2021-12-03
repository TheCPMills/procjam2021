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
    private static int SANDSTONE = 0xffeab676;

    public int getLushColor() {
        return SAND;
    }

    public int getLushColor(float randomVal, float proportion, Biome secondaryBiome) {
        return getLushColor(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    public int getSoilColor() {
        return SANDSTONE;
    }

    public int getSoilColor(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoilColor(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    @Override
    public float getHeight() {
        return 1;
    }
}

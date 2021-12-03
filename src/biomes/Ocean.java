package biomes;
public final class Ocean extends Biome {
    private static Ocean INSTANCE;

    public static Ocean getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Ocean();
        }

        return INSTANCE;
    }

    private static int SAND = 0xffbeab5e;
    private static int SANDSTONE = 0xffeab676;

    public int getLush() {
        return SAND;
    }

    public int getLush(float randomVal, float proportion, Biome secondaryBiome) {
        return getLush(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    public int getSoil() {
        return SANDSTONE;
    }

    public int getSoil(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoil(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    @Override
    public float getHeight() {
        return 1;
    }
}
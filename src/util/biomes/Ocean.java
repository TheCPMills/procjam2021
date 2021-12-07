package util.biomes;
import util.block.Block;

public final class Ocean extends Biome {
    private static Ocean INSTANCE;

    public static Ocean getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Ocean();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.SAND;
    }

    public Block getLush(float randomVal, float proportion, Biome secondaryBiome) {
        return getLush(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    public Block getSoil() {
        return Block.SAND;
    }

    public Block getSoil(float randomVal, float proportion, Biome secondaryBiome) {
        return getSoil(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    @Override
    public float getHeight() {
        return 1;
    }
}
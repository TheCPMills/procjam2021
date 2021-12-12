package util.biomes;

import util.block.Block;

public final class RightOcean extends Biome {
    private static RightOcean INSTANCE;

    public static RightOcean getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RightOcean();
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

    public Block getMineral() {
        return Block.LIMESTONE;
    }

    public Block getMineral(float randomVal, float proportion, Biome secondaryBiome) {
        return getMineral(randomVal < Math.min(0.95, proportion * 5), secondaryBiome);
    }

    @Override
    public float getHeight() {
        return 1;
    }

    public BiomeType getBiomeType() {
        return BiomeType.RIGHT_AQUATIC;
    }
}
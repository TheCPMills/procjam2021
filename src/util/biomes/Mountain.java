package util.biomes;
import util.block.Block;

public final class Mountain extends Biome {
    private static Mountain INSTANCE;

    public static Mountain getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Mountain();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.GRAVEL;
    }

    public Block getSoil() {
        return Block.GRAVEL;
    }

    public Block getMineral() {
        return Block.ANDESITE;
    }

    @Override
    public float getHeight() {
        return -1;
    }

    public BiomeType getBiomeType() {
        return BiomeType.ALPINE;
    }
}
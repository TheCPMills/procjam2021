package util.biomes;
import util.block.Block;

public final class Forest extends Biome {
    private static Forest INSTANCE;

    public static Forest getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Forest();
        }

        return INSTANCE;
    }
    
    public Block getLush() {
        return Block.GRASS;
    }

    public Block getSoil() {
        return Block.DIRT;
    }
}
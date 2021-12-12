package util.biomes;
import util.block.Block;

public class Taiga extends Biome {
    private static Taiga INSTANCE;

    public static Taiga getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Taiga();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.FROSTED_GRASS;
    }

    public Block getSoil() {
        return Block.DIRT;
    }

    public Block getMineral() {
        return Block.DIORITE;
    }
    
    public BiomeType getBiomeType() {
        return BiomeType.TAIGA;
    }
}

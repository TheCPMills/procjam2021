package util.biomes;
import util.block.Block;

public class Savanna extends Biome {
    private static Savanna INSTANCE;

    public static Savanna getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Savanna();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.DRY_GRASS;
    }

    public Block getSoil() {
        return Block.CLAY;
    }

    public Block getMineral() {
        return Block.GRANITE;
    }

    public BiomeType getBiomeType() {
        return BiomeType.SAVANNA;
    }
    
}

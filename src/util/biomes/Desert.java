package util.biomes;
import util.block.Block;

public class Desert extends Biome {
    private static Desert INSTANCE;

    public static Desert getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Desert();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.SAND;
    }

    public Block getSoil() {
        return Block.SAND;
    }

    public Block getMineral() {
        return Block.SANDSTONE;
    }

    public BiomeType getBiomeType() {
        return BiomeType.DESERT;
    }
    
}

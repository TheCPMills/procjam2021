package util.biomes;
import util.block.Block;

public class Grassland extends Biome {
    private static Grassland INSTANCE;

    public static Grassland getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Grassland();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.TEMPERATE_GRASS;
    }

    public Block getSoil() {
        return Block.DIRT;
    }

    public Block getMineral() {
        return Block.MARBLE;
    }

    public BiomeType getBiomeType() {
        return BiomeType.GRASSLAND;
    }
    
}

package util.biomes;
import util.block.Block;

public class Jungle extends Biome {
    private static Jungle INSTANCE;

    public static Jungle getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Jungle();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.TROPICAL_GRASS;
    }

    public Block getSoil() {
        return Block.MUD;
    }

    public Block getMineral() {
        return Block.MOSSY_COBBLESTONE;
    }
    
    public BiomeType getBiomeType() {
        return BiomeType.JUNGLE;
    }
}

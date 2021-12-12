package util.biomes;
import util.block.Block;

public class Tundra extends Biome {
    private static Tundra INSTANCE;

    public static Tundra getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Tundra();
        }

        return INSTANCE;
    }

    public Block getLush() {
        return Block.SNOW;
    }

    public Block getSoil() {
        return Block.SNOW;
    }

    public Block getMineral() {
        return Block.ICE;
    }

    public BiomeType getBiomeType() {
        return BiomeType.TUNDRA;
    }
}

package biomes;
public final class Forest extends Biome {
    private static Forest INSTANCE;

    public static Forest getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Forest();
        }

        return INSTANCE;
    }

    private static int GRASS = 0xff2ac073;
    private static int DIRT = 0xff976b4b;

    public int getLush() {
        return GRASS;
    }

    public int getSoil() {
        return DIRT;
    }
}
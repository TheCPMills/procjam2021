package Biomes;

public final class Forest extends Biome {
    private static Forest INSTANCE;
    
    public static Forest getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Forest();
        }
        
        return INSTANCE;
    }

    private static int GRASS = 0xff2ac073;

    public int getLushColor() {
        return GRASS;
    }
}

package Biomes;

public final class Forest extends Biome {
    private static Forest INSTANCE;
    
    public static Forest getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Forest();
        }
        
        return INSTANCE;
    }
}

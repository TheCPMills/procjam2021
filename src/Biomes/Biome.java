package Biomes;

public class Biome {
    private static Biome INSTANCE;
    
    public static Biome getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Biome();
        }
        
        return INSTANCE;
    }

    public float getHeight() {
        return 0;
    }
}

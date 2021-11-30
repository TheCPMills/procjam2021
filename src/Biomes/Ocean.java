package Biomes;

public final class Ocean extends Biome {
    private static Ocean INSTANCE;
    
    public static Ocean getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Ocean();
        }
        
        return INSTANCE;
    }

    @Override
    public float getHeight() {
        return 1;
    }
}

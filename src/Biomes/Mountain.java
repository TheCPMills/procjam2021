package Biomes;

public final class Mountain extends Biome{
    private static Mountain INSTANCE;
    
    public static Mountain getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Mountain();
        }
        
        return INSTANCE;
    }

    @Override
    public float getHeight() {
        return -1;
    }
}

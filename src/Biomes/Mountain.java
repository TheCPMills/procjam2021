package Biomes;

public final class Mountain extends Biome{
    private static Mountain INSTANCE;
    
    public static Mountain getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Mountain();
        }
        
        return INSTANCE;
    }

    private static int GRASS = 0xff2ac073;
    private static int DIRT = 0xff976b4b;

    public int getLushColor() {
        return GRASS;
    }

    public int getSoilColor() {
        return DIRT;
    }

    @Override
    public float getHeight() {
        return -1;
    }
}

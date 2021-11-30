import java.util.*;
import javanoise.random.*;

public class BiomeGenerator {
    private int WIDTH;
    private int HEIGHT;
    private RNG BIOME_RNG;

    public BiomeGenerator(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        BIOME_RNG = new CBSquares(seed);
    }

    public ArrayList<BiomeInfo> generateBiomes() {
        ArrayList<BiomeInfo> biomeInfo = new ArrayList<BiomeInfo>();

        int mountainXmin = (int) (MathOps.round(BIOME_RNG.next(WIDTH * 3 / 4))) + WIDTH / 8;
        int mountainWidth = (int) (MathOps.round(BIOME_RNG.next(WIDTH / 16))) + WIDTH / 16;
        int mountainXmax = mountainWidth + mountainXmin;
        float mountainMid = (float) (mountainXmax + mountainXmin) / 2;
        int leftOceanmax = (int) (MathOps.round(BIOME_RNG.next(WIDTH / 16))) + WIDTH / 16;
        int rightOceanmin = (int) (MathOps.round(BIOME_RNG.next(WIDTH / 16))) + WIDTH * 15 / 16;

        // initialize and add oceans
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.add(new BiomeInfo());
            if (i <= leftOceanmax) {
                biomeInfo.get(i).addBiome(BiomeType.OCEAN, (1.0f - (float) i / leftOceanmax));
            } else if (i >= rightOceanmin) {
                biomeInfo.get(i).addBiome(BiomeType.OCEAN, (float) (i - rightOceanmin) / (WIDTH - rightOceanmin));
            }
        }

        // add mountain
        for (int i = 0; i < WIDTH; i++) {
            if (i >= mountainXmin && i <= mountainXmax) {
                biomeInfo.get(i).addBiome(BiomeType.MOUNTAIN,
                        1.0f - (float) (Math.abs(mountainMid - i)) / mountainWidth * 2);
            }
        }

        // add forests
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.get(i).addBiome(BiomeType.FOREST, 1);
        }

        return biomeInfo;
    }
}
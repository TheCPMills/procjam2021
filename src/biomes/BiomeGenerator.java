package biomes;

import java.util.ArrayList;
import java.util.Random;

public class BiomeGenerator {
    private int WIDTH;
    private int HEIGHT;
    private Random BIOME_RNG;

    public BiomeGenerator(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        BIOME_RNG = new Random(seed);
    }

    public ArrayList<BiomeInfo> generateBiomes() {
        ArrayList<BiomeInfo> biomeInfo = new ArrayList<BiomeInfo>();

        int mountainXmin = (int) (BIOME_RNG.nextDouble(WIDTH * 3 / 4)) + WIDTH / 8;
        int mountainWidth = (int) (BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH / 16;
        int mountainXmax = mountainWidth + mountainXmin;
        float mountainMid = (float) (mountainXmax + mountainXmin) / 2;
        int leftOceanmax = (int) (BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH / 16;
        int rightOceanmin = (int) (BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH * 7 / 8;

        // initialize and add oceans
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.add(new BiomeInfo(BIOME_RNG));
            if (i <= leftOceanmax) {
                biomeInfo.get(i).addBiome(Ocean.getInstance(), (1.0f - (float) i / leftOceanmax));
            } else if (i >= rightOceanmin) {
                biomeInfo.get(i).addBiome(Ocean.getInstance(), (float) (i - rightOceanmin) / (WIDTH - rightOceanmin));
            }
        }

        // add mountain
        for (int i = 0; i < WIDTH; i++) {
            if (i >= mountainXmin && i <= mountainXmax) {
                biomeInfo.get(i).addBiome(Mountain.getInstance(),
                        1.0f - (float) (Math.abs(mountainMid - i)) / mountainWidth * 2);
            }
        }

        // add forests
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.get(i).addBiome(Forest.getInstance(), 1);
        }

        return biomeInfo;
    }
}

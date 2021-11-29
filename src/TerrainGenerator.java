import java.awt.image.*;
import javax.imageio.*;
import javanoise.noise.*;
import javanoise.noise.fractal.*;

import java.awt.*;
import java.io.*;
import java.util.*;


public class TerrainGenerator {
    private static Random RNG;
    private static Noise REFERENCE_NOISE;
    private static Noise STONE_NOISE;
    private static int WIDTH;
    private static int HEIGHT;
    private static double HEIGHT_VARIATION;
    private static Color[][] REFERENCE_CAVE;
    private static ArrayList<Integer> locations = new ArrayList<Integer>();
    private static ArrayList<Integer> stoneLocations = new ArrayList<Integer>();
    private static ArrayList<BiomeInfo> biomes = new ArrayList<BiomeInfo>();

    // Colors
    private static int SKY = 0xff9bd1ff;
    private static int CLOUD = 0xffdfffff;
    private static int GRASS = 0xff2ac073;
    private static int DIRT = 0xff976b4b;
    private static int MOSS = 0xff35501e;
    private static int MUD = 0xff5c4449;
    private static int SAND = 0xffbeab5e;
    private static int SNOW = 0xffd3ecf1;
    private static int STONE = 0xff808080;
    private static int WATER = 0xff415897;
    private static int LAVA = 0xff8a4926;
    private static int CAVE = 0xff121223;
    private static int WOOD = 0xff996633;
    private static int LEAVES = 0xff339933;

    private enum BiomeType {FOREST, OCEAN, DESERT, MOUNTAIN};
    private static class BiomeInfo {
        public BiomeType primaryType = null;
        public float proportion; // 0 never happens, 1 is primary only
        public BiomeType secondaryType = null;

        public void addBiome(BiomeType biome, float proportion){
            if (primaryType == null){
                this.primaryType = biome;
                this.proportion = proportion;
            }
            else if (secondaryType == null){
                this.secondaryType = biome;
            }
        }

        public double getBiomeHeight(float baseHeight, float maxDeviation){
            float primaryHeight = baseHeight;
            float secondaryHeight = baseHeight;

            switch(primaryType){
                case OCEAN: primaryHeight += maxDeviation; break;
                case MOUNTAIN: primaryHeight -= maxDeviation; break;
                default:
            }
            if (secondaryType != null){
                switch(secondaryType){
                    case OCEAN: secondaryHeight += maxDeviation; break;
                    case MOUNTAIN: secondaryHeight -= maxDeviation; break;
                    default:
                }
            }
            // still 0 to 1, but smoothed
            float trigonometricProportion = -0.5f * (float)Math.cos(proportion * Math.PI) + 0.5f;

            return (primaryHeight - secondaryHeight) * trigonometricProportion + secondaryHeight;
        }
    }

    public static void generate(int seed, int width, int height, double heightVariation) {
        try {
            init(seed, width, height, heightVariation);
            BufferedImage terrainGeneration = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // Initialize reference points
            double surfaceReference = (HEIGHT * 0.2);
            double undergroundReference = (HEIGHT * 0.225);

            // Generate Biome values
            biomes = generateBiomes();

            // Generate terrain shape
            for (int i = 0; i < WIDTH; i++) {
                double baseHeight = biomes.get(i).getBiomeHeight((float)surfaceReference, (float)surfaceReference / 2);
                double baseUnderground = baseHeight + undergroundReference - surfaceReference;
                int location = (int) (baseHeight + (int) ((((0x010101 * (int) ((REFERENCE_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION) + 0.5) - ((int) (128 / HEIGHT_VARIATION)));
                int stoneLocation = (int) (baseUnderground + (int) ((((0x010101 * (int) ((STONE_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION * 2) + 0.5) - ((int) (256 / HEIGHT_VARIATION)));
                locations.add(location);
                stoneLocations.add(stoneLocation);
                for (int j = 0; j < HEIGHT; j++) {
                    if (j >= stoneLocation) {
                        terrainGeneration.setRGB(i, j, STONE);
                    } else if (j > location && j < stoneLocation) {
                        if (biomes.get(i).primaryType == BiomeType.OCEAN && RNG.nextFloat() < Math.min(biomes.get(i).proportion * 3, 0.95)) {
                            terrainGeneration.setRGB(i, j, SAND);
                        } else {
                            terrainGeneration.setRGB(i, j, DIRT);
                        }
                    } else if (j == location) {
                        terrainGeneration.setRGB(i, j, GRASS);
                    } else if (biomes.get(i).primaryType == BiomeType.OCEAN && j < location && j >= surfaceReference) {
                        terrainGeneration.setRGB(i, j, WATER);
                    } else if (j < location) {
                        terrainGeneration.setRGB(i, j, SKY);
                    }
                }
            }

            // Generate cave shape

            int[][] pixelColors = Arrays.stream(REFERENCE_CAVE).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++) {
                    if (pixelColors[col][row] == 0xFFFFFFFF && locations.get(col) <= row) {
                        terrainGeneration.setRGB(col, row, CAVE);
                    }
                }
            }

            addTrees(terrainGeneration, locations);

            File outputFile = new File("assets/2DTerrain.png");
            outputFile.getParentFile().mkdirs();
            ImageIO.write(terrainGeneration, "png", outputFile);
        } catch (IOException ex) {
            System.out.println("Could not complete operation\n" + ex.getMessage());
        }
    }

    private static void init(int seed, int width, int height, double heightVariation) throws IOException {
        REFERENCE_NOISE = new Simplex(seed);
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        STONE_NOISE = new RigidMultiFractal(seed);
        RNG = new Random(seed);

        double low = -0.1;
        double high = 0;

        REFERENCE_CAVE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new FBM(seed), low, high, width, height));
    }

    private static void addTrees(BufferedImage terrainImage, ArrayList<Integer> locations){
        for (int i = 0; i < WIDTH; i++){
            int location = locations.get(i);
            double rVal = RNG.nextDouble();
            if (rVal < 0.125 && validTreeSpace(terrainImage, i, location)){
                putTree(terrainImage, i, location);
            }
        }
    }

    private static boolean validTreeSpace(BufferedImage terrainImage, int x, int y){
        if (x < 1 || y < 10 || x > WIDTH - 2 || y > HEIGHT - 1 || terrainImage.getRGB(x, y) != GRASS){
            return false;
        }
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j >= -10; j--){
                int color = terrainImage.getRGB(x + i, y + j);
                if (color != SKY && color != 0){
                    return false;
                }
            }
        }
        return true;
    }

    private static void putTree(BufferedImage terrainImage, int x, int y){
        int flip = RNG.nextBoolean() ? 1 : 0;
        for(int i = 1; i <= 10; i++){
            if (i < 8){
                terrainImage.setRGB(x, y - i, WOOD);
                terrainImage.setRGB(x + ((i + flip) % 2)*2 - 1, y - i, LEAVES);
            }
            else{
                terrainImage.setRGB(x - 1, y - i, LEAVES);
                terrainImage.setRGB(x, y - i, LEAVES);
                terrainImage.setRGB(x + 1, y - i, LEAVES);
            }
        }
    }

    private static ArrayList<BiomeInfo> generateBiomes(){
        ArrayList<BiomeInfo> biomeInfo = new ArrayList<BiomeInfo>();

        int mountainXmin = RNG.nextInt(WIDTH * 3/4) + WIDTH / 8;
        int mountainWidth = RNG.nextInt(WIDTH / 16) + WIDTH / 16;
        int mountainXmax = mountainWidth + mountainXmin;
        float mountainMid = (float)(mountainXmax + mountainXmin) / 2;
        int leftOceanmax = RNG.nextInt(WIDTH / 16) + WIDTH / 16;
        int rightOceanmin = RNG.nextInt(WIDTH / 16) + WIDTH * 7/8;

        // initialize and add oceans
        for (int i = 0; i < WIDTH; i++){
            biomeInfo.add(new BiomeInfo());
            if (i <= leftOceanmax){
                biomeInfo.get(i).addBiome(BiomeType.OCEAN, (1.0f - (float)i / leftOceanmax));
            }
            else if (i >= rightOceanmin){
                biomeInfo.get(i).addBiome(BiomeType.OCEAN, (float)(i - rightOceanmin) / (WIDTH - rightOceanmin));
            }
        }

        // add mountain
        for (int i = 0; i < WIDTH; i++){
            if (i >= mountainXmin && i <= mountainXmax){
                biomeInfo.get(i).addBiome(BiomeType.MOUNTAIN, 1.0f - (float)(Math.abs(mountainMid - i)) / mountainWidth * 2);
            }
        }

        // add forests
        for (int i = 0; i < WIDTH; i++){
            biomeInfo.get(i).addBiome(BiomeType.FOREST, 1);
        }

        return biomeInfo;
    }

    @SuppressWarnings("unused")
    private static void generateSky() {
        // TODO: Implement sky generation
    }

    @SuppressWarnings("unused")
    private static void generateSurface() {
        // TODO: Generate surface
    }

    @SuppressWarnings("unused")
    private static void generateUnderground() {
        // TODO: Generate underground
    }

    @SuppressWarnings("unused")
    private static void generateCaverns() {
        // TODO: Generate caverns
    }
}
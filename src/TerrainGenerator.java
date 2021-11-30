import java.awt.image.*;
import javax.imageio.*;

import Biomes.*;
import javanoise.noise.*;
import javanoise.noise.fractal.*;
import javanoise.random.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Generates Terrain using Noise and Random Number Generators
 */
public class TerrainGenerator {
    // Image settings
    private static BufferedImage TERRAIN_IMAGE;
    private static int WIDTH;
    private static int HEIGHT;
    private static double HEIGHT_VARIATION;

    // Features RNGs
    private static Random BIOME_RNG;
    private static RNG TREE_RNG;
    private static RNG CLOUD_RNG;

    // Terrain Noise
    private static Noise LUSH_NOISE;
    private static Noise SOIL_NOISE;
    private static Noise MINERAL_NOISE;

    // Reference Points
    private static Color[][] CAVE_REFERENCE;
    private static Color[][] MINERAL_REFERENCE;
    private static Color[][] ORE_AND_GEM_REFERENCE;
    private static ArrayList<Integer> LUSH_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<Integer> SOIL_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<Integer> MINERAL_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<BiomeInfo> biomes = new ArrayList<BiomeInfo>();

    // Grassland/Plains Colors
    private static int GRASS = 0xff2ac073;
    private static int DIRT = 0xff976b4b;
    private static int OAK_WOOD = 0xff996633;
    private static int OAK_LEAVES = 0xff339933;

    // Jungle/Taiga Colors
    private static int MOSS = 0xff35501e;
    private static int MUD = 0xff5c4449;
    private static int MAHOGANY_WOOD = 0;
    private static int MAHOGANY_LEAVES = 0;

    // Desert Colors
    private static int SAND = 0xffbeab5e;
    private static int CLAY = 0;
    private static int CACTUS = 0;

    // Tundra Colors
    private static int SNOW = 0xffd3ecf1;
    private static int ICE = 0;
    private static int PINE_WOOD = 0;
    private static int PINE_LEAVES = 0;

    // Aquatic Colors
    private static int WATER = 0xff0099ff;
    private static int CORAL = 0;
    private static int PALM_WOOD = 0;
    private static int PALM_LEAVES = 0;

    // Ore Colors
    private static int COAL = 0;
    private static int COPPER = 0;
    private static int SIVER = 0;
    private static int IRON = 0;
    private static int GOLD = 0;
    private static int PLATNUM = 0;
    private static int COBALT = 0;
    private static int TITANIUM = 0;
    private static int PALLADIUM = 0;

    // Gem Colors
    private static int DIAMOND = 0;
    private static int RUBY = 0;
    private static int EMERALD = 0;
    private static int QUARTZ = 0;
    private static int AMETHYST = 0;
    // private static int AMBER = 0;
    // private static int TOPAZ = 0;
    // private static int SAPPHIRE = 0;
    // private static int ONYX = 0;
    // private static int OPAL = 0;
    // private static int JADE = 0;
    // private static int GARNET = 0;
    // private static int LAPIS = 0;

    // Mineral Colors
    private static int COBBLESTONE = 0xff808080;
    private static int MARBLE = 0xff858585;
    private static int GRANITE = 0xff878787;
    private static int DIORTIE = 0xff66666d;
    private static int ANDESITE = 0xff79797f;
    private static int LIMESTONE = 0xff8c8c91;
    private static int BASALT = 0xff8f8f8f;
    private static int PUMICE = 0xff7c7c7c;
    // private static int OBSIDIAN = 0;
    // private static int GRAVEL = 0;
    // private static int TUFF = 0;
    // private static int CALCITE = 0;
    // private static int FLINT = 0;
    // private static int SLATE = 0;

    // Background Colors
    private static int SKY = 0xff9bd1ff;
    private static int CLOUD = 0xffdfffff;
    private static int LAVA = 0xff8a4926;
    private static int CAVE = 0xff121223;

    // Biome Info
    /**
     * Tundra - snow and ice Taiga - snowy grass and boreal trees Mountain - stone
     * and gravel Grassland - grass and shrubs Forest - grass and oak trees Jungle -
     * mud, moss, and mahogany trees Savanna - tropical grass and acacia trees
     * Desert - sand, clay, and cacti Ocean - sand, water, and palm tees
     */
    //private enum BiomeType {TUNDRA, MOUNTAIN, TAIGA, GRASSLANDS, FOREST, JUNGLE, SAVANNA, DESERT, OCEAN};
    private static class BiomeInfo {
        public Biome primaryBiome = null;
        public float proportion; // 0 never happens, 1 is primary only
        public Biome secondaryBiome = null;

        public void addBiome(Biome biome, float proportion) {
            if (primaryBiome == null) {
                this.primaryBiome = biome;
                this.proportion = proportion;
            } else if (secondaryBiome == null) {
                this.secondaryBiome = biome;
            }
        }

        public double getBiomeHeight(float baseHeight, float maxDeviation) {
            float primaryHeight = baseHeight;
            float secondaryHeight = baseHeight;

            primaryHeight += primaryBiome.getHeight() * maxDeviation;
            if (secondaryBiome != null) {
                secondaryHeight += secondaryBiome.getHeight() * maxDeviation;
            }
            // still 0 to 1, but smoothed
            float trigonometricProportion = -0.5f * (float) Math.cos(proportion * Math.PI) + 0.5f;

            return (primaryHeight - secondaryHeight) * trigonometricProportion + secondaryHeight;
        }
    }

    /*******************************************
     ***************** METHODS *****************
     *******************************************/
    public static void generate(int seed, int width, int height, double heightVariation) {
        try {
            init(seed, width, height, heightVariation);
            TERRAIN_IMAGE = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // Initialize reference points

            double lushToSoilRatio = 0.85;
            double soilToMineralRatio = 0.95;

            double lushReference = (HEIGHT * 0.25);
            double soilReference = lushReference / lushToSoilRatio;
            double mineralReference = soilReference / soilToMineralRatio;

            double seaLevel = lushReference;

            // Generate Biome values
            biomes = generateBiomes();

            // Generate terrain shape
            for (int i = 0; i < WIDTH; i++) {
                double baseHeight = biomes.get(i).getBiomeHeight((float) lushReference, (float) lushReference / 2);
                double baseSoil = baseHeight + soilReference - lushReference;
                double baseUnderground = baseSoil + mineralReference - soilReference;
                int lushLocation = (int) (baseHeight + (int) ((((0x010101 * (int) ((LUSH_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / (HEIGHT_VARIATION * 1.5)) + 0.5) - ((int) (128 / HEIGHT_VARIATION)));
                int soilLocation = (int) (baseSoil + (int) ((((0x010101 * (int) ((SOIL_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / (HEIGHT_VARIATION)) + 0.5) - ((int) (196 / HEIGHT_VARIATION)));
                if (soilLocation < lushLocation) {
                    soilLocation = (int) (lushLocation * 1.005 + 1);
                }
                int mineralLocation = (int) (baseUnderground + (int) ((((0x010101 * (int) ((MINERAL_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION * 1.5) + 0.5) - ((int) (256 / HEIGHT_VARIATION)));
                LUSH_LOCATIONS.add(lushLocation);
                SOIL_LOCATIONS.add(soilLocation);
                MINERAL_LOCATIONS.add(mineralLocation);
                for (int j = 0; j < HEIGHT; j++) {
                    if (j >= mineralLocation) {
                        generateMinerals(i, j);
                    } else if (j >= soilLocation && j < mineralLocation) {
                        TERRAIN_IMAGE.setRGB(i, j, DIRT);
                    } else if (j >= lushLocation && j < soilLocation) {
                        if (biomes.get(i).primaryBiome instanceof Ocean && BIOME_RNG.nextDouble() < Math.min(0.95, biomes.get(i).proportion * 5)) {
                            TERRAIN_IMAGE.setRGB(i, j, SAND);
                        } else {
                            TERRAIN_IMAGE.setRGB(i, j, GRASS);
                        }
                    } else if (j >= seaLevel && j < lushLocation) {
                        TERRAIN_IMAGE.setRGB(i, j, WATER);
                    } else if (j < lushLocation) {
                        TERRAIN_IMAGE.setRGB(i, j, SKY);
                    }
                }
            }

            // Generate cave shape
            generateCaverns();

            // Generate trees
            addTrees(TERRAIN_IMAGE, LUSH_LOCATIONS);

            // Output image
            File outputFile = new File("assets/2DTerrain.png");
            ImageIO.write(TERRAIN_IMAGE, "png", outputFile);
        } catch (IOException ex) {
            System.out.println("Could not complete operation\n" + ex.getMessage());
        }
    }

    private static void init(int seed, int width, int height, double heightVariation) throws IOException {
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        LUSH_NOISE = new Simplex(seed);
        SOIL_NOISE = new Simplex((int) (seed * 1.0001));
        MINERAL_NOISE = new RigidMultiFractal(seed);
        TREE_RNG = new LCG(seed);
        CLOUD_RNG = new XORShift(seed);
        BIOME_RNG = new Random(seed);

        double low = -0.1;
        double high = 0;
        CAVE_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new FBM(seed, 0.02, 2), low, high, width, height));

        int mineralLevels = 8;
        MINERAL_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Perlin(seed), mineralLevels, width, height));

        int levels = 14 * 10 + 1; // 14 possible ores/gems each with a 10% chance of showing up
        ORE_AND_GEM_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Cellular(seed), levels, width, height));
    }

    private static void addTrees(BufferedImage terrainImage, ArrayList<Integer> locations) {
        for (int i = 0; i < WIDTH; i++) {
            int location = locations.get(i);
            double rVal = TREE_RNG.next(0, 1);
            if (rVal < 0.35 && validTreeSpace(terrainImage, i, location)) {
                putTree(terrainImage, i, location);
            }
        }
    }

    private static boolean validTreeSpace(BufferedImage terrainImage, int x, int y) {
        if (x < 1 || y < 10 || x > WIDTH - 2 || terrainImage.getRGB(x, y) != GRASS) {
            return false;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j >= -10; j--) {
                int color = terrainImage.getRGB(x + i, y + j);
                if (color != SKY && color != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void putTree(BufferedImage terrainImage, int x, int y) {
        int flip = (int) (TREE_RNG.next(0, 1) + 0.5);
        for (int i = 1; i <= 10; i++) {
            if (i < 8) {
                terrainImage.setRGB(x, y - i, OAK_WOOD);
                terrainImage.setRGB(x + ((i + flip) % 2) * 2 - 1, y - i, OAK_LEAVES);
            } else {
                terrainImage.setRGB(x - 1, y - i, OAK_LEAVES);
                terrainImage.setRGB(x, y - i, OAK_LEAVES);
                terrainImage.setRGB(x + 1, y - i, OAK_LEAVES);
            }
        }
    }

    private static ArrayList<BiomeInfo> generateBiomes() {
        ArrayList<BiomeInfo> biomeInfo = new ArrayList<BiomeInfo>();

        int mountainXmin = (int)(BIOME_RNG.nextDouble(WIDTH * 3 / 4)) + WIDTH / 8;
        int mountainWidth = (int)(BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH / 16;
        int mountainXmax = mountainWidth + mountainXmin;
        float mountainMid = (float) (mountainXmax + mountainXmin) / 2;
        int leftOceanmax = (int)(BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH / 16;
        int rightOceanmin = (int)(BIOME_RNG.nextDouble(WIDTH / 16)) + WIDTH * 7 / 8;

        // initialize and add oceans
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.add(new BiomeInfo());
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

    private static void generateClouds() {
        // TODO: Implement sky generation
    }

    private static void generateMinerals(int column, int row) {
        int mineral;
        switch (MINERAL_REFERENCE[column][row].getRed()) {
        case 0:
            mineral = COBBLESTONE;
            break;
        case 34:
            mineral = MARBLE;
            break;
        case 73:
            mineral = GRANITE;
            break;
        case 109:
            mineral = DIORTIE;
            break;
        case 145:
            mineral = ANDESITE;
            break;
        case 182:
            mineral = LIMESTONE;
            break;
        case 218:
            mineral = BASALT;
            break;
        case 255:
            mineral = PUMICE;
            break;
        default:
            mineral = 0xffffffff;
            break;
        }
        TERRAIN_IMAGE.setRGB(column, row, mineral);
    }

    private static void generateOresAndGems() {
        // TODO: Implement sky generation
    }

    private static void generateCaverns() {
        int[][] pixelColors = Arrays.stream(CAVE_REFERENCE)
                .map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                if (pixelColors[col][row] == 0xFFFFFFFF && LUSH_LOCATIONS.get(col) <= row) {
                    TERRAIN_IMAGE.setRGB(col, row, CAVE);
                }
            }
        }
    }
}
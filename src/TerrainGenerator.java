import java.awt.image.*;
import javax.imageio.*;

import javanoise.noise.*;
import javanoise.noise.fractal.*;
import javanoise.random.*;
import util.FloraGenerator;
import util.ImageProcessing;
import util.biomes.*;
import util.block.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class TerrainGenerator {
    // Image settings
    private static BufferedImage TERRAIN_IMAGE;
    private static int WIDTH;
    private static int HEIGHT;
    private static double HEIGHT_VARIATION;

    private static Tile[][] TERRAIN_DATA;

    // Features RNGs
    private static BiomeGenerator BIOME_GENERATOR;
    private static FloraGenerator FLORA_GENERATOR;
    private static TerrainDrawer TERRAIN_DRAWER;

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
    private static ArrayList<BiomeInfo> BIOMES = new ArrayList<BiomeInfo>();

    // ==========================================================================================

    private static void initialize(int seed, int width, int height, double heightVariation) {
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        
        TERRAIN_IMAGE = new BufferedImage(WIDTH * 8, HEIGHT * 8, BufferedImage.TYPE_INT_ARGB);
        TERRAIN_DATA = new Tile[WIDTH][HEIGHT];
        TERRAIN_DRAWER = new TerrainDrawer(TERRAIN_IMAGE);
        
        LUSH_NOISE = new Simplex(seed);
        SOIL_NOISE = new Simplex((int) (seed));
        MINERAL_NOISE = new RigidMultiFractal(seed);

        FLORA_GENERATOR = new FloraGenerator(WIDTH, HEIGHT, seed);
        BIOME_GENERATOR = new BiomeGenerator(WIDTH, HEIGHT, seed);

        double low = -0.025;
        double high = 0.025;
        CAVE_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new FBM(seed), low, high, width, height));
        
        // double low = -0.95;
        // double high = -0.75;
        // CAVE_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Billow(seed, 0.015, InterpolationType.Hermite, 12, 1.3, 0.71), low, high, width, height));

        int mineralLevels = 8;
        MINERAL_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Perlin(seed, 0.05), mineralLevels, width, height));

        int levels = 14 * 10 + 1; // 14 possible ores/gems each with a 10% chance of showing up
        ORE_AND_GEM_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Cellular(seed), levels, width, height));
    }

    public static void generate(int seed, int width, int height, double heightVariation) {
        initialize(seed, width, height, heightVariation);

        // Initialize reference points
        double lushToSoilRatio = 1.25;
        double soilToMineralRatio = 0.75;

        double lushReference = (HEIGHT * 0.35);
        double soilReference = lushReference / lushToSoilRatio;
        double mineralReference = soilReference / soilToMineralRatio;

        double seaLevel = lushReference * 1.05;

        // Generate Biome values
        BIOMES = BIOME_GENERATOR.generateBiomes();

        for (int i = 0; i < WIDTH; i++) {
            double baseHeight = BIOMES.get(i).getBiomeHeight((float) lushReference, (float) lushReference / 2);
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
                    TERRAIN_DATA[i][j] = new ElementalTile(BIOMES.get(i).getSoil());
                } else if (j >= lushLocation && j < soilLocation) {
                    TERRAIN_DATA[i][j] = new ElementalTile(BIOMES.get(i).getLush());
                } else if (j >= seaLevel && j < lushLocation) {
                    TERRAIN_DATA[i][j] = new ElementalTile(Block.WATER);
                } else if (j < lushLocation) {
                    TERRAIN_DATA[i][j] = new ElementalTile(Block.SKY);
                }
            }
        }

        // Generate cave shape
        generateCaverns();

        // Generate trees
        FLORA_GENERATOR.addTrees(TERRAIN_DATA, LUSH_LOCATIONS);
        draw();
    }

    private static void draw() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (TERRAIN_DATA[i][j] != null) {
                    switch (TERRAIN_DATA[i][j].block()) {
                        default:
                            TERRAIN_DRAWER.drawBlock(i * 8, j * 8, TERRAIN_DATA[i][j]);
                            break;
                    }
                }
            }
        }
        File file = new File("assets/2DTerrain.png");
        try {
            ImageIO.write(TERRAIN_IMAGE, "png", file);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    // GENERATORS
    private static void generateMinerals(int i, int j) {
        Block mineral;
        switch (MINERAL_REFERENCE[i][j].getRed()) {
            default:
            case 0:
                mineral = Block.DIORITE;
                break;
            case 34:
                mineral = Block.MARBLE;
                break;
            case 73:
                mineral = Block.GRANITE;
                break;
            case 109:
                mineral = Block.COBBLESTONE;
                break;
            case 145:
                mineral = Block.ANDESITE;
                break;
            case 182:
                mineral = Block.BASALT;
                break;
            case 218:
                mineral = Block.LIMESTONE;
                break;
            case 255:
                mineral = Block.PUMICE;
                break;
        }
        TERRAIN_DATA[i][j] = new ElementalTile(mineral);
    }

    private static void generateCaverns() {
        int[][] pixelColors = Arrays.stream(CAVE_REFERENCE).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                if (pixelColors[col][row] == 0xFFFFFFFF && LUSH_LOCATIONS.get(col) <= row) {
                    TERRAIN_DATA[col][row] = new ElementalTile(Block.CAVE);
                }
            }
        }
    }
}
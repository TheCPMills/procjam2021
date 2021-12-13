import java.awt.image.*;
import javax.imageio.*;

import javanoise.noise.*;
import javanoise.noise.fractal.*;
import util.*;
import util.biomes.*;
import util.block.*;
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
    private static StructureGenerator STRUCTURE_GENERATOR;
    private static TerrainDrawer TERRAIN_DRAWER;

    // Terrain Noise
    private static Noise LUSH_NOISE;
    private static Noise SOIL_NOISE;
    private static Noise MINERAL_NOISE;

    // Reference Points
    private static boolean[][] CAVE_REFERENCE;
    private static int[][] ORE_AND_GEM_REFERENCE;
    private static int[][] MINERAL_REFERENCE;
    private static ArrayList<Integer> LUSH_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<Integer> SOIL_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<Integer> MINERAL_LOCATIONS = new ArrayList<Integer>();
    private static ArrayList<BiomeInfo> BIOMES = new ArrayList<BiomeInfo>();

    private static void initialize(int seed, int width, int height, double heightVariation) {
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        
        TERRAIN_IMAGE = new BufferedImage(WIDTH * 8, HEIGHT * 8, BufferedImage.TYPE_INT_ARGB);
        TERRAIN_DATA = new Tile[WIDTH][HEIGHT];
        TERRAIN_DRAWER = new TerrainDrawer(TERRAIN_IMAGE);
        
        LUSH_NOISE = new Simplex(seed);
        SOIL_NOISE = new Simplex((int) (seed));
        MINERAL_NOISE = new Simplex(seed, 0.00375);

        BIOME_GENERATOR = new BiomeGenerator(WIDTH, seed);
        STRUCTURE_GENERATOR = new StructureGenerator(TERRAIN_DATA, WIDTH, HEIGHT, seed);
        FLORA_GENERATOR = new FloraGenerator(WIDTH, seed, BIOME_GENERATOR);

        int levels = 14 * 10; // 14 possible ores/gems each with a 10% chance of showing up
        ORE_AND_GEM_REFERENCE = new Cellular(seed, 0.25, CellularReturnType.CellValue).generateValues(levels, WIDTH, HEIGHT);

        double low = -0.025;
        double high = 0.025;
        CAVE_REFERENCE = new FBM(seed).generateValues(low, high, width, height);
        
        // double low = -0.95;
        // double high = -0.75;
        // CAVE_REFERENCE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(new Billow(seed, 0.015, InterpolationType.Hermite, 12, 1.3, 0.71), low, high, width, height));

        int mineralLevels = 8;
        MINERAL_REFERENCE = new Perlin(seed, 0.05).generateValues(mineralLevels, width, height);
    }

    public static void generate(int seed, int width, int height, double heightVariation) {
        initialize(seed, width, height, heightVariation);

        // Initialize reference points
        double lushToSoilRatio = 1.25;
        double soilToMineralRatio = 0.45;

        double lushReference = (HEIGHT * 0.35);
        double soilReference = lushReference / lushToSoilRatio;
        double mineralReference = soilReference / soilToMineralRatio;

        double seaLevel = lushReference * 1.05;

        // Generate biomes
        BIOMES = BIOME_GENERATOR.generateBiomes();

        for (int i = 0; i < WIDTH; i++) {
            double baseHeight = BIOMES.get(i).getBiomeHeight((float) lushReference, (float) lushReference * 0.5f);
            double baseSoil = baseHeight + soilReference - lushReference;
            double baseUnderground = baseSoil + mineralReference - soilReference;

            int lushLocation = (int) (baseHeight + (int) ((((0x010101 * (int) ((LUSH_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / (HEIGHT_VARIATION * 1.5)) + 0.5) - ((int) (128 / HEIGHT_VARIATION)));
            int soilLocation = (int) (baseSoil + (int) ((((0x010101 * (int) ((SOIL_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / (HEIGHT_VARIATION)) + 0.5) - ((int) (196 / HEIGHT_VARIATION)));
        
            if (soilLocation < lushLocation) {
                soilLocation = (int) (lushLocation * 1.005 + 1);
            }

            int mineralLocation = (int) ((baseUnderground + (int) ((((0x010101 * (int) ((MINERAL_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION * 1.5) + 0.5) - ((int) (256 / HEIGHT_VARIATION))) - 12.5);
        
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
                
                if (j >= mineralLocation) {
                    generateOresAndGems(i, j);
                }
            }
        }

        // Generate cave shape
        generateCaverns();

        // Generate dungeon
        STRUCTURE_GENERATOR.generateDungeon();

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
        file.getParentFile().mkdirs();
        try {
            ImageIO.write(TERRAIN_IMAGE, "png", file);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }

    // GENERATORS
    private static void generateMinerals(int i, int j) {
        Block mineral;
        switch (MINERAL_REFERENCE[i][j]) {
            default:
            case 0:
                mineral = Block.QUARTZITE;
                break;
            case 1:
                mineral = Block.CALCITE;
                break;
            case 2:
                mineral = Block.SLATE;
                break;
            case 3:
                mineral = BIOMES.get(i).getMineral();
                break;
            case 4:
                mineral = Block.PUMICE;
                break;
            case 5:
                mineral = Block.BASALT;
                break;
            case 6:
                mineral = Block.TUFF;
                break;
            case 7:
                mineral = Block.BIOTITE;
                break;
        }
        TERRAIN_DATA[i][j] = new ElementalTile(mineral);
    }

    private static void generateOresAndGems(int i, int j) {
        Block gem;
        switch (ORE_AND_GEM_REFERENCE[i][j]) {
            case 0:
                gem = Block.COAL;
                break;
            case 10:
                gem = Block.COPPER;
                break;
            case 20:
                gem = Block.SILVER;
                break;
            case 30:
                gem = Block.IRON;
                break;
            case 40:
                gem = Block.GOLD;
                break;
            case 50:
                gem = Block.PLATINUM;
                break;
            case 60:
                gem = Block.COBALT;
                break;
            case 70:
                gem = Block.TITANIUM;
                break;
            case 80:
                gem = Block.PALLADIUM;
                break;
            case 90:
                gem = Block.DIAMOND;
                break;
            case 100:
                gem = Block.RUBY;
                break;
            case 110:
                gem = Block.EMERALD;
                break;
            case 120:
                gem = Block.ONYX;
                break;
            case 130:
                gem = Block.AMETHYST;
                break;
            default:
                gem = null;
                break;
        }

        if (gem != null) {
            TERRAIN_DATA[i][j] = new ElementalTile(gem);
        }
    }

    private static void generateCaverns() {
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                if (CAVE_REFERENCE[col][row] && (LUSH_LOCATIONS.get(col) <= row || MINERAL_LOCATIONS.get(col) <= row)) {
                    TERRAIN_DATA[col][row] = new ElementalTile(Block.CAVE);
                }
            }
        }
    }
}
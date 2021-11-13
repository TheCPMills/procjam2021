import java.awt.image.*;
import javax.imageio.*;
import javanoise.noise.*;
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

    // Colors
    private static int GRASS = 0xFF299432;
    private static int DIRT = 0xFF60301a;
    private static int STONE = 0xFF7a7a7a;
    private static int WATER = 0xFF0020FE;
    private static int LAVA = 0xFF0020FE;
    private static int SAND = 0xFFFEDF00;
    private static int SNOW = 0xFF0020FE;
    private static int JUNGLE = 0xFF0020FE;
    private static int SKY = 0xFF6fb5d8;
    private static int WOOD = 0xFF996633;
    private static int LEAVES = 0xFF339933;

    public static void generate(Noise referenceNoise, Noise caveNoise, Noise stoneNoise, int width, int height,
            double heightVariation, int referenceRow) {
        try {
            init(referenceNoise, caveNoise, stoneNoise, width, height, heightVariation, referenceRow);
            BufferedImage terrainGeneration = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // Generate terrain shape
            for (int i = 0; i < WIDTH; i++) {
                int location = (int) ((HEIGHT * 0.2) + (int) ((((0x010101 * (int) ((REFERENCE_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION) + 0.5) - ((int) (128 / HEIGHT_VARIATION)));
                int stoneLocation = (int) ((HEIGHT * 0.25) + (int) ((((0x010101 * (int) ((STONE_NOISE.getNoise(i, 0) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION * 2) + 0.5) - ((int) (256 / HEIGHT_VARIATION)));
                locations.add(location);
                stoneLocations.add(stoneLocation);
                for (int j = 0; j < HEIGHT; j++) {
                    if (j >= stoneLocation) {
                        terrainGeneration.setRGB(i, j, STONE);
                    } else if (j > location && j < stoneLocation) {
                        terrainGeneration.setRGB(i, j, DIRT);
                    } else if (j == location) {
                        terrainGeneration.setRGB(i, j, GRASS);
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
                        terrainGeneration.setRGB(col, row, 0);
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

    private static void init(Noise referenceNoise, Noise caveNoise, Noise stoneNoise, int width, int height, double heightVariation, int referenceRow) throws IOException {
        REFERENCE_NOISE = referenceNoise;
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        STONE_NOISE = stoneNoise;
        RNG = new Random(0);

        double low = -0.1;
        double high = 0;

        NoiseMapGenerator.generateMap(caveNoise, width, height, "assets/cave-noise");
        NoiseMapGenerator.generateMap(caveNoise, 9, width, height, "assets/cave-levels");
        NoiseMapGenerator.generateMap(caveNoise, low, high, width, height, "assets/cave");
        REFERENCE_CAVE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(caveNoise, low, high, width, height));
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
        if (x < 1 || y < 10 || x > WIDTH - 2 || terrainImage.getRGB(x, y) != GRASS){
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
}
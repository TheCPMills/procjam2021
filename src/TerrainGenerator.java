import java.awt.image.*;
import javax.imageio.*;
import javanoise.noise.*;
import javanoise.noise.fractal.*;
import java.awt.*;
import java.io.*;
import java.util.*;


public class TerrainGenerator {
    private static Noise REFERENCE_NOISE;
    private static Noise STONE_NOISE;
    private static int WIDTH;
    private static int HEIGHT;
    private static double HEIGHT_VARIATION;
    private static Color[][] REFERENCE_CAVE;
    private static ArrayList<Integer> locations = new ArrayList<Integer>();
    private static ArrayList<Integer> stoneLocations = new ArrayList<Integer>();

    // Colors
    private static int GRASS = 0x299432;
    private static int DIRT = 0x60301a;
    private static int STONE = 0x7a7a7a;
    private static int WATER = 0x0020FE;
    private static int LAVA = 0x0020FE;
    private static int SAND = 0xFEDF00;
    private static int SNOW = 0x0020FE;
    private static int JUNGLE = 0x0020FE;
    private static int SKY = 0x6fb5d8;

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

            // Generate cave shape

            int[][] pixelColors = Arrays.stream(REFERENCE_CAVE).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++) {
                    if (pixelColors[col][row] == 0xFFFFFFFF && locations.get(col) <= row) {
                        terrainGeneration.setRGB(col, row, 0);
                    }
                }
            }

            File outputFile = new File("assets/2DTerrain.png");
            outputFile.getParentFile().mkdirs();
            ImageIO.write(terrainGeneration, "png", outputFile);
        }} catch (IOException ex) {
            System.out.println("Could not complete operation\n" + ex.getMessage());
        }
    }

    private static void init(Noise referenceNoise, Noise caveNoise, Noise stoneNoise, int width, int height, double heightVariation, int referenceRow) throws IOException {
        REFERENCE_NOISE = referenceNoise;
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        STONE_NOISE = stoneNoise;

        double low = -0.1;
        double high = 0;

        NoiseMapGenerator.generateMap(caveNoise, width, height, "assets/cave-noise");
        NoiseMapGenerator.generateMap(caveNoise, 9, width, height, "assets/cave-levels");
        NoiseMapGenerator.generateMap(caveNoise, low, high, width, height, "assets/cave");
        REFERENCE_CAVE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(caveNoise, low, high, width, height));
    }
}
import java.awt.image.*;
import javax.imageio.*;
import javanoise.noise.*;
import javanoise.noise.fractal.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;


public class TerrainGenerator {
    private static Noise REFERENCE_NOISE;
    private static int WIDTH;
    private static int HEIGHT;
    private static double HEIGHT_VARIATION;
    private static int REFERENCE_ROW;
    private static Color[][] REFERENCE_CAVE;

    public static void generate(Noise referenceNoise, int width, int height, double heightVariation, int referenceRow) {
        try {
            init(referenceNoise, width, height, heightVariation, referenceRow);
            BufferedImage terrainGeneration = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // Generate terrain shape
            for (int i = 0; i < WIDTH; i++) {
                int location = (HEIGHT / 2) + (int) ((((0x010101 * (int) ((REFERENCE_NOISE.getNoise(i, REFERENCE_ROW) + 1) * 127.5) & 0x00ff0000) >> 16) / HEIGHT_VARIATION) + 0.5) - ((int) (128 / HEIGHT_VARIATION));
                for (int j = 0; j < HEIGHT; j++) {
                    if (j > location) {
                        terrainGeneration.setRGB(i, j, 0x299432);
                    } else if (j == location) {
                        terrainGeneration.setRGB(i, j, 0x60301a);
                    } else {
                        terrainGeneration.setRGB(i, j, 0x6fb5d8);
                    }
                }
            }

            // Generate cave shape

            int[][] pixelColors = Arrays.stream(REFERENCE_CAVE).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            for (int col = 0; col < WIDTH; col++) {
                for (int row = 0; row < HEIGHT; row++) {
                    if (pixelColors[col][row] == 0xFFFFFFFF) {
                        terrainGeneration.setRGB(col, row, 0);
                    }
                }
            }

            File outputFile = new File("assets/2DTerrain.png");
            outputFile.getParentFile().mkdirs();
            ImageIO.write(terrainGeneration, "png", outputFile);
        } catch (IOException ex) {
            System.out.println("Could not complete operation\n" + ex.getMessage());
        }
    }

    private static void init(Noise referenceNoise, int width, int height, double heightVariation, int referenceRow) throws IOException {
        REFERENCE_NOISE = referenceNoise;
        WIDTH = width;
        HEIGHT = height;
        HEIGHT_VARIATION = heightVariation;
        REFERENCE_ROW = referenceRow;

        Noise noise = new FBM(1234567890);
        double low = -0.1;
        double high = 0;

        REFERENCE_CAVE = ImageProcessing.arrayPixels(NoiseMapGenerator.generate(noise, low, high, width, height));
    }
}
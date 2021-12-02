import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.awt.Color;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FloraGen {
    public static void generate(int x, int y, boolean flipped, BufferedImage img) throws IOException {
        int[][] blockColors = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/oak_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);

        int direction = Boolean.compare(flipped, false);

        for (int i = 0; i < blockColors.length; i++) {
            for (int j = 0; j < blockColors[i].length; j++) {
                int newX = x + ((blockColors.length - 1) * direction - (2 * direction - 1) * i);
                int newY = y - (blockColors[i].length - 1 - j);
                img.setRGB(newX, newY, blockColors[i][j]);
            }
        }
    }

    public static void main(String[] args) {
        BufferedImage img = new BufferedImage(33, 65, BufferedImage.TYPE_INT_ARGB);

        try {
            generate(0, 31, false, img);
            generate(0, 64, true, img);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File outputFile = new File("assets/FloraTest.png");
        try {
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

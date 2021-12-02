import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.*;

public class ImageProcessing {
    BufferedImage img;

    public static Color[][] arrayPixels(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedImage image = ImageIO.read(file);

        return arrayPixels(image);
    }

    public static Color[][] arrayPixels(BufferedImage image) {
        Color[][] pixelColors = new Color[image.getWidth()][image.getHeight()];
        int height = image.getHeight();
        int width = image.getWidth();

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                int rgb = image.getRGB(column, row);
                pixelColors[column][row] = new Color(rgb, true);
            }
        }

        return pixelColors;
    }

    public static List<Color> listPixels(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedImage image = ImageIO.read(file);

        return listPixels(image);
    }

    public static List<Color> listPixels(BufferedImage image) {
        List<Color> pixelColors = new LinkedList<>();
        int height = image.getHeight();
        int width = image.getWidth();

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                pixelColors.add(new Color(image.getRGB(column, row), true));
            }
        }

        return pixelColors;
    }

    public static List<Color> spiral(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedImage image = ImageIO.read(file);

        return spiral(image);
    }

    public static List<Color> spiral(BufferedImage image) {
        List<Color> pixelColors = new LinkedList<>();
        int height = image.getHeight(), width = image.getWidth();
        int i, row = 0, column = 0;

        while (row < height && column < width) {
            for (i = column; i < width; ++i) {
                pixelColors.add(new Color(image.getRGB(i, row), true));
            }
            row++;

            for (i = row; i < height; ++i) {
                pixelColors.add(new Color(image.getRGB(width - 1, i), true));
            }
            width--;

            if (row < height) {
                for (i = width - 1; i >= column; --i) {
                    pixelColors.add(new Color(image.getRGB(i, height - 1), true));
                }
                height--;
            }

            if (column < width) {
                for (i = height - 1; i >= row; --i) {
                    pixelColors.add(new Color(image.getRGB(column, i), true));
                }
                column++;
            }
        }

        return pixelColors;
    }
}
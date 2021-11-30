import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.vecmath.Point2i;
import org.javatuples.Pair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FloraGen {
    private static int OAK_WOOD = 0xff996633;
    private static int OAK_LEAVES = 0xff339933;

    private static int MAHOGONY_WOOD = 0xffff0000;
    private static int MAHOGONY_LEAVES = 0xff00ff00;

    private static Pair[] OAK_LOCATIONS = {
            new Pair<>(new Point2i(3, 0), OAK_WOOD), new Pair<>(new Point2i(4, 0), OAK_WOOD),
            new Pair<>(new Point2i(5, 0), OAK_WOOD), new Pair<>(new Point2i(6, 0), OAK_WOOD),
            new Pair<>(new Point2i(4, 1), OAK_WOOD), new Pair<>(new Point2i(5, 1), OAK_WOOD),
            new Pair<>(new Point2i(3, 2), OAK_WOOD), new Pair<>(new Point2i(4, 2), OAK_WOOD),
            new Pair<>(new Point2i(5, 2), OAK_WOOD),
            new Pair<>(new Point2i(4, 3), OAK_WOOD), new Pair<>(new Point2i(5, 3), OAK_WOOD),
            new Pair<>(new Point2i(5, 4), OAK_WOOD),
            new Pair<>(new Point2i(5, 5), OAK_WOOD), new Pair<>(new Point2i(6, 5), OAK_WOOD),
            new Pair<>(new Point2i(5, 6), OAK_WOOD), new Pair<>(new Point2i(6, 6), OAK_WOOD),
            new Pair<>(new Point2i(7, 6), OAK_WOOD),
            new Pair<>(new Point2i(3, 7), OAK_LEAVES), new Pair<>(new Point2i(4, 7), OAK_WOOD),
            new Pair<>(new Point2i(5, 7), OAK_WOOD),
            new Pair<>(new Point2i(2, 8), OAK_LEAVES), new Pair<>(new Point2i(3, 8), OAK_LEAVES),
            new Pair<>(new Point2i(4, 8), OAK_LEAVES), new Pair<>(new Point2i(5, 8), OAK_LEAVES),
            new Pair<>(new Point2i(6, 8), OAK_LEAVES), new Pair<>(new Point2i(8, 8), OAK_LEAVES),
            new Pair<>(new Point2i(1, 9), OAK_LEAVES), new Pair<>(new Point2i(2, 9), OAK_LEAVES),
            new Pair<>(new Point2i(3, 9), OAK_LEAVES), new Pair<>(new Point2i(4, 9), OAK_LEAVES),
            new Pair<>(new Point2i(5, 9), OAK_LEAVES), new Pair<>(new Point2i(6, 9), OAK_LEAVES),
            new Pair<>(new Point2i(7, 9), OAK_LEAVES), new Pair<>(new Point2i(8, 9), OAK_LEAVES),
            new Pair<>(new Point2i(1, 10), OAK_LEAVES), new Pair<>(new Point2i(2, 10), OAK_LEAVES),
            new Pair<>(new Point2i(3, 10), OAK_LEAVES), new Pair<>(new Point2i(4, 10), OAK_LEAVES),
            new Pair<>(new Point2i(5, 10), OAK_LEAVES), new Pair<>(new Point2i(6, 10), OAK_LEAVES),
            new Pair<>(new Point2i(7, 10), OAK_LEAVES), new Pair<>(new Point2i(8, 10), OAK_LEAVES),
            new Pair<>(new Point2i(0, 11), OAK_LEAVES), new Pair<>(new Point2i(1, 11), OAK_LEAVES),
            new Pair<>(new Point2i(2, 11), OAK_LEAVES), new Pair<>(new Point2i(3, 11), OAK_LEAVES),
            new Pair<>(new Point2i(4, 11), OAK_LEAVES), new Pair<>(new Point2i(5, 11), OAK_LEAVES),
            new Pair<>(new Point2i(6, 11), OAK_LEAVES), new Pair<>(new Point2i(7, 11), OAK_LEAVES),
            new Pair<>(new Point2i(8, 11), OAK_LEAVES), new Pair<>(new Point2i(9, 11), OAK_LEAVES),
            new Pair<>(new Point2i(1, 12), OAK_LEAVES), new Pair<>(new Point2i(2, 12), OAK_LEAVES),
            new Pair<>(new Point2i(3, 12), OAK_LEAVES), new Pair<>(new Point2i(4, 12), OAK_LEAVES),
            new Pair<>(new Point2i(5, 12), OAK_LEAVES), new Pair<>(new Point2i(6, 12), OAK_LEAVES),
            new Pair<>(new Point2i(7, 12), OAK_LEAVES), new Pair<>(new Point2i(8, 12), OAK_LEAVES),
            new Pair<>(new Point2i(9, 12), OAK_LEAVES),
            new Pair<>(new Point2i(1, 13), OAK_LEAVES), new Pair<>(new Point2i(2, 13), OAK_LEAVES),
            new Pair<>(new Point2i(3, 13), OAK_LEAVES), new Pair<>(new Point2i(4, 13), OAK_LEAVES),
            new Pair<>(new Point2i(5, 13), OAK_LEAVES), new Pair<>(new Point2i(6, 13), OAK_LEAVES),
            new Pair<>(new Point2i(7, 13), OAK_LEAVES), new Pair<>(new Point2i(8, 13), OAK_LEAVES),
            new Pair<>(new Point2i(2, 14), OAK_LEAVES), new Pair<>(new Point2i(3, 14), OAK_LEAVES),
            new Pair<>(new Point2i(4, 14), OAK_LEAVES), new Pair<>(new Point2i(5, 14), OAK_LEAVES),
            new Pair<>(new Point2i(6, 14), OAK_LEAVES), new Pair<>(new Point2i(7, 14), OAK_LEAVES),
            new Pair<>(new Point2i(1, 15), OAK_LEAVES), new Pair<>(new Point2i(2, 15), OAK_LEAVES),
            new Pair<>(new Point2i(4, 15), OAK_LEAVES), new Pair<>(new Point2i(5, 15), OAK_LEAVES),
            new Pair<>(new Point2i(6, 15), OAK_LEAVES), new Pair<>(new Point2i(7, 15), OAK_LEAVES),
            new Pair<>(new Point2i(8, 15), OAK_LEAVES)
    };

    private static Pair[] MAHOGONY_LOCATIONS = {
            new Pair<>(new Point2i(3, 0), MAHOGONY_WOOD), new Pair<>(new Point2i(4, 0), MAHOGONY_WOOD),
            new Pair<>(new Point2i(5, 0), MAHOGONY_WOOD), new Pair<>(new Point2i(6, 0), MAHOGONY_WOOD),
            new Pair<>(new Point2i(4, 1), MAHOGONY_WOOD), new Pair<>(new Point2i(5, 1), MAHOGONY_WOOD),
            new Pair<>(new Point2i(6, 1), MAHOGONY_WOOD),
            new Pair<>(new Point2i(3, 2), MAHOGONY_WOOD), new Pair<>(new Point2i(4, 2), MAHOGONY_WOOD),
            new Pair<>(new Point2i(5, 2), MAHOGONY_WOOD),
            new Pair<>(new Point2i(4, 3), MAHOGONY_WOOD), new Pair<>(new Point2i(5, 3), MAHOGONY_WOOD),
            new Pair<>(new Point2i(6, 3), MAHOGONY_WOOD),
            new Pair<>(new Point2i(1, 4), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 4), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(5, 4), MAHOGONY_WOOD), new Pair<>(new Point2i(6, 4), MAHOGONY_WOOD),
            new Pair<>(new Point2i(2, 5), MAHOGONY_WOOD), new Pair<>(new Point2i(5, 5), MAHOGONY_WOOD),
            new Pair<>(new Point2i(6, 5), MAHOGONY_WOOD), new Pair<>(new Point2i(7, 5), MAHOGONY_WOOD),
            new Pair<>(new Point2i(8, 5), MAHOGONY_WOOD),
            new Pair<>(new Point2i(1, 6), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 6), MAHOGONY_WOOD),
            new Pair<>(new Point2i(4, 6), MAHOGONY_WOOD), new Pair<>(new Point2i(5, 6), MAHOGONY_WOOD),
            new Pair<>(new Point2i(6, 6), MAHOGONY_WOOD), new Pair<>(new Point2i(7, 6), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 6), MAHOGONY_WOOD),
            new Pair<>(new Point2i(1, 7), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 7), MAHOGONY_WOOD),
            new Pair<>(new Point2i(3, 7), MAHOGONY_WOOD), new Pair<>(new Point2i(4, 7), MAHOGONY_WOOD),
            new Pair<>(new Point2i(5, 7), MAHOGONY_LEAVES), new Pair<>(new Point2i(7, 7), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 7), MAHOGONY_WOOD), new Pair<>(new Point2i(9, 7), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(1, 8), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 8), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(3, 8), MAHOGONY_LEAVES), new Pair<>(new Point2i(4, 8), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(5, 8), MAHOGONY_LEAVES), new Pair<>(new Point2i(6, 8), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(7, 8), MAHOGONY_LEAVES), new Pair<>(new Point2i(8, 8), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(9, 8), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(0, 9), MAHOGONY_LEAVES), new Pair<>(new Point2i(1, 9), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(2, 9), MAHOGONY_LEAVES), new Pair<>(new Point2i(3, 9), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(4, 9), MAHOGONY_LEAVES), new Pair<>(new Point2i(5, 9), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(6, 9), MAHOGONY_LEAVES), new Pair<>(new Point2i(7, 9), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 9), MAHOGONY_LEAVES), new Pair<>(new Point2i(9, 9), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(0, 10), MAHOGONY_LEAVES), new Pair<>(new Point2i(1, 10), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(2, 10), MAHOGONY_LEAVES), new Pair<>(new Point2i(3, 10), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(4, 10), MAHOGONY_LEAVES), new Pair<>(new Point2i(5, 10), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(6, 10), MAHOGONY_LEAVES), new Pair<>(new Point2i(7, 10), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 10), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(0, 11), MAHOGONY_LEAVES), new Pair<>(new Point2i(1, 11), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(2, 11), MAHOGONY_LEAVES), new Pair<>(new Point2i(3, 11), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(4, 11), MAHOGONY_LEAVES), new Pair<>(new Point2i(5, 11), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(6, 11), MAHOGONY_LEAVES), new Pair<>(new Point2i(7, 11), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 11), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(1, 12), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 12), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(3, 12), MAHOGONY_LEAVES), new Pair<>(new Point2i(4, 12), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(5, 12), MAHOGONY_LEAVES), new Pair<>(new Point2i(6, 12), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(7, 12), MAHOGONY_LEAVES), new Pair<>(new Point2i(8, 12), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(9, 12), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(1, 13), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 13), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(3, 13), MAHOGONY_LEAVES), new Pair<>(new Point2i(4, 13), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(5, 13), MAHOGONY_LEAVES), new Pair<>(new Point2i(6, 13), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(7, 13), MAHOGONY_LEAVES), new Pair<>(new Point2i(8, 13), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(1, 14), MAHOGONY_LEAVES), new Pair<>(new Point2i(2, 14), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(3, 14), MAHOGONY_LEAVES), new Pair<>(new Point2i(4, 14), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(6, 14), MAHOGONY_LEAVES), new Pair<>(new Point2i(7, 14), MAHOGONY_LEAVES),
            new Pair<>(new Point2i(8, 14), MAHOGONY_LEAVES), new Pair<>(new Point2i(4, 15), MAHOGONY_LEAVES)
    };

    public static void main(String[] args) {
        BufferedImage img = new BufferedImage(33, 33, BufferedImage.TYPE_INT_RGB);
        generateOakTree(0, 15, false, img);
        generateOakTree(0, 32, true, img);
        generateMahogonyTree(11, 15, false, img);
        generateMahogonyTree(11, 32, true, img);
        File outputFile = new File("assets/FloraTest.png");
        try {
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void generateOakTree(int x, int y, boolean flipped, BufferedImage img) {
        int direction = Boolean.compare(flipped, false);
        for (Pair<Point2i, Integer> location : (Pair<Point2i, Integer>[]) OAK_LOCATIONS) {
            img.setRGB(x + (9 * direction - (2 * Boolean.compare(flipped, false) - 1) * location.getValue0().x),
                    y - location.getValue0().y, location.getValue1());
        }
    }

    public static void generateMahogonyTree(int x, int y, boolean flipped, BufferedImage img) {
        int direction = Boolean.compare(flipped, false);
        for (Pair<Point2i, Integer> location : (Pair<Point2i, Integer>[]) MAHOGONY_LOCATIONS) {
            img.setRGB(x + (9 * direction - (2 * Boolean.compare(flipped, false) - 1) * location.getValue0().x),
                    y - location.getValue0().y, location.getValue1());
        }
    }
}

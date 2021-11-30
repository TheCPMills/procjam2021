import javanoise.random.*;
import java.util.*;

public class FloraGenerator {
    private int WIDTH;
    private int HEIGHT;
    private RNG FLORA_RNG;

    public FloraGenerator(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        FLORA_RNG = new LCG(seed);
    }

    public void addTrees(Block[][] terrainData, ArrayList<Integer> locations) {
        for (int i = 0; i < WIDTH; i += 2) {
            int location = locations.get(i);
            double rVal = FLORA_RNG.next(0, 1);
            if (rVal < 0.1 && validTreeSpace(terrainData, i, location)) {
                putTree(terrainData, i, location);
            }
        }
    }

    public boolean validTreeSpace(Block[][] terrainData, int x, int y) {
        if (x < 1 || y < 10 || x > WIDTH - 2 || Block.getBlockType(terrainData[x][y]) != 0) {
            return false;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j >= -10; j--) {
                int blockType = Block.getBlockType(terrainData[x + i][y + j]);
                if (blockType != -1) { // not sky and not empty
                    return false; // should be false
                }
            }
        }
        return true;
    }

    public void putTree(Block[][] terrainData, int x, int y) {
        int flip = (int) (FLORA_RNG.next(0, 1) + 0.5);
        for (int i = 1; i <= 10; i++) {
            if (i < 8) {
                terrainData[x][y - i] = Block.OAK_WOOD;
                terrainData[x + ((i + flip) % 2) * 2 - 1][y - i] = Block.OAK_LEAVES;
            } else {
                terrainData[x - 1][y - i] = Block.OAK_LEAVES;
                terrainData[x][y - i] = Block.OAK_LEAVES;
                terrainData[x + 1][y - i] = Block.OAK_LEAVES;
            }
        }
    }
}
package util;
import javanoise.random.*;
import util.block.Block;
import util.block.FloraTile;
import util.block.Tile;
import util.block.TileAlignment;

import java.util.*;

public class FloraGenerator {
    private int WIDTH;
    private RNG FLORA_RNG;

    public FloraGenerator(int width, int seed) {
        WIDTH = width;
        FLORA_RNG = new LCG(seed);
    }

    public void addTrees(Tile[][] terrainData, ArrayList<Integer> locations) {
        for (int i = 0; i < WIDTH; i += 2) {
            int location = locations.get(i);
            double rVal = FLORA_RNG.next(0, 1);
            if (rVal < 0.5 && validTreeSpace(terrainData, i, location)) {
                putTree(terrainData, i, location - 1);
            }
        }
    }

    public boolean validTreeSpace(Tile[][] terrainData, int x, int y) {
        if (x < 2 || y < 32 || x > WIDTH - 3 || terrainData[x][y].block().getBlockType() != 0 || terrainData[x + 1][y].block().getBlockType() != 0 || terrainData[x][y].block() != terrainData[x + 1][y].block()) {
            return false;
        }
        for (int i = -2; i <= 2; i++) {
            for (int j = -1; j >= -32; j--) {
                int blockType = terrainData[x + i][y + j].block().getBlockType();
                if (blockType != -1) { // not sky and not empty
                    return false; // should be false
                }
            }
        }
        return true;
    }

    public void putTree(Tile[][] terrainData, int x, int y) {
        int flipped = (int) (FLORA_RNG.next(0, 1) + 0.5);
        Block block;

        switch (terrainData[x][y + 1].block()) {
            default:
            case GRASS:
                block = Block.OAK_TREE;
                break;
            case SAND:
                block = Block.PALM_TREE;
                break;
        }

        terrainData[x][y - 3] = new FloraTile(block, flipped == 1, TileAlignment.LEFT_SUPER);
        terrainData[x][y - 2] = new FloraTile(block, flipped == 1, TileAlignment.LEFT_MID);
        terrainData[x][y - 1] = new FloraTile(block, flipped == 1, TileAlignment.LEFT_BASE);
        terrainData[x][y] = new FloraTile(block, flipped == 1, TileAlignment.LEFT_SUB);

        terrainData[x + 1][y - 3] = new FloraTile(block, flipped == 1, TileAlignment.RIGHT_SUPER);
        terrainData[x + 1][y - 2] = new FloraTile(block, flipped == 1, TileAlignment.RIGHT_MID);
        terrainData[x + 1][y - 1] = new FloraTile(block, flipped == 1, TileAlignment.RIGHT_BASE);
        terrainData[x + 1][y] = new FloraTile(block, flipped == 1, TileAlignment.RIGHT_SUB);
    }
}
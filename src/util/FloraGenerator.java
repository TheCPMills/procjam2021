package util;
import javanoise.random.*;
import util.biomes.*;
import util.block.*;

import java.util.*;

public class FloraGenerator {
    private int WIDTH;
    private RNG FLORA_RNG;
    private BiomeGenerator BIOME_GENERATOR;

    public FloraGenerator(int width, int seed, BiomeGenerator biomeGenerator) {
        WIDTH = width;
        FLORA_RNG = new LCG(seed);
        BIOME_GENERATOR = biomeGenerator;
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
        if (terrainData[x][y].block() == Block.SAND && terrainData[x + 1][y].block() == Block.SAND && 
            terrainData[x][y - 1].block() == Block.WATER && terrainData[x + 1][y - 1].block() == Block.WATER &&
            terrainData[x][y - 2].block() == Block.WATER && terrainData[x + 1][y - 2].block() == Block.WATER &&
            terrainData[x][y - 3].block() == Block.WATER && terrainData[x + 1][y - 3].block() == Block.WATER &&
            terrainData[x][y - 4].block() == Block.WATER && terrainData[x + 1][y - 4].block() == Block.WATER) {

            terrainData[x][y - 4] = new FloraTile(Block.CORAL, false, TileAlignment.LEFT_SUPER);
            terrainData[x][y - 3] = new FloraTile(Block.CORAL, false, TileAlignment.LEFT_MID);
            terrainData[x][y - 2] = new FloraTile(Block.CORAL, false, TileAlignment.LEFT_BASE);
            terrainData[x][y - 1] = new FloraTile(Block.CORAL, false, TileAlignment.LEFT_SUB);

            terrainData[x + 1][y - 4] = new FloraTile(Block.CORAL, false, TileAlignment.RIGHT_SUPER);
            terrainData[x + 1][y - 3] = new FloraTile(Block.CORAL, false, TileAlignment.RIGHT_MID);
            terrainData[x + 1][y - 2] = new FloraTile(Block.CORAL, false, TileAlignment.RIGHT_BASE);
            terrainData[x + 1][y - 1] = new FloraTile(Block.CORAL, false, TileAlignment.RIGHT_SUB);
            return true;
        }

        if (x < 2 || y < 32 || x > WIDTH - 3 || terrainData[x][y].block().getBlockType() != 0 || terrainData[x + 1][y].block().getBlockType() != 0 || terrainData[x][y].block() != terrainData[x + 1][y].block()) {
            return false;
        }

        for (int i = -2; i <= 2; i++) {
            for (int j = -1; j >= -32; j-- ) {
                int blockType = terrainData[x + i][y + j].block().getBlockType();
                if (blockType != -1) { // not sky and not empty
                    return false; // should be false
                }
            }
        }
        return true;
    }

    public void putTree(Tile[][] terrainData, int x, int y) {
        if (terrainData[x][y - 3].block() == Block.CORAL) {
            return;
        }
        
        int flipped = (int) (FLORA_RNG.next(0, 1) + 0.5);
        int foliageType = (int) (FLORA_RNG.next(0, 5) + 0.5);
        Block block;

        switch (BIOME_GENERATOR.getBiome(x)) {
            default:
            case TUNDRA:
                block = (foliageType % 2 == 0) ? Block.JUNIPER_SAVIN : (foliageType % 2 == 1) ? Block.AZURE_BLUET : Block.WINTERBERRY;
                break;
            case TAIGA:
                block = (foliageType % 3 == 0) ? Block.BOREAL_TREE : Block.BARBERRY_BUSH;
                break;
            case ALPINE:
                block = (foliageType % 2 == 0) ? Block.ASPHODEL : (foliageType % 2 == 1) ? Block.IRIS : Block.BELL_FLOWER;
                break;
            case GRASSLAND:
                block = (foliageType % 3 == 0) ? Block.SWITCH_GRASS : Block.DAFFODIL;
                break;
            case FOREST:
                block = (foliageType % 3 == 0) ? Block.OAK_TREE : Block.POPPY;
                break;
            case JUNGLE:
                block = (foliageType % 3 == 0) ? Block.MAHOGANY_TREE : Block.MUSHROOM;
                break;
            case SAVANNA:
                block = (foliageType % 3 == 0) ? Block.ACACIA_TREE : Block.LEMON_GRASS;
                break;
            case DESERT:
                block = (foliageType % 3 == 0) ? Block.CACTUS : Block.WATERLEAF;
                break;
            case LEFT_AQUATIC:
            case RIGHT_AQUATIC:
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
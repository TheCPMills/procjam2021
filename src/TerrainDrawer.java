
import java.awt.Color;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;

import util.*;
import util.block.FaunaTile;
import util.block.Tile;

public class TerrainDrawer {
    private BufferedImage terrainImage;
    private int[][] GRASS;
    private int[][] DIRT;
    private int[][] SAND;
    private int[][] GRAVEL;
    private int[][] COBBLESTONE;
    private int[][] MARBLE;
    private int[][] GRANITE;
    private int[][] DIORITE;
    private int[][] ANDESITE;
    private int[][] LIMESTONE;
    private int[][] BASALT;
    private int[][] PUMICE;
    private int[][] WATER;
    private int[][] CAVE;
    private int[][] SKY;
    private int[][] OAK_TREE;
    private int[][] PALM_TREE;

    public TerrainDrawer(BufferedImage terrainImage) {
        this.terrainImage = terrainImage;
        setTextures();
    }

    public void drawBlock(int x, int y, Tile tile) {
        int[][] blockColors;

        switch (tile.block()) {
            case GRASS:
                blockColors = GRASS;
                break;
            case DIRT:
                blockColors = DIRT;
                break;
            case SAND:
                blockColors = SAND;
                break;
            case GRAVEL:
                blockColors = GRAVEL;
                break;
            case WATER:
                blockColors = WATER;
                break;
            case CAVE:
                blockColors = CAVE;
                break;
            case COBBLESTONE:
                blockColors = COBBLESTONE;
                break;
            case MARBLE:
                blockColors = MARBLE;
                break;
            case GRANITE:
                blockColors = GRANITE;
                break;
            case DIORITE:
                blockColors = DIORITE;
                break;
            case ANDESITE:
                blockColors = ANDESITE;
                break;
            case LIMESTONE:
                blockColors = LIMESTONE;
                break;
            case BASALT:
                blockColors = BASALT;
                break;
            case PUMICE:
                blockColors = PUMICE;
                break;
            case SKY:
                blockColors = SKY;
                break;
            case OAK_TREE:
                blockColors = OAK_TREE;
                break;
            case PALM_TREE:
                blockColors = PALM_TREE;
                break;
            default:
                return;
        }

        if (tile.block().getBlockType() == 3){
            y += 31;
            int i, j, xLimit, yLimit;

            switch (((FaunaTile) (tile)).alignment()) {
                default:
                case LEFT_SUPER:
                    i = 0;
                    j = 0;
                    xLimit = 8;
                    yLimit = 8;
                    break;
                case LEFT_MID:
                    i = 0;
                    j = 8;
                    xLimit = 8;
                    yLimit = 16;
                    break;
                case LEFT_BASE:
                    i = 0;
                    j = 16;
                    xLimit = 8;
                    yLimit = 24;
                    break;
                case LEFT_SUB:
                    i = 0;
                    j = 24;
                    xLimit = 8;
                    yLimit = 32;
                    break;
                case RIGHT_SUPER:
                    i = 8;
                    j = 0;
                    xLimit = 16;
                    yLimit = 8;
                    break;
                case RIGHT_MID:
                    i = 8;
                    j = 8;
                    xLimit = 16;
                    yLimit = 16;
                    break;
                case RIGHT_BASE:
                    i = 8;
                    j = 16;
                    xLimit = 16;
                    yLimit = 24;
                    break;
                case RIGHT_SUB:
                    i = 8;
                    j = 24;
                    xLimit = 16;
                    yLimit = 32;
                    break;
            }

            int direction = Boolean.compare(((FaunaTile) (tile)).direction(), false);

            for (; i < xLimit; i++) {
                for (; j < yLimit; j++) {
                    int color = blockColors[i][j];
                    int newX = x + ((blockColors.length - 1) * direction - (2 * direction - 1) * i) - ((i / 8) * 8);
                    int newY = y - (blockColors[i].length - 1 - j) - ((j / 8) * 8);
                    if (color >> 24 == 0x00) {
                        terrainImage.setRGB(newX, newY, 0xff8ed4e2); // cheap trick that works 99% of the time
                    } else {
                        terrainImage.setRGB(newX, newY, color);
                    }
                }
                j -= 8;
            }
        } else {
            for (int i = 0; i < blockColors.length; i++) {
                for (int j = 0; j < blockColors[i].length; j++) {
                    terrainImage.setRGB(x + i, y + j, blockColors[i][j]);
                }
            }
        }
    }

    public void setTextures(){
        try {
            GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DIRT = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/dirt.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SAND = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/sand.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GRAVEL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/gravel.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            COBBLESTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cobblestone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MARBLE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/marble.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GRANITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/granite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DIORITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/diorite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ANDESITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/andesite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            LIMESTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/limestone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BASALT = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/basalt.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PUMICE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/pumice.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            WATER = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/water.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CAVE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cave.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SKY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/sky.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            OAK_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/oak_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PALM_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/palm_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
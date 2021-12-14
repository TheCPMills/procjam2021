
import java.awt.Color;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;

import util.*;
import util.block.FloraTile;
import util.block.Tile;

public class TerrainDrawer {
    private BufferedImage terrainImage;
    
    private int[][] FROSTED_GRASS;
    private int[][] TEMPERATE_GRASS;
    private int[][] GRASS;
    private int[][] TROPICAL_GRASS;
    private int[][] DRY_GRASS;
    private int[][] WATER;
    private int[][] SNOW;
    private int[][] DIRT;
    private int[][] GRAVEL;
    private int[][] MUD;
    private int[][] CLAY;
    private int[][] SAND;
    private int[][] ICE;
    private int[][] DIORITE;
    private int[][] ANDESITE;
    private int[][] MARBLE;
    private int[][] COBBLESTONE;
    private int[][] MOSSY_COBBLESTONE;
    private int[][] GRANITE;
    private int[][] SANDSTONE;
    private int[][] LIMESTONE;
    private int[][] BASALT;
    private int[][] PUMICE;
    private int[][] CALCITE;
    private int[][] QUARTZITE;
    private int[][] SLATE;
    private int[][] BIOTITE;
    private int[][] TUFF;
    private int[][] OBSIDIAN;
    private int[][] JUNIPER_SAVIN;
    private int[][] AZURE_BLUET;
    private int[][] WINTERBERRY;
    private int[][] BOREAL_TREE;
    private int[][] BARBERRY_BUSH;
    private int[][] ASPHODEL;
    private int[][] IRIS;
    private int[][] BELL_FLOWER;
    private int[][] SWITCH_GRASS;
    private int[][] DAFFODIL;
    private int[][] OAK_TREE;
    private int[][] POPPY;
    private int[][] MAHOGANY_TREE;
    private int[][] MUSHROOM;
    private int[][] ACACIA_TREE;
    private int[][] LEMON_GRASS;
    private int[][] CACTUS;
    private int[][] WATERLEAF;
    private int[][] PALM_TREE;
    private int[][] CORAL;
    private int[][] COAL;
    private int[][] COPPER;
    private int[][] SILVER;
    private int[][] IRON;
    private int[][] GOLD;
    private int[][] PLATINUM;
    private int[][] COBALT;
    private int[][] TITANIUM;
    private int[][] PALLADIUM;
    private int[][] DIAMOND;
    private int[][] RUBY;
    private int[][] EMERALD;
    private int[][] ONYX;
    private int[][] AMETHYST;
    private int[][] SKY;
    private int[][] CAVE;

    public TerrainDrawer(BufferedImage terrainImage) {
        this.terrainImage = terrainImage;
        setTextures();
    }

    public void drawBlock(int x, int y, Tile tile) {
        int[][] blockColors;

        switch (tile.block()) {
            case FROSTED_GRASS:
                blockColors = FROSTED_GRASS;
                break;
            case TEMPERATE_GRASS:
                blockColors = TEMPERATE_GRASS;
                break;
            case GRASS:
                blockColors = GRASS;
                break;
            case TROPICAL_GRASS:
                blockColors = TROPICAL_GRASS;
                break;
            case DRY_GRASS:
                blockColors = DRY_GRASS;
                break;
            case WATER:
                blockColors = WATER;
                break;
            case SNOW:
                blockColors = SNOW;
                break;
            case DIRT:
                blockColors = DIRT;
                break;
            case GRAVEL:
                blockColors = GRAVEL;
                break;
            case MUD:
                blockColors = MUD;
                break;
            case CLAY:
                blockColors = CLAY;
                break;
            case SAND:
                blockColors = SAND;
                break;
            case ICE:
                blockColors = ICE;
                break;
            case DIORITE:
                blockColors = DIORITE;
                break;
            case ANDESITE:
                blockColors = ANDESITE;
                break;
            case MARBLE:
                blockColors = MARBLE;
                break;
            case COBBLESTONE:
                blockColors = COBBLESTONE;
                break;
            case MOSSY_COBBLESTONE:
                blockColors = MOSSY_COBBLESTONE;
                break;
            case GRANITE:
                blockColors = GRANITE;
                break;
            case SANDSTONE:
                blockColors = SANDSTONE;
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
            case CALCITE:
                blockColors = CALCITE;
                break;
            case QUARTZITE:
                blockColors = QUARTZITE;
                break;
            case SLATE:
                blockColors = SLATE;
                break;
            case BIOTITE:
                blockColors = BIOTITE;
                break;
            case TUFF:
                blockColors = TUFF;
                break;
            case OBSIDIAN:
                blockColors = OBSIDIAN;
                break;
            case JUNIPER_SAVIN:
                blockColors = JUNIPER_SAVIN;
                break;
            case AZURE_BLUET:
                blockColors = AZURE_BLUET;
                break;
            case WINTERBERRY:
                blockColors = WINTERBERRY;
                break;
            case BOREAL_TREE:
                blockColors = BOREAL_TREE;
                break;
            case BARBERRY_BUSH:
                blockColors = BARBERRY_BUSH;
                break;
            case ASPHODEL:
                blockColors = ASPHODEL;
                break;
            case IRIS:
                blockColors = IRIS;
                break;
            case BELL_FLOWER:
                blockColors = BELL_FLOWER;
                break;
            case SWITCH_GRASS:
                blockColors = SWITCH_GRASS;
                break;
            case DAFFODIL:
                blockColors = DAFFODIL;
                break;
            case OAK_TREE:
                blockColors = OAK_TREE;
                break;
            case POPPY:
                blockColors = POPPY;
                break;
            case MAHOGANY_TREE:
                blockColors = MAHOGANY_TREE;
                break;
            case MUSHROOM:
                blockColors = MUSHROOM;
                break;
            case ACACIA_TREE:
                blockColors = ACACIA_TREE;
                break;
            case LEMON_GRASS:
                blockColors = LEMON_GRASS;
                break;
            case CACTUS:
                blockColors = CACTUS;
                break;
            case WATERLEAF:
                blockColors = WATERLEAF;
                break;
            case PALM_TREE:
                blockColors = PALM_TREE;
                break;
            case CORAL:
                blockColors = CORAL;
                break;
            case COAL:
                blockColors = COAL;
                break;
            case COPPER:
                blockColors = COPPER;
                break;
            case SILVER:
                blockColors = SILVER;
                break;
            case IRON:
                blockColors = IRON;
                break;
            case GOLD:
                blockColors = GOLD;
                break;
            case PLATINUM:
                blockColors = PLATINUM;
                break;
            case COBALT:
                blockColors = COBALT;
                break;
            case TITANIUM:
                blockColors = TITANIUM;
                break;
            case PALLADIUM:
                blockColors = PALLADIUM;
                break;
            case DIAMOND:
                blockColors = DIAMOND;
                break;
            case RUBY:
                blockColors = RUBY;
                break;
            case EMERALD:
                blockColors = EMERALD;
                break;
            case ONYX:
                blockColors = ONYX;
                break;
            case AMETHYST:
                blockColors = AMETHYST;
                break;
            case SKY:
                blockColors = SKY;
                break;
            case CAVE:
                blockColors = CAVE;
                break;
            default:
                return;
        }

        if (tile.block().getBlockType() == 3) {
            y += 31;
            int i, j, xLimit, yLimit;

            switch (((FloraTile) (tile)).alignment()) {
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

            int direction = Boolean.compare(((FloraTile) (tile)).direction(), false);

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
            FROSTED_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/frosted_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            TEMPERATE_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/temperate_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            TROPICAL_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/tropical_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DRY_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/dry_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            WATER = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/water.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SNOW = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/snow.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DIRT = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/dirt.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GRAVEL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/gravel.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MUD = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/mud.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CLAY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/clay.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SAND = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/sand.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ICE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/ice.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DIORITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/diorite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ANDESITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/andesite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MARBLE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/marble.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            COBBLESTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cobblestone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MOSSY_COBBLESTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/mossy_cobblestone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GRANITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/granite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SANDSTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/sandstone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            LIMESTONE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/limestone.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BASALT = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/basalt.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PUMICE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/pumice.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CALCITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/calcite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            QUARTZITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/quartzite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SLATE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/slate.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BIOTITE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/biotite.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            TUFF = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/tuff.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            OBSIDIAN = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/obsidian.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            JUNIPER_SAVIN = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/juniper_savin.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            AZURE_BLUET = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/azure_bluet.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            WINTERBERRY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/winterberry.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BOREAL_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/boreal_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BARBERRY_BUSH = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/barberry_bush.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ASPHODEL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/asphodel.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            IRIS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/iris.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            BELL_FLOWER = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/bell_flower.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SWITCH_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/switch_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DAFFODIL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/daffodil.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            OAK_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/oak_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            POPPY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/poppy.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MAHOGANY_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/mahogany_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            MUSHROOM = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/mushroom.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ACACIA_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/acacia_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            LEMON_GRASS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/lemon_grass.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CACTUS = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cactus.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            WATERLEAF = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/waterleaf.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PALM_TREE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/palm_tree.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CORAL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/coral.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            COAL = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/coal.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            COPPER = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/copper.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SILVER = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/silver.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            IRON = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/iron.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            GOLD = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/gold.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PLATINUM = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/platinum.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            COBALT = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cobalt.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            TITANIUM = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/titanium.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            PALLADIUM = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/palladium.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            DIAMOND = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/diamond.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            RUBY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/ruby.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            EMERALD = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/emerald.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            ONYX = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/onyx.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            AMETHYST = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/amethyst.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            SKY = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/sky.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
            CAVE = Arrays.stream(ImageProcessing.arrayPixels("assets/textures/cave.png")).map(row -> Arrays.stream(row).mapToInt(Color::getRGB).toArray()).toArray(int[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
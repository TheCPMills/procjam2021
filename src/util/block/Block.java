package util.block;

public enum Block {
    FROSTED_GRASS, TEMPERATE_GRASS, GRASS, TROPICAL_GRASS, DRY_GRASS, WATER, // lush groundcover
    SNOW, DIRT, GRAVEL, MUD, CLAY, SAND, // soil
    ICE, DIORITE, ANDESITE, MARBLE, COBBLESTONE, MOSSY_COBBLESTONE, GRANITE, SANDSTONE, LIMESTONE, BASALT, PUMICE, CALCITE, QUARTZITE, SLATE, BIOTITE, TUFF, // minerals/rocks
    JUNIPER_SAVIN, AZURE_BLUET, WINTERBERRY, BOREAL_TREE, BARBERRY_BUSH, ASPHODEL, IRIS, BELL_FLOWER, SWITCH_GRASS, DAFFODIL, OAK_TREE, POPPY, MAHOGANY_TREE, MUSHROOM, ACACIA_TREE, LEMON_GRASS, CACTUS, WATERLEAF, PALM_TREE, CORAL, // foliage
    COAL, COPPER, SILVER, IRON, GOLD, PLATINUM, COBALT, TITANIUM, PALLADIUM, DIAMOND, RUBY, EMERALD, ONYX, AMETHYST, // ores/gems
    SKY, CAVE;

    public int getBlockType() {
        // 0 = lush
        // 1 = soil
        // 2 = mineral
        // 3 = foliage
        // 4 = ore/gem

        switch (this) {
            case SNOW:
            case FROSTED_GRASS:
            case GRAVEL:
            case TEMPERATE_GRASS:
            case GRASS:
            case TROPICAL_GRASS:
            case DRY_GRASS:
            case SAND:
            case WATER:
                return 0;
            case DIRT:
            case MUD:
            case CLAY:
                return 1;
            case ICE:
            case DIORITE:
            case ANDESITE:
            case MARBLE:
            case COBBLESTONE:
            case MOSSY_COBBLESTONE:
            case GRANITE:
            case SANDSTONE:
            case LIMESTONE:
            case BASALT:
            case PUMICE:
            case CALCITE:
            case QUARTZITE:
            case SLATE:
            case BIOTITE:
            case TUFF:
                return 2;
            case JUNIPER_SAVIN:
            case AZURE_BLUET:
            case WINTERBERRY:
            case BOREAL_TREE:
            case BARBERRY_BUSH:
            case ASPHODEL:
            case IRIS:
            case BELL_FLOWER:
            case SWITCH_GRASS:
            case DAFFODIL:
            case OAK_TREE:
            case POPPY:
            case MAHOGANY_TREE:
            case MUSHROOM:
            case ACACIA_TREE:
            case LEMON_GRASS:
            case CACTUS:
            case WATERLEAF:
            case PALM_TREE:
            case CORAL:
                return 3;
            case COAL:
            case COPPER:
            case SILVER:
            case IRON:
            case GOLD:
            case PLATINUM:
            case COBALT:
            case TITANIUM:
            case PALLADIUM:
            case DIAMOND:
            case RUBY:
            case EMERALD:
            case ONYX:
            case AMETHYST:
                return 4;
            default:
                return -1;
        }
    }
}
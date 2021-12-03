package util.block;

public enum Block {
    GRASS,
    DIRT, SAND, GRAVEL,
    COBBLESTONE, MARBLE, GRANITE, DIORITE, ANDESITE, LIMESTONE, BASALT, PUMICE,
    OAK_TREE, PALM_TREE,
    SKY, CAVE, WATER;

    public int getBlockType() {
        // 0 = lush
        // 1 = soil
        // 2 = mineral
        // 3 = foliage

        switch (this) {
            case GRASS:
            case SAND:
            case GRAVEL:
                return 0;
            case DIRT:
                return 1;
            case COBBLESTONE:
            case MARBLE:
            case GRANITE:
            case DIORITE:
            case ANDESITE:
            case LIMESTONE:
            case BASALT:
            case PUMICE:
                return 2;
            case OAK_TREE:
            case PALM_TREE:
                return 3;
            case WATER:
                return 4;
            default:
                return -1;
        }
    }
}
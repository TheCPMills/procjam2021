public enum Block {
    GRASS, DIRT, SKY, CAVE, CLOUD, OAK_WOOD, OAK_LEAVES, STONE;

    public static int getBlockType(Block block) {
        // 0 = lush
        // 1 = soil
        // 2 = mineral

        if (block == GRASS) {
            return 0;
        } else if (block == DIRT) {
            return 1;
        } else if (block == OAK_WOOD) {
            return 3;
        } else if (block == OAK_LEAVES) {
            return 3;
        } else if (block == STONE) {
            return 2;
        } else {
            return -1;
        }
    }
}
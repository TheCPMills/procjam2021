public abstract class Tile {
    private Block block;

    public Tile(Block block) {
        this.block = block;
    }

    public Block block() {
        return block;
    }
}

class ElementalTile extends Tile {
    public ElementalTile(Block block) {
        super(block);
    }
}

class FaunaTile extends Tile {
    boolean direction;
    TileAlignment alignment;

    public FaunaTile(Block block, boolean direction) {
        super(block);
        this.direction = direction;
        this.alignment = TileAlignment.LEFT_SUB;
    }

    public FaunaTile(Block block, TileAlignment alignment) {
        super(block);
        this.direction = false;
        this.alignment = alignment;
    }

    public FaunaTile(Block block, boolean direction, TileAlignment alignment) {
        super(block);
        this.direction = direction;
        this.alignment = alignment;
    }

    public boolean direction() {
        return direction;
    }

    public TileAlignment alignment() {
        return alignment;
    }
}

enum Block {
    GRASS,
    DIRT,
    COBBLESTONE, MARBLE, GRANITE, DIORITE, ANDESITE, LIMESTONE, BASALT, PUMICE,
    OAK_TREE,
    SKY, CAVE;

    public int getBlockType() {
        // 0 = lush
        // 1 = soil
        // 2 = mineral
        // 3 = flora

        switch (this) {
            case GRASS:
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
                return 3;
            default:
                return -1;
        }
    }
}

enum TileAlignment {
    LEFT_SUPER, LEFT_MID, LEFT_BASE, LEFT_SUB, RIGHT_SUPER, RIGHT_MID, RIGHT_BASE, RIGHT_SUB;
}
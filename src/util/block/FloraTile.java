package util.block;

public class FloraTile extends Tile {
    boolean direction;
    TileAlignment alignment;

    public FloraTile(Block block, boolean direction) {
        super(block);
        this.direction = direction;
        this.alignment = TileAlignment.LEFT_SUB;
    }

    public FloraTile(Block block, TileAlignment alignment) {
        super(block);
        this.direction = false;
        this.alignment = alignment;
    }

    public FloraTile(Block block, boolean direction, TileAlignment alignment) {
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
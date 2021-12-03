package util.block;

public abstract class Tile {
    private Block block;

    public Tile(Block block) {
        this.block = block;
    }

    public Block block() {
        return block;
    }
}
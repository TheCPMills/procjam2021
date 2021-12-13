package util;

import java.util.Random;

import util.block.Block;
import util.block.ElementalTile;
import util.block.Tile;

public class StructureGenerator {

    Tile[][] map;
    Random STRUCTURE_RNG;
    int WIDTH;
    int HEIGHT;
    
    public StructureGenerator(Tile[][] map, int WIDTH, int HEIGHT, int seed) {
        this.map = map;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        STRUCTURE_RNG = new Random(seed);
    }

    public void generateDungeon() {
        int dungeonWidth = STRUCTURE_RNG.nextInt(30, 40);
        int dungeonHeight = STRUCTURE_RNG.nextInt(30, 40);
        int dungeonOriginX = STRUCTURE_RNG.nextInt(0, WIDTH - dungeonWidth);
        int dungeonOriginY = STRUCTURE_RNG.nextInt(HEIGHT/2, HEIGHT - dungeonHeight);

        for(int i = 0; i < dungeonWidth; i++) {
            for(int j = 0; j < dungeonHeight; j++) {
                map[i + dungeonOriginX][j + dungeonOriginY] = new ElementalTile(Block.CAVE);
            }
        }

        //initial walls
        for(int i = 0; i < dungeonWidth; i++) {
            map[i + dungeonOriginX][dungeonOriginY] = new ElementalTile(Block.COBBLESTONE);
            map[i + dungeonOriginX][dungeonOriginY + dungeonHeight - 1] = new ElementalTile(Block.COBBLESTONE);
        }
        for(int j = 1; j < dungeonHeight - 1; j++) {
            map[dungeonOriginX][j + dungeonOriginY] = new ElementalTile(Block.COBBLESTONE);
            map[dungeonOriginX + dungeonWidth - 1][j + dungeonOriginY] = new ElementalTile(Block.COBBLESTONE);
        }

        //initial Room
        Room initialRoom = new Room(dungeonWidth, dungeonHeight, dungeonOriginX, dungeonOriginY);
        if (STRUCTURE_RNG.nextBoolean()) {
            initialRoom.splitHorizontally(map);
        }
        else {
            initialRoom.splitVertically(map);
        }
    }

    private class Room {
        private int x;
        private int y;
        private int width;
        private int height;

        public Room(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }

        public void splitVertically(Tile[][] map) {
            // do not split with increasing chance as room gets smaller
            if (width < 9 + STRUCTURE_RNG.nextInt(0, 3)) {
                return;
            }
            // try until it works, or too many attempts have occurred
            for (int k = 0; k < 30; k++) {
                // get random value for wall placement
                int i = STRUCTURE_RNG.nextInt(4, width - 4);
                // if wall placement doesn't block a door
                if (map[x + i][y].block() == Block.COBBLESTONE && map[x + i][y + height - 1].block() == Block.COBBLESTONE) {
                    // get random door placement along wall
                    int doorPlacement = STRUCTURE_RNG.nextInt(1, height - 1);
                    // place wall
                    for(int j = 1; j < height - 1; j++)
                    {
                        if (Math.abs(j - doorPlacement) > 0) {
                            map[x + i][y + j] = new ElementalTile(Block.COBBLESTONE);
                        }
                    }
                    // create sub-rooms
                    Room westRoom = new Room(i + 1, height, x, y);
                    Room eastRoom = new Room(width - i, height, x + i, y);
                    // recursive call to split those rooms
                    westRoom.splitHorizontally(map);
                    eastRoom.splitHorizontally(map);
                    return;
                }
            }
        }

        public void splitHorizontally(Tile[][] map) {
            // do not split with increasing chance as room gets smaller
            if (height < 9 + STRUCTURE_RNG.nextInt(0, 3)) {
                return;
            }
            // try until it works, or too many attempts have occurred
            for(int k = 0; k < 30; k++) {
                // get random value for wall placement
                int j = STRUCTURE_RNG.nextInt(4, height - 4);
                // if wall placement doesn't block a door
                if (map[x][y + j].block() == Block.COBBLESTONE && map[x + width - 1][y + j].block() == Block.COBBLESTONE) {
                    // get random door placement along wall
                    int doorPlacement = STRUCTURE_RNG.nextInt(1, width - 1);
                    // place wall
                    for(int i = 1; i < width - 1; i++)
                    {
                        if (Math.abs(i - doorPlacement) > 0) {
                            map[x + i][y + j] = new ElementalTile(Block.COBBLESTONE);
                        }
                    }
                    // create sub-rooms
                    Room northRoom = new Room(width, j + 1, x, y);
                    Room southRoom = new Room(width, height - j, x, y + j);
                    // recursive call to split those rooms
                    northRoom.splitVertically(map);
                    southRoom.splitVertically(map);
                    return;
                }
            }
        }
    }
}

package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
        int dungeonWidth = STRUCTURE_RNG.nextInt(20, 30);
        int dungeonHeight = STRUCTURE_RNG.nextInt(20, 30);
        int dungeonOriginX = STRUCTURE_RNG.nextInt(0, WIDTH - dungeonWidth);
        int dungeonOriginY = STRUCTURE_RNG.nextInt(HEIGHT/2, HEIGHT - dungeonHeight);



        for(int i = 0; i < dungeonWidth; i++) {
            for(int j = 0; j < dungeonHeight; j++) {
                map[i + dungeonOriginX][j + dungeonOriginY] = new ElementalTile(Block.COBBLESTONE);
            }
        }
    }

    private class Room {
        public Set<Room> connections = new HashSet<Room>();
        public int width;
        public int height;
        public int doorPosition;
        public Room(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void splitRoom(boolean divideVertically) {
            if (divideVertically) {

            }
        }
    }
}

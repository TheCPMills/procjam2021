import javanoise.noise.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        TerrainGenerator.generate(new Simplex(1234567890), 1024, 128, 15, 7);
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int seed = 1234567890;
        // TerrainGenerator.generate(seed, 50, 100, 0);
        TerrainGenerator.generate(seed, 1024, 256, 2.5);
    }
}

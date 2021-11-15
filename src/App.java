public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int seed = 1234567890;
        TerrainGenerator.generate(seed, 2048, 1024, 2.5);
    }
}

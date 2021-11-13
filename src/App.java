public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int seed = 1234567890;
        TerrainGenerator.generate(seed, 1024, 1024, 15);
    }
}

import javanoise.noise.*;
import javanoise.noise.fractal.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int seed = 1234567890;
        TerrainGenerator.generate(new Simplex(seed), new FBM(seed), new RigidMultiFractal(seed), 1024, 1024, 15, 7);
    }
}

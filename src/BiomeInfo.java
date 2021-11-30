public class BiomeInfo {
    public BiomeType primaryType = null;
    public float proportion; // 0 never happens, 1 is primary only
    public BiomeType secondaryType = null;

    public void addBiome(BiomeType biome, float proportion) {
        if (primaryType == null) {
            this.primaryType = biome;
            this.proportion = proportion;
        } else if (secondaryType == null) {
            this.secondaryType = biome;
        }
    }

    public double getBiomeHeight(float baseHeight, float maxDeviation) {
        float primaryHeight = baseHeight;
        float secondaryHeight = baseHeight;

        switch (primaryType) {
            case OCEAN:
                primaryHeight += maxDeviation;
                break;
            case MOUNTAIN:
                primaryHeight -= maxDeviation;
                break;
            default:
        }
        if (secondaryType != null) {
            switch (secondaryType) {
                case OCEAN:
                    secondaryHeight += maxDeviation;
                    break;
                case MOUNTAIN:
                    secondaryHeight -= maxDeviation;
                    break;
                default:
            }
        }
        // still 0 to 1, but smoothed
        float trigonometricProportion = -0.5f * (float) Math.cos(proportion * Math.PI) + 0.5f;

        return (primaryHeight - secondaryHeight) * trigonometricProportion + secondaryHeight;
    }
}
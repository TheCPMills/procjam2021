package util.biomes;

import java.util.*;
import javanoise.random.*;

public class BiomeGenerator {
    private int WIDTH;
    private Random BIOME_RNG;
    private CBSquares BIOME_SHUFFLER;
    private double BLEND_PERCENTAGE = 0.33;
    private double ALPINE_BLEND_FACTOR = 1.5;

    private int tundraWidth, taigaWidth, mountainWidth, grasslandWidth, forestWidth, jungleWidth, savannaWidth, desertWidth, leftOceanWidth, rightOceanWidth;
    private int tundraMin = -1, taigaMin = -1, mountainMin = -1, grasslandMin = -1, forestMin = -1, jungleMin = -1, savannaMin = -1, desertMin = -1, rightOceanMin = -1;
    private int tundraMax = -1, taigaMax = -1, mountainMax = -1, grasslandMax = -1, forestMax = -1, jungleMax = -1, savannaMax = -1, desertMax = -1, leftOceanMax = -1;

    public BiomeGenerator(int width, int seed) {
        WIDTH = width;
        BIOME_RNG = new Random(seed);
        BIOME_SHUFFLER = new CBSquares(seed);
    }

    public ArrayList<BiomeInfo> generateBiomes() {
        ArrayList<BiomeInfo> biomeInfo = new ArrayList<BiomeInfo>();

        ArrayList<Integer> biomeWidths = new ArrayList<>();
        int remainingWidth = WIDTH;
        double meanWidth = remainingWidth / 10.0;
        double widthVariance = Math.abs(BIOME_RNG.nextDouble() * remainingWidth / 10);

        for (int i = 0; i < 10; i++) {
            double distributionValue = BIOME_RNG.nextGaussian() / 3.0;
            if (Math.abs(distributionValue) > 1.0) {
                distributionValue = Math.signum(distributionValue);
            }
            int width = (int) (meanWidth + distributionValue * widthVariance);
            biomeWidths.add(width);
            remainingWidth -= width;
            meanWidth = remainingWidth / 10.0;
            widthVariance = Math.abs(BIOME_RNG.nextDouble() * remainingWidth / 10);
        }

        if (remainingWidth > 0) {
            int biomeWidthSum = biomeWidths.stream().mapToInt(Integer::intValue).sum();
            for (int i = 0; i < 10; i++) {
                double ratio = (double) biomeWidths.get(i) / (double) (biomeWidthSum);
                biomeWidths.set(i, biomeWidths.get(i) + (int) (ratio * (WIDTH - biomeWidthSum)));
                remainingWidth -= (int) (ratio * (WIDTH - biomeWidthSum));
            }
            int minIndex = biomeWidths.indexOf(biomeWidths.stream().mapToInt(Integer::intValue).min().getAsInt());
            biomeWidths.set(minIndex, (int) biomeWidths.get(minIndex) + remainingWidth);
        }

        int maxIndex = biomeWidths.indexOf(biomeWidths.stream().mapToInt(Integer::intValue).max().getAsInt());
        int temp = biomeWidths.get(0);
        biomeWidths.set(0, biomeWidths.get(maxIndex));
        biomeWidths.set(maxIndex, temp);

        tundraWidth = biomeWidths.remove(1);
        taigaWidth = biomeWidths.remove(1);
        mountainWidth = biomeWidths.remove(1);
        grasslandWidth = biomeWidths.remove(1);
        forestWidth = biomeWidths.remove(0);
        jungleWidth = biomeWidths.remove(0);
        savannaWidth = biomeWidths.remove(0);
        desertWidth = biomeWidths.remove(0);
        leftOceanWidth = biomeWidths.remove(0);
        rightOceanWidth = biomeWidths.remove(0);

        ArrayList<BiomeType> biomesShuffled = new ArrayList<>(Arrays.asList(BiomeType.values()));
        biomesShuffled.removeAll(Arrays.asList(BiomeType.FOREST, BiomeType.LEFT_AQUATIC, BiomeType.RIGHT_AQUATIC));
        Randomizer.shuffle(biomesShuffled, BIOME_SHUFFLER);
        biomesShuffled.add(0, BiomeType.LEFT_AQUATIC);
        biomesShuffled.add(BiomeType.RIGHT_AQUATIC);
        biomesShuffled.add(Math.round(BIOME_RNG.nextDouble()) == 0 ? 4 : 5, BiomeType.FOREST);

        int currentLocation = 0;
        for (BiomeType biomeType : biomesShuffled) {
            switch (biomeType) {
                default:
                case TUNDRA:
                    tundraMin = currentLocation;
                    tundraMax = currentLocation + tundraWidth;;
                    currentLocation = tundraMax + 1;
                    break;
                case TAIGA:
                    taigaMin = currentLocation;
                    taigaMax = currentLocation + taigaWidth;
                    currentLocation = taigaMax + 1;
                    break;
                case ALPINE:
                    mountainMin = currentLocation;
                    mountainMax = currentLocation + mountainWidth;
                    currentLocation = mountainMax + 1;
                    break;
                case GRASSLAND:
                    grasslandMin = currentLocation;
                    grasslandMax = currentLocation + grasslandWidth;
                    currentLocation = grasslandMax + 1;
                    break;
                case FOREST:
                    forestMin = currentLocation;
                    forestMax = currentLocation + forestWidth;
                    currentLocation = forestMax + 1;
                    break;
                case JUNGLE:
                    jungleMin = currentLocation;
                    jungleMax = currentLocation + jungleWidth;
                    currentLocation = jungleMax + 1;
                    break;
                case SAVANNA:
                    savannaMin = currentLocation;
                    savannaMax = currentLocation + savannaWidth;
                    currentLocation = savannaMax + 1;
                    break;
                case DESERT:
                    desertMin = currentLocation;
                    desertMax = currentLocation + desertWidth;
                    currentLocation = desertMax + 1;
                    break;
                case LEFT_AQUATIC:
                    leftOceanMax = currentLocation + leftOceanWidth;
                    currentLocation = leftOceanMax + 1;
                    break;
                case RIGHT_AQUATIC:
                    rightOceanMin = currentLocation;
                    currentLocation = rightOceanMin + rightOceanWidth + 1;
                    break;
            }
        }

        // initialize and add ocean biomes
        for (int i = 0; i < WIDTH; i++) {
            biomeInfo.add(new BiomeInfo(BIOME_RNG));
            if (i <= leftOceanMax) {
                biomeInfo.get(i).addBiome(LeftOcean.getInstance(),
                    (1.0f - (float) i / leftOceanMax));
            } else if (i >= rightOceanMin) {
                biomeInfo.get(i).addBiome(RightOcean.getInstance(),
                    (float) (i - rightOceanMin) / (WIDTH - rightOceanMin));
            }
        }

        // add tundra biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(tundraMin - 1);
            BiomeType rightBiome = getBiome(tundraMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= tundraMin - leftBiomeOverlap && i <= tundraMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Tundra.getInstance(),
                    proportionOfBiome(i, tundraMin, tundraMax));
            }
        }

        // add taiga biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(taigaMin - 1);
            BiomeType rightBiome = getBiome(taigaMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= taigaMin - leftBiomeOverlap && i <= taigaMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Taiga.getInstance(),
                    proportionOfBiome(i, taigaMin, taigaMax));
            }
        }

        // add mountain biome
        for (int i = 0; i < WIDTH; i++) {
            if (i >= mountainMin && i <= mountainMax) {
                biomeInfo.get(i).addBiome(Mountain.getInstance(),
                    1.0f - (float) (Math.abs((mountainMin + mountainMax) / 2.0f - i)) / mountainWidth * 2);
            }
        }

        // add grassland biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(grasslandMin - 1);
            BiomeType rightBiome = getBiome(grasslandMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= grasslandMin - leftBiomeOverlap && i <= grasslandMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Grassland.getInstance(),
                    proportionOfBiome(i, grasslandMin, grasslandMax));
            }
        }

        // add jungle biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(jungleMin - 1);
            BiomeType rightBiome = getBiome(jungleMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= jungleMin - leftBiomeOverlap && i <= jungleMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Jungle.getInstance(),
                    proportionOfBiome(i, jungleMin, jungleMax));
            }
        }

        // add savanna biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(savannaMin - 1);
            BiomeType rightBiome = getBiome(savannaMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= savannaMin - leftBiomeOverlap && i <= savannaMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Savanna.getInstance(),
                    proportionOfBiome(i, savannaMin, savannaMax));
            }
        }

        // add desert biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(desertMin - 1);
            BiomeType rightBiome = getBiome(desertMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= desertMin - leftBiomeOverlap && i <= desertMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Desert.getInstance(),
                    proportionOfBiome(i, desertMin, desertMax));
            }
        }

        // add forest biome
        for (int i = 0; i < WIDTH; i++) {
            BiomeType leftBiome = getBiome(forestMin - 1);
            BiomeType rightBiome = getBiome(forestMax + 1);
            int leftBiomeOverlap, rightBiomeOverlap;
            if (leftBiome == BiomeType.ALPINE) {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                leftBiomeOverlap = (int) (getDefaultBiomeWidth(leftBiome) * BLEND_PERCENTAGE);
            }
            if (rightBiome == BiomeType.ALPINE) {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE * ALPINE_BLEND_FACTOR);
            } else {
                rightBiomeOverlap = (int) (getDefaultBiomeWidth(rightBiome) * BLEND_PERCENTAGE);
            }
            if (i >= forestMin - leftBiomeOverlap && i <= forestMax + rightBiomeOverlap) {
                biomeInfo.get(i).addBiome(Forest.getInstance(), 
                    proportionOfBiome(i, forestMin, forestMax));
            }
        }

        return biomeInfo;
    }

    public BiomeType getBiome(int x) {
        if (x <= leftOceanMax) {
            return BiomeType.LEFT_AQUATIC;
        } else if (x >= rightOceanMin) {
            return BiomeType.RIGHT_AQUATIC;
        } else if (x >= tundraMin && x <= tundraMax) {
            return BiomeType.TUNDRA;
        } else if (x >= taigaMin && x <= taigaMax) {
            return BiomeType.TAIGA;
        } else if (x >= mountainMin && x <= mountainMax) {
            return BiomeType.ALPINE;
        } else if (x >= grasslandMin && x <= grasslandMax) {
            return BiomeType.GRASSLAND;
        } else if (x >= jungleMin && x <= jungleMax) {
            return BiomeType.JUNGLE;
        } else if (x >= savannaMin && x <= savannaMax) {
            return BiomeType.SAVANNA;
        } else if (x >= desertMin && x <= desertMax) {
            return BiomeType.DESERT;
        } else if (x >= forestMin && x <= forestMax) {
            return BiomeType.FOREST;
        } else {
            return null;
        }
    }

    private int getDefaultBiomeWidth(BiomeType biome) {
        switch (biome) {
            case LEFT_AQUATIC:
                return leftOceanWidth;
            case RIGHT_AQUATIC:
                return rightOceanWidth;
            case TUNDRA:
                return tundraWidth;
            case TAIGA:
                return taigaWidth;
            case ALPINE:
                return mountainWidth;
            case GRASSLAND:
                return grasslandWidth;
            case JUNGLE:
                return jungleWidth;
            case SAVANNA:
                return savannaWidth;
            case DESERT:
                return desertWidth;
            case FOREST:
                return forestWidth;
            default:
                return 0;
        }
    }

    private float proportionOfBiome(int x, int biomeMin, int biomeMax) {
        float base = 1.25f;
        float sigma = 10.0f; // 0.19f
        float mu = (biomeMin + biomeMax) / 2.0f;
        float k = (1000.0f / (sigma * (float) Math.sqrt(2 * Math.PI)));

        float exponent = -0.5f * (float) Math.pow((x - mu) / sigma, 2);
        float normalizedProportion = (float) Math.pow(base, exponent);
        float proportion = normalizedProportion * k;
        return (proportion > 1.0f) ? 1.0f : proportion;



    }
}

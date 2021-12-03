# TGen
TGen is a Java library for generating terrain.

## Biomes
|  | Lush Groundcover | Soil | Minerals | Foliage |
|-----|-----|-----|-----|-----|
| <b>Tundra<b> | Snow | Snow | Ice | Juniper Savin, Azure Bluet, Winterberry |
| <b>Taiga<b> | Frosted Grass | Dirt |  | Boreal Tree, Barberry |
| <b>Alpine<b> | Gravel | Gravel |  | Asphodel, Iris, Bell Flower |
| <b>Grassland<b> | Temperate Grass | Dirt |  | Switch Grass, Daffodil |
| <b>Forest<b> | Grass | Dirt |  | Oak Tree, Poppy |
| <b>Jungle<b> | Tropical Grass | Mud |  | Mahogany Tree, Mushroom |
| <b>Savnna<b> | Dry Grass | Clay |  | Acacia Tree, Lemon Grass |
| <b>Desert<b> | Sand | Sand | Sandstone | Cactus, Waterleaf |
| <b>Aquatic<b> | Water | Sand |  | Palm Tree, Coral |

## Ores and Gems
 <b>Ores<b>
- Coal
- Copper
- Silver
- Iron
- Gold
- Platinum
- Cobalt
- Titanium
- Palladium

<b>Gems<b>
- Diamond
- Ruby
- Emerald
- Quartz
- Amethyst

## Usage
```java
public static void main(String[] args) throws Exception {
  int seed = 1234567890;
  int width = 1024; // maximum width supported
  int height = 256; // maximum height supported
  double variation = 2.5;
  TerrainGenerator.generate(seed, width, height, variation);
}
```

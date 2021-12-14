# TGen
By Ryan Stebe and Chase Miller <br />
TGen is a Java library for generating terrain.

## Biomes
|  | Lush Groundcover | Soil | Minerals | Foliage |
|-----|-----|-----|-----|-----|
| <b>Tundra<b> | Snow | Snow | Ice | Juniper Savin, Azure Bluet, Winterberry |
| <b>Taiga<b> | Frosted Grass | Dirt | Diorite | Boreal Tree, Barberry |
| <b>Alpine<b> | Gravel | Gravel | Andesite | Asphodel, Iris, Bell Flower |
| <b>Grassland<b> | Temperate Grass | Dirt | Marble | Switch Grass, Daffodil |
| <b>Forest<b> | Grass | Dirt | Cobblestone | Oak Tree, Poppy |
| <b>Jungle<b> | Tropical Grass | Mud | Mossy Cobblestone | Mahogany Tree, Mushroom |
| <b>Savanna<b> | Dry Grass | Clay | Granite | Acacia Tree, Lemon Grass |
| <b>Desert<b> | Sand | Sand | Sandstone | Cactus, Waterleaf |
| <b>Aquatic<b> | Water | Sand | Limestone | Palm Tree, Coral |

## Other Rock Types
- Basalt
- Pumice
- Calcite
- Quartzite
- Slate
- Biotite
- Tuff

## Ores and Gems
 <b>Ores</b>
- Coal
- Copper
- Silver
- Iron
- Gold
- Platinum
- Cobalt
- Titanium
- Palladium

<b>Gems</b>
- Diamond
- Ruby
- Emerald
- Onyx
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

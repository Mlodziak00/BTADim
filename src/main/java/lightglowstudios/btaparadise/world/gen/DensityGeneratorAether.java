package lightglowstudios.btaparadise.world.gen;

import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.noise.PerlinNoise;

public class DensityGeneratorAether implements DensityGenerator {
    private final World world;
    private final PerlinNoise minLimitNoise;
    private final PerlinNoise maxLimitNoise;
    private final PerlinNoise mainNoise;
    private final PerlinNoise scaleNoise;
    private final PerlinNoise depthNoise;

    public DensityGeneratorAether(World world) {
        this.world = world;
        this.minLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 0);
        this.maxLimitNoise = new PerlinNoise(world.getRandomSeed(), 16, 16);
        this.mainNoise = new PerlinNoise(world.getRandomSeed(), 8, 32);
        this.scaleNoise = new PerlinNoise(world.getRandomSeed(), 10, 48);
        this.depthNoise = new PerlinNoise(world.getRandomSeed(), 16, 58);
    }

    public double[] generateDensityMap(Chunk chunk) {
        int terrainHeight = this.world.getWorldType().getMaxY() + 1 - this.world.getWorldType().getMinY();
        int xSize = 5;
        int ySize = terrainHeight / 8 + 1;
        int zSize = 5;
        int x = chunk.xPosition * 4;
        int y = 0;
        int z = chunk.zPosition * 4;
        double[] densityMapArray = new double[xSize * ySize * zSize];
        double mainNoiseScaleX = 80.0;
        double mainNoiseScaleY = 120.0;
        double mainNoiseScaleZ = 80.0;
        double scaleNoiseScaleX = 1.121;
        double scaleNoiseScaleZ = 1.121;
        double depthNoiseScaleX = 200.0;
        double depthNoiseScaleZ = 200.0;
        double depthBaseSize = (double)terrainHeight / 16.0 + 0.5;
        double coordScale = 171.103;
        double heightScale = 342.206;
        double heightStretch = 4.0;
        double upperLimitScale = 512.0;
        double lowerLimitScale = 512.0;
        double[] scaleArray = this.scaleNoise.get((double[])null, x, z, xSize, zSize, 1.121, 1.121);
        double[] depthArray = this.depthNoise.get((double[])null, x, z, xSize, zSize, 200.0, 200.0);
        double[] mainNoiseArray = this.mainNoise.get((double[])null, (double)x, (double)y, (double)z, xSize, ySize, zSize, 2.1387875000000003, 2.851716666666667, 2.1387875000000003);
        double[] minLimitArray = this.minLimitNoise.get((double[])null, (double)x, (double)y, (double)z, xSize, ySize, zSize, 171.103, 342.206, 171.103);
        double[] maxLimitArray = this.maxLimitNoise.get((double[])null, (double)x, (double)y, (double)z, xSize, ySize, zSize, 171.103, 342.206, 171.103);
        int mainIndex = 0;
        int depthScaleIndex = 0;
        int xSizeScale = 16 / xSize;

        for(int dx = 0; dx < xSize; ++dx) {
            int ix = dx * xSizeScale + xSizeScale / 2;

            for(int dz = 0; dz < zSize; ++dz) {
                int iz = dz * xSizeScale + xSizeScale / 2;
                double temperature = chunk.temperature[ix * 16 + iz];
                double humidity = chunk.humidity[ix * 16 + iz] * temperature;
                humidity = 1.0 - humidity;
                humidity *= humidity;
                humidity *= humidity;
                humidity = 1.0 - humidity;
                double scale = (scaleArray[depthScaleIndex] + 256.0) / 512.0;
                scale *= humidity;
                if (scale > 1.0) {
                    scale = 1.0;
                }

                double depth = depthArray[depthScaleIndex] / 8000.0;
                if (depth < 0.0) {
                    depth = -depth * 0.3;
                }

                depth = depth * 3.0 - 2.0;
                if (depth > 1.0) {
                    depth = 1.0;
                }

                depth /= 8.0;
                depth = 0.0;
                if (scale < 0.0) {
                    scale = 0.0;
                }

                scale += 0.5;
                depth = depth * depthBaseSize * 2.0 / 16.0;
                double offsetY = (double)ySize / 2.0;
                ++depthScaleIndex;

                for(int dy = 0; dy < ySize; ++dy) {
                    double density = 0.0;
                    double densityOffset = ((double)dy - offsetY) * 4.0 / scale;
                    if (densityOffset < 0.0) {
                        densityOffset *= -1.0;
                    }

                    double minDensity = minLimitArray[mainIndex] / 512.0;
                    double maxDensity = maxLimitArray[mainIndex] / 512.0;
                    double mainDensity = (mainNoiseArray[mainIndex] / 10.0 + 1.0) / 2.0;
                    if (mainDensity < 0.0) {
                        density = minDensity;
                    } else if (mainDensity > 1.0) {
                        density = maxDensity;
                    } else {
                        density = minDensity + (maxDensity - minDensity) * mainDensity;
                    }

                    density -= 8.0;
                    int upperLowerLimit = 8;
                    double densityMod;
                    if (dy > ySize - upperLowerLimit) {
                        densityMod = (double)((float)(dy - (ySize - upperLowerLimit)) / ((float)upperLowerLimit - 1.0F));
                        density = density * (1.0 - densityMod) + -30.0 * densityMod;
                    }

                    upperLowerLimit = 2;
                    if (dy < upperLowerLimit) {
                        densityMod = (double)((float)(upperLowerLimit - dy) / ((float)upperLowerLimit - 1.0F));
                        density = density * (1.0 - densityMod) + -30.0 * densityMod;
                    }

                    densityMapArray[mainIndex] = density;
                    ++mainIndex;
                }
            }
        }

        return densityMapArray;
    }
}

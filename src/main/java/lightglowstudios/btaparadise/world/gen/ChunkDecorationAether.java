package lightglowstudios.btaparadise.world.gen;

import lightglowstudios.btaparadise.world.gen.biome.BTAPBiomes;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockSand;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.BiomeOutback;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;
import net.minecraft.core.world.generate.feature.*;
import net.minecraft.core.world.noise.PerlinNoise;
import net.minecraft.core.world.type.WorldTypes;

import java.util.Random;

public class ChunkDecorationAether implements ChunkDecorator {
    private final World world;
    private final PerlinNoise treeDensityNoise;
    private final int treeDensityOverride;

    protected ChunkDecorationAether(World world, int treeDensityOverride) {
        this.world = world;
        this.treeDensityOverride = treeDensityOverride;
        this.treeDensityNoise = new PerlinNoise(world.getRandomSeed(), 8, 74);
    }

    public ChunkDecorationAether(World world) {
        this(world, 20);
    }

    public void decorate(Chunk chunk) {
        int chunkX = chunk.xPosition;
        int chunkZ = chunk.zPosition;
        int minY = this.world.getWorldType().getMinY();
        int maxY = this.world.getWorldType().getMaxY();
        int rangeY = maxY + 1 - minY;
        float oreHeightModifier = (float)rangeY / 128.0F;
        BlockSand.fallInstantly = true;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = this.world.getHeightValue(x + 16, z + 16);
        Biome biome = this.world.getBlockBiome(x + 16, y, z + 16);
        Random rand = new Random(this.world.getRandomSeed());
        long l1 = rand.nextLong() / 2L * 2L + 1L;
        long l2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long)chunkX * l1 + (long)chunkZ * l2 ^ this.world.getRandomSeed());
        Random swampRand = new Random((long)chunkX * l1 + (long)chunkZ * l2 ^ this.world.getRandomSeed());
        double d = 0.25;
        int k4;
        int treeDensity;
        int i11;
        int l14;
        int i14;
        int k16;
        int oceanY;
        int dx;
        if (biome == Biomes.OVERWORLD_SWAMPLAND) {
            for(dx = 0; dx < 16; ++dx) {
                for(k4 = 0; k4 < 16; ++k4) {
                    treeDensity = this.world.getHeightValue(x + dx, z + k4);
                    i11 = this.world.getBlockId(x + dx, treeDensity - 1, z + k4);
                    if (i11 == Block.grass.id) {
                        boolean shouldPlaceWater = swampRand.nextFloat() < 0.5F;
                        if (shouldPlaceWater) {
                            l14 = this.world.getBlockId(x + dx + 1, treeDensity - 1, z + k4);
                            i14 = this.world.getBlockId(x + dx - 1, treeDensity - 1, z + k4);
                            k16 = this.world.getBlockId(x + dx, treeDensity - 1, z + k4 + 1);
                            oceanY = this.world.getBlockId(x + dx, treeDensity - 1, z + k4 - 1);
                            dx = this.world.getBlockId(x + dx, treeDensity - 2, z + k4);
                            if (l14 != 0 && (Block.blocksList[l14].blockMaterial.isSolid() || Block.blocksList[l14].blockMaterial == Material.water) && i14 != 0 && (Block.blocksList[i14].blockMaterial.isSolid() || Block.blocksList[i14].blockMaterial == Material.water) && k16 != 0 && (Block.blocksList[k16].blockMaterial.isSolid() || Block.blocksList[k16].blockMaterial == Material.water) && oceanY != 0 && (Block.blocksList[oceanY].blockMaterial.isSolid() || Block.blocksList[oceanY].blockMaterial == Material.water) && dx != 0 && Block.blocksList[dx].blockMaterial.isSolid()) {
                                this.world.setBlock(x + dx, treeDensity - 1, z + k4, Block.fluidWaterStill.id);
                                this.world.setBlock(x + dx, treeDensity, z + k4, 0);
                            }
                        }
                    }
                }
            }
        }

        for(k4 = 0; (float)k4 < 8.0F * oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16) + 8;
            i11 = minY + rand.nextInt(rangeY);
            i11 = z + rand.nextInt(16) + 8;
            if (rand.nextInt(2) == 0) {
                (new WorldFeatureDungeon(Block.brickClay.id, Block.brickClay.id, (String)null)).generate(this.world, rand, treeDensity, i11, i11);
            } else {
                (new WorldFeatureDungeon(Block.cobbleStone.id, Block.cobbleStoneMossy.id, (String)null)).generate(this.world, rand, treeDensity, i11, i11);
            }
        }

        for(k4 = 0; k4 < 1; ++k4) {
            treeDensity = x + rand.nextInt(16) + 8;
            i11 = z + rand.nextInt(16) + 8;
            i11 = this.world.getHeightValue(treeDensity, i11) - (rand.nextInt(2) + 2);
            if (rand.nextInt(5) == 0) {
                i11 -= rand.nextInt(10) + 30;
            }

            if (rand.nextInt(700) == 0) {
                Random lRand = chunk.getChunkRandom(75644760L);
                (new WorldFeatureLabyrinth()).generate(this.world, lRand, treeDensity, i11, i11);
            }
        }

        for(k4 = 0; (float)k4 < 20.0F * oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreCoalStone.id, 16, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < 20.0F * oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 2);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreIronStone.id, 8, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < 2.0F * oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 4);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreGoldStone.id, 8, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < 8.0F * oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 8);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreRedstoneStone.id, 7, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 8);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreDiamondStone.id, 7, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 2);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.mossStone.id, 32, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        for(k4 = 0; (float)k4 < oreHeightModifier; ++k4) {
            treeDensity = x + rand.nextInt(16);
            i11 = minY + rand.nextInt(rangeY / 8) + rand.nextInt(rangeY / 8);
            i11 = z + rand.nextInt(16);
            (new WorldFeatureOre(Block.oreLapisStone.id, 6, true)).generate(this.world, rand, treeDensity, i11, i11);
        }

        d = 0.5;
        k4 = (int)((this.treeDensityNoise.get((double)x * d, (double)z * d) / 8.0 + rand.nextDouble() * 4.0 + 4.0) / 3.0);
        treeDensity = 0;
        if (rand.nextInt(10) == 0) {
            ++treeDensity;
        }

        if (biome == BTAPBiomes.AETHER_BASE) {
            treeDensity += k4 + 4;
        }

        if (this.treeDensityOverride != -1) {
            treeDensity = this.treeDensityOverride;
        }

        for(i11 = 0; i11 < treeDensity; ++i11) {
            i11 = x + rand.nextInt(16) + 8;
            l14 = z + rand.nextInt(16) + 8;
            WorldFeature feature = biome.getRandomWorldGenForTrees(rand);
            feature.func_517_a(1.0, 1.0, 1.0);
            feature.generate(this.world, rand, i11, this.world.getHeightValue(i11, l14), l14);
        }


        byte byteMeadow = 0;
        if (biome == BTAPBiomes.AETHER_BASE) {
            byteMeadow = 1;
        }

        for(l14 = 0; l14 < byteMeadow; ++l14) {
            i14 = Block.flowerYellow.id;
            if (rand.nextInt(3) != 0) {
                i14 = Block.flowerRed.id;
            }

            k16 = x + rand.nextInt(16) + 8;
            oceanY = rand.nextInt(this.world.getHeightBlocks());
            dx = z + rand.nextInt(16) + 8;
            (new WorldFeatureTallGrass(i14)).generate(this.world, rand, k16, oceanY, dx);
        }

        byte byte0 = 0;
        if (biome == BTAPBiomes.AETHER_BASE) {
            byte0 = 2;
        }

        for(i14 = 0; i14 < byte0; ++i14) {
            k16 = x + rand.nextInt(16) + 8;
            oceanY = minY + rand.nextInt(rangeY);
            dx = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(Block.flowerYellow.id)).generate(this.world, rand, k16, oceanY, dx);
        }

        if (rand.nextInt(2) == 0) {
            k16 = x + rand.nextInt(16) + 8;
            oceanY = minY + rand.nextInt(rangeY);
            dx = z + rand.nextInt(16) + 8;
            (new WorldFeatureFlowers(Block.flowerRed.id)).generate(this.world, rand, k16, oceanY, dx);
        }

        if (rand.nextInt(128) == 0) {
            k16 = x + rand.nextInt(16) + 8;
            oceanY = z + rand.nextInt(16) + 8;
            dx = this.world.getHeightValue(k16, oceanY);
            (new WorldFeaturePumpkin()).generate(this.world, rand, k16, dx, oceanY);
        }

        BlockSand.fallInstantly = false;
    }
}

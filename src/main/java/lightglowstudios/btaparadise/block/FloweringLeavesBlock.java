package lightglowstudios.btaparadise.block;

import lightglowstudios.btaparadise.BTAParadise;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLayerLeaves;
import net.minecraft.core.block.BlockLeavesBase;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.wind.WindManager;

import java.util.Random;

public class FloweringLeavesBlock extends BlockLeavesBase {

    public FloweringLeavesBlock(String key, int id, Material material, boolean flag) {
        super(key, id, material, flag);
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if (rand.nextInt(128) == 0 && world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().hasFallingLeaves) {
            for (int q = -1; q > -16; --q) {
                int id = world.getBlockId(x, y + q, z);
                if (id != 0) {
                    break;
                }
            }
        }
    }

    @Override
    protected Block getSapling() {
        return BTAParadise.floweringSapling;
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.seasonManager.getCurrentSeason() != null && world.seasonManager.getCurrentSeason().hasFallingLeaves) {
            WindManager wind = world.getWorldType().getWindManager();
            float windIntensity = wind.getWindIntensity(world, (float)x, (float)y, (float)z);
            if (rand.nextInt((int)(40.0F + 200.0F * (1.0F - windIntensity))) == 0) {
                world.spawnParticle("fallingleaf", (double)x, (double)((float)y - 0.1F), (double)z, 0.0, 0.0, 0.0);
            }
        }

    }
}

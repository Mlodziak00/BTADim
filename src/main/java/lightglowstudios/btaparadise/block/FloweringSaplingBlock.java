package lightglowstudios.btaparadise.block;

import lightglowstudios.btaparadise.BTAParadise;
import lightglowstudios.btaparadise.world.gen.feature.tree.WorldFeatureParadiseTree;
import net.minecraft.core.block.BlockSaplingBase;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class FloweringSaplingBlock extends BlockSaplingBase {
    public FloweringSaplingBlock(String key, int id) {
        super(key, id);
    }

    public void growTree(World world, int i, int j, int k, Random random) {
        Object obj = null;
        world.setBlock(i, j, k, 0);
        obj = new WorldFeatureParadiseTree(BTAParadise.floweringLeaves.id, BTAParadise.floweringLog.id, 3);
        if (!((WorldFeature)obj).generate(world, random, i, j, k)) {
            world.setBlock(i, j, k, this.id);
        }

    }
}

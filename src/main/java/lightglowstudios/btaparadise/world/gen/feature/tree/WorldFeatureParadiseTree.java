package lightglowstudios.btaparadise.world.gen.feature.tree;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureParadiseTree extends WorldFeature {
    protected int leavesID;
    protected int logID;
    protected int heightMod;

    public WorldFeatureParadiseTree(int leavesID, int logID, int heightMod) {
        this.leavesID = leavesID;
        this.logID = logID;
        this.heightMod = heightMod;
    }
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(2) + this.heightMod;
        boolean flag = true;
        if (y >= 1 && y + l + 1 <= world.getHeightBlocks()) {
            int i1;
            int k1;
            int j2;
            int i3;
            int k3;
            for(i1 = y; i1 <= y + 1 + l; ++i1) {
                k1 = 2;


                for(j2 = x - k1; j2 <= x + k1 && flag; ++j2) {
                    for(i3 = z - k1; i3 <= z + k1 && flag; ++i3) {
                        if (i1 >= 0 && i1 < world.getHeightBlocks()) {
                            k3 = world.getBlockId(j2, i1, i3);
                            if (k3 != 0 && k3 != this.leavesID) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                i1 = world.getBlockId(x, y - 1, z);
                if (Block.hasTag(i1, BlockTags.GROWS_TREES) && y < world.getHeightBlocks() - l - 1) {
                    world.setBlockWithNotify(x, y - 1, z, Block.dirt.id);

                    for(k1 = y - 2 + l; k1 <= y + l; ++k1) {
                        j2 = k1 - (y + l + 1);
                        i3 = 1 - j2 / 2;

                        for(k3 = x - i3; k3 <= x + i3; ++k3) {
                            int l3 = k3;

                            for(int i4 = z - i3; i4 <= z + i3; ++i4) {
                                int j4 = i4 - z;
                                if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !Block.opaqueCubeLookup[world.getBlockId(k3, k1, i4)] && (k3 != x - i3 && k3 != x + i3 || i4 != z - i3 && i4 != z + i3 && i1 != z + i3 )) {
                                    world.setBlockWithNotify(k3, k1, i4, this.leavesID);
                                }
                            }
                        }
                    }

                    for(k1 = 0; k1 < l; ++k1) {
                        j2 = world.getBlockId(x, y + k1, z);
                        if (j2 == 0 || j2 == this.leavesID) {
                            world.setBlockWithNotify(x, y + k1, z, this.logID);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}

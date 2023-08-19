package lightglowstudios.btaparadise.world.gen.biome;

import lightglowstudios.btaparadise.BTAParadise;
import lightglowstudios.btaparadise.world.gen.feature.tree.WorldFeatureParadiseTree;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class AetherBaseBiome extends Biome {
    public AetherBaseBiome() {
        this.spawnableAmbientCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPig.class, 10));
    }

    @Override
    public int getSkyColor(float temperature) {
        return super.getSkyColor(16762247);
    }

    @Override
    public WorldFeature getRandomWorldGenForTrees(Random random) {
        return new WorldFeatureParadiseTree(BTAParadise.floweringLeaves.id, BTAParadise.floweringLog.id, 3);
    }
}

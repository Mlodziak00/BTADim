package lightglowstudios.btaparadise.world.gen;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.TerrainGeneratorLerp;
import net.minecraft.core.world.type.WorldType;

public class TerrainGeneratorAether extends TerrainGeneratorLerp {
    private final DensityGenerator densityGenerator;

    public TerrainGeneratorAether(World world) {
        super(world);
        this.densityGenerator = new DensityGeneratorAether(world);
    }

    public DensityGenerator getDensityGenerator() {
        return this.densityGenerator;
    }

    protected int getBlockAt(int x, int y, int z, double density) {
        WorldType type = this.world.getWorldType();
        return density > 0.0 ? type.getFillerBlock() : 0;
    }
}

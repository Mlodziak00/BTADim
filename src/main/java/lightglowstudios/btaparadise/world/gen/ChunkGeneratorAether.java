package lightglowstudios.btaparadise.world.gen;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.MapGenCaves;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;
import net.minecraft.core.world.generate.chunk.perlin.overworld.SurfaceGeneratorOverworld;

public class ChunkGeneratorAether extends ChunkGeneratorPerlin {
    public ChunkGeneratorAether(World world) {
        super(world, new ChunkDecorationAether(world), new TerrainGeneratorAether(world), new SurfaceGeneratorOverworld(world), new MapGenCaves(false));
    }
}

package lightglowstudios.btaparadise.world.gen.biome;

import lightglowstudios.btaparadise.BTAParadise;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.weather.Weather;

public class BTAPBiomes extends Biomes {
        public static final Biome AETHER_BASE = register(BTAParadise.MOD_ID + "aether.base", (new AetherBaseBiome().setColor(8421731).setBlockedWeathers(Weather.overworldRain, Weather.overworldSnow, Weather.overworldStorm, Weather.overworldFog)));

}

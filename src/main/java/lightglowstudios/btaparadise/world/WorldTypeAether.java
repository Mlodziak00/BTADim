package lightglowstudios.btaparadise.world;

import lightglowstudios.btaparadise.world.gen.ChunkGeneratorAether;
import lightglowstudios.btaparadise.world.gen.biome.BTAPBiomes;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderSingleBiome;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.generate.chunk.perlin.paradise.ChunkGeneratorParadise;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;

public abstract class WorldTypeAether extends WorldType {
    public WorldTypeAether(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
        super(languageKey, defaultWeather, windManager, false, createLightRamp(), defaultSeasonConfig);
    }

    private static float[] createLightRamp() {
        float[] brightnessRamp = new float[32];
        float f = 0.05F;

        for(int i = 0; i <= 31; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            if (i > 15) {
                f1 = 0.0F;
            }

            brightnessRamp[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
        }

        return brightnessRamp;
    }

    public void onWorldCreation(World world) {
        super.onWorldCreation(world);
        world.setWorldTime(576000L);
    }


    public int getOceanY() {
        return 0;
    }

    public int getOceanBlock() {
        return 0;
    }

    public int getFillerBlock() {
        return Block.marble.id;
    }

    public BiomeProvider createBiomeProvider(World world) {
        return new BiomeProviderSingleBiome(BTAPBiomes.AETHER_BASE, 0.5, 0.0, 0.0);
    }

    public ChunkGenerator createChunkGenerator(World world) {
        return new ChunkGeneratorAether(world);
    }

    public boolean isValidSpawn(World world, int x, int y, int z) {
        return true;
    }

    public int getDayNightCycleLengthTicks() {
        return Global.DAY_LENGTH_TICKS * 28 * 2;
    }

    public float getCelestialAngle(World world, long tick, float partialTick) {
        float dayProgress = this.getTimeOfDay(world, tick, partialTick);
        dayProgress -= 0.25F;
        float f2 = dayProgress;
        dayProgress = 1.0F - (float)((Math.cos((double)dayProgress * Math.PI) + 1.0) / 2.0);
        dayProgress = f2 + (dayProgress - f2) / 3.0F;
        return dayProgress;
    }

    public float[] getSunriseColor(float timeOfDay, float partialTick) {
        float[] sunriseCol = new float[4];
        float f2 = 0.4F;
        float f3 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0.0F;
        if (f3 >= f4 - f2 && f3 <= f4 + f2) {
            float c = (f3 - f4) / f2 * 0.5F + 0.5F;
            float a = 1.0F - (1.0F - MathHelper.sin(c * 3.141593F)) * 0.99F;
            a *= a;
            sunriseCol[0] = c * 0.3F + 0.7F;
            sunriseCol[1] = c * c * 0.7F + 0.2F;
            sunriseCol[2] = c * c * 0.0F + 0.2F;
            sunriseCol[3] = a;
            return sunriseCol;
        } else {
            return null;
        }
    }

    public int getSkyDarken(World world, long tick, float partialTick) {
        float f1 = this.getCelestialAngle(world, tick, partialTick);
        float f2 = 1.0F - (MathHelper.cos(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        float weatherOffset = 0.0F;
        if (world.currentWeather != null) {
            weatherOffset = (float)world.currentWeather.subtractLightLevel * world.weatherIntensity * world.weatherPower;
        }

        int subtracted = (int)(f2 * (11.0F - weatherOffset) + weatherOffset);
        if (subtracted > 8) {
            subtracted = 8;
        }

        return subtracted;
    }

    public Vec3d getFogColor(float timeOfDay, float partialTick) {
        int i = 8421536;
        float f2 = MathHelper.cos(timeOfDay * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        float f3 = (float)(i >> 16 & 255) / 255.0F;
        float f4 = (float)(i >> 8 & 255) / 255.0F;
        float f5 = (float)(i & 255) / 255.0F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3d.createVector((double)f3, (double)f4, (double)f5);
    }

    public boolean mayRespawn() {
        return false;
    }

    public float getCloudHeight() {
        return 8.0F;
    }

    public boolean hasGround() {
        return false;
    }
}

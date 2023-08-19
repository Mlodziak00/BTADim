package lightglowstudios.btaparadise.world;

import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import net.minecraft.core.world.wind.WindManagerGeneric;

public class WorldTypeAetherDefault extends WorldTypeAether{
    public WorldTypeAetherDefault(String languageKey)  {
        super(languageKey, Weather.overworldClear, new WindManagerGeneric(), SeasonConfig.builder().withSeasonInCycle(Seasons.PARADISE_GOLD, 28).withSeasonInCycle(Seasons.PARADISE_SILVER, 28).build());
    }
    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return 255;
    }

}

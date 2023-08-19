package lightglowstudios.btaparadise.world;

import lightglowstudios.btaparadise.BTAParadise;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;

public class BTAPWorldTypes extends WorldTypes {
    public static final WorldType AETHER = register("btaparadise:aether.world", new WorldTypeAetherDefault("worldType.aether.default"));
}

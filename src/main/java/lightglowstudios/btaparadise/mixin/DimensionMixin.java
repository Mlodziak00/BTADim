package lightglowstudios.btaparadise.mixin;

import lightglowstudios.btaparadise.BTAParadise;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = Dimension.class, remap = false)
public class DimensionMixin {

    //@ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/Dimension;<init>(Ljava/lang/String;Lnet/minecraft/core/world/Dimension;FI)V", ordinal = 2), index = 3)
    //private static int injected(int portalBlockId){
    //    return BTAParadise.paradisePortalBlock.id;
    //}
}

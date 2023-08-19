package lightglowstudios.btaparadise;

import lightglowstudios.btaparadise.block.FloweringLeavesBlock;
import lightglowstudios.btaparadise.block.FloweringSaplingBlock;
import lightglowstudios.btaparadise.block.ParadiseBlockPortal;
import lightglowstudios.btaparadise.world.BTAPWorldTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLog;
import net.minecraft.core.block.BlockPortal;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.DimensionHelper;


public class BTAParadise implements ModInitializer {
    public static final String MOD_ID = "btaparadise";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    static int baseID = 900;

    public static final Block floweringLog = BlockHelper.createBlock(MOD_ID, new BlockLog("flowering.log.btaparadise", baseID + 1), "floweringlog_tb.png", "floweringlog_tb.png",
            "floweringlog_side.png", BlockSounds.WOOD, 0, 0, 0).withTags(BlockTags.FENCES_CONNECT, BlockTags.MINEABLE_BY_AXE);
    public static final Block floweringLeaves = BlockHelper.createBlock(MOD_ID, new FloweringLeavesBlock("flowering.leaves.btaparadise", baseID + 2, Material.leaves, true),
            "flowering_leaves.png", BlockSounds.GRASS, 0, 0, 0).withTags(BlockTags.MINEABLE_BY_AXE, BlockTags.MINEABLE_BY_HOE, BlockTags.MINEABLE_BY_SWORD, BlockTags.MINEABLE_BY_SHEARS);

    public static final Block floweringSapling = BlockHelper.createBlock(MOD_ID, new FloweringSaplingBlock("flowering.sapling.btaparadise", baseID + 3),
            "flowering_sapling.png", BlockSounds.GRASS, 0, 0, 0).withTags(BlockTags.BROKEN_BY_FLUIDS);

    public static final Block paradisePortalBlock = BlockHelper.createBlock(MOD_ID, new ParadiseBlockPortal("paradise.portal.btaparadise", baseID + 4, 6, Block.glowstone.id, Block.fluidWaterFlowing.id),
            "paradiseportal.png", BlockSounds.GLASS, -1.0f, 0.0f, 0.75f).withTags(BlockTags.BROKEN_BY_FLUIDS);


    @Override
    public void onInitialize() {
        BlockModelDispatcher.getInstance().addDispatch(BTAParadise.floweringSapling, new BlockModelRenderBlocks(1));

        CraftingManager.getInstance().addRecipe(new ItemStack(Block.planksOakPainted, 4, DyeColor.DYE_LIGHT_BLUE.blockMeta),  "#", '#', BTAParadise.floweringLog);

        DimensionHelper.createDimension(6, "aether", Dimension.overworld, 10.0F, BTAParadise.paradisePortalBlock, BTAPWorldTypes.AETHER);


        LOGGER.info(MOD_ID + " initialized.");
        ((BlockPortal)BTAParadise.paradisePortalBlock).portalTriggerId = Block.fluidWaterFlowing.id;

    }

}

package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.event.listeners.ToolModificationListener;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.aetherteam.aetherii.loot.AetherIILootContexts;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.Map;

public class ToolModificationHooks {
    /**
     * Blocks able to be flattened with {@link ToolActions#AXE_STRIP}, and the equivalent result block.
     */
    public static final Map<Block, Block> STRIPPABLES = (new ImmutableMap.Builder<Block, Block>())
            .put(AetherIIBlocks.SKYROOT_LOG.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get())
            .put(AetherIIBlocks.SKYROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get())
            .put(AetherIIBlocks.AMBEROOT_LOG.get(), AetherIIBlocks.STRIPPED_SKYROOT_LOG.get())
            .put(AetherIIBlocks.AMBEROOT_WOOD.get(), AetherIIBlocks.STRIPPED_SKYROOT_WOOD.get())
            .build();

    /**
     * Blocks able to be flattened with {@link ToolActions#SHOVEL_FLATTEN}, and the equivalent result block.
     */
    public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
            .put(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT_PATH.get())
            .put(AetherIIBlocks.AETHER_DIRT.get(), AetherIIBlocks.AETHER_DIRT_PATH.get())
            .put(AetherIIBlocks.COARSE_AETHER_DIRT.get(), AetherIIBlocks.AETHER_DIRT_PATH.get())
            .build();

    /**
     * Blocks able to be tilled with {@link ToolActions#HOE_TILL}, and the equivalent result block.
     */
    public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
            .put(AetherIIBlocks.AETHER_DIRT.get(), AetherIIBlocks.AETHER_FARMLAND.get())
            .put(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_FARMLAND.get())
            .put(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_FARMLAND.get())
            .put(AetherIIBlocks.COARSE_AETHER_DIRT.get(), AetherIIBlocks.AETHER_DIRT.get())
            .build();

    /**
     * Handles modifying blocks when a {@link ToolAction} is performed on them.
     *
     * @param accessor The {@link LevelAccessor} of the level.
     * @param pos      The {@link Block} within the level.
     * @param old      The old {@link BlockState} of the block an action is being performed on.
     * @param action   The {@link ToolAction} being performed on the block.
     * @return The new {@link BlockState} of the block.
     * @see ToolModificationListener#setupToolModifications(BlockEvent.BlockToolModificationEvent)
     */
    public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
        Block oldBlock = old.getBlock();
        if (action == ToolActions.AXE_STRIP) {
            if (STRIPPABLES.containsKey(oldBlock)) {
                return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
            }
        }
        else if (action == ToolActions.SHOVEL_FLATTEN) {
            if (FLATTENABLES.containsKey(oldBlock)) {
                return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
            }
        }
        else if (action == ToolActions.HOE_TILL) {
            if (accessor.getBlockState(pos.above()).isAir()) {
                if (TILLABLES.containsKey(oldBlock)) {
                    return TILLABLES.get(oldBlock).withPropertiesOf(old);
                }
            }
        }
        return old;
    }

    /**
     * Spawns Golden Amber at the user's click position when stripping Golden Oak Logs ({@link AetherIITags.Blocks#AMBEROOT_LOGS}), as long as the tool in usage can harvest Golden Amber ({@link AetherIITags.Items#GOLDEN_AMBER_HARVESTERS}).<br><br>
     * The drops are handled using a special loot context type {@link com.aetherteam.aetherii.loot.AetherIILootContexts#STRIPPING}, used for a loot table found in {@link com.aetherteam.aetherii.data.generators.loot.AetherIIStrippingLoot}.
     *
     * @param accessor The {@link LevelAccessor} of the level.
     * @param state    The {@link BlockState} an action is being performed on.
     * @param stack    The {@link ItemStack} performing an action.
     * @param action   The {@link ToolAction} being performed.
     * @param context  The {@link UseOnContext} of this interaction.
     * @see ToolModificationListener#amberootStripping(BlockEvent.BlockToolModificationEvent)
     */
    public static void stripAmberoot(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
        if (action == ToolActions.AXE_STRIP) {
            if (accessor instanceof Level level) {
                if (state.is(AetherIITags.Blocks.AMBEROOT_LOGS) && stack.is(AetherIITags.Items.GOLDEN_AMBER_HARVESTERS)) {
                    if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                        Vec3 vector = context.getClickLocation();
                        LootParams parameters = new LootParams.Builder(serverLevel).withParameter(LootContextParams.TOOL, stack).create(AetherIILootContexts.STRIPPING);
                        LootTable lootTable = level.getServer().getLootData().getLootTable(AetherIILoot.STRIP_GOLDEN_OAK);
                        List<ItemStack> list = lootTable.getRandomItems(parameters);
                        for (ItemStack itemStack : list) {
                            ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                            itemEntity.setDefaultPickUpDelay();
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }
            }
        }
    }
}
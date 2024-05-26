package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Map;

public class AbilityHooks {

    public static class ToolHooks {
        /**
         * Blocks able to be flattened with {@link ToolActions#SHOVEL_FLATTEN}, and the equivalent result block.
         */
        public static final Map<Block, Block> FLATTENABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_DIRT_PATH.get())
                .put(AetherIIBlocks.AETHER_DIRT.get(), AetherIIBlocks.AETHER_DIRT_PATH.get())
                .build();

        /**
         * Blocks able to be tilled with {@link ToolActions#HOE_TILL}, and the equivalent result block.
         */
        public static final Map<Block, Block> TILLABLES = (new ImmutableMap.Builder<Block, Block>())
                .put(AetherIIBlocks.AETHER_DIRT.get(), AetherIIBlocks.AETHER_FARMLAND.get())
                .put(AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.AETHER_FARMLAND.get())
                .put(AetherIIBlocks.AETHER_DIRT_PATH.get(), AetherIIBlocks.AETHER_FARMLAND.get())
                .build();

        /**
         * Handles modifying blocks when a {@link ToolAction} is performed on them.
         *
         * @param accessor The {@link LevelAccessor} of the level.
         * @param pos      The {@link Block} within the level.
         * @param old      The old {@link BlockState} of the block an action is being performed on.
         * @param action   The {@link ToolAction} being performed on the block.
         * @return The new {@link BlockState} of the block.
         * @see com.aetherteam.aetherii.event.listeners.abilities.ToolAbilityListener#setupToolModifications(BlockEvent.BlockToolModificationEvent)
         */
        public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
            Block oldBlock = old.getBlock();
            if (action == ToolActions.SHOVEL_FLATTEN) {
                if (FLATTENABLES.containsKey(oldBlock)) {
                    return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
                }
            } else if (action == ToolActions.HOE_TILL) {
                if (accessor.getBlockState(pos.above()).isAir()) {
                    if (TILLABLES.containsKey(oldBlock)) {
                        return TILLABLES.get(oldBlock).withPropertiesOf(old);
                    }
                }
            }
            return old;
        }
    }
}
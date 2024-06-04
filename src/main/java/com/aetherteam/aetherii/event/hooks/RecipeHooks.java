package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherIIGameEvents;
import com.aetherteam.aetherii.block.FreezingBlock;
import com.aetherteam.aetherii.blockentity.IcestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class RecipeHooks {
    /**
     * Caches all Icestone freezing recipes, checks if a block is in the cache, and sends a {@link AetherGameEvents#ICESTONE_FREEZABLE_UPDATE} game event update from that block.
     * The game event is used to let Icestone blocks know to freeze another block in a performance-efficient way.
     *
     * @param accessor The {@link LevelAccessor} that the block is in.
     * @param pos      The {@link BlockPos}
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onNeighborNotified(BlockEvent.NeighborNotifyEvent)
     */
    public static void sendIcestoneFreezableUpdateEvent(LevelAccessor accessor, BlockPos pos) {
        if (accessor instanceof Level level && !level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            FreezingBlock.cacheRecipes(level);
            if (FreezingBlock.matchesCache(oldBlockState.getBlock(), oldBlockState, accessor.getBiome(pos)) != null) {
                level.gameEvent(null, AetherIIGameEvents.ICESTONE_FREEZABLE_UPDATE.value(), pos);
            }
        }
    }

    /**
     * Prevents freezing blocks at a position from Icestone if that position is marked to have delayed freezing.
     *
     * @param accessor  The {@link LevelAccessor} that the block is in.
     * @param sourcePos The {@link BlockPos} of the source of the freezing.
     * @param pos       The {@link BlockPos} of the block to freeze.
     * @return Whether freezing a block should be prevented, as a {@link Boolean}.
     * @see com.aetherteam.aether.event.listeners.RecipeListener#onBlockFreeze(FreezeEvent.FreezeFromBlock)
     */
    public static boolean preventBlockFreezing(LevelAccessor accessor, BlockPos sourcePos, BlockPos pos) {
        if (accessor.getBlockEntity(sourcePos) instanceof IcestoneBlockEntity blockEntity) {
            for (Map.Entry<BlockPos, Integer> entry : blockEntity.getLastBrokenPositions().entrySet()) {
                if (entry.getKey().equals(pos) && entry.getValue() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}

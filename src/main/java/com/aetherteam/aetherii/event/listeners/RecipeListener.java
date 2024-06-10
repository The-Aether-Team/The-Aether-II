package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.FreezeEvent;
import com.aetherteam.aetherii.event.hooks.RecipeHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.level.BlockEvent;

public class RecipeListener {
    public static void listen(IEventBus bus) {
        bus.addListener(RecipeListener::onNeighborNotified);
        bus.addListener(RecipeListener::onBlockFreeze);
    }

    /**
     * @see RecipeHooks#checkExistenceBanned(LevelAccessor, BlockPos)
     */
    public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        RecipeHooks.sendIcestoneFreezableUpdateEvent(levelAccessor, blockPos);
    }

    /**
     * @see RecipeHooks#preventBlockFreezing(LevelAccessor, BlockPos, BlockPos)
     */
    public static void onBlockFreeze(FreezeEvent.FreezeFromBlock event) {
        LevelAccessor level = event.getLevel();
        BlockPos sourcePos = event.getSourcePos();
        BlockPos pos = event.getPos();
        if (RecipeHooks.preventBlockFreezing(level, sourcePos, pos)) {
            event.setCanceled(true);
        }
    }
}
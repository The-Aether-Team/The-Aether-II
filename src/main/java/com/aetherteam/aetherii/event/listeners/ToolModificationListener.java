package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.ToolModificationHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ToolModificationListener {
    /**
     * @see com.aetherteam.aetherii.AetherII#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(ToolModificationListener::setupToolModifications);
        bus.addListener(ToolModificationListener::setupStrippingLoot);
    }

    /**
     * @see ToolModificationHooks#setupToolActions(LevelAccessor, BlockPos, BlockState, ItemAbility)
     */
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ItemAbility toolAction = event.getItemAbility();
        BlockState newState = ToolModificationHooks.setupToolActions(levelAccessor, pos, oldState, toolAction);
        if (newState != oldState && !event.isSimulated() && !event.isCanceled()) {
            event.setFinalState(newState);
        }
    }

    /**
     * @see ToolModificationHooks#stripAmberoot(LevelAccessor, BlockState, ItemStack, ItemAbility, UseOnContext)
     */
    public static void setupStrippingLoot(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockState oldState = event.getState();
        ItemStack itemStack = event.getHeldItemStack();
        ItemAbility toolAction = event.getItemAbility();
        UseOnContext context = event.getContext();
        if (!event.isSimulated() && !event.isCanceled()) {
            ToolModificationHooks.stripMossyWisproot(levelAccessor, oldState, itemStack, toolAction, context);
            ToolModificationHooks.stripAmberoot(levelAccessor, oldState, itemStack, toolAction, context);
        }
    }
}
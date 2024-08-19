package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.ToolModificationHooks;
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
        bus.addListener(ToolModificationListener::setupStrippingLoot);
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
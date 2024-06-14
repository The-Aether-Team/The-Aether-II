package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.ToolAbilityHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ToolAbilityListener {
    public static void listen(IEventBus bus) {
        bus.addListener(ToolAbilityListener::addHolystoneToolDrop);
        bus.addListener(ToolAbilityListener::modifyBreakSpeed);
    }

    public static void addHolystoneToolDrop(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.level();
        BlockPos blockPos = event.getPos();
        ItemStack itemStack = player.getMainHandItem();
        BlockState blockState = event.getState();
        if (!event.isCanceled()) {
            ToolAbilityHooks.handleHolystoneToolAbility(player, level, blockPos, itemStack, blockState);
        }
    }

    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        if (!event.isCanceled()) {
            event.setNewSpeed(ToolAbilityHooks.handleZaniteToolAbility(itemStack, event.getNewSpeed()));
        }
    }
}
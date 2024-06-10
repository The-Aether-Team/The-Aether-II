package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.ToolAbilityHooks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class ToolAbilityListener {
    public static void listen(IEventBus bus) {
        bus.addListener(ToolAbilityListener::modifyBreakSpeed);
    }

    public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        if (!event.isCanceled()) {
            event.setNewSpeed(ToolAbilityHooks.handleZaniteToolAbility(itemStack, event.getNewSpeed()));
        }
    }
}
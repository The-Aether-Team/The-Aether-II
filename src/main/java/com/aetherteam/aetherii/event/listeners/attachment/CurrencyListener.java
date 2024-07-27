package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.attachment.CurrencyHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class CurrencyListener {
    public static void listen(IEventBus bus) {
        bus.addListener(CurrencyListener::onJoinLevel);
        bus.addListener(CurrencyListener::onPlayerUpdate);
    }

    public static void onJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        CurrencyHooks.onPlayerJoinLevel(entity);
    }

    public static void onPlayerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        CurrencyHooks.onUpdate(player);
    }
}

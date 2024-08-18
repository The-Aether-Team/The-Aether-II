package com.aetherteam.aetherii.event.listeners.attachment;

import com.aetherteam.aetherii.event.hooks.attachment.SwetHooks;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class SwetListener {
    public static void listen(IEventBus bus) {
        bus.addListener(SwetListener::onPlayerUpdate);
    }

    public static void onPlayerUpdate(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        SwetHooks.onUpdate(player);
    }
}

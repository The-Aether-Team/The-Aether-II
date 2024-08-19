package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.GuidebookDiscoveryHooks;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class GuidebookDiscoveryListener {
    public static void listen(IEventBus bus) {
        bus.addListener(GuidebookDiscoveryListener::onPlayerClone);
        bus.addListener(GuidebookDiscoveryListener::progressAdvancement);
    }


    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        GuidebookDiscoveryHooks.onPlayerClone(player);
    }

    public static void progressAdvancement(AdvancementEvent.AdvancementProgressEvent event) {
        Player player = event.getEntity();
        AdvancementHolder advancementHolder = event.getAdvancement();
        GuidebookDiscoveryHooks.progressAdvancement(player, advancementHolder);
    }
}

package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.event.hooks.OutpostTrackerHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerRespawnPositionEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class OutpostTrackerListener {
    public static void listen(IEventBus bus) {
        bus.addListener(OutpostTrackerListener::positionRespawningPlayer);
    }

    public static void positionRespawningPlayer(PlayerRespawnPositionEvent event) {
        Player player = event.getEntity();
        DimensionTransition transition = OutpostTrackerHooks.findOutpostRespawnLocation(player);
        if (transition != null) {
            event.setDimensionTransition(transition);
        }
    }
}

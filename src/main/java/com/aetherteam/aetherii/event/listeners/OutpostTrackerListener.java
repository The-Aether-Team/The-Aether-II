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
        bus.addListener(OutpostTrackerListener::tick);
        bus.addListener(OutpostTrackerListener::joinLevel);
        bus.addListener(OutpostTrackerListener::positionRespawningPlayer);
        bus.addListener(OutpostTrackerListener::respawnPlayer);
    }

    public static void tick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        OutpostTrackerHooks.onUpdate(player);
    }

    public static void joinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        OutpostTrackerHooks.onPlayerLogin(entity);
    }

    public static void positionRespawningPlayer(PlayerRespawnPositionEvent event) {
        Player player = event.getEntity();
        DimensionTransition transition = OutpostTrackerHooks.findOutpostRespawnLocation(player);
        if (transition != null) {
            event.setDimensionTransition(transition);
        }
    }

    public static void respawnPlayer(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        OutpostTrackerHooks.markNoLongerRespawnAtOutpost(player);
        OutpostTrackerHooks.onRespawn(player);
    }
}

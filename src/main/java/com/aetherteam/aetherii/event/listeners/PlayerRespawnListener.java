package com.aetherteam.aetherii.event.listeners;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.event.hooks.PlayerRespawnHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerRespawnPositionEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PlayerRespawnListener {
    public static void listen(IEventBus bus) {
        bus.addListener(PlayerRespawnListener::positionRespawningPlayer);
        bus.addListener(PlayerRespawnListener::respawnPlayer);
        bus.addListener(PlayerRespawnListener::tick);
    }

    public static void tick(PlayerTickEvent.Post event) {
        var data = event.getEntity().getData(AetherIIDataAttachments.OUTPOST_TRACKER);
        AetherII.LOGGER.info(data.getCampfirePositions().toString());

    }

    public static void positionRespawningPlayer(PlayerRespawnPositionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            var data = serverPlayer.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            if (data.shouldRespawnAtOutpost()) {
                ServerLevel serverLevel = serverPlayer.server.getLevel(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL);
                if (serverLevel != null) {
                    BlockPos closest = data.findClosestPositionTo(event.getEntity().blockPosition());
                    if (closest != null) {
                        ServerPlayer.RespawnPosAngle posAngle = new ServerPlayer.RespawnPosAngle(new Vec3((double) closest.getX() + 0.5, (double) closest.getY() + 0.1, (double) closest.getZ() + 0.5), serverPlayer.getRespawnAngle());
                        DimensionTransition transition = new DimensionTransition(serverLevel, posAngle.position(), Vec3.ZERO, posAngle.yaw(), 0.0F, DimensionTransition.DO_NOTHING);
                        event.setDimensionTransition(transition);
                    }
                }
            }
        }
    }

    public static void respawnPlayer(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).setShouldRespawnAtOutpost(false);
    }
}

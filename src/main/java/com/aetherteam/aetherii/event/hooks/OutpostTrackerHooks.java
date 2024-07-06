package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class OutpostTrackerHooks {
    public static DimensionTransition findOutpostRespawnLocation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            var data = serverPlayer.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            if (data.shouldRespawnAtOutpost()) {
                ServerLevel serverLevel = serverPlayer.server.getLevel(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL);
                if (serverLevel != null) {
                    BlockPos closest = data.findClosestPositionTo(serverLevel, player.blockPosition());
                    if (closest != null) { //todo more random position selection around the position
                        ServerPlayer.RespawnPosAngle posAngle = new ServerPlayer.RespawnPosAngle(new Vec3((double) closest.getX() + 0.5, (double) closest.getY() + 0.1, (double) closest.getZ() + 0.5), serverPlayer.getRespawnAngle());
                        return new DimensionTransition(serverLevel, posAngle.position(), Vec3.ZERO, posAngle.yaw(), 0.0F, DimensionTransition.DO_NOTHING);
                    } else {
                        //todo failed to find outpost message; something happened to the outpost block.
                    }
                }
            }
        }
        return null;
    }

    public static void markNoLongerRespawnAtOutpost(Player player) {
        player.getData(AetherIIDataAttachments.OUTPOST_TRACKER).setShouldRespawnAtOutpost(false);
    }

    public static void onPlayerLogin(Entity entity) {
        if (entity instanceof Player player) {
            var data = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            data.onJoinLevel();
        }
    }

    public static void onUpdate(Player player) {
        var data = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
        data.onUpdate(player);
        AetherII.LOGGER.info(data.getCampfirePositions().toString());
    }

    public static void onRespawn(Player player) {
        var data = player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
        data.handleRespawn();
    }
}

package com.aetherteam.aetherii.event.hooks;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.google.common.collect.Streams;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class OutpostTrackerHooks {
    public static DimensionTransition findOutpostRespawnLocation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            var data = serverPlayer.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            if (data.shouldRespawnAtOutpost()) {
                OutpostTrackerAttachment.CampfirePosition closest = data.findClosestPositionTo((ServerLevel) serverPlayer.level(), player.blockPosition());
                if (closest != null) {
                    ServerLevel serverLevel = serverPlayer.getServer().getLevel(closest.level());
                    if (serverLevel != null) {
                        BlockPos.MutableBlockPos respawnPos = closest.pos().mutable();
                        Optional<BlockPos> newRespawnPos = Streams.stream(BlockPos.randomBetweenClosed(serverLevel.getRandom(), 50, respawnPos.getX() - 3, respawnPos.getY(), respawnPos.getZ() - 3, respawnPos.getX() + 3, respawnPos.getY(), respawnPos.getZ() + 3).iterator())
                                .filter((pos) -> serverLevel.getBlockState(pos).getBlock().isPossibleToRespawnInThis(serverLevel.getBlockState(pos)) && !serverLevel.getBlockState(pos).is(AetherIIBlocks.OUTPOST_CAMPFIRE)).findFirst();
                        if (newRespawnPos.isPresent()) {
                            ServerPlayer.RespawnPosAngle posAngle = new ServerPlayer.RespawnPosAngle(new Vec3((double) newRespawnPos.get().getX() + 0.5, (double) newRespawnPos.get().getY() + 0.1, (double) newRespawnPos.get().getZ() + 0.5), serverPlayer.getRespawnAngle());
                            return new DimensionTransition(serverLevel, posAngle.position(), Vec3.ZERO, posAngle.yaw(), 0.0F, DimensionTransition.DO_NOTHING);
                        }
                    }
                }
                player.displayClientMessage(Component.translatable("aether_ii.message.campfire_respawn_failed"), false);
            }
        }
        return null;
    }
}

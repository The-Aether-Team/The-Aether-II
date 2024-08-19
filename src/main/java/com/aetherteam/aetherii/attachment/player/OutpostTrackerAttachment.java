package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.network.packet.OutpostTrackerSyncPacket;
import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OutpostTrackerAttachment {
    private List<CampfirePosition> campfirePositions;
    private boolean shouldRespawnAtOutpost;
    private boolean shouldSyncAfterJoin;

    public static final Codec<OutpostTrackerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CampfirePosition.CODEC.listOf().fieldOf("campfire_positions").forGetter(OutpostTrackerAttachment::getCampfirePositions),
            Codec.BOOL.fieldOf("should_respawn_at_outpost").forGetter(OutpostTrackerAttachment::shouldRespawnAtOutpost)
    ).apply(instance, OutpostTrackerAttachment::new));

    public OutpostTrackerAttachment() {
        this.campfirePositions = new ArrayList<>();
    }

    protected OutpostTrackerAttachment(List<CampfirePosition> campfirePositions, boolean shouldRespawnAtOutpost) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    public void login(Player player) {
        this.shouldSyncAfterJoin = true;
    }

    public void respawn(Player player) {
        this.setShouldRespawnAtOutpost(false);
        this.shouldSyncAfterJoin = true;
    }

    public void postTickUpdate(Player player) {
        this.syncAfterJoin(player);
    }

    private void syncAfterJoin(Player player) {
        if (this.shouldSyncAfterJoin) {
            if (player instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, new OutpostTrackerSyncPacket(this.getCampfirePositions(), this.shouldRespawnAtOutpost()));
            }
            this.shouldSyncAfterJoin = false;
        }
    }

    public DimensionTransition findOutpostRespawnLocation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (this.shouldRespawnAtOutpost()) {
                OutpostTrackerAttachment.CampfirePosition closest = this.findClosestPositionTo((ServerLevel) serverPlayer.level(), player.blockPosition());
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

    @Nullable
    public CampfirePosition findClosestPositionTo(ServerLevel level, BlockPos pos) {
        CampfirePosition respawnPos = null;
        List<CampfirePosition> toRemove = new ArrayList<>();
        for (CampfirePosition campfirePos : this.campfirePositions) {
            ServerLevel serverLevel = level.getServer().getLevel(campfirePos.level());
            if (serverLevel != null) {
                if (serverLevel.getBlockState(campfirePos.pos()).is(AetherIIBlocks.OUTPOST_CAMPFIRE)) {
                    if (respawnPos == null || pos.distSqr(campfirePos.pos()) < pos.distSqr(respawnPos.pos())) {
                        respawnPos = campfirePos;
                    }
                } else {
                    toRemove.add(campfirePos);
                }
            }
        }
        this.campfirePositions.removeAll(toRemove);
        return respawnPos;
    }

    public void setCampfirePositions(List<CampfirePosition> campfirePositions) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
    }

    public void addCampfirePosition(CampfirePosition pos) {
        this.campfirePositions.add(pos);
    }

    public List<CampfirePosition> getCampfirePositions() {
        return this.campfirePositions;
    }

    public void setShouldRespawnAtOutpost(boolean shouldRespawnAtOutpost) {
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    public boolean shouldRespawnAtOutpost() {
        return this.shouldRespawnAtOutpost;
    }

    public record CampfirePosition(ResourceKey<Level> level, BlockPos pos) {
        public static final Codec<CampfirePosition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(CampfirePosition::level),
                BlockPos.CODEC.fieldOf("position").forGetter(CampfirePosition::pos)
        ).apply(instance, CampfirePosition::new));
    }
}

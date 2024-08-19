package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.network.packet.OutpostTrackerSyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public void handleRespawn(Player player) {
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

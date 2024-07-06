package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.network.packet.OutpostTrackerSyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OutpostTrackerAttachment {
    private List<BlockPos> campfirePositions; //todo i should make a custom campfireposition record that also stores dimension. for futureproofing.
    private boolean shouldRespawnAtOutpost;
    private boolean shouldSyncAfterJoin;

    public static final Codec<OutpostTrackerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().fieldOf("campfire_positions").forGetter(OutpostTrackerAttachment::getCampfirePositions),
            Codec.BOOL.fieldOf("should_respawn_at_outpost").forGetter(OutpostTrackerAttachment::shouldRespawnAtOutpost)
    ).apply(instance, OutpostTrackerAttachment::new));

    public OutpostTrackerAttachment() {
        this.campfirePositions = new ArrayList<>();
    }

    protected OutpostTrackerAttachment(List<BlockPos> campfirePositions, boolean shouldRespawnAtOutpost) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    public void onJoinLevel() {
        this.shouldSyncAfterJoin = true;
    }

    public void handleRespawn() {
        this.shouldSyncAfterJoin = true;
    }

    public void onUpdate(Player player) {
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
    public BlockPos findClosestPositionTo(Level level, BlockPos pos) {
        BlockPos respawnPos = null;
        List<BlockPos> toRemove = new ArrayList<>();
        for (BlockPos campfirePos : this.campfirePositions) {
            if (level.getBlockState(campfirePos).is(AetherIIBlocks.OUTPOST_CAMPFIRE)) {
                if (respawnPos == null || pos.distSqr(campfirePos) < pos.distSqr(respawnPos)) {
                    respawnPos = campfirePos;
                }
            } else {
                toRemove.add(campfirePos);
            }
        }
        this.campfirePositions.removeAll(toRemove);
        return respawnPos;
    }

    public void setCampfirePositions(List<BlockPos> campfirePositions) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
    }

    public void addCampfirePosition(BlockPos pos) {
        this.campfirePositions.add(pos);
    }

    public List<BlockPos> getCampfirePositions() {
        return this.campfirePositions;
    }

    public void setShouldRespawnAtOutpost(boolean shouldRespawnAtOutpost) {
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    public boolean shouldRespawnAtOutpost() {
        return this.shouldRespawnAtOutpost;
    }
}

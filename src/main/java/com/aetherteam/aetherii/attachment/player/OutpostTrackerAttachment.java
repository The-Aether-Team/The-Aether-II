package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OutpostTrackerAttachment {
    private List<CampfirePosition> campfirePositions;
    private boolean shouldRespawnAtOutpost;

    public static final MapCodec<OutpostTrackerAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CampfirePosition.CODEC.listOf().fieldOf("campfire_positions").forGetter(OutpostTrackerAttachment::getCampfirePositions),
            Codec.BOOL.fieldOf("should_respawn_at_outpost").forGetter(OutpostTrackerAttachment::shouldRespawnAtOutpost)
    ).apply(instance, OutpostTrackerAttachment::new));

    public static final StreamCodec<FriendlyByteBuf, OutpostTrackerAttachment> STREAM_CODEC = StreamCodec.composite(
            CampfirePosition.STREAM_CODEC.apply(ByteBufCodecs.list()), OutpostTrackerAttachment::getCampfirePositions,
            OutpostTrackerAttachment::new);

    public OutpostTrackerAttachment() {
        this.campfirePositions = new ArrayList<>();
    }

    protected OutpostTrackerAttachment(List<CampfirePosition> campfirePositions, boolean shouldRespawnAtOutpost) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    protected OutpostTrackerAttachment(List<CampfirePosition> campfirePositions) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
    }

    public void login(Player player) {
        
    }

    public void respawn(Player player) {
        this.setShouldRespawnAtOutpost(false);
    }

    public OutpostRespawnLocation findOutpostRespawnLocation(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (this.shouldRespawnAtOutpost()) {
                OutpostTrackerAttachment.CampfirePosition closest = serverPlayer.level() instanceof ServerLevel currentLevel ? this.findClosestPositionTo(currentLevel, player.blockPosition()) : null;
                if (closest != null) {
                    ServerLevel serverLevel = serverPlayer.level().getServer().getLevel(closest.level());
                    if (serverLevel != null) {
                        BlockPos.MutableBlockPos respawnPos = closest.pos().mutable();
                        BlockPos newRespawnPos = null;
                        for (BlockPos pos : BlockPos.randomBetweenClosed(serverLevel.getRandom(), 50, respawnPos.getX() - 3, respawnPos.getY(), respawnPos.getZ() - 3, respawnPos.getX() + 3, respawnPos.getY(), respawnPos.getZ() + 3)) {
                            if (serverLevel.getBlockState(pos).getBlock().isPossibleToRespawnInThis(serverLevel.getBlockState(pos)) && !serverLevel.getBlockState(pos).is(AetherIIBlocks.OUTPOST_CAMPFIRE.get())) {
                                newRespawnPos = pos;
                                break;
                            }
                        }
                        if (newRespawnPos != null) {
                            return new OutpostRespawnLocation(serverLevel, new Vec3((double) newRespawnPos.getX() + 0.5, (double) newRespawnPos.getY() + 0.1, (double) newRespawnPos.getZ() + 0.5), 0.0F);
                        }
                    }
                }
                player.displayClientMessage(Component.translatable("aether_ii.message.campfire_respawn_failed"), true);
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
                if (serverLevel.getBlockState(campfirePos.pos()).is(AetherIIBlocks.OUTPOST_CAMPFIRE.get())) {
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

    public record OutpostRespawnLocation(ServerLevel level, Vec3 position, float yaw) {
    }

    public record CampfirePosition(ResourceKey<Level> level, BlockPos pos) {
        public static final Codec<CampfirePosition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(CampfirePosition::level),
                BlockPos.CODEC.fieldOf("position").forGetter(CampfirePosition::pos)
        ).apply(instance, CampfirePosition::new));

        public static final StreamCodec<FriendlyByteBuf, CampfirePosition> STREAM_CODEC = StreamCodec.of((buffer, position) -> {
            buffer.writeResourceLocation(position.level().location());
            buffer.writeBlockPos(position.pos());
        }, buffer -> new CampfirePosition(ResourceKey.create(Registries.DIMENSION, buffer.readResourceLocation()), buffer.readBlockPos()));
    }
}

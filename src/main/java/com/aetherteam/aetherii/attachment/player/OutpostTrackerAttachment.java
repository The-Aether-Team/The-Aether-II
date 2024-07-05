package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.network.packet.AerbunnyMountSyncPacket;
import com.aetherteam.aetherii.network.packet.OutpostTrackerSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class OutpostTrackerAttachment implements INBTSynchable {
    private final List<BlockPos> campfirePositions;
    private boolean shouldRespawnAtOutpost;

    public static final Codec<OutpostTrackerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().fieldOf("campfire_positions").forGetter(OutpostTrackerAttachment::getCampfirePositions),
            Codec.BOOL.fieldOf("should_respawn_at_outpost").forGetter(OutpostTrackerAttachment::shouldRespawnAtOutpost)
    ).apply(instance, OutpostTrackerAttachment::new));

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("shouldRespawnAtOutpost", Triple.of(Type.BOOLEAN, (object) -> this.setShouldRespawnAtOutpost((boolean) object), this::shouldRespawnAtOutpost))
    );

    public OutpostTrackerAttachment() {
        this.campfirePositions = new ArrayList<>();
    }

    protected OutpostTrackerAttachment(List<BlockPos> campfirePositions, boolean shouldRespawnAtOutpost) {
        this.campfirePositions = new ArrayList<>(campfirePositions);
        this.shouldRespawnAtOutpost = shouldRespawnAtOutpost;
    }

    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Nullable
    public BlockPos findClosestPositionTo(BlockPos pos) {
        BlockPos respawnPos = null;
        for (BlockPos campfirePos : this.campfirePositions) {
            if (respawnPos == null || pos.distSqr(campfirePos) < pos.distSqr(respawnPos)) {
                respawnPos = campfirePos;
            }
        }
        return respawnPos;
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

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new OutpostTrackerSyncPacket(entityID, key, type, value);
    }
}

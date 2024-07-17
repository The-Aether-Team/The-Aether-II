package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record OutpostTrackerSyncPacket(List<OutpostTrackerAttachment.CampfirePosition> campfirePositions, boolean shouldRespawnAtOutpost) implements CustomPacketPayload {
    public static final Type<OutpostTrackerSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_outpost_tracker_attachment"));

    public static final Codec<OutpostTrackerSyncPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            OutpostTrackerAttachment.CampfirePosition.CODEC.listOf().fieldOf("campfire_positions").forGetter(OutpostTrackerSyncPacket::campfirePositions),
            Codec.BOOL.fieldOf("should_respawn_at_outpost").forGetter(OutpostTrackerSyncPacket::shouldRespawnAtOutpost)
    ).apply(instance, OutpostTrackerSyncPacket::new));

    public static final StreamCodec<ByteBuf, OutpostTrackerSyncPacket> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    @Override
    public Type<OutpostTrackerSyncPacket> type() {
        return TYPE;
    }

    public static void execute(OutpostTrackerSyncPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null) {
            var data = playerEntity.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
            data.setCampfirePositions(payload.campfirePositions());
            data.setShouldRespawnAtOutpost(payload.shouldRespawnAtOutpost());
        } else {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
                var data = Minecraft.getInstance().player.getData(AetherIIDataAttachments.OUTPOST_TRACKER);
                data.setCampfirePositions(payload.campfirePositions());
                data.setShouldRespawnAtOutpost(payload.shouldRespawnAtOutpost());
            }
        }
    }
}

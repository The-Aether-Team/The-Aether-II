package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import com.aetherteam.aetherii.network.AetherPayloadContext;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record DataAttachmentSyncPacket(int entityId, AetherIIDataAttachments.AttachmentType<?> attachmentType, Object value) implements AetherPacketPayload {
    public static final Type<DataAttachmentSyncPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "data_attachment_sync"));

    public static final StreamCodec<FriendlyByteBuf, DataAttachmentSyncPacket> STREAM_CODEC = StreamCodec.of(DataAttachmentSyncPacket::toNetwork, DataAttachmentSyncPacket::fromNetwork);

    public static DataAttachmentSyncPacket fromNetwork(FriendlyByteBuf buffer) {
        int entityId = buffer.readVarInt();
        AetherIIDataAttachments.AttachmentType<?> attachmentType = AetherIIDataAttachments.getType(buffer.readUtf());
        Object value = attachmentType.decode(buffer);
        return new DataAttachmentSyncPacket(entityId, attachmentType, value);
    }

    public static void toNetwork(FriendlyByteBuf buffer, DataAttachmentSyncPacket packet) {
        buffer.writeVarInt(packet.entityId());
        buffer.writeUtf(packet.attachmentType().name());
        packet.attachmentType().encode(buffer, packet.value());
    }

    @Override
    public Type<DataAttachmentSyncPacket> type() {
        return TYPE;
    }

    public static void execute(DataAttachmentSyncPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(payload.entityId());
            if (entity != null) {
                AetherIIDataAttachments.setSynced(entity, payload.attachmentType(), payload.value());
            }
        }
    }
}

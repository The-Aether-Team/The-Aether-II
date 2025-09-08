package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateGuidebookDiscoveryPacket(GuidebookDiscoveryAttachment attachment) implements CustomPacketPayload {
    public static final Type<UpdateGuidebookDiscoveryPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "update_guidebook_discovery"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateGuidebookDiscoveryPacket> STREAM_CODEC = StreamCodec.composite(
            GuidebookDiscoveryAttachment.STREAM_CODEC,
            UpdateGuidebookDiscoveryPacket::attachment,
            UpdateGuidebookDiscoveryPacket::new);

    @Override
    public Type<UpdateGuidebookDiscoveryPacket> type() {
        return TYPE;
    }

    public static void execute(UpdateGuidebookDiscoveryPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).syncAttachment(payload.attachment());
        }
    }
}

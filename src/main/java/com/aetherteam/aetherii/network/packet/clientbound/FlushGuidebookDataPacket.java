package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FlushGuidebookDataPacket() implements CustomPacketPayload {
    public static final Type<FlushGuidebookDataPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "flush_guidebook_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, FlushGuidebookDataPacket> STREAM_CODEC = CustomPacketPayload.codec(FlushGuidebookDataPacket::write, FlushGuidebookDataPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) { }

    public static FlushGuidebookDataPacket decode(RegistryFriendlyByteBuf buf) {
        return new FlushGuidebookDataPacket();
    }

    @Override
    public Type<FlushGuidebookDataPacket> type() {
        return TYPE;
    }

    public static void execute(FlushGuidebookDataPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().player.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clearEntries();
        }
    }
}

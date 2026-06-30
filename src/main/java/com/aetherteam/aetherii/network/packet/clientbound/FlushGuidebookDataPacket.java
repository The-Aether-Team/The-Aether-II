package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record FlushGuidebookDataPacket() implements AetherPacketPayload {
    public static final Type<FlushGuidebookDataPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "flush_guidebook_data"));

    public static final StreamCodec<FriendlyByteBuf, FlushGuidebookDataPacket> STREAM_CODEC = AetherPacketPayload.codec(FlushGuidebookDataPacket::write, FlushGuidebookDataPacket::decode);

    public void write(FriendlyByteBuf buf) { }

    public static FlushGuidebookDataPacket decode(FriendlyByteBuf buf) {
        return new FlushGuidebookDataPacket();
    }

    @Override
    public Type<FlushGuidebookDataPacket> type() {
        return TYPE;
    }

    public static void execute(FlushGuidebookDataPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherIIDataAttachments.get(Minecraft.getInstance().player, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY).clearEntries();
        }
    }
}

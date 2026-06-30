package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.portal.PortalClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record PortalTravelSoundPacket() implements AetherPacketPayload {
    public static final Type<PortalTravelSoundPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "play_portal_travel_sound"));

    public static final StreamCodec<FriendlyByteBuf, PortalTravelSoundPacket> STREAM_CODEC = StreamCodec.unit(new PortalTravelSoundPacket());

    @Override
    public Type<PortalTravelSoundPacket> type() {
        return TYPE;
    }

    public static void execute(PortalTravelSoundPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            PortalClientUtil.playTravelSound();
        }
    }
}
package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.portal.PortalSoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PortalTravelSoundPacket() implements CustomPacketPayload {
    public static final Type<PortalTravelSoundPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "play_portal_travel_sound"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PortalTravelSoundPacket> STREAM_CODEC = CustomPacketPayload.codec(
            PortalTravelSoundPacket::write,
            PortalTravelSoundPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
    }

    public static PortalTravelSoundPacket decode(RegistryFriendlyByteBuf buf) {
        return new PortalTravelSoundPacket();
    }

    @Override
    public Type<PortalTravelSoundPacket> type() {
        return TYPE;
    }

    public static void execute(PortalTravelSoundPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            PortalSoundUtil.playTravelSound();
        }
    }
}
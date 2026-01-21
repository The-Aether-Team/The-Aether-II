package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AerbunnyMessagePacket() implements CustomPacketPayload {
    public static final Type<AerbunnyMessagePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "aerbunny_message"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AerbunnyMessagePacket> STREAM_CODEC = StreamCodec.unit(new AerbunnyMessagePacket());

    @Override
    public Type<AerbunnyMessagePacket> type() {
        return TYPE;
    }

    public static void execute(AerbunnyMessagePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Component component = Component.translatable("aether_ii.message.passenger.onboard", AetherIIKeyMappings.ALLOW_DISMOUNTING_PASSENGER.getTranslatedKeyMessage(), Minecraft.getInstance().options.keyUse.getTranslatedKeyMessage());
            Minecraft.getInstance().gui.setOverlayMessage(component, false);
            Minecraft.getInstance().getNarrator().saySystemNow(component);
        }
    }
}

package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record AerbunnyMessagePacket() implements AetherPacketPayload {
    public static final Type<AerbunnyMessagePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "aerbunny_message"));

    public static final StreamCodec<FriendlyByteBuf, AerbunnyMessagePacket> STREAM_CODEC = StreamCodec.unit(new AerbunnyMessagePacket());

    @Override
    public Type<AerbunnyMessagePacket> type() {
        return TYPE;
    }

    public static void execute(AerbunnyMessagePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Component component = Component.translatable("aether_ii.message.passenger.onboard", AetherIIKeyMappings.ALLOW_DISMOUNTING_PASSENGER.getTranslatedKeyMessage(), Minecraft.getInstance().options.keyUse.getTranslatedKeyMessage());
            Minecraft.getInstance().gui.setOverlayMessage(component, false);
            Minecraft.getInstance().getNarrator().sayNow(component);
        }
    }
}

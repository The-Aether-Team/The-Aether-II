package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ClientGrabItemPacket(ItemStack carryStack) implements AetherPacketPayload {
    public static final Type<ClientGrabItemPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "grab_from_inventory"));

    public static final StreamCodec<FriendlyByteBuf, ClientGrabItemPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStackCodecs.OPTIONAL_STREAM_CODEC,
            ClientGrabItemPacket::carryStack,
            ClientGrabItemPacket::new);

    @Override
    public Type<ClientGrabItemPacket> type() {
        return TYPE;
    }

    public static void execute(ClientGrabItemPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().player.containerMenu.setCarried(payload.carryStack());
        }
    }
}

package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientGrabItemPacket(ItemStack carryStack) implements CustomPacketPayload {
    public static final Type<ClientGrabItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "grab_from_inventory"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientGrabItemPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            ClientGrabItemPacket::carryStack,
            ClientGrabItemPacket::new);

    @Override
    public Type<ClientGrabItemPacket> type() {
        return TYPE;
    }

    public static void execute(ClientGrabItemPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Minecraft.getInstance().player.containerMenu.setCarried(payload.carryStack());
        }
    }
}

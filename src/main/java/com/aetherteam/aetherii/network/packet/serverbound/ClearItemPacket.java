package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClearItemPacket() implements CustomPacketPayload {
    public static final Type<ClearItemPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "clear_items"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClearItemPacket> STREAM_CODEC = CustomPacketPayload.codec(ClearItemPacket::write, ClearItemPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) { }

    public static ClearItemPacket decode(RegistryFriendlyByteBuf buf) {
        return new ClearItemPacket();
    }

    @Override
    public Type<ClearItemPacket> type() {
        return TYPE;
    }

    public static void execute(ClearItemPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
        }
    }
}

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

public record HeldCurrencyPacket(ItemStack itemStack) implements CustomPacketPayload {
    public static final Type<HeldCurrencyPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "held_currency"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HeldCurrencyPacket> STREAM_CODEC = StreamCodec.composite(ItemStack.OPTIONAL_STREAM_CODEC, HeldCurrencyPacket::itemStack, HeldCurrencyPacket::new);

    @Override
    public Type<HeldCurrencyPacket> type() {
        return TYPE;
    }

    public static void execute(HeldCurrencyPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(payload.itemStack());
        }
    }
}

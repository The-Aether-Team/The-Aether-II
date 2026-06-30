package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record HeldCurrencyPacket(ItemStack itemStack) implements AetherPacketPayload {
    public static final Type<HeldCurrencyPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "held_currency"));

    public static final StreamCodec<FriendlyByteBuf, HeldCurrencyPacket> STREAM_CODEC = StreamCodec.composite(ItemStackCodecs.OPTIONAL_STREAM_CODEC, HeldCurrencyPacket::itemStack, HeldCurrencyPacket::new);

    @Override
    public Type<HeldCurrencyPacket> type() {
        return TYPE;
    }

    public static void execute(HeldCurrencyPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(payload.itemStack());
        }
    }
}

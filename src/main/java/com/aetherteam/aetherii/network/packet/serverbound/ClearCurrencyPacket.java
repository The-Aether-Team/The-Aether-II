package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClearCurrencyPacket() implements CustomPacketPayload {
    public static final Type<ClearCurrencyPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "clear_currency"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClearCurrencyPacket> STREAM_CODEC = StreamCodec.unit(new ClearCurrencyPacket());

    @Override
    public Type<ClearCurrencyPacket> type() {
        return TYPE;
    }

    public static void execute(ClearCurrencyPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getData(AetherIIDataAttachments.CURRENCY).setAmount(0);
        }
    }
}

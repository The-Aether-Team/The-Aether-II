package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CurrencyAmountPacket(int amount) implements CustomPacketPayload {
    public static final Type<CurrencyAmountPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "currency_amount"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CurrencyAmountPacket> STREAM_CODEC = CustomPacketPayload.codec(
            CurrencyAmountPacket::write,
            CurrencyAmountPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(amount);
    }

    public static CurrencyAmountPacket decode(RegistryFriendlyByteBuf buf) {
        return new CurrencyAmountPacket(buf.readInt());
    }


    @Override
    public Type<CurrencyAmountPacket> type() {
        return TYPE;
    }

    public static void execute(CurrencyAmountPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getData(AetherIIDataAttachments.CURRENCY.get()).setAmount(payload.amount);
            serverPlayer.syncData(AetherIIDataAttachments.CURRENCY);
        }
    }
}

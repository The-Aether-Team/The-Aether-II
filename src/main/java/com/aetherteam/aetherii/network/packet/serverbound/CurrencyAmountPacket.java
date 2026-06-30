package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record CurrencyAmountPacket(int amount) implements AetherPacketPayload {
    public static final Type<CurrencyAmountPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "currency_amount"));

    public static final StreamCodec<FriendlyByteBuf, CurrencyAmountPacket> STREAM_CODEC = AetherPacketPayload.codec(
            CurrencyAmountPacket::write,
            CurrencyAmountPacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(amount);
    }

    public static CurrencyAmountPacket decode(FriendlyByteBuf buf) {
        return new CurrencyAmountPacket(buf.readInt());
    }

    @Override
    public Type<CurrencyAmountPacket> type() {
        return TYPE;
    }

    public static void execute(CurrencyAmountPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.CURRENCY).setAmount(payload.amount);
            AetherIIDataAttachments.sync(serverPlayer, AetherIIDataAttachments.CURRENCY);
            AetherIIAdvancementTriggers.CURRENCY.get().trigger(serverPlayer, AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.CURRENCY).getAmount());
        }
    }
}

package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.CurrencyAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class CurrencySyncPacket extends SyncEntityPacket<CurrencyAttachment> {
    public static final Type<CurrencySyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_currency_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CurrencySyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            CurrencySyncPacket::write,
            CurrencySyncPacket::decode);

    public CurrencySyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public CurrencySyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static CurrencySyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new CurrencySyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Type<CurrencySyncPacket> type() {
        return TYPE;
    }

    @Override
    public Supplier<AttachmentType<CurrencyAttachment>> getAttachment() {
        return AetherIIDataAttachments.CURRENCY;
    }

    public static void execute(CurrencySyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}

package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
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

public class DamageSystemSyncPacket extends SyncEntityPacket<DamageSystemAttachment> {
    public static final Type<DamageSystemSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_damage_system_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageSystemSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            DamageSystemSyncPacket::write,
            DamageSystemSyncPacket::decode);

    public DamageSystemSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public DamageSystemSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static DamageSystemSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new DamageSystemSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Type<DamageSystemSyncPacket> type() {
        return TYPE;
    }

    @Override
    public Supplier<AttachmentType<DamageSystemAttachment>> getAttachment() {
        return AetherIIDataAttachments.DAMAGE_SYSTEM;
    }

    public static void execute(DamageSystemSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}

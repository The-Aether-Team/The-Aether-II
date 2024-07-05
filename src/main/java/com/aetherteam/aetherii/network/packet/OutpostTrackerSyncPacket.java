package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.OutpostTrackerAttachment;
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

public class OutpostTrackerSyncPacket extends SyncEntityPacket<OutpostTrackerAttachment> {
    public static final Type<OutpostTrackerSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_outpost_tracker_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OutpostTrackerSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            OutpostTrackerSyncPacket::write,
            OutpostTrackerSyncPacket::decode);

    public OutpostTrackerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public OutpostTrackerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<OutpostTrackerSyncPacket> type() {
        return TYPE;
    }

    public static OutpostTrackerSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new OutpostTrackerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<OutpostTrackerAttachment>> getAttachment() {
        return AetherIIDataAttachments.OUTPOST_TRACKER;
    }

    public static void execute(OutpostTrackerSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}

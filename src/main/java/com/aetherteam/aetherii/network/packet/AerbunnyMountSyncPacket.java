package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.player.AerbunnyMountAttachment;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
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

public class AerbunnyMountSyncPacket extends SyncEntityPacket<AerbunnyMountAttachment> {
    public static final Type<AerbunnyMountSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_aerbunny_mount_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AerbunnyMountSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            AerbunnyMountSyncPacket::write,
            AerbunnyMountSyncPacket::decode);

    public AerbunnyMountSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AerbunnyMountSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<AerbunnyMountSyncPacket> type() {
        return TYPE;
    }

    public static AerbunnyMountSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new AerbunnyMountSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AerbunnyMountAttachment>> getAttachment() {
        return AetherIIDataAttachments.AERBUNNY_MOUNT;
    }

    public static void execute(AerbunnyMountSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
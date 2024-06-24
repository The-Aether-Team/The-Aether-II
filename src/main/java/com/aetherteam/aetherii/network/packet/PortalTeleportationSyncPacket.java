package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.PortalTeleportationAttachment;
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

public class PortalTeleportationSyncPacket extends SyncEntityPacket<PortalTeleportationAttachment> {
    public static final Type<PortalTeleportationSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_portal_teleportation_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PortalTeleportationSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            PortalTeleportationSyncPacket::write,
            PortalTeleportationSyncPacket::decode);

    public PortalTeleportationSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public PortalTeleportationSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<PortalTeleportationSyncPacket> type() {
        return TYPE;
    }

    public static PortalTeleportationSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new PortalTeleportationSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<PortalTeleportationAttachment>> getAttachment() {
        return AetherIIDataAttachments.PORTAL_TELEPORTATION;
    }

    public static void execute(PortalTeleportationSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
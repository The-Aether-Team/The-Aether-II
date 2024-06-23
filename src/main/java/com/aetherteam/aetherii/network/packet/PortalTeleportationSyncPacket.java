package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.PortalTeleportationAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class PortalTeleportationSyncPacket extends SyncEntityPacket<PortalTeleportationAttachment> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_portal_teleportation_attachment");

    public PortalTeleportationSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public PortalTeleportationSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static PortalTeleportationSyncPacket decode(FriendlyByteBuf buf) {
        return new PortalTeleportationSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<PortalTeleportationAttachment>> getAttachment() {
        return AetherIIDataAttachments.PORTAL_TELEPORTATION;
    }
}
package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.AerbunnyMountAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class AerbunnyMountSyncPacket extends SyncEntityPacket<AerbunnyMountAttachment> {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "sync_aerbunny_mount_attachment");

    public AerbunnyMountSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AerbunnyMountSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static AerbunnyMountSyncPacket decode(FriendlyByteBuf buf) {
        return new AerbunnyMountSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AerbunnyMountAttachment>> getAttachment() {
        return AetherIIDataAttachments.AERBUNNY_MOUNT;
    }
}
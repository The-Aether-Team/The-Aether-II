package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.RideMobAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class RideMobSyncPacket extends SyncEntityPacket<RideMobAttachment> {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "sync_ride_mob_attachment");

    public RideMobSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public RideMobSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static RideMobSyncPacket decode(FriendlyByteBuf buf) {
        return new RideMobSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<RideMobAttachment>> getAttachment() {
        return AetherIIDataAttachments.RIDE_MOB;
    }
}
package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.DamageSystemAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class DamageSystemSyncPacket extends SyncEntityPacket<DamageSystemAttachment> {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "sync_damage_system_attachment");

    public DamageSystemSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public DamageSystemSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static DamageSystemSyncPacket decode(FriendlyByteBuf buf) {
        return new DamageSystemSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<DamageSystemAttachment>> getAttachment() {
        return AetherIIDataAttachments.DAMAGE_SYSTEM;
    }
}

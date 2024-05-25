package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.AetherIIPlayerAttachment;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class AetherIIPlayerSyncPacket extends SyncEntityPacket<AetherIIPlayerAttachment> {
    public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "sync_aether_ii_player_attachment");

    public AetherIIPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherIIPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static AetherIIPlayerSyncPacket decode(FriendlyByteBuf buf) {
        return new AetherIIPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AetherIIPlayerAttachment>> getAttachment() {
        return AetherIIDataAttachments.AETHER_II_PLAYER;
    }
}
package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
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

public class AetherIIPlayerSyncPacket extends SyncEntityPacket<AetherIIPlayerAttachment> {
    public static final Type<AetherIIPlayerSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_player_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AetherIIPlayerSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            AetherIIPlayerSyncPacket::write,
            AetherIIPlayerSyncPacket::decode);

    public AetherIIPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherIIPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<AetherIIPlayerSyncPacket> type() {
        return TYPE;
    }

    public static AetherIIPlayerSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new AetherIIPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AetherIIPlayerAttachment>> getAttachment() {
        return AetherIIDataAttachments.PLAYER;
    }

    public static void execute(AetherIIPlayerSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
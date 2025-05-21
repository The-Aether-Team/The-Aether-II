package com.aetherteam.aetherii.network.packet;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AbilityBehaviorAttachment;
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

public class AbilityBehaviorSyncPacket extends SyncEntityPacket<AbilityBehaviorAttachment> {
    public static final Type<AbilityBehaviorSyncPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "sync_ability_behavior_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityBehaviorSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            AbilityBehaviorSyncPacket::write,
            AbilityBehaviorSyncPacket::decode);

    public AbilityBehaviorSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AbilityBehaviorSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<AbilityBehaviorSyncPacket> type() {
        return TYPE;
    }

    public static AbilityBehaviorSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new AbilityBehaviorSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<AbilityBehaviorAttachment>> getAttachment() {
        return AetherIIDataAttachments.ABILITY_BEHAVIOR;
    }

    public static void execute(AbilityBehaviorSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}

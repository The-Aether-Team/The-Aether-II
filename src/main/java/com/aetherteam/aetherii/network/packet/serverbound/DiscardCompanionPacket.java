package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DiscardCompanionPacket(int entityID) implements CustomPacketPayload {
    public static final Type<DiscardCompanionPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "discard_companion"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscardCompanionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardCompanionPacket::entityID,
            DiscardCompanionPacket::new);

    @Override
    public Type<DiscardCompanionPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardCompanionPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            companion.discard();
        }
    }
}
package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DiscardEntityPacket(int entityID) implements CustomPacketPayload {
    public static final Type<DiscardEntityPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "discard_entity"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscardEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardEntityPacket::entityID,
            DiscardEntityPacket::new);

    @Override
    public Type<DiscardEntityPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardEntityPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof Entity entity) {
            entity.discard();
        }
    }
}

package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record DiscardCompanionPacket(int entityID) implements AetherPacketPayload {
    public static final Type<DiscardCompanionPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "discard_companion"));

    public static final StreamCodec<FriendlyByteBuf, DiscardCompanionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardCompanionPacket::entityID,
            DiscardCompanionPacket::new);

    @Override
    public Type<DiscardCompanionPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardCompanionPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            companion.discard();
        }
    }
}
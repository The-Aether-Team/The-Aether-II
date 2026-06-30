package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record MovementDataPacket(boolean isJumping, boolean isMovingHorizontally, boolean isMovingOverall) implements AetherPacketPayload {
    public static final Type<MovementDataPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "movement_data"));

    public static final StreamCodec<FriendlyByteBuf, MovementDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, MovementDataPacket::isJumping,
            ByteBufCodecs.BOOL, MovementDataPacket::isMovingHorizontally,
            ByteBufCodecs.BOOL, MovementDataPacket::isMovingOverall,
            MovementDataPacket::new);

    @Override
    public Type<MovementDataPacket> type() {
        return TYPE;
    }

    public static void execute(MovementDataPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            AetherIIPlayerAttachment attachment = AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.PLAYER);
            attachment.setJumping(payload.isJumping());
            attachment.setMovingHorizontally(payload.isMovingHorizontally());
            attachment.setMovingOverall(payload.isMovingOverall());
        }
    }
}

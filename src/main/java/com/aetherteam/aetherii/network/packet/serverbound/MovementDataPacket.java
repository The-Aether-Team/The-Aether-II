package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MovementDataPacket(boolean isJumping, boolean isMovingHorizontally, boolean isMovingOverall) implements CustomPacketPayload {
    public static final Type<MovementDataPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "movement_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MovementDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, MovementDataPacket::isJumping,
            ByteBufCodecs.BOOL, MovementDataPacket::isMovingHorizontally,
            ByteBufCodecs.BOOL, MovementDataPacket::isMovingOverall,
            MovementDataPacket::new);

    @Override
    public Type<MovementDataPacket> type() {
        return TYPE;
    }

    public static void execute(MovementDataPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            AetherIIPlayerAttachment attachment = serverPlayer.getData(AetherIIDataAttachments.PLAYER);
            attachment.setJumping(payload.isJumping());
            attachment.setMovingHorizontally(payload.isMovingHorizontally());
            attachment.setMovingOverall(payload.isMovingOverall());
        }
    }
}

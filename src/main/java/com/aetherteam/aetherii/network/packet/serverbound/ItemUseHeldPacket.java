package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.AetherIIPlayerAttachment;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ItemUseHeldPacket(boolean itemUseKeyHeld) implements CustomPacketPayload {
    public static final Type<ItemUseHeldPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "item_use_held"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemUseHeldPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ItemUseHeldPacket::itemUseKeyHeld,
            ItemUseHeldPacket::new);

    @Override
    public Type<ItemUseHeldPacket> type() {
        return TYPE;
    }

    public static void execute(ItemUseHeldPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            AetherIIPlayerAttachment attachment = serverPlayer.getData(AetherIIDataAttachments.PLAYER);
            attachment.setItemUseKeyHeld(payload.itemUseKeyHeld());
        }
    }
}

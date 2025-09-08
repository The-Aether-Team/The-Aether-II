package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClearAccessoriesPacket() implements CustomPacketPayload {
    public static final Type<ClearAccessoriesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "clear_accessories"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClearAccessoriesPacket> STREAM_CODEC = CustomPacketPayload.codec(ClearAccessoriesPacket::write, ClearAccessoriesPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) { }

    public static ClearAccessoriesPacket decode(RegistryFriendlyByteBuf buf) {
        return new ClearAccessoriesPacket();
    }

    @Override
    public Type<ClearAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(ClearAccessoriesPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.getData(AetherIIDataAttachments.ACCESSORIES).clearContent();
        }
    }
}

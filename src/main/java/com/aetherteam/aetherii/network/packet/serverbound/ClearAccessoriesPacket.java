package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ClearAccessoriesPacket() implements AetherPacketPayload {
    public static final Type<ClearAccessoriesPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "clear_accessories"));

    public static final StreamCodec<FriendlyByteBuf, ClearAccessoriesPacket> STREAM_CODEC = AetherPacketPayload.codec(ClearAccessoriesPacket::write, ClearAccessoriesPacket::decode);

    public void write(FriendlyByteBuf buf) { }

    public static ClearAccessoriesPacket decode(FriendlyByteBuf buf) {
        return new ClearAccessoriesPacket();
    }

    @Override
    public Type<ClearAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(ClearAccessoriesPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.ACCESSORIES).clearContent();
        }
    }
}

package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record ClearItemPacket() implements AetherPacketPayload {
    public static final Type<ClearItemPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "clear_items"));

    public static final StreamCodec<FriendlyByteBuf, ClearItemPacket> STREAM_CODEC = AetherPacketPayload.codec(ClearItemPacket::write, ClearItemPacket::decode);

    public void write(FriendlyByteBuf buf) { }

    public static ClearItemPacket decode(FriendlyByteBuf buf) {
        return new ClearItemPacket();
    }

    @Override
    public Type<ClearItemPacket> type() {
        return TYPE;
    }

    public static void execute(ClearItemPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
        }
    }
}

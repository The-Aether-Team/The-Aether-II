package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record OpenInventoryPacket(ItemStack carryStack) implements AetherPacketPayload {
    public static final Type<OpenInventoryPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "open_inventory"));

    public static final StreamCodec<FriendlyByteBuf, OpenInventoryPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStackCodecs.OPTIONAL_STREAM_CODEC,
            OpenInventoryPacket::carryStack,
            OpenInventoryPacket::new);

    @Override
    public Type<OpenInventoryPacket> type() {
        return TYPE;
    }

    public static void execute(OpenInventoryPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = (serverPlayer.isCreative() ? payload.carryStack() : serverPlayer.containerMenu.getCarried()).copy();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.doCloseContainer();
            if (!itemStack.isEmpty()) {
                if (!serverPlayer.isCreative()) {
                    serverPlayer.containerMenu.setCarried(itemStack);
                }
            }
        }
    }
}

package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.network.packet.clientbound.ClientGrabItemPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenGuidebookPacket(ItemStack carryStack) implements CustomPacketPayload {
    public static final Type<OpenGuidebookPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "open_guidebook"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenGuidebookPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            OpenGuidebookPacket::carryStack,
            OpenGuidebookPacket::new);

    @Override
    public Type<OpenGuidebookPacket> type() {
        return TYPE;
    }

    public static void execute(OpenGuidebookPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.isCreative() ? payload.carryStack() : serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, user) -> new GuidebookEquipmentMenu(id, inventory), Component.translatable("gui.aether_ii.guidebook.equipment.title")));
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
                PacketDistributor.sendToPlayer(serverPlayer, new ClientGrabItemPacket(itemStack));
            }
        }
    }
}

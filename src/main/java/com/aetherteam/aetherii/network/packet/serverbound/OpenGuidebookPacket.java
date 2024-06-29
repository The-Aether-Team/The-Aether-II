package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenGuidebookPacket() implements CustomPacketPayload {
    public static final Type<OpenGuidebookPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "open_guidebook"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenGuidebookPacket> STREAM_CODEC = CustomPacketPayload.codec(
            OpenGuidebookPacket::write,
            OpenGuidebookPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {

    }

    public static OpenGuidebookPacket decode(RegistryFriendlyByteBuf buf) {
        return new OpenGuidebookPacket();
    }

    @Override
    public Type<OpenGuidebookPacket> type() {
        return TYPE;
    }

    public static void execute(OpenGuidebookPacket payload, IPayloadContext context) { //todo get working in creative with itemstack bytebuf stream etc stuff
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = serverPlayer.containerMenu.getCarried();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, player) -> new GuidebookEquipmentMenu(id, inventory), Component.empty()));
            if (!itemStack.isEmpty()) {
                serverPlayer.containerMenu.setCarried(itemStack);
            }
        }
    }
}

package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.inventory.menu.provider.ExtraDataMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;
import net.minecraftforge.network.NetworkHooks;

public record OpenGuidebookPacket(ItemStack carryStack) implements AetherPacketPayload {
    public static final Type<OpenGuidebookPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "open_guidebook"));

    public static final StreamCodec<FriendlyByteBuf, OpenGuidebookPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStackCodecs.OPTIONAL_STREAM_CODEC,
            OpenGuidebookPacket::carryStack,
            OpenGuidebookPacket::new);

    @Override
    public Type<OpenGuidebookPacket> type() {
        return TYPE;
    }

    public static void execute(OpenGuidebookPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = (serverPlayer.isCreative() ? payload.carryStack() : serverPlayer.containerMenu.getCarried()).copy();
            serverPlayer.containerMenu.setCarried(ItemStack.EMPTY);
            ItemStack stackForNewMenu = itemStack.copy();
            NetworkHooks.openScreen(serverPlayer, new ExtraDataMenuProvider(
                    (id, inventory, user) -> {
                        GuidebookEquipmentMenu menu = new GuidebookEquipmentMenu(id, inventory);
                        if (!stackForNewMenu.isEmpty()) {
                            menu.setCarried(stackForNewMenu.copy());
                        }
                        return menu;
                    },
                    (menu, buffer) -> ByteBufCodecs.INT.encode(buffer, -1),
                    Component.translatable("gui.aether_ii.guidebook.equipment.title")),
                    (buffer) -> ByteBufCodecs.INT.encode(buffer, -1));
        }
    }
}

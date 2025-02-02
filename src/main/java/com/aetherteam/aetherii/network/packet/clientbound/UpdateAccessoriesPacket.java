package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record UpdateAccessoriesPacket(List<ItemStack> stacks) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateAccessoriesPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "update_accessories"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAccessoriesPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC,
            UpdateAccessoriesPacket::stacks,
            UpdateAccessoriesPacket::new);

    @Override
    public CustomPacketPayload.Type<UpdateAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(UpdateAccessoriesPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (int i = 0; i < payload.stacks().size(); i++) {
                Minecraft.getInstance().player.getData(AetherIIDataAttachments.ACCESSORIES).setItem(i, payload.stacks().get(i));
            }
        }
    }
}

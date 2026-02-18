package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record SetAccessoriesPacket(int entityId, List<ItemStack> list) implements CustomPacketPayload {
    public static final Type<SetAccessoriesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "set_accessories"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetAccessoriesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SetAccessoriesPacket::entityId,
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()), SetAccessoriesPacket::list,
            SetAccessoriesPacket::new);

    @Override
    public Type<SetAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(SetAccessoriesPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
                List<ItemStack> listed = payload.list();
                for (int i = 0; i < listed.size(); i++) {
                    ItemStack stack = listed.get(i);
                    livingEntity.getData(AetherIIDataAttachments.ACCESSORIES.get()).setItemWithEquip(livingEntity, i, stack);
                }
            }
        }
    }
}

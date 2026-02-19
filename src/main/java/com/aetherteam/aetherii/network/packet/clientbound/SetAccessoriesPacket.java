package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record SetAccessoriesPacket(int entityId, List<Pair<Integer, ItemStack>> list) implements CustomPacketPayload {
    public static final Type<SetAccessoriesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "set_accessories"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetAccessoriesPacket> STREAM_CODEC = StreamCodec.of(SetAccessoriesPacket::toNetwork, SetAccessoriesPacket::fromNetwork);

    public static SetAccessoriesPacket fromNetwork(RegistryFriendlyByteBuf buffer) {
        List<Pair<Integer, ItemStack>> newList = Lists.newArrayList();
        int entityId = buffer.readVarInt();
        int i;
        do {
            i = buffer.readByte();
            int slot = i & 127;
            ItemStack itemstack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            newList.add(Pair.of(slot, itemstack));
        } while((i & -128) != 0);
        return new SetAccessoriesPacket(entityId, newList);
    }

    public static void toNetwork(RegistryFriendlyByteBuf buffer, SetAccessoriesPacket packet) {
        buffer.writeVarInt(packet.entityId());
        int i = packet.list().size();
        for (int j = 0; j < i; ++j) {
            Pair<Integer, ItemStack> pair = packet.list().get(j);
            int slot = pair.getFirst();
            boolean flag = j != i - 1;
            buffer.writeByte(flag ? slot | -128 : slot);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, pair.getSecond());
        }
    }

    @Override
    public Type<SetAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(SetAccessoriesPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
                payload.list().forEach((pair) -> livingEntity.getData(AetherIIDataAttachments.ACCESSORIES.get()).setItemWithEquip(livingEntity, pair.getFirst(), pair.getSecond()));
            }
        }
    }
}

package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

import java.util.List;

public record SetAccessoriesPacket(int entityId, List<Pair<Integer, ItemStack>> list) implements AetherPacketPayload {
    public static final Type<SetAccessoriesPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "set_accessories"));

    public static final StreamCodec<FriendlyByteBuf, SetAccessoriesPacket> STREAM_CODEC = StreamCodec.of(SetAccessoriesPacket::toNetwork, SetAccessoriesPacket::fromNetwork);

    public static SetAccessoriesPacket fromNetwork(FriendlyByteBuf buffer) {
        List<Pair<Integer, ItemStack>> newList = Lists.newArrayList();
        int entityId = buffer.readVarInt();
        int i;
        do {
            i = buffer.readByte();
            int slot = i & 127;
            ItemStack itemstack = buffer.readItem();
            newList.add(Pair.of(slot, itemstack));
        } while((i & -128) != 0);
        return new SetAccessoriesPacket(entityId, newList);
    }

    public static void toNetwork(FriendlyByteBuf buffer, SetAccessoriesPacket packet) {
        buffer.writeVarInt(packet.entityId());
        int i = packet.list().size();
        for (int j = 0; j < i; ++j) {
            Pair<Integer, ItemStack> pair = packet.list().get(j);
            int slot = pair.getFirst();
            boolean flag = j != i - 1;
            buffer.writeByte(flag ? slot | -128 : slot);
            buffer.writeItem(pair.getSecond());
        }
    }

    @Override
    public Type<SetAccessoriesPacket> type() {
        return TYPE;
    }

    public static void execute(SetAccessoriesPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
                payload.list().forEach((pair) -> AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.ACCESSORIES).setItemWithEquip(livingEntity, pair.getFirst(), pair.getSecond()));
            }
        }
    }
}

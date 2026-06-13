package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.miscellaneous.CompanionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.UUID;

public record DiscardEntityPacket(int entityID) implements CustomPacketPayload {
    public static final Type<DiscardEntityPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "discard_entity"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscardEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardEntityPacket::entityID,
            DiscardEntityPacket::new);

    @Override
    public Type<DiscardEntityPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardEntityPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity entity) {
            List<ItemEntity> itemEntityList = playerEntity.level().getEntitiesOfClass(ItemEntity.class, playerEntity.getBoundingBox().inflate(2.0));
            for (ItemEntity itemEntity : itemEntityList) {
                ItemStack stack = itemEntity.getItem();
                UUID uuid = stack.get(AetherIIDataComponents.COMPANION_UUID);
                if (uuid != null && entity.getUUID().equals(uuid)) {
                    CompoundTag tag = CompanionItem.removeCompanion(entity, playerEntity);
                    stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                    return;
                }
            }
            entity.discard();
        }
    }
}

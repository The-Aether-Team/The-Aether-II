package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.miscellaneous.CompanionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;

import java.util.List;
import java.util.UUID;

public record StoreCompanionItemEntityPacket(int entityID) implements AetherPacketPayload {
    public static final Type<StoreCompanionItemEntityPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "store_companion_in_item_entity"));

    public static final StreamCodec<FriendlyByteBuf, StoreCompanionItemEntityPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, StoreCompanionItemEntityPacket::entityID,
            StoreCompanionItemEntityPacket::new);

    @Override
    public Type<StoreCompanionItemEntityPacket> type() {
        return TYPE;
    }

    public static void execute(StoreCompanionItemEntityPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            List<ItemEntity> itemEntityList = playerEntity.level().getEntitiesOfClass(ItemEntity.class, playerEntity.getBoundingBox().inflate(2.0));
            for (ItemEntity itemEntity : itemEntityList) {
                ItemStack stack = itemEntity.getItem();
                UUID uuid = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_UUID);
                if (uuid != null && companion.getUUID().equals(uuid)) {
                    CompoundTag tag = CompanionItem.removeCompanion(companion, playerEntity);
                    AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
                    return;
                }
            }
            companion.discard();
        }
    }
}

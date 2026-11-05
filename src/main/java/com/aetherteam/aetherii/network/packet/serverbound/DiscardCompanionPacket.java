package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.miscellaneous.CompanionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DiscardCompanionPacket(int entityID, ItemStack carriedStack) implements CustomPacketPayload {
    public static final Type<DiscardCompanionPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "discard_companion"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DiscardCompanionPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, DiscardCompanionPacket::entityID,
            ItemStack.STREAM_CODEC, DiscardCompanionPacket::carriedStack,
            DiscardCompanionPacket::new);

    @Override
    public Type<DiscardCompanionPacket> type() {
        return TYPE;
    }

    public static void execute(DiscardCompanionPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof LivingEntity companion) {
            if (companion instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && companion.getData(AetherIIDataAttachments.COMPANION)) {
                if (owner.hasInfiniteMaterials()) {
                    if (owner.containerMenu instanceof InventoryMenu) {
                        if (CompanionItem.UUIDsMatch(payload.carriedStack(), companion)) {
                            CompoundTag tag = CompanionItem.removeCompanion(companion, owner);
                            payload.carriedStack().set(AetherIIDataComponents.COMPANION_NBT, tag);
                        }
                    }
                }
            }
        }
    }
}
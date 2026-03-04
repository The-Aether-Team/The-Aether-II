package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BreakItemPacket(int entityId, ItemStack stack) implements CustomPacketPayload {
    public static final Type<BreakItemPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "break_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BreakItemPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            BreakItemPacket::entityId,
            ItemStack.STREAM_CODEC,
            BreakItemPacket::stack,
            BreakItemPacket::new);

    @Override
    public Type<BreakItemPacket> type() {
        return TYPE;
    }

    public static void execute(BreakItemPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
                ItemStack itemStack = payload.stack();
                if (!itemStack.isEmpty()) {
                    Holder<SoundEvent> holder = itemStack.get(DataComponents.BREAK_SOUND);
                    if (holder != null && !livingEntity.isSilent()) {
                        livingEntity.level().playLocalSound(
                                livingEntity.getX(),
                                livingEntity.getY(),
                                livingEntity.getZ(),
                                holder.value(),
                                livingEntity.getSoundSource(),
                                0.8F,
                                0.8F + livingEntity.level().random.nextFloat() * 0.4F,
                                false);
                    }
                    livingEntity.spawnItemParticles(itemStack, 5);
                }
            }
        }
    }
}

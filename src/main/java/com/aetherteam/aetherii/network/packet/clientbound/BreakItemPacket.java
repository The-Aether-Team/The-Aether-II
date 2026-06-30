package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.util.ItemStackCodecs;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.client.Minecraft;
import com.aetherteam.aetherii.item.components.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.network.AetherPayloadContext;
import net.minecraft.world.phys.Vec3;

public record BreakItemPacket(int entityId, ItemStack stack) implements AetherPacketPayload {
    public static final Type<BreakItemPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "break_item"));

    public static final StreamCodec<FriendlyByteBuf, BreakItemPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            BreakItemPacket::entityId,
            ItemStackCodecs.STREAM_CODEC,
            BreakItemPacket::stack,
            BreakItemPacket::new);

    @Override
    public Type<BreakItemPacket> type() {
        return TYPE;
    }

    public static void execute(BreakItemPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
                ItemStack itemStack = payload.stack();
                if (!itemStack.isEmpty()) {
                    SoundEvent sound = AetherIIDataComponents.get(itemStack, DataComponents.BREAK_SOUND);
                    if (sound != null && !livingEntity.isSilent()) {
                        livingEntity.level().playLocalSound(
                                livingEntity.getX(),
                                livingEntity.getY(),
                                livingEntity.getZ(),
                                sound,
                                livingEntity.getSoundSource(),
                                0.8F,
                                0.8F + livingEntity.level().getRandom().nextFloat() * 0.4F,
                                false);
                    }
                    for (int i = 0; i < 5; ++i) {
                        Vec3 motion = new Vec3(
                                (livingEntity.level().getRandom().nextFloat() - 0.5) * 0.1,
                                livingEntity.level().getRandom().nextFloat() * 0.1 + 0.1,
                                (livingEntity.level().getRandom().nextFloat() - 0.5) * 0.1);
                        livingEntity.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), livingEntity.getX(), livingEntity.getY(0.5), livingEntity.getZ(), motion.x(), motion.y(), motion.z());
                    }
                }
            }
        }
    }
}

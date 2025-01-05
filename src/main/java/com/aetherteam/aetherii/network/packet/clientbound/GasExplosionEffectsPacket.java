package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GasExplosionEffectsPacket(BlockPos pos, boolean playSound) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<GasExplosionEffectsPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gas_explosion_effects"));

    public static final StreamCodec<RegistryFriendlyByteBuf, GasExplosionEffectsPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            GasExplosionEffectsPacket::pos,
            ByteBufCodecs.BOOL,
            GasExplosionEffectsPacket::playSound,
            GasExplosionEffectsPacket::new);

    @Override
    public CustomPacketPayload.Type<GasExplosionEffectsPacket> type() {
        return TYPE;
    }

    public static void execute(GasExplosionEffectsPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (payload.playSound()) {
                Minecraft.getInstance().level.playLocalSound(
                        payload.pos().getX(),
                        payload.pos().getY(),
                        payload.pos().getZ(),
                        SoundEvents.FIRECHARGE_USE,
                        SoundSource.BLOCKS,
                        10.0F,
                        (1.0F + (Minecraft.getInstance().level.getRandom().nextFloat() - Minecraft.getInstance().level.getRandom().nextFloat()) * 0.2F) * 0.7F,
                        true);
            }
            for (int i = 0; i <= 5; i++) {
                Minecraft.getInstance().level.addParticle(ParticleTypes.FLAME,
                        payload.pos().getX() + Minecraft.getInstance().level.getRandom().nextDouble(),
                        payload.pos().getY() + Minecraft.getInstance().level.getRandom().nextDouble(),
                        payload.pos().getZ() + Minecraft.getInstance().level.getRandom().nextDouble(),
                        0, 0, 0);
            }
            for (Entity entity : Minecraft.getInstance().level.getEntities(null, AABB.encapsulatingFullBlocks(payload.pos(), payload.pos()))) {
                if (entity instanceof LivingEntity livingEntity) {
                    double d10 = 0.35 * (1.0 - livingEntity.getAttributeValue(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE));
                    double d5 = (livingEntity.getX() - payload.pos().getX()) * d10;
                    double d7 = (livingEntity.getEyeY() - payload.pos().getY()) * d10 * 0.25;
                    double d9 = (livingEntity.getZ() - payload.pos().getZ()) * d10;
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(new Vec3(d5, d7, d9)));
                }
            }
        }
    }
}

package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record HestveilExplosionEffectsPacket(BlockPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<HestveilExplosionEffectsPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hestveil_explosion_effects"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HestveilExplosionEffectsPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            HestveilExplosionEffectsPacket::pos,
            HestveilExplosionEffectsPacket::new);

    @Override
    public CustomPacketPayload.Type<HestveilExplosionEffectsPacket> type() {
        return TYPE;
    }

    public static void execute(HestveilExplosionEffectsPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
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

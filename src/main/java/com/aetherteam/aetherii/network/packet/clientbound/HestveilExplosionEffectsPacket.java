package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record HestveilExplosionEffectsPacket(BlockPos pos) implements AetherPacketPayload {
    public static final AetherPacketPayload.Type<HestveilExplosionEffectsPacket> TYPE = new AetherPacketPayload.Type<>(new ResourceLocation(AetherII.MODID, "hestveil_explosion_effects"));

    public static final StreamCodec<FriendlyByteBuf, HestveilExplosionEffectsPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            HestveilExplosionEffectsPacket::pos,
            HestveilExplosionEffectsPacket::new);

    @Override
    public AetherPacketPayload.Type<HestveilExplosionEffectsPacket> type() {
        return TYPE;
    }

    public static void execute(HestveilExplosionEffectsPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (Entity entity : Minecraft.getInstance().level.getEntities(null, new AABB(payload.pos()).inflate(1.0))) {
                if (entity instanceof LivingEntity livingEntity) {
                    double d10 = 0.225 * (1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    double d5 = (livingEntity.getX() - payload.pos().getX()) * d10;
                    double d7 = (livingEntity.getEyeY() - payload.pos().getY()) * d10 * 0.25;
                    double d9 = (livingEntity.getZ() - payload.pos().getZ()) * d10;
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(new Vec3(d5, d7, d9)));
                }
            }
        }
    }
}

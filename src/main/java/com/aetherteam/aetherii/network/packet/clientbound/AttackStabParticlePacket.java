package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.AetherPayloadContext;
import org.joml.Vector3fc;

public record AttackStabParticlePacket(Vector3fc playerPos, Vector3fc targetPos) implements AetherPacketPayload {
    public static final Type<AttackStabParticlePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "attack_stab_particles"));

    public static final StreamCodec<FriendlyByteBuf, AttackStabParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            AttackStabParticlePacket::playerPos,
            ByteBufCodecs.VECTOR3F,
            AttackStabParticlePacket::targetPos,
            AttackStabParticlePacket::new);

    @Override
    public Type<AttackStabParticlePacket> type() {
        return TYPE;
    }

    public static void execute(AttackStabParticlePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            float shade = Minecraft.getInstance().level.getRandom().nextFloat() * 0.15F + 0.85F;

            float viewX = -(Minecraft.getInstance().player.getViewXRot(1.0F) * Mth.DEG_TO_RAD);
            float viewY = -(Minecraft.getInstance().player.getViewYRot(1.0F) * Mth.DEG_TO_RAD);

            Vec3 direction = new Vec3(0, 0, 1.0).xRot(viewX).yRot(viewY);

            int count = 4;
            float offset = (float) (Minecraft.getInstance().level.getRandom().nextGaussian() * Mth.HALF_PI);
            for (int i = 0; i < count; i++) {
                float radius = 0.25F;
                float interval = i * (Mth.TWO_PI / count);
                float xR = radius * Mth.cos(offset + interval);
                float zR = radius * Mth.sin(offset + interval);

                float dist = payload.playerPos().distance(payload.targetPos()) / 3.5F;
                Vec3 position = new Vec3(xR, zR, dist).xRot(viewX).yRot(viewY);
                double x = payload.playerPos().x() + position.x();
                double y = payload.playerPos().y() + position.y();
                double z = payload.playerPos().z() + position.z();

                Minecraft.getInstance().level.addParticle(new AttackStabParticleOption(shade), x, y, z, direction.x() / 2.5, direction.y() / 2.5, direction.z() / 2.5);
            }
        }
    }
}

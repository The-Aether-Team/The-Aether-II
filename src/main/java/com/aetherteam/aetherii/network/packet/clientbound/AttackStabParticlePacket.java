package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.options.AttackStabParticleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

public record AttackStabParticlePacket(Vector3f playerPos, Vector3f targetPos) implements CustomPacketPayload {
    public static final Type<AttackStabParticlePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "attack_stab_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttackStabParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            AttackStabParticlePacket::playerPos,
            ByteBufCodecs.VECTOR3F,
            AttackStabParticlePacket::targetPos,
            AttackStabParticlePacket::new);

    @Override
    public Type<AttackStabParticlePacket> type() {
        return TYPE;
    }

    public static void execute(AttackStabParticlePacket payload, IPayloadContext context) {
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

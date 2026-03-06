package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.options.AttackShockParticleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3fc;

public record AttackShockParticlePacket(Vector3fc targetPos, float playerRot) implements CustomPacketPayload {
    public static final Type<AttackShockParticlePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "attack_shock_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttackShockParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            AttackShockParticlePacket::targetPos,
            ByteBufCodecs.FLOAT,
            AttackShockParticlePacket::playerRot,
            AttackShockParticlePacket::new);

    @Override
    public Type<AttackShockParticlePacket> type() {
        return TYPE;
    }

    public static void execute(AttackShockParticlePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            float shade = Minecraft.getInstance().level.getRandom().nextFloat() * 0.15F + 0.85F;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                float rotation = direction.toYRot() + payload.playerRot() - 45.0F;
                Vec3 position = new Vec3(0, 0, -1).yRot(-(rotation * Mth.DEG_TO_RAD));
                Minecraft.getInstance().level.addParticle(new AttackShockParticleOption(rotation, shade), payload.targetPos().x() + position.x(), payload.targetPos().y() + 0.05F, payload.targetPos().z() + position.z(), position.x() / 15.0F, 0, position.z() / 15.0F);
            }
        }
    }
}

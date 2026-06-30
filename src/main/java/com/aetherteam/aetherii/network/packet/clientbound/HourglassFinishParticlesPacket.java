package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.aetherteam.aetherii.network.AetherPayloadContext;
import org.joml.Vector3f;

public record HourglassFinishParticlesPacket(BlockPos pos) implements AetherPacketPayload {
    public static final Type<HourglassFinishParticlesPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "hourglass_finish_particles"));

    public static final StreamCodec<FriendlyByteBuf, HourglassFinishParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS, HourglassFinishParticlesPacket::pos,
            HourglassFinishParticlesPacket::new);

    @Override
    public Type<HourglassFinishParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(HourglassFinishParticlesPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (int i = 0; i < 20; i++) {
                float radius = 0.2F;
                float x = radius * Mth.cos(Mth.PI);
                float y = 1.0F;
                float z = radius * Mth.sin(Mth.PI);
                float deltaX = z * Mth.cos(i) - x * Mth.sin(i);
                float deltaZ = x * Mth.cos(i) + z * Mth.sin(i);
                Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.84705883F, 0.3019608F), 0.5F), payload.pos().getX() + deltaX + 0.5, payload.pos().getY() + y, payload.pos().getZ() + deltaZ + 0.5, 0.0, 0.25, 0.0);
            }
        }
    }
}

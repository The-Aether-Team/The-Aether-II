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

public record AltarParticlesPacket(BlockPos pos) implements AetherPacketPayload {
    public static final Type<AltarParticlesPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "altar_particles"));

    public static final StreamCodec<FriendlyByteBuf, AltarParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            AltarParticlesPacket::pos,
            AltarParticlesPacket::new);

    @Override
    public Type<AltarParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(AltarParticlesPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (int i = 0; i < 40; i++) {
                float radius = 0.45F;
                float x = radius * Mth.cos(Mth.PI);
                float y = 1.2F;
                float z = radius * Mth.sin(Mth.PI);
                float deltaX = z * Mth.cos(i) - x * Mth.sin(i);
                float deltaZ = x * Mth.cos(i) + z * Mth.sin(i);
                Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(0.85882354F, 0.79607844F, 0.50980395F), 1.0F), payload.pos().getX() + deltaX + 0.5, payload.pos().getY() + y, payload.pos().getZ() + deltaZ + 0.5, 0.0, 10.0, 0.0);
                Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(0.5803922F, 0.34117648F, 0.19607843F), 0.75F), payload.pos().getX() + deltaX + 0.5, payload.pos().getY() + y, payload.pos().getZ() + deltaZ + 0.5, 0.0, 1.0, 0.0);
            }
        }
    }
}

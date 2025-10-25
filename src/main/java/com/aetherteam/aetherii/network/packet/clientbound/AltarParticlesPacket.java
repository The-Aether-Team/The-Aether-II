package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AltarParticlesPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<AltarParticlesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "altar_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AltarParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            AltarParticlesPacket::pos,
            AltarParticlesPacket::new);

    @Override
    public Type<AltarParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(AltarParticlesPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (int i = 0; i < 40; i++) {
                float radius = 0.45F;
                float x = radius * Mth.cos(Mth.PI);
                float y = 1.2F;
                float z = radius * Mth.sin(Mth.PI);
                float deltaX = z * Mth.cos(i) - x * Mth.sin(i);
                float deltaZ = x * Mth.cos(i) + z * Mth.sin(i);
                Minecraft.getInstance().level.addParticle(new DustParticleOptions(14403138, 1.0F), payload.pos().getX() + deltaX + 0.5, payload.pos().getY() + y, payload.pos().getZ() + deltaZ + 0.5, 0.0, 10.0, 0.0);
                Minecraft.getInstance().level.addParticle(new DustParticleOptions(9721330, 0.75F), payload.pos().getX() + deltaX + 0.5, payload.pos().getY() + y, payload.pos().getZ() + deltaZ + 0.5, 0.0, 1.0, 0.0);
            }
        }
    }
}

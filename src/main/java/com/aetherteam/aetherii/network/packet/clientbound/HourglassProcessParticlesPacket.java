package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.particle.options.GravityDustParticleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record HourglassProcessParticlesPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<HourglassProcessParticlesPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "hourglass_process_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, HourglassProcessParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, HourglassProcessParticlesPacket::pos,
            HourglassProcessParticlesPacket::new);

    @Override
    public Type<HourglassProcessParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(HourglassProcessParticlesPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            RandomSource random = Minecraft.getInstance().level.getRandom();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Vec3 vec3 = Vec3.atCenterOf(payload.pos());
                double x = vec3.x + (direction.getStepX() == 0 ? Mth.nextDouble(random, -0.25F, 0.25F) : direction.getStepX() * 0.25);
                double z = vec3.z + (direction.getStepZ() == 0 ? Mth.nextDouble(random, -0.25F, 0.25F) : direction.getStepZ() * 0.25);
                Minecraft.getInstance().level.addParticle(new GravityDustParticleOption(0xFFD84D, 0.05F, 0.4F), x, vec3.y + Mth.nextDouble(random, 0.25F, 0.4F), z, 0.0, 0.0, 0.0);
            }
        }
    }
}

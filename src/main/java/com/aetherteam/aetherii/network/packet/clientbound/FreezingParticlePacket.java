package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FreezingParticlePacket(Block block, BlockPos pos) implements CustomPacketPayload {
    public static final Type<FreezingParticlePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "freezing_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, FreezingParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.BLOCK), FreezingParticlePacket::block,
            BlockPos.STREAM_CODEC, FreezingParticlePacket::pos,
            FreezingParticlePacket::new);

    @Override
    public Type<FreezingParticlePacket> type() {
        return TYPE;
    }

    public static void execute(FreezingParticlePacket payload, IPayloadContext context) {
        if (context.player() != null && context.player().level() != null) {
            for (Direction direction : Direction.values()) {
                for (int i = 0; i < 25; i++) {
                    ParticleUtils.spawnParticleOnFace(context.player().level(), payload.pos(), direction, new BlockParticleOption(ParticleTypes.BLOCK, payload.block().defaultBlockState()), Vec3.ZERO, 0.5F);
                }
            }
        }
    }
}

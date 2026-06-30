package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record FreezingParticlePacket(Block block, BlockPos pos) implements AetherPacketPayload {
    public static final Type<FreezingParticlePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "freezing_particles"));

    public static final StreamCodec<FriendlyByteBuf, FreezingParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.BLOCK), FreezingParticlePacket::block,
            ByteBufCodecs.BLOCK_POS, FreezingParticlePacket::pos,
            FreezingParticlePacket::new);

    @Override
    public Type<FreezingParticlePacket> type() {
        return TYPE;
    }

    public static void execute(FreezingParticlePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            for (Direction direction : Direction.values()) {
                for (int i = 0; i < 25; i++) {
                    ParticleUtils.spawnParticleOnFace(context.player().level(), payload.pos(), direction, new BlockParticleOption(ParticleTypes.BLOCK, payload.block().defaultBlockState()), Vec3.ZERO, 0.5F);
                }
            }
        }
    }
}

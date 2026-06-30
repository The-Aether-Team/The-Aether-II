package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record AlkahestFizzPacket(BlockPos pos, Direction face) implements AetherPacketPayload {
    public static final Type<AlkahestFizzPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "alkahest_fizz"));

    public static final StreamCodec<FriendlyByteBuf, AlkahestFizzPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BLOCK_POS,
            AlkahestFizzPacket::pos,
            ByteBufCodecs.DIRECTION,
            AlkahestFizzPacket::face,
            AlkahestFizzPacket::new);

    @Override
    public Type<AlkahestFizzPacket> type() {
        return TYPE;
    }

    public static void execute(AlkahestFizzPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            ParticleUtils.spawnParticlesOnBlockFace(context.player().level(), payload.pos().offset(payload.face().getOpposite().getNormal()), ParticleTypes.SMOKE, UniformInt.of(10, 20), payload.face(), () -> Vec3.ZERO, 0.5);
        }
    }
}

package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record SkiffParticlesPacket(int entityID) implements AetherPacketPayload {
    public static final Type<SkiffParticlesPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "skiff_particles"));

    public static final StreamCodec<FriendlyByteBuf, SkiffParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkiffParticlesPacket::entityID,
            SkiffParticlesPacket::new
    );

    @Override
    public Type<SkiffParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(SkiffParticlesPacket payload, AetherPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof CloudSkiff skiff) {
            skiff.level().broadcastEntityEvent(skiff, (byte) CloudSkiff.PARTICLE_EVENT);
        }
    }
}

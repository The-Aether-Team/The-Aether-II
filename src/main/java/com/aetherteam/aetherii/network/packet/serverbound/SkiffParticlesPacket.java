package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SkiffParticlesPacket(int entityID) implements CustomPacketPayload {
    public static final Type<SkiffParticlesPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "skiff_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SkiffParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkiffParticlesPacket::entityID,
            SkiffParticlesPacket::new
    );

    @Override
    public Type<SkiffParticlesPacket> type() {
        return TYPE;
    }

    public static void execute(SkiffParticlesPacket payload, IPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof CloudSkiff skiff) {
            skiff.level().broadcastEntityEvent(skiff, (byte) CloudSkiff.PARTICLE_EVENT);
        }
    }
}

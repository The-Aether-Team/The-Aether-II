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

public record SkiffSteeringPacket(int entityID, CloudSkiff.SteeringState steeringState) implements CustomPacketPayload {
    public static final Type<SkiffSteeringPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "skiff_steering"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SkiffSteeringPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkiffSteeringPacket::entityID,
            CloudSkiff.SteeringState.STREAM_CODEC, SkiffSteeringPacket::steeringState,
            SkiffSteeringPacket::new
    );

    @Override
    public Type<SkiffSteeringPacket> type() {
        return TYPE;
    }

    public static void execute(SkiffSteeringPacket payload, IPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof CloudSkiff skiff) {
            skiff.setSteeringState(payload.steeringState());
        }
    }
}

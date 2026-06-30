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

public record SkiffSteeringPacket(int entityID, CloudSkiff.SteeringState steeringState) implements AetherPacketPayload {
    public static final Type<SkiffSteeringPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "skiff_steering"));

    public static final StreamCodec<FriendlyByteBuf, SkiffSteeringPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkiffSteeringPacket::entityID,
            CloudSkiff.SteeringState.STREAM_CODEC, SkiffSteeringPacket::steeringState,
            SkiffSteeringPacket::new
    );

    @Override
    public Type<SkiffSteeringPacket> type() {
        return TYPE;
    }

    public static void execute(SkiffSteeringPacket payload, AetherPayloadContext context) {
        Player sender = context.player();
        if (sender.level().getServer() != null && sender.level().getEntity(payload.entityID()) instanceof CloudSkiff skiff) {
            skiff.setSteeringState(payload.steeringState());
        }
    }
}

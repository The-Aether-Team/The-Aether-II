package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record SetVehiclePacket(int passengerID, int vehicleID) implements AetherPacketPayload {
    public static final Type<SetVehiclePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "set_mount"));

    public static final StreamCodec<FriendlyByteBuf, SetVehiclePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetVehiclePacket::passengerID,
            ByteBufCodecs.INT,
            SetVehiclePacket::vehicleID,
            SetVehiclePacket::new);

    @Override
    public Type<SetVehiclePacket> type() {
        return TYPE;
    }

    public static void execute(SetVehiclePacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity passenger = Minecraft.getInstance().player.level().getEntity(payload.passengerID());
            Entity vehicle = Minecraft.getInstance().player.level().getEntity(payload.vehicleID());
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle, true);
            }
        }
    }
}

package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetVehiclePacket(int passengerID, int vehicleID) implements CustomPacketPayload {
    public static final Type<SetVehiclePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "set_mount"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetVehiclePacket> STREAM_CODEC = CustomPacketPayload.codec(
            SetVehiclePacket::write,
            SetVehiclePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.passengerID());
        buf.writeInt(this.vehicleID());
    }

    public static SetVehiclePacket decode(RegistryFriendlyByteBuf buf) {
        int passengerID = buf.readInt();
        int vehicleID = buf.readInt();
        return new SetVehiclePacket(passengerID, vehicleID);
    }

    @Override
    public Type<SetVehiclePacket> type() {
        return TYPE;
    }

    public static void execute(SetVehiclePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity passenger = Minecraft.getInstance().player.level().getEntity(payload.passengerID());
            Entity vehicle = Minecraft.getInstance().player.level().getEntity(payload.vehicleID());
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle);
            }
        }
    }
}

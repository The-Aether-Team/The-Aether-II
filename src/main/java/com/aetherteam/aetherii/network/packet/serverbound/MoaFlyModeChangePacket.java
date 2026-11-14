package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MoaFlyModeChangePacket(int entityID) implements CustomPacketPayload {
    public static final Type<MoaFlyModeChangePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "moa_fly_mode"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MoaFlyModeChangePacket> STREAM_CODEC = CustomPacketPayload.codec(
            MoaFlyModeChangePacket::write,
            MoaFlyModeChangePacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static MoaFlyModeChangePacket decode(RegistryFriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new MoaFlyModeChangePacket(entityID);
    }

    @Override
    public Type<MoaFlyModeChangePacket> type() {
        return TYPE;
    }

    public static void execute(MoaFlyModeChangePacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof Moa moa && playerEntity.getVehicle() != null && playerEntity.getVehicle().equals(moa)) {
            if (!moa.onGround()) {
                moa.changeFlyMode();
            }
        }
    }
}

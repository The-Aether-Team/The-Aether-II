package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

/**
 * Sets the {@link Aerbunny#DATA_PUFFINESS_ID} value to 11. This is needed in a packet for precise animation syncing.
 */
public record MoaFlyModeChangePacket(int entityID) implements AetherPacketPayload {
    public static final Type<MoaFlyModeChangePacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "moa_fly_mode"));

    public static final StreamCodec<FriendlyByteBuf, MoaFlyModeChangePacket> STREAM_CODEC = AetherPacketPayload.codec(
            MoaFlyModeChangePacket::write,
            MoaFlyModeChangePacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static MoaFlyModeChangePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new MoaFlyModeChangePacket(entityID);
    }

    @Override
    public Type<MoaFlyModeChangePacket> type() {
        return TYPE;
    }

    public static void execute(MoaFlyModeChangePacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof Moa moa) {
            moa.changeFlyMode();
        }
    }
}

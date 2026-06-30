package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Aerbunny;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

/**
 * Sets the {@link Aerbunny#DATA_PUFFINESS_ID} value to 11. This is needed in a packet for precise animation syncing.
 */
public record AerbunnyPuffPacket(int entityID) implements AetherPacketPayload {
    public static final Type<AerbunnyPuffPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "aerbunny_puff"));

    public static final StreamCodec<FriendlyByteBuf, AerbunnyPuffPacket> STREAM_CODEC = AetherPacketPayload.codec(
            AerbunnyPuffPacket::write,
            AerbunnyPuffPacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static AerbunnyPuffPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new AerbunnyPuffPacket(entityID);
    }

    @Override
    public Type<AerbunnyPuffPacket> type() {
        return TYPE;
    }

    public static void execute(AerbunnyPuffPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.level().getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof Aerbunny aerbunny) {
            aerbunny.puff();
            aerbunny.level().broadcastEntityEvent(aerbunny, (byte) Aerbunny.PUFF_PARTICLE_EVENT);
        }
    }
}

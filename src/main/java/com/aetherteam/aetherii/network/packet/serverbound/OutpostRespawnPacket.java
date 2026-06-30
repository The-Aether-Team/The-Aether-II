package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record OutpostRespawnPacket() implements AetherPacketPayload {
    public static final Type<OutpostRespawnPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "outpost_respawn"));

    public static final StreamCodec<FriendlyByteBuf, OutpostRespawnPacket> STREAM_CODEC = AetherPacketPayload.codec(
            OutpostRespawnPacket::write,
            OutpostRespawnPacket::decode);

    public void write(FriendlyByteBuf buf) {

    }

    public static OutpostRespawnPacket decode(FriendlyByteBuf buf) {
        return new OutpostRespawnPacket();
    }

    @Override
    public Type<OutpostRespawnPacket> type() {
        return TYPE;
    }

    public static void execute(OutpostRespawnPacket payload, AetherPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.level().getServer() != null) {
            AetherIIDataAttachments.get(playerEntity, AetherIIDataAttachments.OUTPOST_TRACKER).setShouldRespawnAtOutpost(true);
        }
    }
}

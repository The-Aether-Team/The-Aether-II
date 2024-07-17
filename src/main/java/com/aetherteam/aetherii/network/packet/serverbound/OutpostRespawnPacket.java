package com.aetherteam.aetherii.network.packet.serverbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OutpostRespawnPacket() implements CustomPacketPayload {
    public static final Type<OutpostRespawnPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "outpost_respawn"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OutpostRespawnPacket> STREAM_CODEC = CustomPacketPayload.codec(
            OutpostRespawnPacket::write,
            OutpostRespawnPacket::decode);

    public void write(RegistryFriendlyByteBuf buf) {

    }

    public static OutpostRespawnPacket decode(RegistryFriendlyByteBuf buf) {
        return new OutpostRespawnPacket();
    }

    @Override
    public Type<OutpostRespawnPacket> type() {
        return TYPE;
    }

    public static void execute(OutpostRespawnPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity != null && playerEntity.getServer() != null) {
            playerEntity.getData(AetherIIDataAttachments.OUTPOST_TRACKER).setShouldRespawnAtOutpost(true);
        }
    }
}

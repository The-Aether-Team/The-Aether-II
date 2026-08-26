package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MusicBlockPlayPacket(Holder<JukeboxSong> songHolder, BlockPos pos) implements CustomPacketPayload {
    public static final Type<MusicBlockPlayPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AetherII.MODID, "music_block_play"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MusicBlockPlayPacket> STREAM_CODEC = StreamCodec.composite(
            JukeboxSong.STREAM_CODEC, MusicBlockPlayPacket::songHolder,
            BlockPos.STREAM_CODEC, MusicBlockPlayPacket::pos,
            MusicBlockPlayPacket::new);

    @Override
    public Type<MusicBlockPlayPacket> type() {
        return TYPE;
    }

    public static void execute(MusicBlockPlayPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherIIClientProxy.playMusicBlock(payload.songHolder(), payload.pos());
        }
    }
}

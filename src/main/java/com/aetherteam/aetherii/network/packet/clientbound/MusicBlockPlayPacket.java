package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import com.aetherteam.aetherii.network.AetherPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.item.components.JukeboxSong;
import com.aetherteam.aetherii.network.AetherPayloadContext;

public record MusicBlockPlayPacket(Holder<JukeboxSong> songHolder, BlockPos pos) implements AetherPacketPayload {
    public static final Type<MusicBlockPlayPacket> TYPE = new Type<>(new ResourceLocation(AetherII.MODID, "music_block_play"));

    public static final StreamCodec<FriendlyByteBuf, MusicBlockPlayPacket> STREAM_CODEC = StreamCodec.composite(
            JukeboxSong.STREAM_CODEC, MusicBlockPlayPacket::songHolder,
            ByteBufCodecs.BLOCK_POS, MusicBlockPlayPacket::pos,
            MusicBlockPlayPacket::new);

    @Override
    public Type<MusicBlockPlayPacket> type() {
        return TYPE;
    }

    public static void execute(MusicBlockPlayPacket payload, AetherPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherIIClientProxy.playMusicBlock(payload.songHolder(), payload.pos());
        }
    }
}

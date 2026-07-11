package com.aetherteam.aetherii.network.packet.clientbound;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.instance.MergedChannelSoundInstance;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.ClientLevelAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.LevelEventHandlerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
            LevelEventHandlerAccessor handler = (LevelEventHandlerAccessor) ((ClientLevelAccessor) Minecraft.getInstance().level).aether_ii$getLevelEventHandler();
            SoundInstance removedInstance = handler.aether_ii$getPlayingJukeboxSongs().remove(payload.pos());
            if (removedInstance != null) {
                Minecraft.getInstance().getSoundManager().stop(removedInstance);
            }
            JukeboxSong song = payload.songHolder().value();
            SoundEvent sound = song.soundEvent().value();
            SoundInstance instance = MergedChannelSoundInstance.forSong(sound, Vec3.atCenterOf(payload.pos()));
            handler.aether_ii$getPlayingJukeboxSongs().put(payload.pos(), instance);
            Minecraft.getInstance().getSoundManager().play(instance);
            Minecraft.getInstance().gui.setNowPlaying(song.description());
            for (LivingEntity entity : Minecraft.getInstance().level.getEntitiesOfClass(LivingEntity.class, new AABB(payload.pos()).inflate(3.0))) {
                entity.setRecordPlayingNearby(payload.pos(), true);
            }
        }
    }
}

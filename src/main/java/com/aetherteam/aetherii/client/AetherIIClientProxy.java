package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.client.sound.instance.MusicPlayerSoundInstance;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundEngineAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundManagerAccessor;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AetherIIClientProxy {
    public static boolean isMusicPlayerActive(SoundEvent soundEvent) {
        SoundEngine soundEngine = ((SoundManagerAccessor) Minecraft.getInstance().getSoundManager()).aether_ii$getSoundEngine();
        Map<SoundInstance, ChannelAccess.ChannelHandle> soundInstances = ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel();
        List<Identifier> sounds = soundInstances.keySet().stream().filter((soundInstance) -> soundInstance instanceof MusicPlayerSoundInstance).map(SoundInstance::getIdentifier).toList();
        return sounds.contains(soundEvent.location());
    }

    public static void startMusicPlayer(Holder<SoundEvent> sound, SoundSource source, double x, double y, double z, float volume, float pitch, long seed) {
        Minecraft.getInstance().getSoundManager().play(new MusicPlayerSoundInstance(sound.value(), source, volume, pitch, RandomSource.create(seed), x, y, z));
    }

    public static void onMusicPlayerStart(Holder<JukeboxSong> song) {
        Minecraft.getInstance().gui.setNowPlaying(song.value().description());
    }

    public static void stopMusicPlayer(SoundEvent soundEvent, SoundSource source) {
        Minecraft.getInstance().getSoundManager().stop(soundEvent.location(), source);
    }

    public static void stopOtherMusicPlayerSound(SoundSource source) {
        SoundEngine soundEngine = ((SoundManagerAccessor) Minecraft.getInstance().getSoundManager()).aether_ii$getSoundEngine();
        Map<SoundInstance, ChannelAccess.ChannelHandle> soundInstances = ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel();
        soundInstances.keySet().stream().filter((soundInstance) -> soundInstance instanceof MusicPlayerSoundInstance).map(SoundInstance::getIdentifier).forEach(location -> Minecraft.getInstance().getSoundManager().stop(location, source));
    }

    public static boolean isAerbunnyInteractable() {
        return AetherIIKeyMappings.ALLOW_DISMOUNTING_PASSENGER.isDown();
    }

    public static AdvancementHolder getAdvancementParent(AdvancementHolder advancement) {
        if (Minecraft.getInstance().player != null && advancement.value().parent().isPresent()) {
            return Minecraft.getInstance().player.connection.getAdvancements().get(advancement.value().parent().get());
        }
        return null;
    }

    public static void setSectionDirty(SectionPos pos) {
        Minecraft.getInstance().levelRenderer.setSectionDirty(pos.x(), pos.y(), pos.z());
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Nullable
    public static <T> T getClientPlayerData(Supplier<AttachmentType<T>> holder) {
        if (Minecraft.getInstance().player != null) {
            return Minecraft.getInstance().player.getData(holder);
        } else {
            return null;
        }
    }
}

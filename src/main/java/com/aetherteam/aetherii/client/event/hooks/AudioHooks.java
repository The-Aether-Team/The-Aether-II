package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.FadeOutSoundInstance;
import com.aetherteam.aetherii.client.sound.instance.MusicSoundInstance;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class AudioHooks {
    public static final Music AETHER_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_CAVES);

    public static <T extends LivingEntity & AetherBossMob<?>> Music getSituationalMusic() {
        Music musicInfo = null;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            Holder<Biome> biome = minecraft.player.level().getBiome(minecraft.player.blockPosition());
            if (biome.is(AetherIITags.Biomes.AETHER_MUSIC) && !(minecraft.screen instanceof WinScreen)) {
                if (isAetherBossMusicActive()) {
                    T boss = getBossFromFight();
                    if (boss != null && boss.getHealth() > 0) {
                        musicInfo = boss.getBossMusic();
                    }
                } else {
                    long time = minecraft.player.level().getDayTime() % 24000L;
                    boolean day = time >= 0 && time < 12000;
                    boolean sunset = time >= 12000 && time < 14000;
                    boolean night = time >= 14000 && time < 22000;
                    boolean sunrise = time >= 22000;

                    if (minecraft.player.position().y <= 80) {
                        musicInfo = AETHER_CAVES;
                    } else if (day) {
                        musicInfo = biome.value().getBackgroundMusic().orElse(Musics.GAME);
                    } else if (sunset) {
                        musicInfo = AETHER_SUNSET;
                    } else if (night) {
                        musicInfo = AETHER_NIGHT;
                    } else if (sunrise) {
                        musicInfo = AETHER_SUNRISE;
                    }
                }
            }
        }

        SoundEngine soundEngine = getSoundEngine();
        Optional<MusicSoundInstance> musicInstance = soundEngine.instanceToChannel.keySet().stream()
                .filter(MusicSoundInstance.class::isInstance)
                .map(MusicSoundInstance.class::cast)
                .findFirst();
        if (musicInstance.isPresent()) {
            boolean requestingBossMusic = isAetherBossMusic(musicInfo);
            if (!requestingBossMusic || musicInstance.get().isBossMusic()) {
                musicInfo = null;
            }
        }

        Optional<SoundInstance> portalSoundInstance = soundEngine.instanceToChannel.keySet().stream().filter((soundInstance) -> {
            Holder<SoundEvent> playingSound = getSoundEvent(soundInstance);
            return playingSound != null && playingSound.is(AetherIITags.SoundEvents.ACTIVATED_PORTAL_SOUNDS);
        }).findFirst();
        if (portalSoundInstance.isPresent()) {
            musicInfo = null;
        }

        return musicInfo;
    }

    public static Music createAetherMusic(RegistryObject<SoundEvent> event) {
        Holder<SoundEvent> holder = event.getHolder().orElseGet(() -> Holder.direct(event.get()));
        return new Music(holder, 3600, 10800, false);
    }

    public static boolean isAetherBossMusic(Music musicInfo) {
        if (musicInfo != null && musicInfo.getEvent() != null && musicInfo.getEvent().unwrapKey().isPresent()) {
            Holder<SoundEvent> sound = BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(musicInfo.getEvent().unwrapKey().get());
            return sound.is(AetherIITags.SoundEvents.BOSS_MUSIC);
        }
        return false;
    }

    public static <T extends LivingEntity & AetherBossMob<?>> boolean isAetherBossMusicActive() {
        T boss = getBossFromFight();
        return !getAetherBossFights().isEmpty() && Minecraft.getInstance().gui.getBossOverlay().shouldPlayMusic() && boss != null && boss.getHealth() > 0;
    }

    public static Map<UUID, Integer> getAetherBossFights() {
        return RenderHooks.BOSS_EVENTS;
    }

    @SuppressWarnings("unchecked")
    public static <T extends LivingEntity & AetherBossMob<?>> T getBossFromFight() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.player.level() != null) {
            for (Map.Entry<UUID, Integer> event : getAetherBossFights().entrySet()) {
                Entity entity = minecraft.player.level().getEntity(event.getValue());
                if (entity instanceof LivingEntity && entity instanceof AetherBossMob<?>) {
                    return (T) entity;
                }
            }
        }
        return null;
    }

    /**
     * Prevents ambient Aether Portal sounds from overlapping trigger/travel portal sounds.
     */
    public static boolean preventAmbientPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.AMBIENT_PORTAL_SOUNDS)) {
                return isAnySoundPlaying(soundEngine, (playingSound) -> playingSound.is(AetherIITags.SoundEvents.PORTAL_SOUNDS));
            }
        }
        return false;
    }

    public static boolean preventMusicDuringPortal(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.MUSIC)) {
                return isAnySoundPlaying(soundEngine, (playingSound) -> playingSound.is(AetherIITags.SoundEvents.PORTAL_SOUNDS));
            }
        }
        return false;
    }

    /**
     * Stops ambient Aether Portal loops once a trigger/travel portal sound starts.
     */
    public static void overrideActivatedPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.ACTIVATED_PORTAL_SOUNDS)) {
                for (SoundInstance playingInstance : new ArrayList<>(soundEngine.instanceToChannel.keySet())) {
                    Holder<SoundEvent> playingSound = getSoundEvent(playingInstance);
                    if (playingSound != null && playingSound.is(AetherIITags.SoundEvents.AMBIENT_PORTAL_SOUNDS) && playingInstance instanceof FadeOutSoundInstance fadeOutSoundInstance) {
                        fadeOutSoundInstance.fadeOut();
                    }
                }
            }
        }
    }

    private static boolean isAnySoundPlaying(SoundEngine soundEngine, Predicate<Holder<SoundEvent>> predicate) {
        for (SoundInstance playingInstance : soundEngine.instanceToChannel.keySet()) {
            Holder<SoundEvent> playingSound = getSoundEvent(playingInstance);
            if (playingSound != null && predicate.test(playingSound)) {
                return true;
            }
        }
        return false;
    }

    private static Holder<SoundEvent> getSoundEvent(SoundInstance sound) {
        SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(sound.getLocation());
        if (soundEvent != null) {
            Optional<ResourceKey<SoundEvent>> resourceKey = BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent);
            if (resourceKey.isPresent()) {
                return BuiltInRegistries.SOUND_EVENT.getHolderOrThrow(resourceKey.get());
            }
        }
        return null;
    }

    public static SoundEngine getSoundEngine() {
        return Minecraft.getInstance().getSoundManager().soundEngine;
    }
}

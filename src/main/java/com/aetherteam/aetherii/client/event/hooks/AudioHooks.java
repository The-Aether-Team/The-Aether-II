package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.FadeOutSoundInstance;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.BossHealthOverlayAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.MusicManagerAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundEngineAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AudioHooks { //todo creative music override
    public static final Music AETHER_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    public static <T extends LivingEntity & AetherBossMob<?>> MusicInfo getSituationalMusic() {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            float volume = biome.value().getBackgroundMusicVolume();
            if (biome.is(AetherIITags.Biomes.AETHER_MUSIC)) {
                if (!(Minecraft.getInstance().screen instanceof WinScreen)) {
                    if (isAetherBossMusicActive()) {
                        T boss = getBossFromFight();
                        if (boss != null && boss.getHealth() > 0) {
                            Music bossMusic = boss.getBossMusic();
                            if (bossMusic != null) {
                                return new MusicInfo(boss.getBossMusic(), volume);
                            }
                        }
                    } else {
                        long time = Minecraft.getInstance().player.clientLevel.getLevelData().getDayTime() % 24000L;
                        boolean day = time >= 0 && time < 12000;
                        boolean sunset = time >= 12000 && time < 14000;
                        boolean night = time >= 14000 && time < 22000;
                        boolean sunrise = time >= 22000;

                        if (Minecraft.getInstance().player.position().y <= 80) {
                            return new MusicInfo(AETHER_CAVES, volume);
                        } else {
                            if (day) {
                                Optional<WeightedList<Music>> optional = biome.value().getBackgroundMusic();
                                if (optional.isPresent()) {
                                    Optional<Music> optional1 = optional.get().getRandom(Minecraft.getInstance().level.random);
                                    return new MusicInfo(optional1.orElse(null), volume);
                                } else {
                                    return new MusicInfo(Musics.GAME, volume);
                                }
                            } else if (sunset) {
                                return new MusicInfo(AETHER_SUNSET, volume);
                            } else if (night) {
                                return new MusicInfo(AETHER_NIGHT, volume);
                            } else if (sunrise) {
                                return new MusicInfo(AETHER_SUNRISE, volume);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Music createAetherMusic(Holder<SoundEvent> event) {
        return new Music(event, 3600, 10800, false);
    }

    public static boolean isAetherBossMusic(Music music) {
        if (music.event().getKey() != null) {
            Holder<SoundEvent> sound = BuiltInRegistries.SOUND_EVENT.get(music.event().getKey()).orElse(null);
            if (sound != null) {
                return sound.is(AetherIITags.SoundEvents.BOSS_MUSIC);
            }
        }
        return false;
    }

    public static boolean isAetherBossMusicActive() {
        return !getAetherBossFights().isEmpty() && Minecraft.getInstance().gui.getBossOverlay().shouldPlayMusic();
    }

    public static Map<UUID, LerpingBossEvent> getAetherBossFights() {
        return ((BossHealthOverlayAccessor) Minecraft.getInstance().gui.getBossOverlay()).getEvents().entrySet().stream().filter((entry) -> RenderHooks.isAetherBossBar(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T extends LivingEntity & AetherBossMob<?>> T getBossFromFight() {
        for (Map.Entry<UUID, LerpingBossEvent> event : getAetherBossFights().entrySet()) {
            UUID eventUUID = event.getKey();
            int entityId = RenderHooks.BOSS_EVENTS.get(eventUUID);
            Entity entity = Minecraft.getInstance().player.level().getEntity(entityId);
            if (entity instanceof LivingEntity && entity instanceof AetherBossMob<?>) {
                return (T) entity;
            }
        }
        return null;
    }

    public static boolean shouldCancelMusic(SoundInstance sound) {
        Holder<SoundEvent> soundEvent = getSoundEvent(sound);
        if (sound.getSource() == SoundSource.MUSIC && soundEvent != null && !soundEvent.is(AetherIITags.SoundEvents.ACHIEVEMENT_SOUNDS)) {
            // Check whether there is Aether music and the sound that attempts to play does not match it.
            MusicManager manager = Minecraft.getInstance().getMusicManager();
            MusicManagerAccessor accessor = (MusicManagerAccessor) manager;
            return Minecraft.getInstance().getSituationalMusic().music() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(Minecraft.getInstance().getSituationalMusic().music().event().value(), 1.0F).getLocation())
                    || (accessor.aether_ii$getCurrentMusic() != null && !sound.getLocation().equals(accessor.aether_ii$getCurrentMusic().getLocation()));
        }
        return false;
    }

    /**
     * Prevents ambient Aether Portal sounds from overlapping other portal sounds.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static boolean preventAmbientPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.AMBIENT_PORTAL_SOUNDS)) {
                return ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel().keySet().stream().anyMatch((playingInstance) -> {
                    Holder<SoundEvent> playingSound = getSoundEvent(playingInstance);
                    return playingSound != null && playingSound.is(AetherIITags.SoundEvents.PORTAL_SOUNDS);
                });
            }
        }
        return false;
    }

    /**
     * Stops ambient Aether Portal sounds when other portal sounds are activated.
     *
     * @see com.aetherteam.aether.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static void overrideActivatedPortalSound(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.ACTIVATED_PORTAL_SOUNDS)) {
                ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel().keySet().forEach((playingInstance) -> {
                    Holder<SoundEvent> playingSound = getSoundEvent(playingInstance);
                    if (playingSound != null && playingSound.is(AetherIITags.SoundEvents.AMBIENT_PORTAL_SOUNDS)) {
                        if (playingInstance instanceof FadeOutSoundInstance fadeOutSoundInstance) {
                            fadeOutSoundInstance.fadeOut();
                        }
                    }
                });
            }
        }
    }

    private static Holder<SoundEvent> getSoundEvent(SoundInstance sound) {
        SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.getValue(sound.getLocation());
        if (soundEvent != null) {
            Optional<ResourceKey<SoundEvent>> optionalResourceKey = BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent);
            if (optionalResourceKey.isPresent()) {
                return BuiltInRegistries.SOUND_EVENT.getOrThrow(optionalResourceKey.get());
            }
        }
        return null;
    }
}
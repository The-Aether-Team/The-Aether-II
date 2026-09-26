package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.instance.FadeOutSoundInstance;
import com.aetherteam.aetherii.client.sound.instance.MusicSoundInstance;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.BossHealthOverlayAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundEngineAccessor;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.SoundManagerAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardCompanionPacket;
import com.aetherteam.aetherii.network.packet.serverbound.EnteredStructurePacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class AudioHooks {
    public static final Music AETHER_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_CAVES);
    public static final Music AETHER_MINESHAFT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_MINESHAFT);

    public static <T extends LivingEntity & AetherBossMob<?>> Music getSituationalMusic() {
        Music musicInfo = null;
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            if (biome.is(AetherIITags.Biomes.AETHER_MUSIC)) {
                if (!(Minecraft.getInstance().screen instanceof WinScreen)) {
                    if (isAetherBossMusicActive()) {
                        T boss = getBossFromFight();
                        if (boss != null && boss.getHealth() > 0) {
                            Music bossMusic = boss.getBossMusic();
                            if (bossMusic != null) {
                                musicInfo = boss.getBossMusic();
                            }
                        }
                    } else {
                        ClientPacketDistributor.sendToServer(new EnteredStructurePacket());

                        long time = Minecraft.getInstance().player.level().getDefaultClockTime() % 24000L;
                        boolean day = time >= 0 && time < 12000;
                        boolean sunset = time >= 12000 && time < 14000;
                        boolean night = time >= 14000 && time < 22000;
                        boolean sunrise = time >= 22000;


                        if (Minecraft.getInstance().player.position().y <= 80) {
                            musicInfo = AETHER_CAVES;
                        } else {
                            if (day) {
                                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                                BackgroundMusic backgroundmusic = camera.attributeProbe().getValue(EnvironmentAttributes.BACKGROUND_MUSIC, 1.0F);
                                boolean flag = Minecraft.getInstance().player.getAbilities().instabuild && Minecraft.getInstance().player.getAbilities().mayfly;
                                boolean flag1 = Minecraft.getInstance().player.isUnderWater();

                                musicInfo = backgroundmusic.select(flag, flag1).orElse(Musics.GAME);

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
            }
        }

        SoundEngine soundEngine = ((SoundManagerAccessor) Minecraft.getInstance().getSoundManager()).aether_ii$getSoundEngine();
        Optional<SoundInstance> musicInstance = ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel().keySet().stream().filter((soundInstance) -> soundInstance instanceof MusicSoundInstance).findFirst();
        if (musicInstance.isPresent()) {
            musicInfo = null;
        }

        Optional<SoundInstance> portalSoundInstance = ((SoundEngineAccessor) soundEngine).aether_ii$getInstanceToChannel().keySet().stream().filter((soundInstance) -> {
            Holder<SoundEvent> playingSound = getSoundEvent(soundInstance);
            return playingSound != null && playingSound.is(AetherIITags.SoundEvents.ACTIVATED_PORTAL_SOUNDS);
        }).findFirst();
        if (portalSoundInstance.isPresent()) {
            musicInfo = null;
        }

        return musicInfo;
    }

    public static Music createAetherMusic(Holder<SoundEvent> event) {
        return new Music(event, 3600, 10800, false);
    }

    public static boolean isAetherBossMusic(Music musicInfo) {
        if (musicInfo != null && musicInfo.sound() != null && musicInfo.sound().getKey() != null) {
            Holder<SoundEvent> sound = BuiltInRegistries.SOUND_EVENT.get(musicInfo.sound().getKey()).orElse(null);
            if (sound != null) {
                return sound.is(AetherIITags.SoundEvents.BOSS_MUSIC);
            }
        }
        return false;
    }

    public static <T extends LivingEntity & AetherBossMob<?>> boolean isAetherBossMusicActive() {
        T boss = getBossFromFight();
        return !getAetherBossFights().isEmpty() && Minecraft.getInstance().gui.getBossOverlay().shouldPlayMusic() && boss != null && boss.getHealth() > 0;
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

    public static boolean preventMusicDuringPortal(SoundEngine soundEngine, SoundInstance sound) {
        if (sound != null) {
            Holder<SoundEvent> soundEvent = getSoundEvent(sound);
            if (soundEvent != null && soundEvent.is(AetherIITags.SoundEvents.MUSIC)) {
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
        SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.getValue(sound.getIdentifier());
        if (soundEvent != null) {
            Optional<ResourceKey<SoundEvent>> optionalResourceKey = BuiltInRegistries.SOUND_EVENT.getResourceKey(soundEvent);
            if (optionalResourceKey.isPresent()) {
                return BuiltInRegistries.SOUND_EVENT.getOrThrow(optionalResourceKey.get());
            }
        }
        return null;
    }
}
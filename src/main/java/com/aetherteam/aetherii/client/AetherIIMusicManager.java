package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class AetherIIMusicManager {
    public static final Music AETHER_NIGHT = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    private static final RandomSource random = RandomSource.create();
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;

    public static void tick() {
        Music music = getSituationalMusic();
        if (music != null) {
            if (currentMusic != null) {
                if (!music.getEvent().value().getLocation().equals(currentMusic.getLocation()) && music.replaceCurrentMusic()) {
                    minecraft.getSoundManager().stop(currentMusic);
                    nextSongDelay = Mth.nextInt(random, 0, music.getMinDelay() / 2);
                }

                if (!minecraft.getSoundManager().isActive(currentMusic)) {
                    currentMusic = null;
                    nextSongDelay = Math.min(nextSongDelay, Mth.nextInt(random, music.getMinDelay(), music.getMaxDelay()));
                }
            }

            nextSongDelay = Math.min(nextSongDelay, music.getMaxDelay());
            if (currentMusic == null && nextSongDelay-- <= 0) {
                startPlaying(music);
            }
        } else {
            currentMusic = null;
            if (nextSongDelay-- <= 0) {
                nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, 3000, 6000));
            }
        }
    }

    public static void startPlaying(Music music) {
        musicManager.stopPlaying();
        currentMusic = SimpleSoundInstance.forMusic(music.getEvent().value());
        if (currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            minecraft.getSoundManager().play(currentMusic);
        }
        nextSongDelay = Integer.MAX_VALUE;
    }

    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic);
            currentMusic = null;
        }
        nextSongDelay += 100;
    }

    @Nullable
    public static SoundInstance getCurrentMusic() {
        return currentMusic;
    }

    /**
     * @return Regular {@link Music}, or nighttime {@link Music}, when it is nighttime.
     */
    public static Music getSituationalMusic() {
        if (!(minecraft.screen instanceof WinScreen)) {
            if (minecraft.player != null) {
                Holder<Biome> holder = minecraft.player.level().getBiome(minecraft.player.blockPosition());
                long time = minecraft.player.clientLevel.getLevelData().getDayTime() % 24000L;
                boolean night = time >= 14000 && time < 22000;
                boolean sunrise = time >= 12000 && time < 14000;
                boolean sunset = time >= 22000;

                if (minecraft.player.position().y <= 80) {
                    return AETHER_CAVES;
                }
                else {
                    if (night) {
                        return AETHER_NIGHT;
                    } else if (sunrise) {
                        return AETHER_SUNRISE;
                    } else if (sunset) {
                        return AETHER_SUNSET;
                    } else if (isCreative(holder, minecraft.player)) {
                        return holder.value().getBackgroundMusic().orElse(Musics.GAME);
                    }
                }
            }
        }
        return null;
    }

    public static boolean isCreative(Holder<Biome> holder, LocalPlayer player) {
        return holder.is(AetherIITags.Biomes.AETHER_MUSIC) && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC)) && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }
}
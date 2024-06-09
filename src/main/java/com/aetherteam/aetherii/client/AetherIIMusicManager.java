package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class AetherIIMusicManager {
    private static final RandomSource random = RandomSource.create();
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();
    @Nullable
    private static SoundInstance currentMusic;
    private static int nextSongDelay = 100;

    /**
     * [CODE COPY] - {@link MusicManager#tick()}.<br><br>
     * Modified to have a {@link Music} null check.
     */
    public static void tick() {
        Music music = getSituationalMusic();
        if (music != null) {
            if (currentMusic != null) {
                if (!music.getEvent().value().getLocation().equals(currentMusic.getLocation()) && music.replaceCurrentMusic()) {
                    minecraft.getSoundManager().stop(currentMusic); // Non-copy, cancels vanilla music if Aether music starts
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
            if (currentMusic == null || !minecraft.getSoundManager().isActive(currentMusic)) {
                currentMusic = null;
                if (nextSongDelay-- <= 0) {
                    nextSongDelay = Math.min(Integer.MAX_VALUE, Mth.nextInt(random, AetherIIConfig.CLIENT.music_backup_min_delay.get(), AetherIIConfig.CLIENT.music_backup_max_delay.get()));
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link MusicManager#startPlaying(Music)}.
     */
    public static void startPlaying(Music pSelector) {
        musicManager.stopPlaying(); // Non-copy, cancels vanilla music if Aether music starts
        currentMusic = SimpleSoundInstance.forMusic(pSelector.getEvent().value());
        if (currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
            minecraft.getSoundManager().play(currentMusic);
        }
        nextSongDelay = Integer.MAX_VALUE;
    }

    /**
     * [CODE COPY] - {@link MusicManager#stopPlaying()}.
     */
    public static void stopPlaying() {
        if (currentMusic != null) {
            minecraft.getSoundManager().stop(currentMusic); // Non-copy, cancels vanilla music if Aether music starts
            currentMusic = null;
        }
        nextSongDelay += 100;
    }

    @Nullable
    public static SoundInstance getCurrentMusic() {
        return currentMusic;
    }

    /**
     * Determines when to play different music.
     *
     * @return The {@link Music} to play.
     */
    @Nullable
    public static Music getSituationalMusic() {
        if (!(minecraft.screen instanceof WinScreen)) {
            if (minecraft.player != null) { // Otherwise replace creative music with biome music in the Aether.
                Holder<Biome> holder = minecraft.player.level().getBiome(minecraft.player.blockPosition());
                if (isCreative(holder, minecraft.player)) {
                    return (holder.value().getBackgroundMusic().orElse(Musics.GAME));
                }
            }
        }
        return null;
    }

    /**
     * [CODE COPY] - {@link Minecraft#getSituationalMusic()}.<br><br>
     * Based on vanilla creative music checks, but also checks if the biome plays Aether music.
     */
    public static boolean isCreative(Holder<Biome> holder, Player player) {
        return player.level().dimension() == AetherIIDimensions.AETHER_HIGHLANDS_LEVEL
                && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))
                && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }
}

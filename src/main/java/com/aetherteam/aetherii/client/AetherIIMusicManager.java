package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

public class AetherIIMusicManager {
    public static final Music AETHER_DAY = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER);
    public static final Music AETHER_NIGHT = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final MusicManager musicManager = Minecraft.getInstance().getMusicManager();

    public static Music getSituationalMusic(SoundInstance playingMusic) {
        if (!(minecraft.screen instanceof WinScreen)) {
            if (minecraft.player != null) {
                Holder<Biome> biome = minecraft.player.level().getBiome(minecraft.player.blockPosition());
                if (biome.is(AetherIITags.Biomes.AETHER_MUSIC)) {
                    long time = minecraft.player.clientLevel.getLevelData().getDayTime() % 24000L;
                    boolean day = time >= 0 && time < 12000;
                    boolean night = time >= 14000 && time < 22000;
                    boolean sunset = time >= 12000 && time < 14000;
                    boolean sunrise = time >= 22000;

                    if (minecraft.player.position().y <= 80) {
                        return AETHER_CAVES;
                    } else {
                        if (day) {
                            return AETHER_DAY;
                        } else if (night) {
                            return AETHER_NIGHT;
                        } else if (sunset) {
                            return AETHER_SUNSET;
                        } else if (sunrise) {
                            return AETHER_SUNRISE;
                        }
                    }
                }
                return biome.value().getBackgroundMusic().isPresent() ? biome.value().getBackgroundMusic().get() : null;
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static boolean isCreative(Holder<Biome> holder, LocalPlayer player) {
        return !holder.is(AetherIITags.Biomes.AETHER_MUSIC) && !musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC)) && player.getAbilities().instabuild && player.getAbilities().mayfly;
    }
}
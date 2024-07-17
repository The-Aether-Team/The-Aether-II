package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;

public class MusicHooks {
    public static final Music AETHER_NIGHT = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = Musics.createGameMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    public static Music getSituationalMusic(Holder<Biome> biome) {
        if (!(Minecraft.getInstance().screen instanceof WinScreen)) {
            if (Minecraft.getInstance().player != null) {

                long time = Minecraft.getInstance().player.clientLevel.getLevelData().getDayTime() % 24000L;
                boolean day = time >= 500 && time < 11500;
                boolean sunset = time >= 11500 && time < 14500;
                boolean night = time >= 14500 && time < 21500;
                boolean sunrise = time >= 21500 || (time >= 0 && time < 500);

                if (Minecraft.getInstance().player.position().y <= 80) {
                    return AETHER_CAVES;
                } else {
                    if (day) {
                        return biome.value().getBackgroundMusic().get();
                    } else if (sunset) {
                        return AETHER_SUNSET;
                    } else if (night) {
                        return AETHER_NIGHT;
                    } else if (sunrise) {
                        return AETHER_SUNRISE;
                    }
                }
            }
        }
        return null;
    }
}
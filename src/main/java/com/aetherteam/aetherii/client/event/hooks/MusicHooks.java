package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;

public class MusicHooks {
    public static final Music AETHER_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    public static Music getSituationalMusic(Holder<Biome> biome) {
        if (!(Minecraft.getInstance().screen instanceof WinScreen)) {

            long time = Minecraft.getInstance().player.clientLevel.getLevelData().getDayTime() % 24000L;
            boolean day = time >= 0 && time < 12000;
            boolean sunset = time >= 12000 && time < 14000;
            boolean night = time >= 14000 && time < 22000;
            boolean sunrise = time >= 22000;

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
        return null;
    }

    public static Music createAetherMusic(Holder<SoundEvent> event) {
        return new Music(event, 600, 2400, false);
    }
}
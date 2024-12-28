package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;

import java.awt.*;
import java.util.Optional;

public class MusicHooks {
    public static final Music AETHER_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_NIGHT);
    public static final Music AETHER_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNRISE);
    public static final Music AETHER_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_SUNSET);
    public static final Music AETHER_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_AMBIENCE);

    public static MusicInfo getSituationalMusic() {
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            float volume = biome.value().getBackgroundMusicVolume();
            if (biome.is(AetherIITags.Biomes.AETHER_MUSIC)) {
                if (!(Minecraft.getInstance().screen instanceof WinScreen)) {
                    long time = Minecraft.getInstance().player.clientLevel.getLevelData().getDayTime() % 24000L;
                    boolean day = time >= 0 && time < 12000;
                    boolean sunset = time >= 12000 && time < 14000;
                    boolean night = time >= 14000 && time < 22000;
                    boolean sunrise = time >= 22000;
                  
                    if (Minecraft.getInstance().player.position().y <= 80) {
                        return new MusicInfo(AETHER_CAVES, volume);
                    } else {
                        if (day) {
                            Optional<SimpleWeightedRandomList<Music>> optional = biome.value().getBackgroundMusic();
                            if (optional.isPresent()) {
                                Optional<Music> optional1 = optional.get().getRandomValue(Minecraft.getInstance().level.random);
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
        return null;
    }

    public static Music createAetherMusic(Holder<SoundEvent> event) {
        return new Music(event, 3600, 10800, false);
    }
}
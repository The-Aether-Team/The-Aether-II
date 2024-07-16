package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.client.AetherIIMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class AudioHooks {

    public static void playAetherMusic(SoundInstance sound) {
        if (sound.getSource() == SoundSource.MUSIC) {
            if (((AetherIIMusicManager.getSituationalMusic() == null || sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherIIMusicManager.getSituationalMusic().getEvent().value()).getLocation())))) {
                if (AetherIIMusicManager.getCurrentMusic() != null) {
                    sound.getLocation();
                    AetherIIMusicManager.getCurrentMusic().getLocation();
                }
            }
        }
    }

    public static void tick() {
        if (!Minecraft.getInstance().isPaused()) {
            AetherIIMusicManager.tick();
        }
    }

    public static void stop() {
        AetherIIMusicManager.stopPlaying();
    }
}
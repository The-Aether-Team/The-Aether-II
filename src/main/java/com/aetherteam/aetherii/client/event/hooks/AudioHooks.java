package com.aetherteam.aetherii.client.event.hooks;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.client.AetherIIMusicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.event.TickEvent;

public class AudioHooks {
    /**
     * Stops other music from playing over Aether music.
     *
     * @see com.aetherteam.aetherii.client.event.listeners.AudioListener#onPlaySound(PlaySoundEvent)
     */
    public static boolean shouldCancelSound(SoundInstance sound) {
        if (!AetherIIConfig.CLIENT.disable_music_manager.get()) {
            if (sound.getSource() == SoundSource.MUSIC) {
                // Check whether there is Aether music and the sound that attempts to play does not match it.
                return AetherIIMusicManager.getSituationalMusic() != null && !sound.getLocation().equals(SimpleSoundInstance.forMusic(AetherIIMusicManager.getSituationalMusic().getEvent().value()).getLocation())
                        || (AetherIIMusicManager.getCurrentMusic() != null && !sound.getLocation().equals(AetherIIMusicManager.getCurrentMusic().getLocation()));
            }
        }
        return false;
    }

    /**
     * Ticks the Aether's music manager.
     *
     * @see com.aetherteam.aetherii.client.event.listeners.AudioListener#onClientTick(TickEvent.ClientTickEvent)
     */
    public static void tick() {
        if (!AetherIIConfig.CLIENT.disable_music_manager.get() && !Minecraft.getInstance().isPaused()) {
            if (!Minecraft.getInstance().isPaused()) {
                AetherIIMusicManager.tick();
            }
        }
    }

    /**
     * Resets the music on respawn.
     *
     * @see com.aetherteam.aetherii.client.event.listeners.AudioListener#onPlayerRespawn(ClientPlayerNetworkEvent.Clone)
     */
    public static void stop() {
        if (!AetherIIConfig.CLIENT.disable_music_manager.get()) {
            AetherIIMusicManager.stopPlaying();
        }
    }
}
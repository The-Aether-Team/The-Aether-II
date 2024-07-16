package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

public class AudioListener {

    public static void listen(IEventBus bus) {
        bus.addListener(AudioListener::onMusicPlayed);
        bus.addListener(AudioListener::onClientTick);
        bus.addListener(AudioListener::onPlayerRespawn);
    }

    public static void onMusicPlayed(SelectMusicEvent event) {
        SoundInstance sound = event.getPlayingMusic();
        if (sound != null) {
            AudioHooks.playAetherMusic(sound);
        }
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        AudioHooks.tick();
    }

    public static void onPlayerRespawn(ClientPlayerNetworkEvent.Clone event) {
        AudioHooks.stop();
    }
}
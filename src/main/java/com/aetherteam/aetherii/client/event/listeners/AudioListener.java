package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.AetherIIMusicManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

public class AudioListener {

    public static void listen(IEventBus bus) {
        bus.addListener(AudioListener::onMusicPlayed);
    }

    public static void onMusicPlayed(SelectMusicEvent event) {
        event.setMusic(AetherIIMusicManager.getSituationalMusic());
    }
}
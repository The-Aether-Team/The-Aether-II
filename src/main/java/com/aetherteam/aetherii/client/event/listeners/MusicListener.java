package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.hooks.MusicHooks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

public class MusicListener {

    public static void listen(IEventBus bus) {
        bus.addListener(MusicListener::onMusicPlayed);
    }

    public static void onMusicPlayed(SelectMusicEvent event) {
        if (event.getMusic() != null) {
            event.setMusic(MusicHooks.getSituationalMusic());
        }
    }
}
package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.client.event.listeners.attachment.DimensionRenderEffectListeners;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

public class AudioListener {

    public static void listen(IEventBus bus) {
        bus.addListener(AudioListener::playMusic);
    }

    public static void playMusic(SelectMusicEvent event) {

    }
}
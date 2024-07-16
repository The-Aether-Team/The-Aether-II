package com.aetherteam.aetherii.client.event.listeners;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.event.hooks.MusicHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.SelectMusicEvent;

public class MusicListener {

    public static void listen(IEventBus bus) {
        bus.addListener(MusicListener::onMusicPlayed);
    }

    public static void onMusicPlayed(SelectMusicEvent event) {
        Music music = event.getOriginalMusic();

        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null && (music == Musics.CREATIVE || music == Musics.UNDER_WATER)) {
            Holder<Biome> biome = Minecraft.getInstance().player.level().getBiome(Minecraft.getInstance().player.blockPosition());
            if (biome.is(AetherIITags.Biomes.AETHER_MUSIC)) {
                event.setMusic(MusicHooks.getSituationalMusic(biome));
            }
        }
    }
}
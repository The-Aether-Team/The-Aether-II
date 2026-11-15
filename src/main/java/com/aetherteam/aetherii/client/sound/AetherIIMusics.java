package com.aetherteam.aetherii.client.sound;

import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

public class AetherIIMusics {
    public static final Music AETHER_HOLY_ISLES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES);
    public static final Music AETHER_HOLY_ISLES_NIGHT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES_NIGHT);
    public static final Music AETHER_HOLY_ISLES_SUNRISE = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES_SUNRISE);
    public static final Music AETHER_HOLY_ISLES_SUNSET = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES_SUNSET);
    public static final Music AETHER_HOLY_ISLES_CAVES = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES_CAVES);
    public static final Music AETHER_HOLY_ISLES_UNDERCLOUD_MINESHAFT = createAetherMusic(AetherIISoundEvents.MUSIC_AETHER_HOLY_ISLES_UNDERCLOUD_MINESHAFT);

    public static final Music SLIDER = createBossMusic(AetherIISoundEvents.MUSIC_BOSS_SLIDER);

    public static Music createAetherMusic(Holder<SoundEvent> event) {
        return new Music(event, 3600, 10800, false);
    }

    public static Music createBossMusic(Holder<SoundEvent> event) {
        return new Music(event, 0, 0, true);
    }
}

package com.aetherteam.aetherii.client.sound.instance;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class MusicPlayerSoundInstance extends SimpleSoundInstance {
    public MusicPlayerSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, RandomSource random, double x, double y, double z) {
        super(soundEvent, source, volume, pitch, random, x, y, z);
    }
}

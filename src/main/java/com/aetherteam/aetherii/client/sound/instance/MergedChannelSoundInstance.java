package com.aetherteam.aetherii.client.sound.instance;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class MergedChannelSoundInstance extends SimpleSoundInstance {
    public MergedChannelSoundInstance(ResourceLocation location, SoundSource source, float volume, float pitch, RandomSource random, boolean looping, int delay, Attenuation attenuation, double x, double y, double z, boolean relative) {
        super(location, source, volume, pitch, random, looping, delay, attenuation, x, y, z, relative);
    }

    public static MergedChannelSoundInstance forSong(SoundEvent sound, Vec3 pos) {
        return new MergedChannelSoundInstance(sound.getLocation(), SoundSource.RECORDS, 4.0F, 1.0F, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.LINEAR, pos.x, pos.y, pos.z, false);
    }
}

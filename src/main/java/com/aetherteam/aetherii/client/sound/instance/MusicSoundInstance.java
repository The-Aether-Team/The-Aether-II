package com.aetherteam.aetherii.client.sound.instance;

import com.aetherteam.aetherii.client.event.hooks.MusicHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicInfo;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class MusicSoundInstance extends AbstractTickableSoundInstance {
    private static final int FADE_LIMIT = 50;
    private final boolean isBossMusic;
    private int fade;

    public MusicSoundInstance(SoundEvent event, SoundSource source, float volume, float pitch, RandomSource random, boolean looping, int delay, SoundInstance.Attenuation attenuation, double x, double y, double z, boolean relative, boolean isBossMusic) {
        super(event, source, random);
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.looping = looping;
        this.delay = delay;
        this.attenuation = attenuation;
        this.relative = relative;
        this.isBossMusic = isBossMusic;
    }

    public static MusicSoundInstance forMusic(SoundEvent sound) {
        return new MusicSoundInstance(
                sound,
                SoundSource.MUSIC,
                1.0F,
                1.0F,
                SoundInstance.createUnseededRandom(),
                false,
                0,
                SoundInstance.Attenuation.NONE,
                0.0,
                0.0,
                0.0,
                true,
                false
        );
    }

    public static MusicSoundInstance forBossMusic(SoundEvent sound) {
        return new MusicSoundInstance(
                sound,
                SoundSource.MUSIC,
                1.0F,
                1.0F,
                SoundInstance.createUnseededRandom(),
                true,
                0,
                SoundInstance.Attenuation.NONE,
                0.0,
                0.0,
                0.0,
                true,
                true
        );
    }

    @Override
    public void tick() {
        MusicInfo musicInfo = Minecraft.getInstance().getSituationalMusic();
        if (this.isBossMusic()) {
            if (musicInfo.music() == null || !MusicHooks.isAetherBossMusicActive()) {
                this.fade();
            }
        } else {
            if (musicInfo.music() != null && MusicHooks.isAetherBossMusicActive()) {
                this.fade();
            }
        }
    }

    protected void fade() {
        this.setVolume((float) Math.exp(-(this.fade / (FADE_LIMIT / 3.0))));
        this.fade++;
        if (this.fade >= FADE_LIMIT) {
            this.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isBossMusic() {
        return this.isBossMusic;
    }
}

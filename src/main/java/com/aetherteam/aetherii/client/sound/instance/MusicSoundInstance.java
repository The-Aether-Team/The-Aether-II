package com.aetherteam.aetherii.client.sound.instance;

import com.aetherteam.aetherii.client.event.hooks.AudioHooks;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.WeighedSoundEventsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

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

    public static MusicSoundInstance forMenuMusic(SoundEvent sound) {
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
    public @Nullable WeighedSoundEvents resolve(SoundManager handler) {
        if (this.location.equals(SoundManager.INTENTIONALLY_EMPTY_SOUND_LOCATION)) {
            this.sound = SoundManager.INTENTIONALLY_EMPTY_SOUND;
            return SoundManager.INTENTIONALLY_EMPTY_SOUND_EVENT;
        } else {
            WeighedSoundEvents weighedsoundevents = handler.getSoundEvent(this.location);
            if (weighedsoundevents == null) {
                this.sound = SoundManager.EMPTY_SOUND;
            } else {
                if (((WeighedSoundEventsAccessor) weighedsoundevents).aether_ii$getList().size() > 1) {
                    SoundInstance lastMusic = MixinHooks.LAST_MUSIC;
                    if (lastMusic instanceof MusicSoundInstance musicSoundInstance && musicSoundInstance.getSound() != null) {
                        Sound newSound = null;
                        while (newSound == null || musicSoundInstance.getSound().getLocation().equals(newSound.getLocation())) {
                            newSound = weighedsoundevents.getSound(this.random);
                        }
                        this.sound = newSound;
                        return weighedsoundevents;
                    }
                }
                this.sound = weighedsoundevents.getSound(this.random);
            }
            return weighedsoundevents;
        }
    }

    @Override
    public void tick() {
        Music music = Minecraft.getInstance().getSituationalMusic();
        if (this.isBossMusic()) {
            if (music == null || !AudioHooks.isAetherBossMusicActive()) {
                this.fade();
            }
        } else {
            if (music != null && AudioHooks.isAetherBossMusicActive()) {
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

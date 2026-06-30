package com.aetherteam.aetherii.client.sound.instance;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class PortalTriggerSoundInstance extends AbstractTickableSoundInstance {
    private final Player player;
    private final float startingVolume;
    private int fade;

    public PortalTriggerSoundInstance(
        Player player,
        SoundEvent event,
        SoundSource source,
        float volume,
        float pitch,
        RandomSource random,
        boolean looping,
        int delay,
        Attenuation attenuation,
        double x,
        double y,
        double z,
        boolean relative
    ) {
        super(event, source, random);
        this.player = player;
        this.volume = volume;
        this.startingVolume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.looping = looping;
        this.delay = delay;
        this.attenuation = attenuation;
        this.relative = relative;
    }

    public static PortalTriggerSoundInstance forLocalAmbience(Player player, SoundEvent pSound, float pPitch, float pVolume) {
        return new PortalTriggerSoundInstance(
            player,
            pSound,
            SoundSource.AMBIENT,
            pVolume,
            pPitch,
            SoundInstance.createUnseededRandom(),
            false,
            0,
            Attenuation.NONE,
            0.0,
            0.0,
            0.0,
            true
        );
    }

    @Override
    public void tick() {
        if (this.player == null || AetherIIDataAttachments.get(this.player, AetherIIDataAttachments.PLAYER).getPortalIntensity() <= 0.0) {
            this.fade++;
            this.volume = (float) Math.exp(-(this.fade / (75 / 1.5))) - (1 - this.startingVolume);
            if (this.fade >= 75) {
                this.stop();
            }
        }
    }
}

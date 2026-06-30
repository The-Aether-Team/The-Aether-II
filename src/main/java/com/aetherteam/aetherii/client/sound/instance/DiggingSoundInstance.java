package com.aetherteam.aetherii.client.sound.instance;

import com.aetherteam.aetherii.entity.DiggingMob;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;

public class DiggingSoundInstance<T extends Mob & DiggingMob> extends AbstractTickableSoundInstance {
    private final T mob;

    public DiggingSoundInstance(T mob, SoundEvent soundEvent) {
        super(soundEvent, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.mob = mob;
        this.attenuation = Attenuation.LINEAR;
        this.looping = false;
        this.delay = 0;
    }

    public boolean canPlaySound() {
        return !this.mob.isSilent();
    }

    public void tick() {
        if (!this.mob.isRemoved() && this.mob.getTarget() == null && this.mob.canPlayDiggingSound()) {
            this.x = (float) this.mob.getX();
            this.y = (float) this.mob.getY();
            this.z = (float) this.mob.getZ();
            this.volume = 1.0F;
            this.pitch = 1.0F;
        } else {
            this.stop();
        }
    }
}
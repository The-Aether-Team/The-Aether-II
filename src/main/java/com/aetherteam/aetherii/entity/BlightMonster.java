package com.aetherteam.aetherii.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

/**
 * This interface has several methods for handling monsters related to the Blight.
 */
public interface BlightMonster {

    /**
     * Call this from your entity's tick method.
     */
    default void burnEffects(Entity entity, RandomSource random, int tickCount){
        for (int i = 0; i < 7; ++i) {
            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SMOKE, entity.getRandomX(0.3), entity.getRandomY() - 0.1, entity.getRandomZ(0.3), 1, 0, 0, 0, random.nextGaussian() * 0.02);
            }
        }
        if(tickCount % 13 == 0){
            entity.level().playSound(entity, entity.getOnPos(), SoundEvents.LAVA_EXTINGUISH, SoundSource.HOSTILE, 0.5f, 1.0f);
        }
    }

    int getHideTime();

    void setHideTime(int hideTime);
}

package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

/**
 * This interface has several methods for handling monsters related to the Blight.
 */
public interface Blighted {
    /**
     * Call this from your entity's aiStep method.
     */
    default void burnEffects(Entity entity, RandomSource random, int tickCount, float scale) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < this.getHideTime(); ++i) {
                serverLevel.sendParticles(ParticleTypes.SMOKE, entity.getRandomX(scale), entity.getRandomY(), entity.getRandomZ(scale), 1, 0, 0, 0, random.nextGaussian() * 0.02);
            }
            entity.hurtServer(serverLevel, serverLevel.damageSources().generic(), 1.0F);
        }
        if (tickCount % 13 == 0) {
            entity.level().playSound(entity, entity.getOnPos(), AetherIISoundEvents.ENTITY_BLIGHTED_BURN.get(), SoundSource.HOSTILE, 0.5f, 1.0f);
        }
    }

    default boolean isUniformSunBurnTick(Mob mob) {
        if (mob.level().isDay() && !mob.level().isClientSide) {
            float f = mob.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
            boolean flag = mob.isInWaterRainOrBubble() || mob.isInPowderSnow || mob.wasInPowderSnow;
            if (f > 0.5F && !flag && mob.level().canSeeSky(blockpos)) {
                return true;
            }
        }
        return false;
    }

    int getHideTime();

    void setHideTime(int hideTime);
}
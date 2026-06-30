package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GravitationalPullEffect extends MobEffect {
    public GravitationalPullEffect() {
        super(MobEffectCategory.HARMFUL, 0xEC7FCD);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isNoGravity()) {
            entity.setNoGravity(false);
        }
        entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.15, 0));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

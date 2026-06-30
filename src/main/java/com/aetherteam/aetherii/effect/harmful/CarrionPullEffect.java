package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CarrionPullEffect extends MobEffect {
    public CarrionPullEffect() {
        super(MobEffectCategory.HARMFUL, 0xBEE3F3);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.1, 0));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

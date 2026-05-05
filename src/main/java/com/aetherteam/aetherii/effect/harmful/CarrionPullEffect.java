package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CarrionPullEffect extends MobEffect {
    public CarrionPullEffect() {
        super(MobEffectCategory.HARMFUL, 0xBEE3F3);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.1, 0));
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

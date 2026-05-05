package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GravitationalPullEffect extends MobEffect {
    public GravitationalPullEffect() {
        super(MobEffectCategory.HARMFUL, 0xEC7FCD);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (entity.isNoGravity()) {
            entity.setNoGravity(false);
        }
        entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.15, 0));
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

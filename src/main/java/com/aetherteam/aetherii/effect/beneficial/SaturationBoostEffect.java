package com.aetherteam.aetherii.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SaturationBoostEffect extends MobEffect {

    public SaturationBoostEffect() {
        super(MobEffectCategory.BENEFICIAL, 14394744);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
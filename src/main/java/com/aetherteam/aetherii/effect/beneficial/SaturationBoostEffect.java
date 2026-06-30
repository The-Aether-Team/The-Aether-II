package com.aetherteam.aetherii.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SaturationBoostEffect extends MobEffect {
    public SaturationBoostEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xDBA578);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}

package com.aetherteam.aetherii.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ToxinEffect extends MobEffect {
    public ToxinEffect() {
        super(MobEffectCategory.HARMFUL, 6533213);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
}

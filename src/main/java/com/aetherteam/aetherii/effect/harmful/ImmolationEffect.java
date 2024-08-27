package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class ImmolationEffect extends MobEffect {
    public ImmolationEffect() {
        super(MobEffectCategory.HARMFUL, 16746283);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

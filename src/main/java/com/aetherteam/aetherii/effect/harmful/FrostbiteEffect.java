package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class FrostbiteEffect extends MobEffect {
    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 4361110);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

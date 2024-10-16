package com.aetherteam.aetherii.effect.beneficial;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class SaturationBoostEffect extends InstantenousMobEffect {

    public SaturationBoostEffect() {
        super(MobEffectCategory.BENEFICIAL, 14394744);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide && livingEntity instanceof Player player) {
            player.getFoodData().eat(amplifier + 1, 1.0F);
        }

        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

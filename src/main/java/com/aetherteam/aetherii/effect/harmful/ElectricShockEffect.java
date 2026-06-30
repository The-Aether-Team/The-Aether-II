package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class ElectricShockEffect extends InstantenousMobEffect {
    public ElectricShockEffect() {
        super(MobEffectCategory.HARMFUL, 0xBED1E8);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(livingEntity.damageSources().magic(), 6 << amplifier);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity trueSource, LivingEntity livingEntity, int amplifier, double distance) {
        int damageValue = (int) (distance * (6 << amplifier) + 0.5);
        if (source == null) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.SHOCK), damageValue);
        } else {
            livingEntity.hurt(AetherIIDamageTypes.indirectEntityDamageSource(livingEntity.level(), AetherIIDamageTypes.SHOCK, source, trueSource), damageValue);
        }
    }
}

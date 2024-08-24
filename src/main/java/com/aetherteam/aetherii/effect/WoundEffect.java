package com.aetherteam.aetherii.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import javax.annotation.Nullable;
import java.util.Set;

public class WoundEffect extends InstantenousMobEffect {
    protected WoundEffect() {
        super(MobEffectCategory.HARMFUL, 12721951);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        int damage = (int) (health * (double) (4 << amplifier) + 0.5); //todo total health percentage instead to make it dependent on mob target?
        if (source == null) {
            livingEntity.hurt(livingEntity.damageSources().magic(), (float) damage); //todo damage sources
        } else {
            livingEntity.hurt(livingEntity.damageSources().indirectMagic(source, indirectSource), (float) damage);
        }
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

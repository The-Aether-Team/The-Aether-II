package com.aetherteam.aetherii.effect.harmful;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import javax.annotation.Nullable;
import java.util.Set;

public class WoundEffect extends InstantenousMobEffect {
    public WoundEffect() {
        super(MobEffectCategory.HARMFUL, 13118248);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        //todo total health percentage instead to make it dependent on mob target?
        if (source == null) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 4.0F); //todo damage sources
        } else {
            livingEntity.hurt(livingEntity.damageSources().indirectMagic(source, indirectSource), 4.0F);
        }
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

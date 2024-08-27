package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ToxinEffect extends MobEffect {
    public ToxinEffect() {
        super(MobEffectCategory.HARMFUL, 7720557);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() > 1.0F) {
            Registry<DamageType> damageTypes = livingEntity.damageSources().damageTypes;
            Holder.Reference<DamageType> damageType = damageTypes.getHolderOrThrow(AetherIIDamageTypes.TOXIN);
            livingEntity.hurt(new DamageSource(damageType), 1.0F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}

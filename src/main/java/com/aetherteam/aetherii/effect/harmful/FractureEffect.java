package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.item.AetherIIEffectCures;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class FractureEffect extends MobEffect {
    private float lastFallDistance = 0.0F;

    public FractureEffect() {
        super(MobEffectCategory.HARMFUL, 14078644);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) { //todo apply multiplier to damage type weakness for mobs to increase it.
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(0.75, 1.0, 0.75));
        if (livingEntity.getDeltaMovement().y() > 0) {
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1.0, 0.9, 1.0));
        }
        if (this.lastFallDistance == 0.0F) {
            this.lastFallDistance = livingEntity.fallDistance;
        }
        if (livingEntity.fallDistance <= 0 && this.lastFallDistance > 2) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 1.0F);
            this.lastFallDistance = 0.0F;
        }
        if (livingEntity.isSprinting()) {
            livingEntity.hurt(livingEntity.damageSources().magic(), 1.0F);
            livingEntity.setSprinting(false);
        }
        this.lastFallDistance = livingEntity.fallDistance;
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
        cures.add(AetherIIEffectCures.SPLINT);
    }
}

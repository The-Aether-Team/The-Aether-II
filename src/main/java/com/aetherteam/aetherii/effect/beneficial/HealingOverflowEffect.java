package com.aetherteam.aetherii.effect.beneficial;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HealingOverflowEffect extends MobEffect {
    public HealingOverflowEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFCD400);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        if (mob.getAbsorptionAmount() <= 0.0F) {
            mob.removeEffect(AetherIIMobEffects.HEALING_OVERFLOW);
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity mob, int amplifier) {
        super.onEffectStarted(mob, amplifier);
        mob.setAbsorptionAmount(Math.max(mob.getAbsorptionAmount(), amplifier));
    }
}

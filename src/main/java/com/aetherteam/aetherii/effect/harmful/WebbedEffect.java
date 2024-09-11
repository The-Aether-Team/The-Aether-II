package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Set;

public class WebbedEffect extends MobEffect {
    public WebbedEffect() {
        super(MobEffectCategory.HARMFUL, 13092283);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getDeltaMovement().length() > 1.5) {
            livingEntity.removeEffect(AetherIIEffects.WEBBED);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }

    public static void reduceByJumping(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        entity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).reduceBuildup(AetherIIEffects.WEBBED, 5);
    }
}

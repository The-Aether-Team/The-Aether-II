package com.aetherteam.aetherii.effect.neutral;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NaturalCamouflageEffect extends MobEffect {
    public NaturalCamouflageEffect() {
        super(MobEffectCategory.NEUTRAL, 8497005);
    }

    public static void onEntityPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(AetherIIEffects.NATURAL_CAMOUFLAGE)) {
            livingEntity.setDeltaMovement(entity.getDeltaMovement().multiply(0.0, 1.0, 0.0));
        }
    }

    public static void adjustVisibilityModifier(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.NATURAL_CAMOUFLAGE)) {
            event.modifyVisibility(event.getVisibilityModifier() * 0.5F);
        }
    }
}

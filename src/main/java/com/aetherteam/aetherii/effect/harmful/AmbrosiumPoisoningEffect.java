package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class AmbrosiumPoisoningEffect extends MobEffect {
    public AmbrosiumPoisoningEffect() {
        super(MobEffectCategory.HARMFUL, 0xE7D87A);
    }

    public static void preventHealing(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AetherIIMobEffects.AMBROSIUM_POISONING.get())) {
            event.setCanceled(true);
        }
    }
}

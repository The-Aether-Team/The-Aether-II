package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

import java.util.Set;

public class AmbrosiumPoisoningEffect extends MobEffect {
    public AmbrosiumPoisoningEffect() {
        super(MobEffectCategory.HARMFUL, 15194234);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }

    public static void preventHealing(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AetherIIEffects.AMBROSIUM_POISONING)) {
            event.setCanceled(true);
        }
    }
}

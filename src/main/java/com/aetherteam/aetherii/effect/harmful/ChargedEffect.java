package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class ChargedEffect extends MobEffect {
    public ChargedEffect() {
        super(MobEffectCategory.HARMFUL, 12505576);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        boolean remove = false;
        for (Entity entity : livingEntity.level().getEntities(livingEntity, AABB.ofSize(livingEntity.position(), 5, 5, 5), (entity) -> entity instanceof LivingEntity living && living.hasEffect(AetherIIEffects.CHARGED))) {
            if (entity instanceof LivingEntity living) {
                livingEntity.hurt(AetherIIDamageTypes.damageSource(living.level(), AetherIIDamageTypes.SHOCK), 4.0F);
                living.removeEffect(AetherIIEffects.CHARGED);
                remove = true;
            }
        }
        if (remove) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.SHOCK), 4.0F);
            livingEntity.removeEffect(AetherIIEffects.CHARGED);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i == 0 || duration % i == 0;
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }
}

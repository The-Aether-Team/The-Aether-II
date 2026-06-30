package com.aetherteam.aetherii.effect.harmful;

import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class ChargedEffect extends MobEffect {
    public ChargedEffect() {
        super(MobEffectCategory.HARMFUL, 0xBED1E8);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        boolean remove = false;
        for (Entity entity : livingEntity.level().getEntities(livingEntity, AABB.ofSize(livingEntity.position(), 5, 5, 5), (entity) -> entity instanceof LivingEntity living && living.hasEffect(AetherIIMobEffects.CHARGED.get()))) {
            if (entity instanceof LivingEntity living) {
                livingEntity.hurt(AetherIIDamageTypes.damageSource(living.level(), AetherIIDamageTypes.CHARGED), 4.0F);
                living.removeEffect(AetherIIMobEffects.CHARGED.get());
                remove = true;
            }
        }
        if (remove) {
            livingEntity.hurt(AetherIIDamageTypes.damageSource(livingEntity.level(), AetherIIDamageTypes.CHARGED), 4.0F);
            livingEntity.removeEffect(AetherIIMobEffects.CHARGED.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}

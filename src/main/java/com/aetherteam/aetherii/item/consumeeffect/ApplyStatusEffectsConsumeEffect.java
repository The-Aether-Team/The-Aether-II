package com.aetherteam.aetherii.item.consumeeffect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public record ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects, float probability) implements ConsumeEffect {
    public ApplyStatusEffectsConsumeEffect(List<MobEffectInstance> effects) {
        this(effects, 1.0F);
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        if (level.isClientSide() || level.random.nextFloat() > this.probability()) {
            return false;
        }
        for (MobEffectInstance effect : this.effects()) {
            entity.addEffect(new MobEffectInstance(effect));
        }
        return !this.effects().isEmpty();
    }
}

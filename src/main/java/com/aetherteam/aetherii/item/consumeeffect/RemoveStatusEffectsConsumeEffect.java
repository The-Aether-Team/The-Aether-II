package com.aetherteam.aetherii.item.consumeeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public record RemoveStatusEffectsConsumeEffect(List<MobEffect> effects) implements ConsumeEffect {
    public RemoveStatusEffectsConsumeEffect(MobEffect effect) {
        this(List.of(effect));
    }

    public RemoveStatusEffectsConsumeEffect(RegistryObject<MobEffect> effect) {
        this(effect.get());
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        boolean removed = false;
        for (MobEffect effect : this.effects()) {
            removed |= entity.removeEffect(effect);
        }
        return removed;
    }
}

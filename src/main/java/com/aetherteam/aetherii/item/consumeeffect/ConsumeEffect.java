package com.aetherteam.aetherii.item.consumeeffect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ConsumeEffect {
    boolean apply(Level level, ItemStack stack, LivingEntity entity);
}

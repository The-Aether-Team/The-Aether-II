package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    RandomSource aether_ii$getRandom();
}
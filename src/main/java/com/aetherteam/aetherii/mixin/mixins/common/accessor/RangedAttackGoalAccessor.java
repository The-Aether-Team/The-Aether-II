package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RangedAttackGoal.class)
public interface RangedAttackGoalAccessor {
    @Accessor("attackTime")
    int aether_ii$getAttackTime();
}

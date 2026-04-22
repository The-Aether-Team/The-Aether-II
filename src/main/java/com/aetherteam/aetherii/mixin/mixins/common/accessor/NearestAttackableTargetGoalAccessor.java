package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NearestAttackableTargetGoal.class)
public interface NearestAttackableTargetGoalAccessor {
    @Mutable
    @Accessor("targetConditions")
    void aether_ii$setTargetConditions(TargetingConditions targetingConditions);
}

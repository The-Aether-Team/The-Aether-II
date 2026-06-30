package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/*
 * This Class does not Melee Attack. used to Move to Target Like Creeper
 */
public class FakeMeleeAttackGoal extends MeleeAttackGoal {
    public FakeMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target, double distanceToTargetSqr) {
    }
}

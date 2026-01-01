package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;

public class ClosedAnimationMeleeAttackGoal extends PreAnimationMeleeAttackGoal {
    private double attackThresholdSqr;

    public ClosedAnimationMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, int attackAction, int attackLength, float attackThreshold) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen, attackAction, attackLength);
        this.attackThresholdSqr = attackThreshold * attackThreshold;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) < this.attackThresholdSqr;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) < this.attackThresholdSqr;
    }
}

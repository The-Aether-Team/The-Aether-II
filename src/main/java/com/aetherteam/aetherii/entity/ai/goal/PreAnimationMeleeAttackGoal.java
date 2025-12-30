package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PreAnimationMeleeAttackGoal extends MeleeAttackGoal {
    private int ticksUntilNextAttack;
    private final int attackAction;
    private final int attackLength;

    private boolean attack;
    private final float attackThresholdSqr;

    public PreAnimationMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold, int attackAction, int attackLength) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.attackAction = attackAction;
        this.attackLength = attackLength;
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

    @Override
    public void start() {
        super.start();
        this.ticksUntilNextAttack = 0;
        this.attack = false;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if ((this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) && !this.attack) {
            this.resetAttackCooldown();
            this.attack = true;
        }

        if (this.attack && this.ticksUntilNextAttack == this.attackLength) {
            this.attackAnimation();
        }

        if (this.canPerformAttack(target)) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(getServerLevel(this.mob.level()), target);
            this.attackAction();
        }

        if (this.attack) {
            --this.ticksUntilNextAttack;
        }

        if (this.ticksUntilNextAttack <= 0) {
            this.attack = false;
        }
    }

    public void attackAnimation() {
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 4);
    }

    public void attackAction() {
    }

    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(this.attackLength);
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack == this.attackAction;
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
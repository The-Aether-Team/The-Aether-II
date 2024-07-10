package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CockatriceMeleeAttackGoal extends MeleeAttackGoal {
    private int ticksUntilNextAttack;
    private boolean attack;
    private final double speedModifier;

    public CockatriceMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (super.canUse() && this.mob.distanceToSqr(this.mob.getTarget()) <= 3 * 3) {
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.mob.distanceToSqr(this.mob.getTarget()) < 10 * 10;
    }

    @Override
    public void start() {
        super.start();
        this.ticksUntilNextAttack = 0;
        this.attack = false;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (!(this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) && (!this.attack)) {
            this.resetAttackCooldown();
            this.attack = false;
        } else {
            this.attack = true;
        }
        if (this.attack && this.ticksUntilNextAttack == 30) {
            this.mob.level().broadcastEntityEvent(this.mob, (byte) 61);
        }

        if (this.canPerformAttack(target)) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
            this.mob.setZza(0.3F);
        }

        if (this.attack) {
            --this.ticksUntilNextAttack;
        }

        if (this.ticksUntilNextAttack <= 0) {
            this.attack = false;
        }
    }


    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(30);
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack == 10 + 3;
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

}

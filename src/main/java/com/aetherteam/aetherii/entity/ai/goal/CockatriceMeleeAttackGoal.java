package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CockatriceMeleeAttackGoal extends MeleeAttackGoal {
    private int ticksUntilNextAttack;

    public CockatriceMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) < 6;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.mob.distanceTo(this.mob.getTarget()) < 6;
    }

    @Override
    public void start() {
        super.start();
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.getNavigation().stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (!(this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) && (this.ticksUntilNextAttack >= 49 || this.ticksUntilNextAttack <= 0)) {
            this.resetAttackCooldown();
        } else if (this.ticksUntilNextAttack == 49) {
            this.mob.level().broadcastEntityEvent(this.mob, (byte) 61);
        }

        if (this.canPerformAttack(target)) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(target);
        }

        --this.ticksUntilNextAttack;
    }


    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(50);
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack == 17;
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

}

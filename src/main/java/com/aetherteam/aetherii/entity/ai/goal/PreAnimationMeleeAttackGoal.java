package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PreAnimationMeleeAttackGoal extends MeleeAttackGoal {
    protected boolean attack;
    public int attackTicks;
    protected final int attackAction;
    protected final int attackLength;

    public PreAnimationMeleeAttackGoal(PathfinderMob attacker, double speed, int attackAction, int attackLength) {
        this(attacker, speed, true, attackAction, attackLength);
    }

    public PreAnimationMeleeAttackGoal(PathfinderMob attacker, double speed, boolean longPath, int attackAction, int attackLength) {
        super(attacker, speed, longPath);
        this.attackAction = attackAction;
        this.attackLength = attackLength;
    }

    @Override
    public void start() {
        super.start();
        this.attackTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.attack = false;
        this.mob.setAggressive(false);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();

        return super.canContinueToUse() || livingentity != null && livingentity.isAlive() && this.attack;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target, double distanceToTargetSqr) {
        if (this.isTimeToAttack()) {
            if (this.canPerformAttack(target)) {
                this.doAttack(target);
                this.attackAction();
            }
        } else if (this.attackTicks >= this.attackLength) {
            this.resetAttackCooldown();
            this.attack = false;
        } else if (!this.attack) {
            if (!this.canPerformAttack(target)) {
                this.resetAttackCooldown();
            } else {
                this.attack = true;
                this.attackAnimation();
            }
        }
        if (this.attack) {
            this.attackTicks = Mth.clamp(this.attackTicks + 1, 0, this.attackLength);
        } else {
            this.attackTicks = 0;
        }
    }

    protected void doAttack(LivingEntity living) {
        this.mob.doHurtTarget(living);
    }

    public void attackAnimation() {
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 4);
    }

    public void attackAction() {
    }

    protected boolean canPerformAttack(LivingEntity target) {
        return this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(target) <= this.getAttackReachSqr(target) && this.mob.getSensing().hasLineOfSight(target);
    }


    protected void resetAttackCooldown() {
        this.attackTicks = 0;
        this.attack = false;
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.attackTicks == this.attackAction;
    }


    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}

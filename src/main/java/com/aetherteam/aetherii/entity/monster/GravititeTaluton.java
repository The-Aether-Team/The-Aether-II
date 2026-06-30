package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class GravititeTaluton extends Taluton implements RangedAttackMob {
    public static final int ATTACK_DURATION = 100;

    public static int ATTACK_START_EVENT = 100;
    public static int ATTACK_STOP_EVENT = 101;
    public static int RELOAD_STOP_EVENT = 102;
    public static int RELOAD_START_EVENT = 103;

    public GravititeTalutonRangedAttackGoal attackGoal;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState reloadAnimationState = new AnimationState();
    public boolean debrisVisible;

    public GravititeTaluton(EntityType<? extends GravititeTaluton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.attackGoal = new GravititeTalutonRangedAttackGoal(this, 1.0, ATTACK_DURATION, 10.0F);
        this.goalSelector.addGoal(1, this.attackGoal);
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_START_EVENT) {
            this.attackAnimationState.start(this.tickCount);
        } else if (id == ATTACK_STOP_EVENT) {
            this.attackAnimationState.stop();
            this.debrisVisible = false;
        } else if (id == RELOAD_STOP_EVENT) {
            this.reloadAnimationState.stop();
        } else if (id == RELOAD_START_EVENT) {
            if (!this.debrisVisible) {
                this.debrisVisible = true;
                this.reloadAnimationState.start(this.tickCount);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.min(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackGoal != null) {
            int attackTime = this.attackGoal.attackTime;
            switch(attackTime) {
                case 22 -> this.level().broadcastEntityEvent(this, (byte) ATTACK_START_EVENT);
                case 40 -> this.level().broadcastEntityEvent(this, (byte) RELOAD_STOP_EVENT);
                case 1 -> this.level().broadcastEntityEvent(this, (byte) ATTACK_STOP_EVENT);
                case 60 -> this.level().broadcastEntityEvent(this, (byte) RELOAD_START_EVENT);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        GravititeDebrisShot debrisShot = new GravititeDebrisShot(this, this.level());
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(0.5) - debrisShot.getY();
        double d2 = target.getZ() - this.getZ();
        if (this.level() instanceof ServerLevel serverLevel) {
            debrisShot.shoot(d0, d1, d2, 0.5F, 0.0F);
            serverLevel.addFreshEntity(debrisShot);
        }
        this.playSound(AetherIISoundEvents.ENTITY_GRAVITITE_TALUTON_SHOOT.get(), this.getSoundVolume(), (this.level().getRandom().nextFloat() - this.level().getRandom().nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public void jumpFromGround() { }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_GRAVITITE_TALUTON_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_GRAVITITE_TALUTON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_GRAVITITE_TALUTON_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_GRAVITITE_TALUTON_STEP.get(), 0.15F, 1.0F);
    }

    protected static class GravititeTalutonRangedAttackGoal extends Goal {
        private final GravititeTaluton gravititeTaluton;
        protected int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;
        private LivingEntity trackedTarget;

        public GravititeTalutonRangedAttackGoal(GravititeTaluton gravititeTaluton, double speedModifier, int attackInterval, float attackRadius) {
            this(gravititeTaluton, speedModifier, attackInterval, attackInterval, attackRadius);
        }

        public GravititeTalutonRangedAttackGoal(GravititeTaluton gravititeTaluton, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
            this.gravititeTaluton = gravititeTaluton;
            this.speedModifier = speedModifier;
            this.attackIntervalMin = attackIntervalMin;
            this.attackIntervalMax = attackIntervalMax;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.gravititeTaluton.getTarget() != null && this.gravititeTaluton.getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null;
        }

        @Override
        public void start() {
            this.trackedTarget = this.gravititeTaluton.getTarget();
        }

        @Override
        public void stop() {
            this.seeTime = 0;
            this.attackTime = -1;
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                double distance = this.gravititeTaluton.distanceToSqr(this.trackedTarget);
                boolean canSee = this.gravititeTaluton.getSensing().hasLineOfSight(this.trackedTarget);

                if (canSee) {
                    ++this.seeTime;
                } else {
                    this.seeTime = 0;
                }
                if (distance <= (double) this.attackRadiusSqr && this.seeTime >= 5) {
                    this.gravititeTaluton.getNavigation().stop();
                } else {
                    this.gravititeTaluton.getNavigation().moveTo(this.trackedTarget, this.speedModifier);
                }
                this.gravititeTaluton.getLookControl().setLookAt(this.trackedTarget, 30.0F, 30.0F);

                if (--this.attackTime == 0) {
                    float f = (float) Math.sqrt(distance) / this.attackRadius;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    this.gravititeTaluton.performRangedAttack(this.trackedTarget, f1);
                    this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                    if (!canSee || this.gravititeTaluton.getTarget() == null || !this.gravititeTaluton.getTarget().isAlive()) {
                        this.trackedTarget = null;
                    }
                } else if (this.attackTime < 0) {
                    this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distance) / this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
                }
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}

package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.GravititeDebrisShot;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.RangedAttackGoalAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GravititeTaluton extends Taluton implements RangedAttackMob {
    public static final int ATTACK_DURATION = 100;

    public static int ATTACK_START_EVENT = 100;
    public static int ATTACK_STOP_EVENT = 101;
    public static int RELOAD_STOP_EVENT = 102;
    public static int RELOAD_START_EVENT = 103;

    public RangedAttackGoal attackGoal;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState reloadAnimationState = new AnimationState();
    public boolean debrisVisible;

    public GravititeTaluton(EntityType<? extends GravititeTaluton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.attackGoal = new RangedAttackGoal(this, 1.0, ATTACK_DURATION, 10.0F);
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
                .add(Attributes.STEP_HEIGHT, 1.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_START_EVENT) {
            this.attackAnimationState.start(this.tickCount);
        } else if (id == ATTACK_STOP_EVENT) {
            this.attackAnimationState.stop();
        } else if (id == RELOAD_STOP_EVENT) {
            this.debrisVisible = false;
            this.reloadAnimationState.stop();
        } else if (id == RELOAD_START_EVENT) {
            this.debrisVisible = true;
            this.reloadAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
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
            int attackTime = ((RangedAttackGoalAccessor) this.attackGoal).aether_ii$getAttackTime();
            switch(attackTime) {
                case 22 -> this.level().broadcastEntityEvent(this, (byte) ATTACK_START_EVENT);
                case 1 -> this.level().broadcastEntityEvent(this, (byte) RELOAD_STOP_EVENT);
                case ATTACK_DURATION -> this.level().broadcastEntityEvent(this, (byte) ATTACK_STOP_EVENT);
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
}

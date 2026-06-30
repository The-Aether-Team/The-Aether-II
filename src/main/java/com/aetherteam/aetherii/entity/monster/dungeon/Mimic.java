package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.goal.PreAnimationMeleeAttackGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Mimic extends Monster {
    public final AnimationState spawnAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public static int ATTACK_EVENT = 100;

    public Mimic(EntityType<? extends Mimic> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MimicMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Mimic.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.5)
                .add(Attributes.MOVEMENT_SPEED, 0.33)
                .add(Attributes.FOLLOW_RANGE, 12.0);
    }

    @Override
    protected void updateWalkAnimation(float p_382793_) {
        float f2 = Math.min(p_382793_ * (8.0F), 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    /**
     * Prevents Mimics from hurting each other and spawns particles when one is hurt by any other entity, and sets the entity as a target.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!(source.getDirectEntity() instanceof Mimic)) {
            if (source.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
                if (!(livingEntity instanceof Player player) || !player.isCreative()) {
                    this.setTarget(livingEntity);
                }
            }
            return super.hurt(source, amount);
        } else {
            return false;
        }
    }

    /**
     * Handle sounds when a target is hurt.
     *
     * @param entity The hurt {@link Entity}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (entity instanceof LivingEntity livingEntity) { // Choose between attack or kill sound depending on remaining target health.
            SoundEvent sound = livingEntity.getHealth() <= 0.0 ? AetherIISoundEvents.ENTITY_MIMIC_KILL.get() : AetherIISoundEvents.ENTITY_MIMIC_ATTACK.get();
            this.playSound(sound, 1.0F, this.getVoicePitch());
        }
        return result;
    }

    @Override
    public void spawnAnim() {
        if (this.level().isClientSide()) {
            this.spawnAnimationState.start(this.tickCount);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 70);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_MIMIC_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_MIMIC_DEATH.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_EVENT) {
            this.attackAnimationState.start(this.tickCount);
        } else if (id == 70) {
            this.spawnAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    protected static class MimicMeleeAttackGoal extends PreAnimationMeleeAttackGoal {
        private int ticksUntilNextAttack;
        private boolean attack;

        public MimicMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen, 9, 20);
        }

        @Override
        public void attackAnimation() {
            this.mob.level().broadcastEntityEvent(this.mob, (byte) ATTACK_EVENT);
        }
    }

}

package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

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
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 8.0);
    }

    /**
     * Prevents Mimics from hurting each other and spawns particles when one is hurt by any other entity, and sets the entity as a target.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (!(source.getDirectEntity() instanceof Mimic)) {
            if (source.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 20; i++) {
                        serverLevel.sendParticles(this.getHurtParticle(), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 1, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05F);
                    }
                }
                if (!(livingEntity instanceof Player player) || !player.isCreative()) {
                    this.setTarget(livingEntity);
                }
            }
            return super.hurtServer(level, source, amount);
        } else {
            return false;
        }
    }

    /**
     * @return The type of {@link ParticleOptions} to render when a Mimic is hurt.
     */
    public ParticleOptions getHurtParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHEST.defaultBlockState());
    }

    /**
     * Handle sounds when a target is hurt.
     *
     * @param entity The hurt {@link Entity}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity entity) {
        boolean result = super.doHurtTarget(level, entity);
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
    protected boolean shouldDespawnInPeaceful() {
        return true;
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

    protected static class MimicMeleeAttackGoal extends MeleeAttackGoal {
        private int ticksUntilNextAttack;
        private boolean attack;

        public MimicMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse();
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

            if (this.attack && this.ticksUntilNextAttack == 20) {
                this.mob.level().broadcastEntityEvent(this.mob, (byte) ATTACK_EVENT);
            }

            if (this.canPerformAttack(target)) {
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(getServerLevel(this.mob.level()), target);
                this.mob.setZza(0.2F);
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
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        @Override
        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack == 11;
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

}


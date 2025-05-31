package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Cockatrice extends Monster implements RangedAttackMob, Blighted {
    public static int HIDE_ANIMATION_START = 200;
    public static int HIDE_ANIMATION_LENGTH = 50;
    public static int HIDE_PARTICLE_START = HIDE_ANIMATION_START + 5;
    public static int HIDE_LENGTH = HIDE_ANIMATION_START + HIDE_ANIMATION_LENGTH;
    
    public static int CLAW_ATTACK_EVENT = 100;
    public static int DART_ATTACK_EVENT = 101;
    public static int DIG_EVENT = 102;

    public static final EntityDataAccessor<Integer> DATA_HIDE_ID = SynchedEntityData.defineId(Cockatrice.class, EntityDataSerializers.INT);

    public AnimationState clawAttackAnimationState = new AnimationState();
    public AnimationState dartAttackAnimationState = new AnimationState();
    public AnimationState digAnimationState = new AnimationState();

    public Cockatrice(EntityType<? extends Cockatrice> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.5));
        this.goalSelector.addGoal(4, new Cockatrice.CockatriceMeleeAttackGoal(this, 1.15F, true, 6.0F));
        this.goalSelector.addGoal(5, new Cockatrice.CockatriceRangedAttackGoal(this, 0.8F, 200, 300, 15.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Cockatrice.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HIDE_ID, 0);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == CLAW_ATTACK_EVENT) {
            this.dartAttackAnimationState.stop();
            this.clawAttackAnimationState.start(this.tickCount);
        } else if (id == DART_ATTACK_EVENT) {
            this.clawAttackAnimationState.stop();
            this.dartAttackAnimationState.start(this.tickCount);
        } else if (id == DIG_EVENT) {
            this.digAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (Blighted.super.inSunlight(this) || this.getHideTime() >= HIDE_ANIMATION_START) {
            if (this.getHideTime() <= HIDE_LENGTH) {
                Blighted.super.weaken(this, this.getRandom(), this.tickCount, 0.65F);

                if (this.getHideTime() == HIDE_ANIMATION_START) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.broadcastEntityEvent(this, (byte) DIG_EVENT);
                    }
                }

                if (this.getHideTime() >= HIDE_PARTICLE_START) {
                    this.spawnDigParticles();
                    this.stopInPlace();
                    this.removeAllGoals((goal) -> true);

                    if (this.getHideTime() == HIDE_LENGTH) {
                        this.discard();
                    }
                }
            }
            this.setHideTime(this.getHideTime() + 1);
        } else {
            this.setHideTime(0);
        }
    }

    private void spawnDigParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < Math.max(0, (this.getHideTime() - (HIDE_PARTICLE_START)) * 2); ++i) {
                BlockParticleOption blockParticles = new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn());
                serverLevel.sendParticles(blockParticles,
                        this.getRandomX(1.0F), this.getY() + 0.25, this.getRandomZ(1.0F), 1,
                        0, 0, 0, this.getRandom().nextGaussian() * 0.02);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        VenomousDart dart = new VenomousDart(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        dart.shoot(d1, d0 + d4, d3, 1.25F, 6.0F);
        this.playSound(AetherIISoundEvents.ENTITY_COCKATRICE_SHOOT.value(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIEffects.VENOM.get() && super.canBeAffected(effect);
    }

    @Override
    public int getHideTime() {
        return this.getEntityData().get(DATA_HIDE_ID);
    }

    @Override
    public void setHideTime(int hideTime) {
        this.getEntityData().set(DATA_HIDE_ID, hideTime);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_COCKATRICE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_COCKATRICE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_COCKATRICE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_COCKATRICE_STEP.get(), 0.15F, 1.0F);
    }

    protected static class CockatriceMeleeAttackGoal extends MeleeAttackGoal {
        private int ticksUntilNextAttack;
        private boolean attack;
        private final float attackThreshold;
        private final float attackThresholdSqr;

        public CockatriceMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
            this.attackThreshold = attackThreshold;
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
            if (!(this.mob.isWithinMeleeAttackRange(target) && this.mob.getSensing().hasLineOfSight(target)) && (!this.attack)) {
                this.resetAttackCooldown();
                this.attack = false;
            } else {
                this.attack = true;
            }
            if (this.attack && this.ticksUntilNextAttack == 30) {
                this.mob.level().broadcastEntityEvent(this.mob, (byte) CLAW_ATTACK_EVENT);
            }

            if (this.canPerformAttack(target)) {
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(getServerLevel(this.mob.level()), target);
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

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    protected static class CockatriceRangedAttackGoal extends Goal {
        private final Mob mob;
        private final RangedAttackMob rangedAttackMob;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public CockatriceRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius) {
            this(rangedAttackMob, speedModifier, attackInterval, attackInterval, attackRadius);
        }

        public CockatriceRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
            if (!(rangedAttackMob instanceof LivingEntity)) {
                throw new IllegalArgumentException("CockatriceRangedAttackGoal requires Mob implements RangedAttackMob");
            } else {
                this.rangedAttackMob = rangedAttackMob;
                this.mob = (Mob) rangedAttackMob;
                this.speedModifier = speedModifier;
                this.attackIntervalMin = attackIntervalMin;
                this.attackIntervalMax = attackIntervalMax;
                this.attackRadius = attackRadius;
                this.attackRadiusSqr = attackRadius * attackRadius;
                this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            }
        }

        @Override
        public boolean canUse() {
            return this.mob.getTarget() != null && this.mob.distanceToSqr(this.mob.getTarget()) >= 6 * 6;
        }

        @Override
        public void stop() {
            this.seeTime = 0;
            this.attackTime = -1;
        }

        @Override
        public void tick() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
                boolean canSee = this.mob.getSensing().hasLineOfSight(target);

                if (canSee) {
                    ++this.seeTime;
                } else {
                    this.seeTime = 0;
                }
                if (distance <= (double) this.attackRadiusSqr && this.seeTime >= 5) {
                    this.mob.getNavigation().stop();
                }
                if (distance > (double) this.attackRadiusSqr || this.seeTime < 5 || !this.isTimeToAttack()) {
                    this.mob.getNavigation().moveTo(target, this.speedModifier);
                }
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

                if (canSee) {
                    float f = (float) Math.sqrt(distance) / this.attackRadius;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    if (++this.attackTime >= 0) {
                        if (this.attackTime == 0) {
                            this.mob.level().broadcastEntityEvent(this.mob, (byte) DART_ATTACK_EVENT);
                        }
                        if (this.isTimeToAttack()) {
                            this.rangedAttackMob.performRangedAttack(target, f1);
                        }
                        if (this.attackTime == 20 * Mth.floor(3.375)) {
                            this.attackTime = -Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
                        }
                    }
                }
            }
        }

        private boolean isTimeToAttack() {
            int i = this.attackTime;
            return i == Mth.floor(1.25 * 20) || i == Mth.floor(1.85 * 20) || i == Mth.floor(2.42 * 20) || i == Mth.floor(2.67 * 20);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}

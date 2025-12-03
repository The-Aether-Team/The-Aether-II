package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.DetonationProjectile;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SentryGolem extends Monster implements RangedAttackMob {
    public static final EntityDataAccessor<Integer> DATA_FIRE_TIME_ID = SynchedEntityData.defineId(SentryGolem.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_RANGED_ID = SynchedEntityData.defineId(SentryGolem.class, EntityDataSerializers.BOOLEAN);
    public int timeTilToss = 50;
    public int avoidCooldown;
    private SentryGolemStrollGoal randomStrollGoal;

    public SentryGolem(EntityType<? extends SentryGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new SentryGolemStrollGoal(this, 1.0);

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SentryGolemMeleeAttackGoal(this, 1.15F, true, 6.0F));
        this.goalSelector.addGoal(2, this.randomStrollGoal);
        this.goalSelector.addGoal(3, new ThrowExplosiveAttackGoal(this, 60, 0.08F, 52.0F, 255.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 50.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FIRE_TIME_ID, 0);
        builder.define(DATA_RANGED_ID, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.avoidCooldown > 0) {
                --this.avoidCooldown;
            }

            if (this.getTarget() != null) {
                if (this.timeTilToss != 0) {
                    --this.timeTilToss;
                } else {
                    this.timeTilToss = 50;
                }
                this.randomStrollGoal.setInterval(RandomStrollGoal.DEFAULT_INTERVAL * 5);
            } else {
                this.randomStrollGoal.setInterval(RandomStrollGoal.DEFAULT_INTERVAL);
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel p_376911_, DamageSource p_376689_, float p_376584_) {

        if (this.randomStrollGoal != null && this.isRanged() && this.avoidCooldown <= 0) {
            this.randomStrollGoal.trigger();
            this.avoidCooldown = 100 + this.random.nextInt(100);
        }

        return super.hurtServer(p_376911_, p_376689_, p_376584_);
    }

    @Override
    public @org.jetbrains.annotations.Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        this.setLeftHanded(true);
        return data;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AetherIIItems.DEMOLITION_HAMMER.asItem()));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distance) {
        DetonationProjectile bomb = new DetonationProjectile(this, this.level());
        double x = target.getX() - this.getX();
        double y = target.getEyeY() - this.getY();
        double z = target.getZ() - this.getZ();
        double length = Math.sqrt(x * x + z * z);
        bomb.shoot(x, y + (length * 0.5F), z, (float) (0.5F + (length * 0.01F)), 8.0F);
        bomb.setYRot(this.yBodyRot);
        this.playSound(AetherIISoundEvents.ENTITY_SENTRY_GOLEM_THROW_BOMB.get(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(bomb);
    }

    public int getFireTime() {
        return this.entityData.get(DATA_FIRE_TIME_ID);
    }

    public void setFireTime(int time) {
        this.entityData.set(DATA_FIRE_TIME_ID, time);
    }

    public boolean isRanged() {
        return this.entityData.get(DATA_RANGED_ID);
    }

    public void setRanged(boolean ranged) {
        this.entityData.set(DATA_RANGED_ID, ranged);
    }


    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SENTRY_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_SENTRY_GOLEM_SAY.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SENTRY_GOLEM_DEATH.get();
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
    }

    public static class ThrowExplosiveAttackGoal extends Goal {
        private final SentryGolem golem;
        @Nullable
        private LivingEntity target;
        private final int maxRangedAttackTime;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final float maxAttackRange;
        private final float minAttackRange;

        public ThrowExplosiveAttackGoal(SentryGolem golem, int maxRangedAttackTime, double speedModifier, float minAttackRange, float maxAttackRange) {
            this.golem = golem;
            this.maxRangedAttackTime = maxRangedAttackTime;
            this.speedModifier = speedModifier;
            this.minAttackRange = minAttackRange;
            this.maxAttackRange = maxAttackRange;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity targetEntity = this.golem.getTarget();
            if (targetEntity != null && targetEntity.isAlive()) {
                this.target = targetEntity;
                return true;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return this.canUse() || this.target.isAlive() && !this.golem.getNavigation().isDone();
        }

        @Override
        public void start() {
            super.start();
            this.golem.setRanged(true);
        }

        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.golem.setRanged(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            double distance = this.golem.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean canSee = this.golem.getSensing().hasLineOfSight(this.target);
            if (canSee) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }

            if (distance <= (double) this.minAttackRange && this.seeTime >= 20) {
                Vec3 randomPos = LandRandomPos.getPos(this.golem, 16, 7);
                if (randomPos != null) {
                    this.golem.getNavigation().moveTo(randomPos.x(), randomPos.y(), randomPos.z(), this.speedModifier);
                } else {
                    this.golem.getNavigation().moveTo(this.target, this.speedModifier);
                }
            } else if (distance <= (double) this.maxAttackRange && this.seeTime >= 20) {
                this.golem.getNavigation().stop();
            } else {
                this.golem.getNavigation().moveTo(this.target, this.speedModifier);
            }

            this.golem.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

            this.attackTime = Math.max(this.attackTime - 1, 0);
            this.golem.setFireTime(this.attackTime);
            if (this.attackTime <= 30) {
                //this.golem.setHandState((byte) 1);
            }

            if (this.attackTime <= 0 && distance <= (double) this.maxAttackRange && canSee) {
                this.golem.performRangedAttack(this.target, 1.0F);
                this.attackTime = this.maxRangedAttackTime;
                this.golem.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    protected static class SentryGolemMeleeAttackGoal extends MeleeAttackGoal {
        private int ticksUntilNextAttack;
        private boolean attack;
        private final float attackThresholdSqr;
        private final SentryGolem sentryGolem;

        public SentryGolemMeleeAttackGoal(SentryGolem golem, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(golem, speedModifier, followingTargetEvenIfNotSeen);
            this.attackThresholdSqr = attackThreshold * attackThreshold;
            this.sentryGolem = golem;
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
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void stop() {
            super.stop();
        }
    }

    private class SentryGolemStrollGoal extends WaterAvoidingRandomStrollGoal {
        public SentryGolemStrollGoal(SentryGolem sentryGolem, double speed) {
            super(sentryGolem, speed);
        }

        @Override
        protected @org.jetbrains.annotations.Nullable Vec3 getPosition() {
            //should not do the Moving When Target Found
            if (this.mob.getTarget() != null) {
                return LandRandomPos.getPosAway(this.mob, 10, 7, this.mob.getTarget().position());
            }

            return super.getPosition();
        }
    }
}

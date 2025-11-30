package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.DetonationProjectile;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
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
    public int timeTilToss = 50;
    public SentryGolem(EntityType<? extends SentryGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SentryGolemMeleeAttackGoal(this, 1.15F, true, 6.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new ThrowExplosiveAttackGoal(this, 60, 0.08F, 52.0F, 240.0F));
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
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null) {
                if (this.timeTilToss != 0) {
                    --this.timeTilToss;
                } else {
                    this.timeTilToss = 50;
                }
            }
        }
    }

    @Override
    public @org.jetbrains.annotations.Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();

        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AetherIIItems.DEMOLITION_HAMMER.asItem()));
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

        public void stop() {
            this.target = null;
            this.seeTime = 0;
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
}

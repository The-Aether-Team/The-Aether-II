package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.CooldownEntity;
import com.aetherteam.aetherii.entity.FakeShiftEntity;
import com.aetherteam.aetherii.entity.ai.goal.ClosedAnimationMeleeAttackGoal;
import com.aetherteam.aetherii.entity.projectile.DemolitionProjectile;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class SentryGolem extends PathfinderMob implements RangedAttackMob, CooldownEntity, FakeShiftEntity {
    public static final EntityDataAccessor<Integer> DATA_FIRE_TIME_ID = SynchedEntityData.defineId(SentryGolem.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> DATA_RANGED_ID = SynchedEntityData.defineId(SentryGolem.class, EntityDataSerializers.BOOLEAN);
    public int timeTilToss = 50;
    public int avoidCooldown;
    private SentryGolemStrollGoal randomStrollGoal;
    private final ItemCooldowns cooldowns;

    public final AnimationState checkSelfAnimationState = new AnimationState();
    public final AnimationState lookAroundAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackReadyAnimationState = new AnimationState();
    public final AnimationState attackRangeReadyAnimationState = new AnimationState();
    public final AnimationState attackRangeAnimationState = new AnimationState();

    public int attackAnimationTick;
    public final int attackAnimationLength = 20;
    public int attackRangeAnimationTick;
    public final int attackRangeAnimationLength = 20;

    private int idleAnimationCooldown;
    private int idleTick;
    public final int idleLength = 120;

    public SentryGolem(EntityType<? extends SentryGolem> entityType, Level level) {
        super(entityType, level);
        this.setupIdleAnimationCooldown();
        this.cooldowns = new ItemCooldowns();
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new SentryGolemStrollGoal(this, 0.75F);

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SentryGolemMeleeAttackGoal(this, 1.15F, true, 7.5F));
        this.goalSelector.addGoal(2, this.randomStrollGoal);
        this.goalSelector.addGoal(3, new ThrowExplosiveAttackGoal(this, 20, 0.8F, 52.0F, 255.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 15.0);
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
        } else {
            this.setupAnimationStates();
        }
        this.cooldowns.tick();
    }

    @Override
    public void aiStep() {
        this.updateSwingTime();
        super.aiStep();
    }

    private void setupIdleAnimationCooldown() {
        this.idleAnimationCooldown = 200 + this.random.nextInt(200);
    }

    private void setupAnimationStates() {
        if (--this.idleAnimationCooldown <= 0) {
            if (this.random.nextBoolean()) {
                this.checkSelfAnimationState.start(this.tickCount);
            } else {
                this.lookAroundAnimationState.start(this.tickCount);
            }
            this.idleTick = 0;
            this.setupIdleAnimationCooldown();
        }


        if (this.idleTick < this.idleLength) {
            this.idleTick++;
        }

        if (this.idleTick >= this.idleLength) {
            this.checkSelfAnimationState.stop();
            this.lookAroundAnimationState.stop();
        }

        if (this.attackAnimationTick < this.attackAnimationLength) {
            this.attackAnimationTick++;
        }

        if (this.attackAnimationTick >= this.attackAnimationLength) {
            this.attackAnimationState.stop();
        }

        if (this.attackRangeAnimationTick < this.attackRangeAnimationLength) {
            this.attackRangeAnimationTick++;
        }

        if (this.attackRangeAnimationTick >= this.attackRangeAnimationLength) {
            this.attackRangeAnimationState.stop();
        }


        if (this.isRanged()) {
            if (!this.attackRangeAnimationState.isStarted()) {
                this.attackAnimationState.stop();
                this.attackReadyAnimationState.stop();
                this.attackRangeReadyAnimationState.startIfStopped(this.tickCount);
            }
        } else if (this.isAggressive() && !this.attackAnimationState.isStarted()) {
            this.attackReadyAnimationState.startIfStopped(this.tickCount);
            this.attackRangeAnimationState.stop();
            this.attackRangeReadyAnimationState.stop();
        } else {
            this.attackRangeReadyAnimationState.stop();
            this.attackReadyAnimationState.stop();
        }

    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f2 = Math.min(partialTick * (12.0F), 1.0F);
        this.walkAnimation.update(f2, 1.0F, 1.0F);
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 4) {
            this.attackAnimationState.start(this.tickCount);
            this.attackReadyAnimationState.stop();
            this.attackRangeAnimationState.stop();
            this.attackRangeReadyAnimationState.stop();
            this.attackAnimationTick = 0;
        } else if (p_21375_ == 61) {
            this.attackAnimationState.stop();
            this.attackReadyAnimationState.stop();
            this.attackRangeAnimationState.start(this.tickCount);
            this.attackRangeReadyAnimationState.stop();
            this.attackRangeAnimationTick = 0;
            this.cooldowns.addCooldown(this.getMainHandItem(), 60);
        } else {
            super.handleEntityEvent(p_21375_);
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
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        this.populateDefaultEquipmentEnchantments(level, randomsource, difficulty);
        this.setLeftHanded(true);
        this.setPersistenceRequired();
        return data;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        ItemStack weapon = new ItemStack(AetherIIItems.HAMMER_OF_DEMOLITION.asItem());
        weapon.set(DataComponents.ATTRIBUTE_MODIFIERS, TieredHammerItem.createAttributes(AetherIIToolMaterials.HAMMER_OF_DEMOLITION, 0.0F, 0.0F, List.of()));
        this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean flag) {

    }

    @Override
    public void performRangedAttack(LivingEntity target, float distance) {
        DemolitionProjectile bomb = new DemolitionProjectile(this, this.level());
        double x = target.getX() - this.getX();
        double y = target.getEyeY() - this.getY();
        double z = target.getZ() - this.getZ();
        double length = Math.sqrt(x * x + z * z);
        bomb.shoot(x, y + (length * 0.5F), z, (float) (0.5F + (length * 0.01F)), 8.0F);
        bomb.setYRot(this.yBodyRot);
        this.playSound(AetherIISoundEvents.ENTITY_SENTRY_GOLEM_THROW_BOMB.get(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(bomb);
        this.cooldowns.addCooldown(this.getMainHandItem(), 40);

        this.level().broadcastEntityEvent(this, (byte) 61);
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

    public ItemCooldowns getCooldowns() {
        return this.cooldowns;
    }

    /**
     * Required despite call to {@link Mob#setPersistenceRequired()} in constructor.
     */
    @Override
    public void checkDespawn() {
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
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
    public boolean shouldDropExperience() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot(ServerLevel p_433619_) {
        return true;
    }

    @Override
    public void readAdditionalSaveData(ValueInput tag) {
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput tag) {
        super.addAdditionalSaveData(tag);
    }

    @Override
    public boolean doHurtTarget(ServerLevel p_376642_, Entity p_21372_) {
        p_376642_.broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(p_376642_, p_21372_);
    }

    @Override
    public boolean isFakeShift() {
        return this.isRanged();
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource damageSource) {
        Entity damageEntity = damageSource.getEntity();
        if (damageEntity != null) {
            return damageEntity.getType().builtInRegistryHolder().is(AetherIITags.EntityTypes.SENTRY_RUINS_MOBS) || super.isInvulnerableTo(level, damageSource);
        }
        return super.isInvulnerableTo(level, damageSource);
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
                return this.golem.distanceToSqr(targetEntity) >= this.minAttackRange;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return this.canUse() || this.target.isAlive() && !this.golem.getNavigation().isDone() && this.golem.distanceToSqr(this.target) >= this.minAttackRange;
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
                this.golem.getNavigation().moveTo(this.target, this.speedModifier);
            } else if (distance <= (double) this.maxAttackRange && this.seeTime >= 20) {
                this.golem.getNavigation().stop();
            } else {
                this.golem.getNavigation().moveTo(this.target, this.speedModifier);
            }

            this.golem.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

            if (this.golem.cooldowns.isOnCooldown(this.golem.getMainHandItem())) {
                this.attackTime = this.maxRangedAttackTime;
            }  else {
                this.attackTime = Math.max(this.attackTime - 1, 0);
            }

            this.golem.setFireTime(this.attackTime);

            if (this.attackTime <= 0 && !this.golem.cooldowns.isOnCooldown(this.golem.getMainHandItem()) && distance <= (double) this.maxAttackRange && canSee) {
                this.golem.performRangedAttack(this.target, 1.0F);
                this.attackTime = this.maxRangedAttackTime;
                this.golem.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    protected static class SentryGolemMeleeAttackGoal extends ClosedAnimationMeleeAttackGoal {
        public SentryGolemMeleeAttackGoal(SentryGolem golem, double speedModifier, boolean followingTargetEvenIfNotSeen, float attackThreshold) {
            super(golem, speedModifier, followingTargetEvenIfNotSeen, 2, 20, attackThreshold);
        }
    }

    private static class SentryGolemStrollGoal extends WaterAvoidingRandomStrollGoal {
        public SentryGolemStrollGoal(SentryGolem sentryGolem, double speed) {
            super(sentryGolem, speed);
        }

        @Override
        protected @Nullable Vec3 getPosition() {
            //should not do the Moving When Target Found
            if (this.mob.getTarget() != null) {
                return LandRandomPos.getPosAway(this.mob, 10, 7, this.mob.getTarget().position());
            }

            return super.getPosition();
        }
    }
}

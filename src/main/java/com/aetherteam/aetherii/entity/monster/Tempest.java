package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.controller.FlyingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.FlyingLookGoal;
import com.aetherteam.aetherii.entity.projectile.TempestThunderball;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Tempest extends Mob implements Blighted {
    public static int HIDE_ANIMATION_START = 200;
    public static int HIDE_ANIMATION_LENGTH = 95;
    public static int HIDE_PARTICLE_START = HIDE_ANIMATION_START + 35;
    public static int HIDE_LENGTH = HIDE_ANIMATION_START + HIDE_ANIMATION_LENGTH;

    public static int HIDE_START_EVENT = 100;
    public static int ATTACK_START_EVENT = 101;

    public static final EntityDataAccessor<Integer> DATA_PROJECTILE_CHARGE_ID = SynchedEntityData.defineId(Tempest.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_HIDE_ID = SynchedEntityData.defineId(Tempest.class, EntityDataSerializers.INT);

    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState hideAnimationState = new AnimationState();

    public Tempest(EntityType<? extends Tempest> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new Tempest.ThunderballAttackGoal(this, 20.0F));
        this.goalSelector.addGoal(5, new Tempest.RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new FlyingLookGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PROJECTILE_CHARGE_ID, -40);
        this.entityData.define(DATA_HIDE_ID, 0);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == HIDE_START_EVENT) {
            this.attackAnimationState.stop();
            this.hideAnimationState.start(this.tickCount);
        } else if (id == ATTACK_START_EVENT) {
            this.hideAnimationState.stop();
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public static boolean checkTempestSpawnRules(EntityType<? extends Tempest> tempest, ServerLevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return (level.getDifficulty() != Difficulty.PEACEFUL && ((spawnReason == MobSpawnType.SPAWNER || spawnReason == MobSpawnType.TRIGGERED || spawnReason == MobSpawnType.SPAWN_EGG || spawnReason == MobSpawnType.MOB_SUMMONED || spawnReason == MobSpawnType.COMMAND || spawnReason == MobSpawnType.DISPENSER) || Monster.isDarkEnoughToSpawn(level, pos, random)) && checkMobSpawnRules(tempest, level, spawnReason, pos, random)) && isValidSpawnBlock(level, pos);
    }

    private static boolean isValidSpawnBlock(LevelAccessor level, BlockPos pos){
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.AERCLOUDS) || level.getBlockState(pos.below()).is(AetherIIBlocks.AETHER_GRASS_BLOCK.get()) || level.getBlockState(pos.below()).is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS);
    }

    @Override
    public void travel(Vec3 p_415638_) {
        if (this.isControlledByLocalInstance()) {
            this.moveRelative(0.02F, p_415638_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.91));
        }
        this.calculateEntityAnimation(false);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (Blighted.super.inSunlight(this) || this.getHideTime() >= HIDE_ANIMATION_START) {
            if (this.getHideTime() <= HIDE_LENGTH) {
                Blighted.super.weaken(this, this.getRandom(), this.tickCount, 0.65F);

                if (this.getHideTime() == HIDE_ANIMATION_START) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.broadcastEntityEvent(this, (byte) HIDE_START_EVENT);
                    }
                }

                if (this.getHideTime() >= HIDE_PARTICLE_START) {
                    this.spawnShroudParticles();
                    this.getNavigation().stop();
                    this.setDeltaMovement(Vec3.ZERO);
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

    private void spawnShroudParticles() {
        if (this.tickCount % 3 == 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < Math.max(0, this.getHideTime() - HIDE_PARTICLE_START); ++i) {
                    serverLevel.sendParticles(AetherIIParticleTypes.TEMPEST_SMOKE.get(),
                            this.getRandomX(0.85F), this.getRandomY(), this.getRandomZ(0.85F), 1,
                            0, 0, 0, this.getRandom().nextGaussian() * 0.02);
                }
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.getDirectEntity() instanceof AreaEffectCloud || source.getDirectEntity() instanceof TempestThunderball || super.isInvulnerableTo(source);
    }

    @Override
    public int getHideTime() {
        return this.getEntityData().get(DATA_HIDE_ID);
    }

    @Override
    public void setHideTime(int hideTime) {
        this.getEntityData().set(DATA_HIDE_ID, hideTime);
    }

    public int getProjectileChargeTime() {
        return this.getEntityData().get(DATA_PROJECTILE_CHARGE_ID);
    }

    public void setProjectileChargeTime(int chargeTime) {
        this.getEntityData().set(DATA_PROJECTILE_CHARGE_ID, chargeTime);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_TEMPEST_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_TEMPEST_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_TEMPEST_DEATH.get();
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    public static class ThunderballAttackGoal extends Goal {
        private final Tempest tempest;
        private final float attackThresholdSqr;
        private LivingEntity trackedTarget;

        public ThunderballAttackGoal(Tempest tempest, float attackThreshold) {
            this.tempest = tempest;
            this.attackThresholdSqr = attackThreshold * attackThreshold;
        }

        @Override
        public boolean canUse() {
            return this.tempest.getTarget() != null && this.tempest.getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null;
        }

        @Override
        public void start() {
            this.trackedTarget = this.tempest.getTarget();
        }

        @Override
        public void stop() {
            this.tempest.setProjectileChargeTime(-40);
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                boolean canSee = this.tempest.hasLineOfSight(this.trackedTarget);

                this.tempest.setProjectileChargeTime(this.tempest.getProjectileChargeTime() + 1);

                if (this.tempest.getProjectileChargeTime() == -20) {
                    Vec3 toTarget = this.tempest.position().vectorTo(this.trackedTarget.position());
                    Vec3 scaled = toTarget.scale(8.0F / toTarget.length());
                    this.tempest.getMoveControl().setWantedPosition(scaled.x(), scaled.y(), scaled.z(), 0.6);

                } else if (this.tempest.getProjectileChargeTime() == 1) {
                    this.tempest.level().broadcastEntityEvent(this.tempest, (byte) ATTACK_START_EVENT);

                } else if (this.tempest.getProjectileChargeTime() == 10 && this.tempest.getAmbientSound() != null) {
                    this.tempest.playSound(this.tempest.getAmbientSound(), 0.75F, (this.tempest.getRandom().nextFloat() - this.tempest.getRandom().nextFloat()) * 0.2F + 1.0F);

                } else if (this.tempest.getProjectileChargeTime() == 25) {
                    Vec3 look = this.tempest.getViewVector(1.0F);
                    double accelX = this.trackedTarget.getX() - (this.tempest.getX() + look.x * 0.25);
                    double accelY = this.trackedTarget.getY(0.5) - (this.tempest.getY(0.25));
                    double accelZ = this.trackedTarget.getZ() - (this.tempest.getZ() + look.z * 0.25);
                    this.tempest.playSound(AetherIISoundEvents.ENTITY_TEMPEST_SHOOT.get(), 0.75F, (this.tempest.getRandom().nextFloat() - this.tempest.getRandom().nextFloat()) * 0.2F + 1.0F);
                    TempestThunderball thunderBall = new TempestThunderball(this.tempest.level(), this.tempest, accelX, accelY, accelZ);
                    thunderBall.setPos(this.tempest.getX() + look.x * 0.75, this.tempest.getY(0.25), this.tempest.getZ() + look.z * 0.75);
                    this.tempest.level().addFreshEntity(thunderBall);
                    this.tempest.setProjectileChargeTime(-40);

                    if (!canSee || this.tempest.getTarget() == null || !this.tempest.getTarget().isAlive() || this.tempest.distanceToSqr(this.trackedTarget) < this.attackThresholdSqr) {
                        this.trackedTarget = null;
                    }
                }
            }
        }
    }

    public static class RandomFloatAroundGoal extends Goal {
        private final Tempest tempest;

        public RandomFloatAroundGoal(Tempest tempest) {
            this.tempest = tempest;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.tempest.getHideTime() < Tempest.HIDE_ANIMATION_START) {
                MoveControl moveControl = this.tempest.getMoveControl();
                if (!moveControl.hasWanted()) {
                    return true;
                } else {
                    double d0 = moveControl.getWantedX() - this.tempest.getX();
                    double d1 = moveControl.getWantedY() - this.tempest.getY();
                    double d2 = moveControl.getWantedZ() - this.tempest.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    return d3 < 1.0 || d3 > 3600.0;
                }
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity livingEntity = this.tempest.getTarget();
            RandomSource random = this.tempest.getRandom();
            double d0 = this.tempest.getX() + (random.nextFloat() * 2.0F - 1.0F) * 12.0F;
            double d1 = this.tempest.getY() + (random.nextFloat() * 2.0F - 1.0F) * 4.0F;
            double d2 = this.tempest.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 12.0F;
            if (livingEntity != null) {
                d0 = livingEntity.getX() + (random.nextFloat() * 2.0F - 1.0F) * 8.0F;
                d1 = livingEntity.getY() + random.nextFloat() * 3.0F;
                d2 = livingEntity.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 8.0F;
            } else if (LevelUtil.isBrightOutside(this.tempest.level())) {
                for (int i = 0; i < 10; ++i) {
                    Vec3 vec3 = this.tempest.position();
                    Vec3 target = vec3.add((random.nextFloat() * 2.0F - 1.0F) * 6.0F, (random.nextFloat() * 2.0F - 1.0F) * 4.0F, (random.nextFloat() * 2.0F - 1.0F) * 6.0F);
                    if (!this.tempest.level().canSeeSky(BlockPos.containing(target))) {
                        d0 = target.x();
                        d1 = target.y();
                        d2 = target.z();
                        break;
                    }
                }
            }
            this.tempest.getMoveControl().setWantedPosition(d0, d1, d2, 0.6);
        }
    }
}

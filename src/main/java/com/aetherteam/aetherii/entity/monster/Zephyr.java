package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.projectile.ZephyrWebbingBall;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class Zephyr extends FlyingMob implements Enemy {
    private static final EntityDataAccessor<Integer> DATA_CHARGE_TIME_ID = SynchedEntityData.defineId(Zephyr.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_BLOW_ID = SynchedEntityData.defineId(Zephyr.class, EntityDataSerializers.BOOLEAN);

    public AnimationState blowAnimationState = new AnimationState();
    public AnimationState webAnimationState = new AnimationState();

    public Zephyr(EntityType<? extends Zephyr> type, Level level) {
        super(type, level);
        this.moveControl = new ZephyrMoveControl(this);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(5, new ZephyrShootSnowballGoal(this));
        this.goalSelector.addGoal(7, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new ZephyrLookGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.FOLLOW_RANGE, 50.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CHARGE_TIME_ID, 0);
        builder.define(DATA_BLOW_ID, false);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.blowAnimationState.start(this.tickCount);
        } else if (pId == 62) {
            this.webAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    /**
     * Zephyrs can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} is true, if they are spawning in view of the sky,
     * if the difficulty isn't peaceful, and they spawn with a random chance of 1/11.
     *
     * @param zephyr The {@link Zephyr} {@link EntityType}.
     * @param level  The {@link LevelAccessor}.
     * @param reason The {@link MobSpawnType} reason.
     * @param pos    The spawn {@link BlockPos}.
     * @param random The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkZephyrSpawnRules(EntityType<? extends Zephyr> zephyr, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return reason == MobSpawnType.SPAWNER || level.canSeeSky(pos) && level.getDifficulty() != Difficulty.PEACEFUL && level.getBlockState(pos.below()).is(AetherIITags.Blocks.AERCLOUDS);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    /**
     * Handles values used for the Zephyr's animation and removing the Zephyr if it goes below or above the build height.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getY() < this.level().getMinBuildHeight() - 2 || this.getY() > this.level().getMaxBuildHeight()) {
            this.discard();
        }
        if (this.level().isClientSide()) {
            if (this.isBlow()) {
                Vec3 look = this.getViewVector(1.0F);
                this.level().addParticle(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(), this.getX(), this.getY() + 0.35F, this.getZ(), look.x * 1.5F + random.nextFloat() * 0.1F, look.y * 1.5F + random.nextFloat() * 0.1F, look.z * 1.5F + random.nextFloat() * 0.1F);
            }
        }

        if (this.isBlow()) {
            Vec3 look = this.getViewVector(1.0F);
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(5, 0, 5).expandTowards(0, -2, 0).move(look.scale(6.5F)), entity -> entity != this);

            list.forEach(entity -> entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(0.2F).add(0, 0.05F, 0))));
        }
    }

    public int getChargeTime() {
        return this.getEntityData().get(DATA_CHARGE_TIME_ID);
    }

    public void setChargeTime(int chargeTime) {
        this.getEntityData().set(DATA_CHARGE_TIME_ID, chargeTime);
    }

    public boolean isBlow() {
        return this.getEntityData().get(DATA_BLOW_ID);
    }

    public void setBlow(boolean blow) {
        this.getEntityData().set(DATA_BLOW_ID, blow);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_ZEPHYR_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_ZEPHYR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_ZEPHYR_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastLookGoal}.
     */
    protected static class ZephyrLookGoal extends Goal {
        private final Zephyr zephyr;

        public ZephyrLookGoal(Zephyr zephyr) {
            this.zephyr = zephyr;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (this.zephyr.getTarget() == null) {
                Vec3 vec3d = this.zephyr.getDeltaMovement();
                this.zephyr.setYRot(-((float) Mth.atan2(vec3d.x(), vec3d.z())) * Mth.RAD_TO_DEG);
                this.zephyr.yBodyRot = this.zephyr.getYRot();
            } else {
                LivingEntity livingEntity = this.zephyr.getTarget();
                if (livingEntity.distanceToSqr(this.zephyr) < 4096.0) {
                    double x = livingEntity.getX() - this.zephyr.getX();
                    double z = livingEntity.getZ() - this.zephyr.getZ();
                    this.zephyr.setYRot(-((float) Mth.atan2(x, z)) * Mth.RAD_TO_DEG);
                    this.zephyr.setYBodyRot(this.zephyr.getYRot());
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastMoveControl}.
     */
    protected static class ZephyrMoveControl extends MoveControl {
        private final Zephyr zephyr;
        private int floatDuration;

        public ZephyrMoveControl(Zephyr zephyr) {
            super(zephyr);
            this.zephyr = zephyr;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.zephyr.getRandom().nextInt(5) + 2;
                    Vec3 vec3d = new Vec3(this.wantedX - this.zephyr.getX(), this.wantedY - this.zephyr.getY(), this.wantedZ - this.zephyr.getZ());
                    double d0 = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.canReach(vec3d, Mth.ceil(d0))) {
                        this.zephyr.setDeltaMovement(this.zephyr.getDeltaMovement().add(vec3d.scale(0.1)));
                    } else {
                        this.operation = Operation.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vec3 pos, int distance) {
            AABB axisalignedbb = this.zephyr.getBoundingBox();
            for (int i = 1; i < distance; ++i) {
                axisalignedbb = axisalignedbb.move(pos);
                if (!this.zephyr.level().noCollision(this.zephyr, axisalignedbb)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.GhastShootFireballGoal}.
     */
    protected static class ZephyrShootSnowballGoal extends Goal {
        private final Zephyr zephyr;

        public ZephyrShootSnowballGoal(Zephyr zephyr) {
            this.zephyr = zephyr;
        }

        @Override
        public boolean canUse() {
            return zephyr.getTarget() != null;
        }

        @Override
        public void start() {
            this.zephyr.setChargeTime(0);

        }

        @Override
        public void stop() {
            this.zephyr.setChargeTime(0);
            this.zephyr.setBlow(false);
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.zephyr.getTarget();
            if (livingEntity != null) {
                //if slow down applied. star blow
                if (livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) && livingEntity.distanceToSqr(this.zephyr) < 76 && this.zephyr.hasLineOfSight(livingEntity) && !this.zephyr.isBlow()) {
                    Level level = this.zephyr.level();
                    this.zephyr.setChargeTime(this.zephyr.getChargeTime() + 1);
                    if (this.zephyr.getChargeTime() == 10) {
                        if (this.zephyr.getAmbientSound() != null) {
                            this.zephyr.playSound(this.zephyr.getAmbientSound(), this.zephyr.getSoundVolume(), (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
                        }
                    } else if (this.zephyr.getChargeTime() == 1) {
                        this.zephyr.level().broadcastEntityEvent(this.zephyr, (byte) 61);
                    } else if (this.zephyr.getChargeTime() == 25) {
                        this.zephyr.setBlow(true);
                    }
                    //if not slow. shooting snowball
                } else if (!livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    this.zephyr.setBlow(false);
                    if (livingEntity.distanceToSqr(this.zephyr) < 1600.0 && this.zephyr.hasLineOfSight(livingEntity)) {
                        Level level = this.zephyr.level();
                        this.zephyr.setChargeTime(this.zephyr.getChargeTime() + 1);
                        if (this.zephyr.getChargeTime() == 10) {
                            if (this.zephyr.getAmbientSound() != null) {
                                this.zephyr.playSound(this.zephyr.getAmbientSound(), this.zephyr.getSoundVolume(), (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
                            }
                        } else if (this.zephyr.getChargeTime() == 13) {
                            this.zephyr.level().broadcastEntityEvent(this.zephyr, (byte) 62);
                        } else if (this.zephyr.getChargeTime() == 20) {
                            Vec3 look = this.zephyr.getViewVector(1.0F);
                            double accelX = livingEntity.getX() - (this.zephyr.getX() + look.x() * 1.5);
                            double accelY = livingEntity.getY() - (this.zephyr.getY() + 0.35);
                            double accelZ = livingEntity.getZ() - (this.zephyr.getZ() + look.z() * 1.5);
                            this.zephyr.playSound(AetherIISoundEvents.ENTITY_ZEPHYR_SHOOT.get(), this.zephyr.getSoundVolume(), (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.2F + 1.0F);
                            ZephyrWebbingBall snowball = new ZephyrWebbingBall(level, this.zephyr, accelX, accelY, accelZ);
                            snowball.setPos(this.zephyr.getX() + look.x() * 1.55, this.zephyr.getY() + 0.35, this.zephyr.getZ() + look.z() * 1.55);
                            level.addFreshEntity(snowball);
                            this.zephyr.setChargeTime(-40);
                        }
                    } else if (this.zephyr.getChargeTime() > 0) {
                        this.zephyr.setChargeTime(this.zephyr.getChargeTime() - 1);
                    }
                } else {
                    if (this.zephyr.getChargeTime() > 0) {
                        this.zephyr.setChargeTime(this.zephyr.getChargeTime() - 1);
                    } else if (this.zephyr.getChargeTime() <= 0) {
                        this.zephyr.setBlow(false);
                    }

                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.entity.monster.Ghast.RandomFloatAroundGoal}.
     */
    protected static class RandomFloatAroundGoal extends Goal {
        private final Zephyr zephyr;

        public RandomFloatAroundGoal(Zephyr zephyr) {
            this.zephyr = zephyr;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.zephyr.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double d0 = moveControl.getWantedX() - this.zephyr.getX();
                double d1 = moveControl.getWantedY() - this.zephyr.getY();
                double d2 = moveControl.getWantedZ() - this.zephyr.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0 || d3 > 3600.0;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity livingEntity = this.zephyr.getTarget();
            if (livingEntity != null && livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                RandomSource random = this.zephyr.getRandom();
                double d0 = livingEntity.getX() + (random.nextFloat() * 2.0F - 1.0F) * 8.0F;
                double d1 = livingEntity.getY() + (random.nextFloat() * 2.0F - 1.0F) * 4.0F;
                double d2 = livingEntity.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 8.0F;
                this.zephyr.getMoveControl().setWantedPosition(d0, d1, d2, 1.2);

            } else {
                RandomSource random = this.zephyr.getRandom();
                double d0 = this.zephyr.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                double d1 = this.zephyr.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                double d2 = this.zephyr.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                this.zephyr.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
            }
        }
    }
}

package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.ai.controller.FlyingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.FlyingLookGoal;
import com.aetherteam.aetherii.entity.projectile.ZephyrWebbingBall;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class Zephyr extends PathfinderMob implements Enemy {
    public static int BLOW_ATTACK_EVENT = 100;
    public static int SHOOT_ATTACK_EVENT = 101;

    private static final EntityDataAccessor<Integer> DATA_PROJECTILE_CHARGE_ID = SynchedEntityData.defineId(Zephyr.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_BLOW_CHARGE_ID = SynchedEntityData.defineId(Zephyr.class, EntityDataSerializers.INT);

    public AnimationState blowAnimationState = new AnimationState();
    public AnimationState webAnimationState = new AnimationState();

    public Zephyr(EntityType<? extends Zephyr> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new ZephyrBlowAwayGoal(this, 8));
        this.goalSelector.addGoal(5, new ZephyrShootSnowballGoal(this, 8, 40));
        this.goalSelector.addGoal(6, new RandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new FlyingLookGoal(this));
        this.targetSelector.addGoal(1, new ZephyrNearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_PROJECTILE_CHARGE_ID, -40);
        builder.define(DATA_BLOW_CHARGE_ID, -40);
    }

    /**
     * Zephyrs can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, EntitySpawnReason, BlockPos, RandomSource)} is true, if they are spawning in view of the sky,
     * if the difficulty isn't peaceful, and they spawn with a random chance of 1/11.
     *
     * @param zephyr The {@link Zephyr} {@link EntityType}.
     * @param level  The {@link LevelAccessor}.
     * @param reason The {@link EntitySpawnReason} reason.
     * @param pos    The spawn {@link BlockPos}.
     * @param random The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkZephyrSpawnRules(EntityType<? extends Zephyr> zephyr, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && (reason != EntitySpawnReason.NATURAL || random.nextInt(11) == 0)
                && level.canSeeSky(pos)
                && !inRadiusOfCampfire(level, pos, 24, 48);
    }

    /**
     * Checks whether a Zephyr has a campfire, a campfire with a signal fire or an outpost campfire within its radius.
     *
     * @param level  The {@link LevelAccessor} to check in.
     * @param pos    The starting {@link BlockPos}.
     * @param radius The {@link Integer} radius around the position.
     * @return Whether the blocks were found in the radius, as a {@link Boolean}.
     */
    public static boolean inRadiusOfCampfire(LevelAccessor level, BlockPos pos, int radius, int radiusExtended) {
        for (ChunkPos chunk : ChunkPos.rangeClosed(new ChunkPos(pos), radiusExtended).toList()) {
            ChunkAccess chunkAccess = level.getChunk(chunk.x, chunk.z, ChunkStatus.FULL, false);
            if (chunkAccess != null) {
                for (BlockPos blockEntityPos : chunkAccess.getBlockEntitiesPos()) {
                    if (blockEntityPos.distSqr(pos) <= radius * radius) {
                        BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
                        if (blockEntity != null) {
                            BlockState state = blockEntity.getBlockState();
                            if (state.is(AetherIITags.Blocks.WEAK_ZEPHYR_SPAWNABLE_DETERRENT) && state.getValue(BlockStateProperties.LIT)) {
                                return true;
                            }
                        }
                    } else if (blockEntityPos.distSqr(pos) <= radiusExtended * radiusExtended) {
                        BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
                        if (blockEntity != null) {
                            BlockState state = blockEntity.getBlockState();
                            if ((state.is(AetherIITags.Blocks.WEAK_ZEPHYR_SPAWNABLE_DETERRENT) && level.getBlockState(blockEntityPos.below()).is(AetherIIBlocks.BRETTL_GRASS_BUNDLE)) || state.is(AetherIITags.Blocks.STRONG_ZEPHYR_SPAWNABLE_DETERRENT)) {
                                if (state.getValue(BlockStateProperties.LIT)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == BLOW_ATTACK_EVENT) {
            this.webAnimationState.stop();
            this.blowAnimationState.start(this.tickCount);
        } else if (id == SHOOT_ATTACK_EVENT) {
            this.blowAnimationState.stop();
            this.webAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void travel(Vec3 p_415638_) {
        this.travelFlying(p_415638_, 0.02F);
    }

    public boolean isPosNearNearestRepellent(Zephyr zephyr, BlockPos pos) {
        if (level() instanceof ServerLevel serverLevel) {
            Optional<BlockPos> optional = findNearestRepellent(serverLevel, zephyr);
            return optional.isPresent() && optional.get().closerThan(pos, 16.0);
        }
        return false;
    }

    public Optional<BlockPos> findNearestRepellent(ServerLevel serverLevel, Zephyr zephyr) {
        return BlockPos.findClosestMatch(zephyr.blockPosition(), 16, 12, pos ->
                serverLevel.getBlockState(pos).is(AetherIITags.Blocks.ZEPHYR_REPELLENT)
                && serverLevel.getBlockState(pos).getValue(BlockStateProperties.LIT) != null
                && serverLevel.getBlockState(pos).getValue(BlockStateProperties.LIT));
    }


    public void repellingBehavior(Zephyr zephyr) {
        if (zephyr.level() instanceof ServerLevel serverLevel) {
            Optional<BlockPos> optional = zephyr.findNearestRepellent(serverLevel, zephyr);
            if (optional.isPresent()) {
                Vec3 avoidPos = new Vec3(optional.get().getX(), optional.get().getY(), optional.get().getZ());

                Vec3 vec3 = HoverRandomPos.getPos(zephyr, 24, 20, avoidPos.x, avoidPos.z, (float) (Math.PI / 2), 3, 1);
                if ((vec3 != null) && !(avoidPos.distanceToSqr(vec3.x, vec3.y, vec3.z) < avoidPos.distanceToSqr(zephyr.position()))) {
                    zephyr.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0);
                }
            }
        }
    }

    /**
     * Handles values used for the Zephyr's animation and removing the Zephyr if it goes below or above the build height.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getBlowChargeTime() >= 25 && this.getBlowChargeTime() < 50) {
            Vec3 look = this.getViewVector(1.0F);
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(5, 0, 5).expandTowards(0, -2, 0).move(look.scale(10.5F)), entity -> entity != this && !entity.getType().is(AetherIITags.Entities.ZEPHYR_BLOW_BLACKLIST));
            list.forEach(entity -> {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(AetherIITags.Items.SENTRY_ARMOR)) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(0.05F)));
                    } else if (livingEntity.hasEffect(AetherIIEffects.WEBBED)) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(1.2F).add(0, 0.05F, 0)));
                    } else {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(0.2F).add(0, 0.05F, 0)));
                    }
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(0.2F).add(0, 0.05F, 0)));
                }
            });

            if (this.level().isClientSide()) {
                this.level().addParticle(AetherIIParticleTypes.ZEPHYR_SNOWFLAKE.get(),
                        this.getX(), this.getY(0.45F), this.getZ(),
                        look.x * 1.5F + this.getRandom().nextFloat() * 0.1F,
                        look.y * 1.5F + this.getRandom().nextFloat() * 0.1F,
                        look.z * 1.5F + this.getRandom().nextFloat() * 0.1F);
            }
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return true;
    }

    public int getProjectileChargeTime() {
        return this.getEntityData().get(DATA_PROJECTILE_CHARGE_ID);
    }

    public void setProjectileChargeTime(int chargeTime) {
        this.getEntityData().set(DATA_PROJECTILE_CHARGE_ID, chargeTime);
    }

    public int getBlowChargeTime() {
        return this.getEntityData().get(DATA_BLOW_CHARGE_ID);
    }

    public void setBlowChargeTime(int chargeTime) {
        this.getEntityData().set(DATA_BLOW_CHARGE_ID, chargeTime);
    }

    @Override
    protected float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 450;
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
    public boolean onClimbable() {
        return false;
    }

    protected static class ZephyrBlowAwayGoal extends Goal {
        private final Zephyr zephyr;
        private final float attackThreshold;
        private final float attackThresholdSqr;
        private LivingEntity trackedTarget;

        public ZephyrBlowAwayGoal(Zephyr zephyr, float attackThreshold) {
            this.zephyr = zephyr;
            this.attackThreshold = attackThreshold;
            this.attackThresholdSqr = attackThreshold * attackThreshold;
        }

        @Override
        public boolean canUse() {
            return this.zephyr.getTarget() != null
                    && this.zephyr.getTarget().isAlive()
                    && this.zephyr.distanceToSqr(this.zephyr.getTarget()) < this.attackThresholdSqr
                    && this.zephyr.getProjectileChargeTime() == -40
                    && !this.zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition());
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null && this.zephyr.getProjectileChargeTime() == -40 && !this.zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition());
        }

        @Override
        public void start() {
            this.zephyr.setBlowChargeTime(-40);
            this.trackedTarget = this.zephyr.getTarget();
        }

        @Override
        public void stop() {
            this.zephyr.setBlowChargeTime(-40);
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                boolean canSee = this.zephyr.hasLineOfSight(this.trackedTarget);

                this.zephyr.setBlowChargeTime(this.zephyr.getBlowChargeTime() + 1);

                if (this.zephyr.distanceTo(this.trackedTarget) < this.attackThreshold) {
                    if (this.zephyr.isPosNearNearestRepellent(this.zephyr, this.zephyr.blockPosition())) {
                        this.zephyr.repellingBehavior(zephyr);
                    } else {
                        double d0 = this.zephyr.getX() + (this.zephyr.getRandom().nextFloat() * 2.0F - 1.0F) * 4.0F;
                        double d2 = this.zephyr.getZ() + (this.zephyr.getRandom().nextFloat() * 2.0F - 1.0F) * 4.0F;
                        this.zephyr.getMoveControl().setWantedPosition(d0, this.trackedTarget.getY(), d2, 0.1);
                    }
                }

                if (this.zephyr.getBlowChargeTime() == 1) {
                    this.zephyr.level().broadcastEntityEvent(this.zephyr, (byte) BLOW_ATTACK_EVENT);

                } else if (this.zephyr.getBlowChargeTime() == 10) {
                    if (this.zephyr.getAmbientSound() != null) {
                        this.zephyr.playSound(this.zephyr.getAmbientSound(), this.zephyr.getSoundVolume(), (this.zephyr.getRandom().nextFloat() - this.zephyr.getRandom().nextFloat()) * 0.2F + 1.0F);
                    }
                } else if (this.zephyr.getBlowChargeTime() == 60) {
                    this.zephyr.setBlowChargeTime(-40);

                    if (!canSee || this.zephyr.getTarget() == null || !this.zephyr.getTarget().isAlive() || this.zephyr.distanceToSqr(this.trackedTarget) >= this.attackThresholdSqr) {
                        this.trackedTarget = null;
                    }
                }
            }
        }
    }

    protected static class ZephyrShootSnowballGoal extends Goal {
        private final Zephyr zephyr;
        private final float attackThresholdSqr;
        private final float attackFarLimitSqr;
        private LivingEntity trackedTarget;

        public ZephyrShootSnowballGoal(Zephyr zephyr, float attackThreshold, float attackFarLimit) {
            this.zephyr = zephyr;
            this.attackThresholdSqr = attackThreshold * attackThreshold;
            this.attackFarLimitSqr = attackFarLimit * attackFarLimit;
        }

        @Override
        public boolean canUse() {
            return this.zephyr.getTarget() != null
                    && this.zephyr.getTarget().isAlive()
                    && this.zephyr.distanceToSqr(this.zephyr.getTarget()) >= this.attackThresholdSqr
                    && this.zephyr.distanceToSqr(this.zephyr.getTarget()) < this.attackFarLimitSqr
                    && this.zephyr.getBlowChargeTime() == -40
                    && !this.zephyr.getTarget().hasEffect(AetherIIEffects.WEBBED)
                    && !this.zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition());
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null && this.zephyr.getBlowChargeTime() == -40 && !this.zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition());
        }

        @Override
        public void start() {
            this.zephyr.setProjectileChargeTime(-40);
            this.trackedTarget = this.zephyr.getTarget();
        }

        @Override
        public void stop() {
            this.zephyr.setProjectileChargeTime(-40);
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                this.zephyr.setProjectileChargeTime(this.zephyr.getProjectileChargeTime() + 1);

                if (this.zephyr.distanceToSqr(this.trackedTarget) < this.attackThresholdSqr) {
                    double d0 = this.zephyr.getX() + (this.zephyr.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F;
                    double d1 = this.zephyr.getY() + (this.zephyr.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F;
                    double d2 = this.zephyr.getZ() + (this.zephyr.getRandom().nextFloat() * 2.0F - 1.0F) * 16.0F;
                    this.zephyr.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
                }

                if (this.zephyr.getProjectileChargeTime() == 10) {
                    if (this.zephyr.getAmbientSound() != null) {
                        this.zephyr.playSound(this.zephyr.getAmbientSound(), this.zephyr.getSoundVolume(), (this.zephyr.getRandom().nextFloat() - this.zephyr.getRandom().nextFloat()) * 0.2F + 1.0F);
                    }
                } else if (this.zephyr.getProjectileChargeTime() == 13) {
                    this.zephyr.level().broadcastEntityEvent(this.zephyr, (byte) SHOOT_ATTACK_EVENT);

                } else if (this.zephyr.getProjectileChargeTime() == 20) {
                    Vec3 look = this.zephyr.getViewVector(1.0F);
                    double accelX = this.trackedTarget.getX() - (this.zephyr.getX() + look.x() * 1.5);
                    double accelY = this.trackedTarget.getY() - (this.zephyr.getY() + 0.35);
                    double accelZ = this.trackedTarget.getZ() - (this.zephyr.getZ() + look.z() * 1.5);
                    this.zephyr.playSound(AetherIISoundEvents.ENTITY_ZEPHYR_SHOOT.get(), this.zephyr.getSoundVolume(), (this.zephyr.getRandom().nextFloat() - this.zephyr.getRandom().nextFloat()) * 0.2F + 1.0F);
                    ZephyrWebbingBall snowball = new ZephyrWebbingBall(this.zephyr.level(), this.zephyr, accelX, accelY, accelZ);
                    snowball.setPos(this.zephyr.getX() + look.x() * 1.55, this.zephyr.getY() + 0.35, this.zephyr.getZ() + look.z() * 1.55);
                    this.zephyr.level().addFreshEntity(snowball);
                    this.zephyr.setProjectileChargeTime(-40);

                    if (!this.zephyr.hasLineOfSight(this.trackedTarget)
                            || this.zephyr.getTarget() == null
                            || !this.zephyr.getTarget().isAlive()
                            || this.zephyr.distanceToSqr(this.trackedTarget) < this.attackThresholdSqr
                            || this.zephyr.distanceToSqr(this.zephyr.getTarget()) >= this.attackFarLimitSqr
                            || this.zephyr.getTarget().hasEffect(AetherIIEffects.WEBBED)) {
                        this.trackedTarget = null;
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
            if (this.zephyr.isPosNearNearestRepellent(this.zephyr, this.zephyr.blockPosition())) {
                this.zephyr.repellingBehavior(zephyr);
            } else {
                LivingEntity target = this.zephyr.getTarget();
                RandomSource random = this.zephyr.getRandom();
                if (this.zephyr.isPosNearNearestRepellent(this.zephyr, zephyr.blockPosition())) {
                    this.zephyr.setTarget(null);
                }
                if (target == null) {
                    aimlessWandering(random);
                } else if ((this.zephyr.getProjectileChargeTime() == -40 && this.zephyr.getRandom().nextInt(6) != 0) || target.hasEffect(AetherIIEffects.WEBBED)) {
                    Vec3 goal = target.position().offsetRandom(random, 12.0F);

                    this.zephyr.getMoveControl().setWantedPosition(goal.x(), target.getY() + (random.nextFloat() * 2.0F - 1.0F), goal.z(), 1.0);

                } else if (this.zephyr.getBlowChargeTime() == -40 && !target.hasEffect(AetherIIEffects.WEBBED)) {
                    Vec3 goal = target.position().offsetRandom(random, 24.0F);

                    this.zephyr.getMoveControl().setWantedPosition(goal.x(), target.getY() + (random.nextFloat() * 2.0F - 1.0F) * 6.0F, goal.z(), 1.5);
                }
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void aimlessWandering(RandomSource random) {
            if (this.zephyr.isPosNearNearestRepellent(this.zephyr, this.zephyr.blockPosition())) {
                this.zephyr.repellingBehavior(zephyr);
            } else {
                double d0 = this.zephyr.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                double d1 = this.zephyr.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                double d2 = this.zephyr.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
                this.zephyr.getMoveControl().setWantedPosition(d0, d1, d2, 1.0);
            }
        }
    }

    public static class ZephyrNearestAttackableTargetGoal<T extends LivingEntity> extends TargetGoal {
        private static final int DEFAULT_RANDOM_INTERVAL = 10;
        protected final Class<T> targetType;
        protected final int randomInterval;
        protected @Nullable LivingEntity target;
        protected TargetingConditions targetConditions;

        public ZephyrNearestAttackableTargetGoal(Zephyr zephyr, Class<T> p_26065_, boolean p_26066_, boolean p_26067_) {
            this(zephyr, p_26065_, DEFAULT_RANDOM_INTERVAL, p_26066_, p_26067_, null);
        }

        public ZephyrNearestAttackableTargetGoal(Zephyr zephyr, Class<T> p_26054_, int p_26055_, boolean p_26056_, boolean p_26057_, TargetingConditions.@Nullable Selector p_376600_) {
            super(zephyr, p_26056_, p_26057_);
            this.targetType = p_26054_;
            this.randomInterval = reducedTickDelay(p_26055_);
            this.setFlags(EnumSet.of(Flag.TARGET));
            this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(p_376600_);
        }

        public boolean canUse() {
            if (this.mob instanceof Zephyr zephyr) {
                if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0 && !(zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition()))) {
                    return false;
                } else {
                    this.findTarget();
                    return this.target != null;
                }
            }
            return this.target != null;
        }

        protected AABB getTargetSearchArea(double p_26069_) {
            return this.mob.getBoundingBox().inflate(p_26069_, p_26069_, p_26069_);
        }

        protected void findTarget() {
            ServerLevel serverlevel = getServerLevel(this.mob);
                if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
                    this.target = serverlevel.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> true), this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                } else {
                    this.target = serverlevel.getNearestPlayer(this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                }
        }

        public void start() {
            if (this.mob instanceof Zephyr zephyr && !(zephyr.isPosNearNearestRepellent(zephyr, zephyr.blockPosition()))) {
                this.mob.setTarget(this.target);
                super.start();
            }
        }

        public void setTarget(@Nullable LivingEntity p_26071_) {
            this.target = p_26071_;
        }

        private TargetingConditions getTargetConditions() {
            return this.targetConditions.range(this.getFollowDistance());
        }
    }
}
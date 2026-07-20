package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class Aerwhale extends PathfinderMob {

    public static final List<Vec3> ENTITY_ATTACHMENT_POINT = ImmutableList.of(new Vec3(0.0, 0.5, 0.5));
    public static final List<Vec3> LEASHER_ATTACHMENT_POINT = ImmutableList.of(new Vec3(0.0, 0.5, 0.0));
    public static final List<Vec3> SHARED_QUAD_ATTACHMENT_POINTS = ImmutableList.of(
            new Vec3(-0.5, 0.5, 0.5), new Vec3(-0.5, 0.5, -0.5), new Vec3(0.5, 0.5, -0.5), new Vec3(0.5, 0.5, 0.5)
    );

    public Aerwhale(EntityType<? extends Aerwhale> type, Level level) {
        super(type, level);
        this.moveControl = new AerwhaleMoveControl(this, true);
        this.lookControl = new AerwhaleLookControl(this);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        var navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        return navigation;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(5, new Aerwhale.RandomFloatAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FLYING_SPEED, 0.2)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.CAMERA_DISTANCE, 8.0)
                .add(Attributes.STEP_HEIGHT, 0.4);
    }

    /**
     * Aerwhales can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} is true, if they aren't spawning in fluid,
     * if they are spawning at a light level above 8, if they are spawning in view of the sky, and they spawn with a random chance of 1/40.
     *
     * @param aerwhale The {@link Aerwhale} {@link EntityType}.
     * @param level    The {@link LevelAccessor}.
     * @param reason   The {@link EntitySpawnReason} reason.
     * @param pos      The spawn {@link BlockPos}.
     * @param random   The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkAerwhaleSpawnRules(EntityType<? extends Aerwhale> aerwhale, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getFluidState(pos).is(Fluids.EMPTY)
                && level.getRawBrightness(pos, 0) > 8
                && level.canSeeSky(pos)
                && (reason != EntitySpawnReason.NATURAL || random.nextInt(40) == 0);
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    /**
     * The purpose of this method override is to fix the weird movement from flying mobs.
     *
     * @param vector The {@link Vec3} for travel movement.
     */
    @Override
    public void travel(Vec3 vector) {
        float speed = (float)this.getAttributeValue(Attributes.FLYING_SPEED) * 5.0f / 3.0f;
        this.travelFlying(vector, speed, speed, speed);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (!level.isEmptyBlock(pos)) {
            return 0.0f;
        } else {
            var mutablepos = pos.mutable();
            for (int i = 0; i < 10; i++) {
                if (!level.isEmptyBlock(mutablepos.move(Direction.DOWN))) {
                    return 5.0f;
                }
            }
            return !level.isEmptyBlock(mutablepos.move(Direction.DOWN)) ? 10.0f : 5.0f;
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    public float getVoicePitch() {
        return 1.0f;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 1200;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_AERWHALE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherIISoundEvents.ENTITY_AERWHALE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_AERWHALE_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 2.0F;
    }

    /**
     * [CODE COPY] - {@link Animal#getBaseExperienceReward()}.
     */
    @Override
    public int getBaseExperienceReward(ServerLevel level) {
        return 1 + this.random.nextInt(3);
    }
    
    /**
     * [CODE COPY] - {@link PathfinderMob#shouldStayCloseToLeashHolder()}.
     */
    protected boolean shouldStayCloseToLeashHolder() {
        return false;
    }

    /**
     * [CODE COPY] - {@link PathfinderMob#followLeashSpeed()}.
     */
    protected double followLeashSpeed() {
        return 1.0;
    }

    @Override
    public double leashElasticDistance() {
        return 10.0;
    }

    @Override
    public double leashSnapDistance() {
        return 16.0;
    }

    @Override
    public boolean supportQuadLeashAsHolder() {
        return true;
    }

    @Override
    public boolean supportQuadLeash() {
        return true;
    }

    @Override
    public Vec3 getLeashOffset() {
        Entity entity = (Entity) this;
        return new Vec3(0.0, entity.getEyeHeight(), 0);
    }

    @Override
    public Vec3[] getQuadLeashHolderOffsets() {
        return createQuadLeashOffsetsWithRotate(this, 0.0F, 0.35F, 0.35F, 0.5F);
    }

    public Vec3[] getQuadLeashOffsets() {
        return createQuadLeashOffsetsWithRotate(this, 0.0F, 0.35F, 0.35F, 0.7F);
    }

    private Vec3[] createQuadLeashOffsetsWithRotate(Entity entity, double zOffset, double z, double x, double y) {
        float f = entity.getBbWidth();
        double d0 = zOffset * (double) f;
        double d1 = z * (double) f;
        double d2 = x * (double) f;
        double d3 = y * (double) entity.getBbHeight();
        double lookAngleY = entity.getViewVector(1.0F).y;

        return new Vec3[]{new Vec3(-d2, d3 + lookAngleY, d1 + d0), new Vec3(-d2, d3 - lookAngleY, -d1 + d0), new Vec3(d2, d3 + lookAngleY, -d1 + d0), new Vec3(d2, d3 - lookAngleY, d1 + d0)};
    }


    @Override
    public void onElasticLeashPull() {
        super.onElasticLeashPull();
        this.getMoveControl().setWait();
    }

    @Override
    public boolean isFlyingVehicle() {
        return true;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public static void faceMovementDirection(Mob mob) {
        faceMovementDirection(mob, mob.getDeltaMovement());
    }

    public static void faceMovementDirection(Mob mob, Vec3 motion) {        
        float xRotTarget = Mth.clamp(Mth.wrapDegrees(-((float)Mth.atan2(motion.y, Math.hypot(motion.x, motion.z))) * Mth.RAD_TO_DEG), -90.0F, 90.0F);
        float xRot = mob.getXRot();
        mob.setXRot(Mth.approachDegrees(xRot, xRotTarget, 2F));
        
        float yRotTarget = Mth.wrapDegrees(-((float)Mth.atan2(motion.x, motion.z)) * Mth.RAD_TO_DEG - 90.0F);
        float yRot = mob.getYRot();
        mob.setYRot(Mth.approachDegrees(yRot, yRotTarget, 5F));
        mob.yHeadRot = mob.yBodyRot = mob.getYRot();
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new AerwhaleBodyRotationControl(this);
    }

    public static class AerwhaleBodyRotationControl extends BodyRotationControl {
        private final Mob whale;

        public AerwhaleBodyRotationControl(Mob whale) {
            super(whale);
            this.whale = whale;
        }

        @Override
        public void clientTick() {
            this.whale.yHeadRot = this.whale.yBodyRot = this.whale.getYRot() + 90.0f;
            super.clientTick();
        }
    }

    /**
     * Sets the next position that the Aerwhale should travel to.
     * 
     * [CODE COPY] - {@link Ghast.RandomFloatAroundGoal}
     */
    public static class RandomFloatAroundGoal extends Goal {
        private static final int MAX_ATTEMPTS = 64;
        private final Mob whale;
        private final int distanceToBlocks;

        public RandomFloatAroundGoal(Mob whale) {
            this(whale, 0);
        }

        public RandomFloatAroundGoal(Mob whale, int distanceToBlocks) {
            this.whale = whale;
            this.distanceToBlocks = distanceToBlocks;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            MoveControl movecontrol = this.whale.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.whale.getX();
                double d1 = movecontrol.getWantedY() - this.whale.getY();
                double d2 = movecontrol.getWantedZ() - this.whale.getZ();
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
            Vec3 vec3 = getSuitableFlyToPosition(this.whale, this.distanceToBlocks);
            if (vec3 != null) {
                this.whale.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0);
            }
        }

        /**
         * [CODE COPY] - {@link Ghast.RandomFloatAroundGoal#getSuitableFlyToPosition}
         */
        @Nullable
        public static Vec3 getSuitableFlyToPosition(Mob mob, int distanceToBlocks) {
            var level = mob.level();
            var random = mob.getRandom();
            Vec3 pos = mob.position();
            Vec3 newPos;

            int i = 0;
            do {
                newPos = chooseRandomPositionWithRestriction(mob, pos, random);
                if (newPos != null && isGoodTarget(level, newPos, distanceToBlocks)) {
                    return newPos;
                }
            } while (++i < MAX_ATTEMPTS);

            if (newPos != null) {
                newPos = chooseRandomPosition(level, pos, random);
            }

            return newPos;
        }

        /**
         * [CODE COPY] - {@link Ghast.RandomFloatAroundGoal#isGoodTarget}
         */
        private static boolean isGoodTarget(Level level, Vec3 pos, int distanceToBlocks) {
            if (distanceToBlocks <= 0) {
                return true;
            } else {
                BlockPos blockpos = BlockPos.containing(pos);
                if (!level.getBlockState(blockpos).isAir()) {
                    return false;
                } else {
                    for (Direction direction : Direction.values()) {
                        for (int i = 1; i < distanceToBlocks; i++) {
                            BlockPos blockpos1 = blockpos.relative(direction, i);
                            if (!level.getBlockState(blockpos1).isAir()) {
                                return true;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        private static Vec3 chooseRandomPosition(Level level, Vec3 pos, RandomSource random) {
            double d0 = pos.x + increaseBy32((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = Math.clamp(pos.y + (random.nextFloat() * 2.0F - 1.0F) * 16.0F, level.getMinY(), level.getMaxY());
            double d2 = pos.z + increaseBy32((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            return new Vec3(d0, d1, d2);
        }

        private static float increaseBy32(float f) {
            return f + (f >= 0.0f ? 32.0f : -32.0f);
        }

        /**
         * [CODE COPY] - {@link Ghast.RandomFloatAroundGoal#chooseRandomPositionWithRestriction}
         */
        @Nullable
        private static Vec3 chooseRandomPositionWithRestriction(Mob mob, Vec3 pos, RandomSource random) {
            Vec3 vec3 = chooseRandomPosition(mob.level(), pos, random);
            return mob.hasHome() && !mob.isWithinHome(vec3) ? null : vec3;
        }
    }

    /**
     * Custom Aerwhale move controller to help with keeping a smooth travel course.
     */
    public static class AerwhaleMoveControl extends MoveControl {
        private final Mob whale;
        private final boolean careful;

        public AerwhaleMoveControl(Mob whale, boolean careful) {
            super(whale);
            this.whale = whale;
            this.careful = careful;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - this.whale.getX(), this.wantedY - this.whale.getY(), this.wantedZ - this.whale.getZ());
                if (this.canReach(vec3)) {
                    double speed = this.whale.getAttributeValue(Attributes.FLYING_SPEED) * 0.05;
                    this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(vec3.normalize().scale(speed)));
                    Aerwhale.faceMovementDirection(this.whale, vec3);
                } else {
                    this.operation = MoveControl.Operation.WAIT;
                }
            }
        }

        public Vec3 extendVecForReach(Vec3 delta) {
            double x = delta.x;
            double y = delta.y;
            double z = delta.z;

            if (x > 0) {
                ++x;
            } else if (x < 0) {
                --x;
            }
            if (y > 0) {
                ++y;
            } else if (y < 0) {
                --y;
            }
            if (z > 0) {
                ++z;
            } else if (z < 0) {
                --z;
            }
            return new Vec3(x, y, z);
        }

        /**
         * [CODE COPY] - {@link Ghast.GhastMoveControl#canReach(Vec3)}
         */
        private boolean canReach(Vec3 delta) {
            AABB aabb = this.mob.getBoundingBox();
            AABB aabb1 = aabb.move(extendVecForReach(delta));

            if (this.careful) {
                for (BlockPos blockpos : BlockPos.betweenClosed(aabb1)) {
                    if (!this.blockTraversalPossible(this.whale.level(), null, null, blockpos, false, false)) {
                        return false;
                    }
                }
            }

            boolean flag = this.whale.isInWater();
            boolean flag1 = this.whale.isInLava();
            Vec3 vec3 = this.whale.position();
            Vec3 vec31 = vec3.add(delta);
            return BlockGetter.forEachBlockIntersectedBetween(
                vec3,
                vec31,
                aabb1,
                    (pos, index) -> aabb.intersects(pos) || this.blockTraversalPossible(this.whale.level(), vec3, vec31, pos, flag, flag1)
            );
        }

        /**
         * [CODE COPY] - {@link Ghast.GhastMoveControl#blockTraversalPossible(BlockGetter, Vec3, Vec3, BlockPos, boolean, boolean)}
         */
        private boolean blockTraversalPossible(
            BlockGetter level, @Nullable Vec3 from, @Nullable Vec3 to, BlockPos pos, boolean isInLava, boolean isInWater
        ) {
            BlockState blockstate = level.getBlockState(pos);
            if (blockstate.isAir()) {
                return true;
            } else {
                boolean flag = from != null && to != null;
                boolean flag1 = flag
                    ? !this.whale
                        .collidedWithShapeMovingFrom(
                            from, to, blockstate.getCollisionShape(level, pos).move(new Vec3(pos)).toAabbs()
                        )
                    : blockstate.getCollisionShape(level, pos).isEmpty();
                if (!this.careful) {
                    return flag1;
                } else {

                    BlockState blockState = level.getBlockState(pos);
                    BlockState onStandingState = level.getBlockState(this.whale.blockPosition());

                    FluidState fluidstate = level.getFluidState(pos);
                    if (!fluidstate.isEmpty() && (!flag || this.whale.collidedWithFluid(fluidstate, pos, from, to))) {
                        if (fluidstate.is(FluidTags.WATER)) {
                            return isInLava;
                        }

                        if (fluidstate.is(FluidTags.LAVA)) {
                            return isInWater;
                        }
                    }

                    if (blockState.is(AetherIITags.Blocks.AERCLOUDS) && !onStandingState.is(AetherIITags.Blocks.AERCLOUDS)) {
                        return false;
                    }

                    return flag1;
                }
            }
        }
    }

    public static class AerwhaleLookControl extends LookControl {
        public AerwhaleLookControl(Mob whale) {
            super(whale);
        }

        @Override
        public void tick() {
            Aerwhale.faceMovementDirection(this.mob);
        }
    }
}

package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Swet extends Mob implements Enemy {
    private static final EntityDataAccessor<Boolean> DATA_MID_JUMP_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_WATER_DAMAGE_SCALE_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.FLOAT);

    private boolean wasOnGround;
    private int jumpTimer;
    private float swetHeight = 1.0F;
    private float swetHeightO = 1.0F;
    private float swetWidth = 1.0F;
    private float swetWidthO = 1.0F;

    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();

    public Swet(EntityType<? extends Swet> type, Level level) {
        super(type, level);
        this.moveControl = new Swet.SwetMoveControl(this);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ConsumeGoal(this));
        this.goalSelector.addGoal(1, new HuntGoal(this));
        this.goalSelector.addGoal(2, new SwetRandomDirectionGoal(this));
        this.goalSelector.addGoal(4, new SwetKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (target) -> !(target.getRootVehicle() instanceof Swet)));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 14.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MID_JUMP_ID, false);
        builder.define(DATA_WATER_DAMAGE_SCALE_ID, 0.0F);
    }

    /**
     * Refreshes the Swet's bounding box dimensions.
     *
     * @param dataAccessor The {@link EntityDataAccessor} for the entity.
     */
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        if (DATA_WATER_DAMAGE_SCALE_ID.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    /**
     * Swets can spawn if the block at the spawn location is in the {@link AetherTags.Blocks#SWET_SPAWNABLE_ON} tag, if they are spawning at a light level above 8,
     * and  if the difficulty isn't peaceful.
     *
     * @param swet   The {@link Swet} {@link EntityType}.
     * @param level  The {@link LevelAccessor}.
     * @param reason The {@link MobSpawnType} reason.
     * @param pos    The spawn {@link BlockPos}.
     * @param random The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkSwetSpawnRules(EntityType<? extends Swet> swet, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.SWET_SPAWNABLE_ON)
                && level.getRawBrightness(pos, 0) <= 8
                && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Handles Swet behavior.
     */
    @Override
    public void tick() {
        // Handle dissolving in water.
        if (this.isInWater()) {
            this.spawnDissolveParticles();
            if (this.getWaterDamageScale() < 1.0F) {
                this.setWaterDamageScale(this.getWaterDamageScale() + 0.02F);
            }
        }
        super.tick();

        if (this.onGround() && !this.wasOnGround) {
            float f = this.getDimensions(this.getPose()).width() * 2.0F;
            float f1 = f / 2.0F;
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            if (this.level().isClientSide()) {
                this.groundAnimationState.start(this.tickCount);
                this.jumpAnimationState.stop();
            }
        }


        // Spawn particles when no target is captured.
        if (!this.hasPrey() && this.canSpawnSplashParticles()) {
            if (this.level().isClientSide()) {
                double d = (float) this.getX() + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
                double d1 = (float) this.getY() + this.getBbHeight();
                double d2 = (float) this.getZ() + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.3F;
                this.level().addParticle(ParticleTypes.SPLASH, d, d1 - 0.25, d2, 0.0, 0.0, 0.0);
            }
        }

        // Handle jump behavior and animation.
        if (!this.isNoAi()) {
            this.setMidJump(!this.onGround());
            if (this.level().isClientSide()) {
                this.swetHeightO = this.swetHeight;
                this.swetWidthO = this.swetWidth;
                if (this.getMidJump()) {
                    this.jumpTimer++;
                } else {
                    this.jumpTimer = 0;
                }
                if (this.getJumpTimer() > 1) {
                    this.swetHeight = 1.425F;
                    this.swetWidth = 0.875F;

                    float scale = Math.min(this.getJumpTimer(), 10);
                    if (this.getJumpTimer() > 2) {
                        this.swetHeight -= 0.04F * scale;
                        this.swetWidth += 0.04F * scale;
                    }
                    if (this.getJumpTimer() > 3) {
                        this.swetHeight -= 0.02F * scale;
                        this.swetWidth += 0.02F * scale;
                    }
                } else {
                    this.swetHeight = this.swetHeight < 1.0F ? this.swetHeight + 0.25F : 1.0F;
                    this.swetWidth = this.swetWidth > 1.0F ? this.swetWidth - 0.25F : 1.0F;
                }
            }
            this.wasOnGround = this.onGround();
        }


        if (this.isFriendly()) { // Handle fall damage immunity when mounted.
            this.resetFallDistance();
        }
    }


    /**
     * Spawn dissolve particles in {@link Swet#handleEntityEvent(byte)}.
     */
    public void spawnDissolveParticles() {
        if (this.level() instanceof ServerLevel level) {
            level.broadcastEntityEvent(this, (byte) 70);
        }
    }

    /**
     * @return Whether the Swet is in the middle of a jump, as a {@link Boolean}.
     */
    public boolean getMidJump() {
        return this.getEntityData().get(DATA_MID_JUMP_ID);
    }

    /**
     * Sets whether the Swet is in the middle of a jump.
     *
     * @param midJump The {@link Boolean} value.
     */
    public void setMidJump(boolean midJump) {
        this.getEntityData().set(DATA_MID_JUMP_ID, midJump);
    }

    /**
     * @return The {@link Float} scale of water damage the Swet has received.
     */
    public float getWaterDamageScale() {
        return this.getEntityData().get(DATA_WATER_DAMAGE_SCALE_ID);
    }

    /**
     * Sets the water damage the Swet has received.
     *
     * @param scale The {@link Float} value.
     */
    public void setWaterDamageScale(float scale) {
        this.getEntityData().set(DATA_WATER_DAMAGE_SCALE_ID, scale);
    }


    /**
     * @return The {@link Float} height of the Swet model.
     */
    public float getSwetHeight() {
        return this.swetHeight;
    }

    /**
     * @return The old {@link Float} height of the Swet model.
     */
    public float getSwetHeightO() {
        return this.swetHeightO;
    }

    /**
     * @return The {@link Float} width of the Swet model.
     */
    public float getSwetWidth() {
        return this.swetWidth;
    }

    /**
     * @return The old {@link Float} width of the Swet model.
     */
    public float getSwetWidthO() {
        return this.swetWidthO;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SWET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SWET_DEATH.get();
    }

    protected SoundEvent getSquishSound() {
        return AetherIISoundEvents.ENTITY_SWET_SQUISH.get();
    }

    /**
     * @return A {@link Boolean} for whether the Swet has captured prey (a passenger).
     */
    public boolean hasPrey() {
        return getFirstPassenger() != null;
    }

    /**
     * @return A {@link Boolean} for whether the Swet can spawn splash particles.
     */
    public boolean canSpawnSplashParticles() {
        return true;
    }

    /**
     * @return A {@link Boolean} for whether the Swet is being controlled, meaning it is friendly.
     */
    public boolean isFriendly() {
        return this.getControllingPassenger() != null;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 1) {
            this.spawnSprintParticle();
            this.jumpAnimationState.start(this.tickCount);
        } else if (id == 61) {
            this.groundAnimationState.start(this.tickCount);
        } else if (id == 70) {
            for (int i = 0; i < 10; i++) {
                double f = this.getRandom().nextFloat() * Mth.TWO_PI;
                double f1 = this.getRandom().nextFloat() * this.swetWidth + 0.25F;
                double f2 = (this.getRandom().nextFloat() * this.swetHeight) - (this.getRandom().nextGaussian() * 0.02 * 10.0);
                double f3 = Mth.sin((float) f) * f1;
                double f4 = Mth.cos((float) f) * f1;
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + f3, this.getY() + f2, this.getZ() + f4, f3 * 1.5 + this.getDeltaMovement().x(), 4.0, f4 * 1.5 + this.getDeltaMovement().z());
            }
        } else if (id == 71) {
            this.absMoveTo(this.getX(), this.getY(), this.getZ());
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("WaterDamageScale", this.getWaterDamageScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("WaterDamageScale")) {
            this.setWaterDamageScale(tag.getFloat("WaterDamageScale"));
        }
    }

    /**
     * Makes the Swet jump 3 times when it has a consumed target, in an attempt to damage the target.
     */
    public static class ConsumeGoal extends Goal {
        private final Swet swet;
        private int jumps = 0;

        private float chosenDegrees = 0;

        public ConsumeGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.swet.hasPrey()
                    && this.swet.getPassengers().get(0) instanceof LivingEntity passenger
                    ;
        }

        @Override
        public void tick() {
            if (this.jumps <= 3) {
                if (this.swet.onGround()) {
                    this.swet.level().broadcastEntityEvent(this.swet, (byte) 71); // This is to make sure the Swet actually touches the ground on the client.
                    this.swet.playSound(AetherIISoundEvents.ENTITY_SWET_JUMP.get(), 1.0F, ((this.swet.getRandom().nextFloat() - this.swet.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);

                    this.chosenDegrees = (float) this.swet.getRandom().nextInt(360);
                    if (this.jumps == 0) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 0.65, 0));
                    } else if (this.jumps == 1) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 0.75, 0));
                    } else if (this.jumps == 2) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 1.55, 0));
                    } else {
                        this.swet.getPassengers().get(0).stopRiding();
                        this.swet.spawnDissolveParticles();
                        this.swet.discard();
                    }
                    if (!this.swet.getMidJump()) {
                        this.jumps++;
                    }
                }

                if (!this.swet.wasOnGround) {
                    if (this.swet.getJumpTimer() < 6) {
                        if (this.jumps == 1) {
                            this.moveHorizontal(0.0F, 0.1F, this.chosenDegrees);
                        } else if (this.jumps == 2) {
                            this.moveHorizontal(0.0F, 0.15F, this.chosenDegrees);
                        } else if (this.jumps == 3) {
                            this.moveHorizontal(0.0F, 0.3F, this.chosenDegrees);
                        }
                    }
                }
            }
        }

        public void moveHorizontal(float strafe, float forward, float rotation) {
            float f = Mth.square(strafe) + Mth.square(forward);

            f = Mth.sqrt(f);
            if (f < 1.0F) f = 1.0F;
            strafe = strafe * f;
            forward = forward * f;
            float f1 = Mth.sin(rotation * 0.017453292F);
            float f2 = Mth.cos(rotation * 0.017453292F);

            this.swet.setDeltaMovement(strafe * f2 - forward * f1, this.swet.getDeltaMovement().y(), (forward * f2 + strafe * f1));
            if (this.swet.getMoveControl() instanceof SwetMoveControl swetMoveControl) {
                swetMoveControl.yRot = rotation % 360;
            }
        }
    }

    private int getJumpTimer() {
        return this.jumpTimer;
    }

    /**
     * Locates a target to look towards to start jumping to, and handles consuming the target when colliding.
     */
    public static class HuntGoal extends Goal {
        private final Swet swet;

        public HuntGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.swet.getTarget();
            if (this.swet.hasPrey() || target == null || !target.isAlive() || (target instanceof Player player && player.getAbilities().invulnerable)) {
                return false;
            } else {
                return this.swet.getMoveControl() instanceof SwetMoveControl;
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.swet.getTarget();
            if (this.swet.hasPrey() || target == null || !target.isAlive()) {
                return false;
            } else if (target instanceof Player player && player.getAbilities().invulnerable) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void tick() {
            if (this.swet.getMoveControl() instanceof SwetMoveControl swetMoveControl) {
                LivingEntity target = this.swet.getTarget();
                if (target != null) {
                    this.swet.lookAt(target, 10.0F, 10.0F);
                    swetMoveControl.setDirection(this.swet.getYRot(), true);
                    if (this.swet.getBoundingBox().intersects(target.getBoundingBox())) {
                        //this.swet.consumePassenger(target);
                    }
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link Slime.SlimeKeepOnJumpingGoal}.<br><br>
     * Also checks if the Swet is able to jump.
     */
    public static class SwetKeepOnJumpingGoal extends Goal {
        private final Swet swet;

        public SwetKeepOnJumpingGoal(Swet swetEntity) {
            this.swet = swetEntity;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !this.swet.isPassenger() && this.swet.getMoveControl() instanceof SwetMoveControl moveHelperController && moveHelperController.canJump;
        }

        public void tick() {
            MoveControl movecontrol = this.swet.getMoveControl();
            if (movecontrol instanceof SwetMoveControl swetMoveControl) {
                swetMoveControl.setWantedMovement(1.0D);
            }
        }
    }

    /**
     * [CODE COPY] - {@link Slime.SlimeMoveControl}.<br><br>
     * Also tracks whether the Swet can jump in {@link SwetKeepOnJumpingGoal}.
     */
    public static class SwetMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final Swet swet;
        private boolean isAggressive;
        private boolean canJump;

        public SwetMoveControl(Swet swet) {
            super(swet);
            this.swet = swet;
            this.yRot = 180.0F * swet.getYRot() / Mth.PI;
        }

        public void setDirection(float yRot, boolean isAggressive) {
            this.yRot = yRot;
            this.isAggressive = isAggressive;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        public void setCanJump(boolean canJump) {
            this.canJump = canJump;
        }

        public void tick() {
            if (this.swet.isFriendly()) {
                return;
            }
            this.swet.setYRot(this.rotlerp(this.swet.getYRot(), this.yRot, 90.0F));
            this.swet.setYHeadRot(this.swet.getYRot());
            this.swet.setYBodyRot(this.swet.getYRot());
            if (this.operation != Operation.MOVE_TO) {
                this.swet.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.swet.onGround()) {
                    this.swet.setSpeed((float) (this.speedModifier * this.swet.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.swet.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 6;
                        }
                        this.swet.getJumpControl().jump();
                        this.swet.level().broadcastEntityEvent(this.swet, (byte) 1);
                        this.swet.playSound(AetherIISoundEvents.ENTITY_SWET_JUMP.get(), 1.0F, ((this.swet.getRandom().nextFloat() - this.swet.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    } else {
                        this.swet.xxa = 0.0F;
                        this.swet.zza = 0.0F;
                        this.swet.setSpeed(0.0F);
                    }
                } else {
                    this.swet.setSpeed((float) (this.speedModifier * this.swet.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    /**
     * [CODE COPY] - {@link Slime.SlimeRandomDirectionGoal}.<br><br>
     * Also has code to handle preventing the Swet from jumping off of ledges.
     */
    public static class SwetRandomDirectionGoal extends Goal {
        private final Swet swet;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public SwetRandomDirectionGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.swet.getTarget() == null && (this.swet.onGround() || this.swet.isInFluidType() || this.swet.hasEffect(MobEffects.LEVITATION)) && this.swet.getMoveControl() instanceof SwetMoveControl;
        }

        public void tick() {
            SwetMoveControl moveHelperController = (SwetMoveControl) this.swet.getMoveControl();
            float rot = moveHelperController.yRot;
            Vec3 offset = new Vec3(-Math.sin(rot * Mth.DEG_TO_RAD) * 2, 0.0, Math.cos(rot * Mth.DEG_TO_RAD) * 2);
            BlockPos offsetPos = BlockPos.containing(this.swet.position().add(offset));
            // Rotate the Swet if the next position in the direction it is facing is beyond its fall distance to jump to.
            if (this.swet.level().getHeight(Heightmap.Types.WORLD_SURFACE, offsetPos.getX(), offsetPos.getZ()) < offsetPos.getY() - this.swet.getMaxFallDistance()) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.swet.getRandom().nextInt(60));
                this.chosenDegrees += 180;
                moveHelperController.setCanJump(false);
            } else {
                if (--this.nextRandomizeTime <= 0) {
                    this.nextRandomizeTime = this.adjustedTickDelay(40 + this.swet.getRandom().nextInt(60));
                    this.chosenDegrees = (float) this.swet.getRandom().nextInt(360);
                }
                moveHelperController.setCanJump(true);
            }
            moveHelperController.setDirection(this.chosenDegrees, false);
        }
    }
}

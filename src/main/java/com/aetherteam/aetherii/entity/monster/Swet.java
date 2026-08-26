package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class Swet extends Monster {
    public static int JUMP_EVENT = 100;
    public static int LAND_EVENT = 101;
    public static int DISSOLVE_EVENT = 102;

    private static final EntityDataAccessor<Boolean> DATA_MID_JUMP_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_WATER_DAMAGE_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_FOOD_SATURATION_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_SWET_SCALE_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.FLOAT);

    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState groundAnimationState = new AnimationState();

    private boolean wasOnGround;

    public Swet(EntityType<? extends Swet> type, Level level) {
        super(type, level);
        this.moveControl = new Swet.SwetMoveControl(this);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new HuntGoal(this));
        this.goalSelector.addGoal(2, new SwetRandomDirectionGoal(this));
        this.goalSelector.addGoal(4, new SwetKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (target, level) -> target instanceof Player player && this.canLatch(player)));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 14.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MID_JUMP_ID, false);
        builder.define(DATA_WATER_DAMAGE_ID, 0.0F);
        builder.define(DATA_FOOD_SATURATION_ID, 1.0F);
        builder.define(DATA_SWET_SCALE_ID, 0.95F);
    }

    public static boolean checkSwetSpawnRules(EntityType<Swet> entityType, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.SWET_SPAWNABLE_ON) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(level, pos, random) && checkMobSpawnRules(entityType, level, reason, pos, random);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == JUMP_EVENT) {
            this.spawnSprintParticle();
            this.groundAnimationState.stop();
            this.jumpAnimationState.start(this.tickCount);
        } else if (id == LAND_EVENT) {
            this.jumpAnimationState.stop();
            this.groundAnimationState.start(this.tickCount);
        } else if (id == DISSOLVE_EVENT) {
            for (int i = 0; i < 10; i++) {
                double f = this.getRandom().nextFloat() * Mth.TWO_PI;
                double f1 = this.getRandom().nextFloat() * this.getDimensions(this.getPose()).width() + 0.25F;
                double f2 = (this.getRandom().nextFloat() * this.getDimensions(this.getPose()).height()) - (this.getRandom().nextGaussian() * 0.02 * 10.0);
                double f3 = Mth.sin((float) f) * f1;
                double f4 = Mth.cos((float) f) * f1;
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + f3, this.getY() + f2, this.getZ() + f4, f3 * 1.5 + this.getDeltaMovement().x(), 4.0, f4 * 1.5 + this.getDeltaMovement().z());
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * Handles Swet behavior.
     */
    @Override
    public void tick() {
        // Handle dissolving in water.
        if (this.isInWater() || level().isRainingAt(new BlockPos(((int) position().x()), ((int) position().y()), ((int) position().z())))) {
            this.spawnDissolveParticles();
            if (this.getWaterDamage() < 1.0F) {
                this.setWaterDamage(this.getWaterDamage() + 0.05F);
                if (this.getSwetScale() > 0.0F) {
                    this.setSwetScale(Math.max(0.0F, this.getSwetScale() - 0.05F));
                }
            }
        } else if (this.isWaterDamaged()) {
            this.setWaterDamage(this.getWaterDamage() - 0.001F);
        } else if (!this.isWaterDamaged() && this.getSwetScale() < 0.95F) {
            this.setSwetScale(Math.min(this.getSwetScale() + 0.001F, 0.95F));
        }

        super.tick();

        if (this.onGround() && !this.wasOnGround) {
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) / 0.8F);
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.broadcastEntityEvent(this, (byte) LAND_EVENT);
            }
        }

        if (this.getFoodSaturation() > 0) {
            this.setFoodSaturation(this.getFoodSaturation() - 0.001F);
            if (this.getSwetScale() > 0.95F) {
                this.setSwetScale(Math.max(0.95F, this.getSwetScale() - 0.00005F));
            }
        }

        if (!this.isNoAi()) {
            this.wasOnGround = this.onGround();
        }
    }

    /**
     * Spawn dissolve particles in {@link Swet#handleEntityEvent(byte)}.
     */
    public void spawnDissolveParticles() {
        if (this.level() instanceof ServerLevel level) {
            level.broadcastEntityEvent(this, (byte) DISSOLVE_EVENT);
        }
    }

    public boolean canLatch(Player player) {
        return !player.isInWater()
                && player.getFoodData().getFoodLevel() > 0
                && this.getFoodSaturation() <= 3
                && !this.isWaterDamaged()
                && this.getSwetScale() >= 0.95F
                && player.getData(AetherIIDataAttachments.SWET_LATCH).canLatchOn();
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        float scale = Mth.clamp(this.getSwetScale(), 0.6F, 1.25F);
        return EntityDimensions.scalable(scale, scale).withEyeHeight(scale / 2.0F);
    }

    protected int getJumpDelay() {
        return this.getRandom().nextInt(20) + 10;
    }

    @Override
    protected float getJumpPower() {
        float jumpPower = super.getJumpPower();
        if (this.isInWall()) {
            jumpPower = 0.75F;
        } else if (this.isWaterDamaged()) {
            jumpPower = jumpPower * 0.5F;
        } else if (this.getSwetScale() > 0.95F) {
            jumpPower = jumpPower * (1.0F - (this.getSwetScale() - 0.95F));
        }
        return jumpPower;
    }

    public boolean isWaterDamaged() {
        return this.getWaterDamage() > 0.0F;
    }

    /**
     * @return The {@link Float} scale of water damage the Swet has received.
     */
    public float getWaterDamage() {
        return this.getEntityData().get(DATA_WATER_DAMAGE_ID);
    }

    /**
     * Sets the water damage the Swet has received.
     *
     * @param scale The {@link Float} value.
     */
    public void setWaterDamage(float scale) {
        this.getEntityData().set(DATA_WATER_DAMAGE_ID, scale);
    }

    /**
     * @return The {@link Float} Saturation the Swet has received.
     */
    public float getFoodSaturation() {
        return this.getEntityData().get(DATA_FOOD_SATURATION_ID);
    }

    /**
     * Sets the Saturation the Swet has received.
     *
     * @param foodSaturation The {@link Float} value.
     */
    public void setFoodSaturation(float foodSaturation) {
        this.getEntityData().set(DATA_FOOD_SATURATION_ID, foodSaturation);
    }

    /**
     * @return The {@link Float} scale of the Swet.
     */
    public float getSwetScale() {
        return this.getEntityData().get(DATA_SWET_SCALE_ID);
    }

    /**
     * Sets the scale the Swet.
     *
     * @param scale The {@link Float} value.
     */
    public void setSwetScale(float scale) {
        this.getEntityData().set(DATA_SWET_SCALE_ID, scale);
        this.refreshDimensions();
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

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putFloat("WaterDamageScale", this.getWaterDamage());
        output.putFloat("SwetScale", this.getSwetScale());
        output.putFloat("Saturation", this.getFoodSaturation());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setWaterDamage(input.getFloatOr("WaterDamageScale", 0.0F));
        this.setSwetScale(input.getFloatOr("SwetScale", 0.95F));
        this.setFoodSaturation(input.getFloatOr("Saturation", 0.0F));
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

        @Override
        public void tick() {
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
                        this.swet.level().broadcastEntityEvent(this.swet, (byte) JUMP_EVENT);
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

        @Override
        public boolean canUse() {
            return this.swet.getTarget() == null && (this.swet.onGround() || this.swet.isInWater() /*|| this.swet.isInFluidType()*/ || this.swet.hasEffect(MobEffects.LEVITATION)) && this.swet.getMoveControl() instanceof SwetMoveControl;
        }

        @Override
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
            if (target == null || !target.isAlive() || (target instanceof Player player && player.getAbilities().invulnerable)) {
                return false;
            } else {
                return this.swet.getMoveControl() instanceof SwetMoveControl && target instanceof Player player && this.swet.canLatch(player);
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.swet.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            } else if (target instanceof Player player && player.getAbilities().invulnerable) {
                return false;
            } else {
                return target instanceof Player player && this.swet.canLatch(player);
            }
        }

        @Override
        public void tick() {
            if (this.swet.getMoveControl() instanceof SwetMoveControl swetMoveControl) {
                LivingEntity target = this.swet.getTarget();
                if (target != null) {
                    this.swet.lookAt(target, 10.0F, 10.0F);
                    swetMoveControl.setCanJump(true);
                    swetMoveControl.setDirection(this.swet.getYRot(), true);
                    if (this.swet.getBoundingBox().intersects(target.getBoundingBox())) {
                        if (target instanceof Player player) {
                            player.getData(AetherIIDataAttachments.SWET_LATCH.get()).latchSwet(player, this.swet);
                        }
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

        @Override
        public boolean canUse() {
            return !this.swet.isPassenger() && this.swet.getMoveControl() instanceof SwetMoveControl moveHelperController && moveHelperController.canJump;
        }

        @Override
        public void tick() {
            MoveControl movecontrol = this.swet.getMoveControl();
            if (movecontrol instanceof SwetMoveControl swetMoveControl) {
                swetMoveControl.setWantedMovement(1.0);
            }
        }
    }
}

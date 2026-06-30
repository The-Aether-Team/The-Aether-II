package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.PlantCuttingMob;
import com.aetherteam.aetherii.entity.projectile.ToxicDart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AechorPlant extends PlantMob implements RangedAttackMob, PlantCuttingMob {
    public static int DART_ATTACK_EVENT = 100;

    private static final EntityDataAccessor<Boolean> DATA_TARGETING_ENTITY_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.BOOLEAN);

    public AnimationState attackAnimationState = new AnimationState();

    public AechorPlant(EntityType<? extends AechorPlant> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AechorPlant.ShootDartGoal(this, 60, 10.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGETING_ENTITY_ID, false);
        this.entityData.define(DATA_PLAYER_GROWN_ID, false);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setPos(Vec3.atBottomCenterOf(this.blockPosition()));
        return spawnData;
    }

    /**
     * Aechor Plants can spawn if the block at the spawn location is in the {@link AetherIITags.Blocks#AECHOR_PLANT_SPAWNABLE_ON} tag, if they are spawning at a light level above 8,
     * if the difficulty isn't peaceful, and they spawn with a random chance of 1/10.
     *
     * @param aechorPlant The {@link AechorPlant} {@link EntityType}.
     * @param level       The {@link LevelAccessor}.
     * @param reason      The {@link MobSpawnType} reason.
     * @param pos         The spawn {@link BlockPos}.
     * @param random      The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkAechorPlantSpawnRules(EntityType<? extends AechorPlant> aechorPlant, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.AECHOR_PLANT_SPAWNABLE_ON)
                && level.getRawBrightness(pos, 0) > 8
                && level.canSeeSky(pos)
                && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == DART_ATTACK_EVENT) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * Kills the Aechor Plant if it is not on a valid block or on a vehicle, and also handles setting whether it is targeting an entity on client and server.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.level().getBlockState(this.blockPosition().below()).is(AetherIITags.Blocks.AECHOR_PLANT_SPAWNABLE_ON) && !this.isPassenger()) {
            if (!this.level().isClientSide()) {
                this.kill();
            }
        }
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null) {
                this.setTargetingEntity(true);
            } else if (this.getTarget() == null && this.isTargetingEntity()) {
                this.setTargetingEntity(false);
            }
        }
        if (this.deathTime == 1) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, AetherIIBlocks.AECHOR_CUTTING.get().defaultBlockState()),
                        this.getX(), this.getY(0.66), this.getZ(), 50,
                        this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05);
            }
        }
    }

    public void makePoofParticles() { }

    /**
     * Shoots a Poison Needle from the center of the Aechor Plant.
     *
     * @param target         The target {@link LivingEntity}.
     * @param distanceFactor The {@link Float} distance factor for targeting.
     */
    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Vec3 originVec = this.position();
        Vec3 targetVec = target.position();
        int amount = 10;
        for (int i = 1; i <= amount; i++) {
            ToxicDart needle = new ToxicDart(this, this.level());

            float velocity = 0.65F;
            double gravity = 0.05;

            double theta = (Mth.TWO_PI / amount) * (i + this.getRandom().nextDouble());

            double x1 = targetVec.subtract(originVec).length();
            double y1 = targetVec.y() - originVec.y();

            double root = velocity * velocity * velocity * velocity - gravity * (gravity * x1 * x1 + 2.0 * y1 * velocity * velocity);
            root = Math.sqrt(Math.max(0.0, root));
            double yTrajectory = Math.atan((velocity * velocity + root) / (gravity * x1));

            yTrajectory = Math.max(yTrajectory, 35 * Mth.DEG_TO_RAD);

            double vec2Dx = Math.cos(yTrajectory);
            double vec2Dy = Math.sin(yTrajectory);

            double vec3Dx = vec2Dx * Math.cos(theta);
            double vec3Dz = vec2Dx * Math.sin(theta);

            Vec3 trajectory = new Vec3(vec3Dx, vec2Dy, vec3Dz);

            needle.shoot(trajectory.x(), trajectory.y(), trajectory.z(), velocity, 1.0F);
            this.level().addFreshEntity(needle);
        }

        this.playSound(AetherIISoundEvents.ENTITY_AECHOR_PLANT_SHOOT.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    /**
     * Disallows Aechor Plants from being pushed.
     *
     * @param x The {@link Double} for x-motion.
     * @param y The {@link Double} for y-motion.
     * @param z The {@link Double} for z-motion.
     */
    @Override
    public void push(double x, double y, double z) { }

    /**
     * Disallows Aechor Plants from jumping.
     */
    @Override
    public void jumpFromGround() { }

    /**
     * Disallows Aechor Plants from being leashed.
     */
    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    /**
     * @return Whether an entity is being targeted, as a {@link Boolean}.
     */
    public boolean isTargetingEntity() {
        return this.getEntityData().get(DATA_TARGETING_ENTITY_ID);
    }

    /**
     * Sets whether an entity is being targeted.
     *
     * @param targetingEntity The {@link Boolean} value.
     */
    public void setTargetingEntity(boolean targetingEntity) {
        this.getEntityData().set(DATA_TARGETING_ENTITY_ID, targetingEntity);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_AECHOR_PLANT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_AECHOR_PLANT_DEATH.get();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return this.distanceTo(entity) <= 8.0 && super.hasLineOfSight(entity);
    }

    /**
     * Makes Aechor Plants immune to Inebriation.
     *
     * @param effect The {@link MobEffectInstance} to check whether this mob is affected by.
     * @return Whether the mob is affected.
     */
    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIMobEffects.TOXIN.get() && super.canBeAffected(effect);
    }

    @Override
    public boolean isPlayerGrown() {
        return this.entityData.get(DATA_PLAYER_GROWN_ID);
    }

    @Override
    public void setPlayerGrown(boolean playerGrown) {
        this.entityData.set(DATA_PLAYER_GROWN_ID, playerGrown);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("PlayerGrown", this.isPlayerGrown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setPlayerGrown(tag.contains("PlayerGrown") && tag.getBoolean("PlayerGrown"));
    }

    public static class ShootDartGoal extends Goal {
        private final AechorPlant aechorPlant;
        private int attackTime = -1;
        private final int attackInterval;
        private final float attackRadius;
        private LivingEntity trackedTarget;

        public ShootDartGoal(AechorPlant aechorPlant, int attackInterval, float attackRadius) {
            this.aechorPlant = aechorPlant;
            this.attackInterval = attackInterval;
            this.attackRadius = attackRadius;
        }

        @Override
        public boolean canUse() {
            return this.aechorPlant.getTarget() != null && this.aechorPlant.getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.trackedTarget != null;
        }

        @Override
        public void start() {
            this.trackedTarget = this.aechorPlant.getTarget();
        }

        @Override
        public void stop() {
            this.attackTime = -1;
            this.trackedTarget = null;
        }

        @Override
        public void tick() {
            if (this.trackedTarget != null) {
                double distance = this.aechorPlant.distanceToSqr(this.trackedTarget);
                boolean canSee = this.aechorPlant.getSensing().hasLineOfSight(this.trackedTarget);

                if (this.attackTime == this.attackInterval - 10) {
                    this.aechorPlant.level().broadcastEntityEvent(this.aechorPlant, (byte) DART_ATTACK_EVENT);
                }

                if (--this.attackTime == 0) {
                    float f = (float) Math.sqrt(distance) / this.attackRadius;
                    float f1 = Mth.clamp(f, 0.1F, 1.0F);
                    this.aechorPlant.performRangedAttack(this.trackedTarget, f1);
                    this.attackTime = this.attackInterval;
                    if (!canSee || this.aechorPlant.getTarget() == null || !this.aechorPlant.getTarget().isAlive()) {
                        this.trackedTarget = null;
                    }
                } else if (this.attackTime < 0) {
                    this.attackTime = this.attackInterval;
                }
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}

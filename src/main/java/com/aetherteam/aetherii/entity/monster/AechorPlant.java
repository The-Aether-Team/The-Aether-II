package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.projectile.ToxicDart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class AechorPlant  extends PathfinderMob implements RangedAttackMob {
//    private static final EntityDataAccessor<Integer> DATA_SIZE_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Integer> DATA_POISON_REMAINING_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_TARGETING_ENTITY_ID = SynchedEntityData.defineId(AechorPlant.class, EntityDataSerializers.BOOLEAN);

    public AnimationState attackAnimationState = new AnimationState();

    public AechorPlant(EntityType<? extends AechorPlant> type, Level level) {
        super(type, level);
        this.xpReward = 5;
//        this.setPoisonRemaining(2);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AechorPlant.ShootDartGoal(this, 60, 10.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
//        this.getEntityData().define(DATA_SIZE_ID, 0);
//        this.getEntityData().define(DATA_POISON_REMAINING_ID, 0);
        builder.define(DATA_TARGETING_ENTITY_ID, false);
    }

//    /**
//     * Refreshes the Aechor Plant's bounding box dimensions.
//     *
//     * @param dataAccessor The {@link EntityDataAccessor} for the entity.
//     */
//    @Override
//    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
//        if (DATA_SIZE_ID.equals(dataAccessor)) {
//            this.refreshDimensions();
//        }
//        super.onSyncedDataUpdated(dataAccessor);
//    }

    /**
     * Sets up random sizing for the Aechor Plant.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link MobSpawnType} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @param tag        The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
//        this.setSize(this.getRandom().nextInt(4) + 1);
        this.setPos(Vec3.atBottomCenterOf(this.blockPosition()));
        return spawnData;
    }

    /**
     * Aechor Plants can spawn if the block at the spawn location is in the {@link AetherTags.Blocks#AECHOR_PLANT_SPAWNABLE_ON} tag, if they are spawning at a light level above 8,
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
                && level.getDifficulty() != Difficulty.PEACEFUL
                && (reason != MobSpawnType.NATURAL || random.nextInt(10) == 0);
    }

    /**
     * Kills the Aechor Plant if it is not on a valid block or on a vehicle, and also handles setting whether it is targeting an entity on client and server.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.level().getBlockState(this.blockPosition().below()).is(AetherIITags.Blocks.AECHOR_PLANT_SPAWNABLE_ON) && !this.isPassenger()) {
            this.kill();
        }
        if (!this.level().isClientSide()) {
            if (this.getTarget() != null) {
                this.setTargetingEntity(true);
            } else if (this.getTarget() == null && this.getTargetingEntity()) {
                this.setTargetingEntity(false);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 61) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    //    /**
//     * Fills a Skyroot Bucket with poison and reduces the amount of poison left collectible from this Aechor Plant.
//     *
//     * @param player The interacting {@link Player}.
//     * @param hand   The {@link InteractionHand}.
//     * @return The {@link InteractionResult}.
//     */
//    @Override
//    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
//        ItemStack itemStack = player.getItemInHand(hand);
//        if (itemStack.is(AetherItems.SKYROOT_BUCKET.get()) && this.getPoisonRemaining() > 0) {
//            this.setPoisonRemaining(this.getPoisonRemaining() - 1);
//            ItemStack itemStack1 = ItemUtils.createFilledResult(itemStack, player, AetherItems.SKYROOT_POISON_BUCKET.get().getDefaultInstance());
//            player.setItemInHand(hand, itemStack1);
//            return InteractionResult.sidedSuccess(this.level().isClientSide());
//        } else {
//            return super.mobInteract(player, hand);
//        }
//    }

    /**
     * Disallows Aechor Plants from being pushed.
     *
     * @param x The {@link Double} for x-motion.
     * @param y The {@link Double} for y-motion.
     * @param z The {@link Double} for z-motion.
     */
    @Override
    public void push(double x, double y, double z) {
    }

    /**
     * Disallows Aechor Plants from jumping.
     */
    @Override
    public void jumpFromGround() {
    }

    /**
     * Disallows Aechor Plants from being leashed.
     */
    @Override
    public boolean canBeLeashed() {
        return false;
    }

    /**
     * Spawns particles when the Aechor Plant's hurt animation is complete.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
//        if (this.hurtTime == 0) {
//            for (int i = 0; i < 8; ++i) {
//                double d1 = this.getX() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5;
//                double d2 = this.getY() + 0.25 + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5;
//                double d3 = this.getZ() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5;
//                double d4 = (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5;
//                double d5 = (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.5;
//                this.level().addParticle(ParticleTypes.PORTAL, d1, d2, d3, d4, 0.25, d5);
//            }
//        }
        return super.hurt(source, amount);
    }

    /**
     * Shoots a Poison Needle from the center of the Aechor Plant.
     *
     * @param target         The target {@link LivingEntity}.
     * @param distanceFactor The {@link Float} distance factor for targeting.
     */
    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        double xDist = target.getX() - this.getX();
        double zDist = target.getZ() - this.getZ();
        double sqrt = Math.sqrt(xDist * xDist + zDist * zDist + 0.1);
        double y = 0.1 + sqrt * 0.5 + (this.getY() - target.getY()) * 0.25;
        double distance = 5.0 / sqrt;

        int amount = 8;
        for (int i = 1; i <= amount; i++) {
            ToxicDart needle = new ToxicDart(this, this.level());
            float angle = (Mth.TWO_PI / amount) * (i + this.getRandom().nextFloat());
            double x = distance * Mth.cos(angle);
            double z = distance * Mth.sin(angle);
            needle.shoot(x, y + 0.5F, z, 0.285F + (float) y * 0.08F, 1.0F);
            this.level().addFreshEntity(needle);
        }


//        this.playSound(AetherSoundEvents.ENTITY_AECHOR_PLANT_SHOOT.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

//    /**
//     * @return The {@link Integer} for the size of the Aechor Plant.
//     */
//    public int getSize() {
//        return this.getEntityData().get(DATA_SIZE_ID);
//    }
//
//    /**
//     * Sets the size of the Aechor Plant.
//     *
//     * @param size The {@link Integer} value.
//     */
//    public void setSize(int size) {
//        this.getEntityData().set(DATA_SIZE_ID, size);
//    }
//
//    /**
//     * @return The {@link Integer} for the remaining poison that can be collected from the Aechor Plant.
//     */
//    public int getPoisonRemaining() {
//        return this.getEntityData().get(DATA_POISON_REMAINING_ID);
//    }
//
//    /**
//     * Sets the amount of remaining poison that can be collected from the Aechor Plant.
//     *
//     * @param poisonRemaining The {@link Integer} value.
//     */
//    public void setPoisonRemaining(int poisonRemaining) {
//        this.getEntityData().set(DATA_POISON_REMAINING_ID, poisonRemaining);
//    }

    /**
     * @return Whether an entity is being targeted, as a {@link Boolean}.
     */
    public boolean getTargetingEntity() {
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

//    @Override
//    protected SoundEvent getHurtSound(DamageSource damageSource) {
//        return AetherSoundEvents.ENTITY_AECHOR_PLANT_HURT.get();
//    }
//
//    @Override
//    protected SoundEvent getDeathSound() {
//        return AetherSoundEvents.ENTITY_AECHOR_PLANT_DEATH.get();
//    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return this.distanceTo(entity) <= 8.0 && super.hasLineOfSight(entity);
    }

//    /**
//     * Handles the hitbox for the randomized sizing of Aechor Plants.
//     *
//     * @param pose The {@link Pose} to get dimensions for.
//     * @return The {@link EntityDimensions}.
//     */
//    @Override
//    public EntityDimensions getDefaultDimensions(Pose pose) {
//        float width = 0.75F + this.getSize() * 0.125F;
//        float height = 0.5F + this.getSize() * 0.075F;
//        return EntityDimensions.fixed(width, height);
//    }

//    @Override
//    public double getEyeY() {
//        return super.getEyeY();
//    }
//
//    @Override
//    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
//        return size.height / 1.15F;
//    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    /**
     * Makes Aechor Plants immune to Inebriation.
     *
     * @param effect The {@link MobEffectInstance} to check whether this mob is affected by.
     * @return Whether the mob is affected.
     */
    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIEffects.TOXIN.get() && super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
//        tag.putInt("Size", this.getSize());
//        tag.putInt("Poison Remaining", this.getPoisonRemaining());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
//        if (tag.contains("Size")) {
//            this.setSize(tag.getInt("Size"));
//        }
//        if (tag.contains("Poison Remaining")) {
//            this.setPoisonRemaining(tag.getInt("Poison Remaining"));
//        }
    }

    public static class ShootDartGoal extends Goal {
        private final AechorPlant aechorPlant;
        private LivingEntity target;
        private int attackTime = -1;
        private final int attackInterval;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public ShootDartGoal(AechorPlant aechorPlant, int attackInterval, float attackRadius) {
            this.aechorPlant = aechorPlant;
            this.attackInterval = attackInterval;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = this.aechorPlant.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                this.target = livingentity;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() || this.target.isAlive() && !this.aechorPlant.getNavigation().isDone();
        }

        @Override
        public void stop() {
            this.target = null;
            this.attackTime = -1;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            double d0 = this.aechorPlant.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());

            if (this.attackTime == this.attackInterval) {
                this.aechorPlant.level().broadcastEntityEvent(this.aechorPlant, (byte) 61);
            }

            if (--this.attackTime == 0) {
                float f = (float) Math.sqrt(d0) / this.attackRadius;
                float f1 = Mth.clamp(f, 0.1F, 1.0F);
                this.aechorPlant.performRangedAttack(this.target, f1);
                this.attackTime = this.attackInterval;
            } else if (this.attackTime < 0) {
                this.attackTime = this.attackInterval;
            }
        }
    }
}

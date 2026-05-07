package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.entity.PlantCuttingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CarrionSprout extends PlantMob implements PlantCuttingMob {
    private static final EntityDataAccessor<Boolean> DATA_TRAP_ID = SynchedEntityData.defineId(CarrionSprout.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_TRAP_TRIGGER_ID = SynchedEntityData.defineId(CarrionSprout.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN_ID = SynchedEntityData.defineId(CarrionSprout.class, EntityDataSerializers.BOOLEAN);

    public AnimationState trapActiveAnimationState = new AnimationState();
    public AnimationState trapDeactiveAnimationState = new AnimationState();
    public int trapTriggerTick;
    public int noTouchTick;

    public CarrionSprout(EntityType<? extends CarrionSprout> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public void push(Entity entity) {
        if (!this.isTrapState()) {
            super.push(entity);
        }
        if (!(entity instanceof CarrionSprout)) {
            this.handleTrigger(entity);
        }
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    public void playerTouch(Player entity) {
        //this.handleTrigger(entity);
    }

    private void handleTrigger(Entity entity) {
        boolean previouslyTriggered = this.isTrapTrigger();

        if (this.isTrapState()) {
            this.noTouchTick = 30;
            this.setTrapTrigger(true);
        }

        if (this.isTrapTrigger()) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.forceAddEffect(new MobEffectInstance(AetherIIMobEffects.CARRION_TRAP, 1, 0, false, false, false), this);

                if (this.trapTriggerTick <= 0) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        DamageSource damageSource = AetherIIDamageTypes.entityDamageSource(this.level(), AetherIIDamageTypes.CARRION_SPROUT, this);
                        if (!livingEntity.isInvulnerableTo(serverLevel, damageSource)) {
                            entity.hurtServer(serverLevel, AetherIIDamageTypes.entityDamageSource(this.level(), AetherIIDamageTypes.CARRION_SPROUT, this), 2.0F);
                        }
                    }
                }
            }

            if (!previouslyTriggered) {
                this.playSound(AetherIISoundEvents.ENTITY_CARRION_SPROUT_TRAP.get(), 2.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    public void activeAnimation() {
        this.trapDeactiveAnimationState.stop();
        this.trapActiveAnimationState.startIfStopped(this.tickCount);
    }

    public void deactiveAnimation() {
        this.trapActiveAnimationState.stop();
        this.trapDeactiveAnimationState.startIfStopped(this.tickCount);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_TRAP_ID, false);
        builder.define(DATA_TRAP_TRIGGER_ID, false);
        builder.define(DATA_PLAYER_GROWN_ID, false);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        this.setPos(Vec3.atBottomCenterOf(this.blockPosition()));
        return spawnData;
    }

    /**
     * Aechor Plants can spawn if the block at the spawn location is in the {@link AetherIITags.Blocks#AECHOR_PLANT_SPAWNABLE_ON} tag, if they are spawning at a light level above 8,
     * if the difficulty isn't peaceful, and they spawn with a random chance of 1/10.
     *
     * @param carrionSprout The {@link CarrionSprout} {@link EntityType}.
     * @param level         The {@link LevelAccessor}.
     * @param reason        The {@link EntitySpawnReason} reason.
     * @param pos           The spawn {@link BlockPos}.
     * @param random        The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkCarrionSproutSpawnRules(EntityType<? extends CarrionSprout> carrionSprout, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherIITags.Blocks.CARRION_SPROUT_SPAWNABLE_ON)
                && level.getRawBrightness(pos, 0) > 8
                && level.canSeeSky(pos)
                && level.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Kills the Aechor Plant if it is not on a valid block or on a vehicle, and also handles setting whether it is targeting an entity on client and server.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.level().getBlockState(this.blockPosition().below()).is(AetherIITags.Blocks.CARRION_SPROUT_SPAWNABLE_ON) && !this.isPassenger()) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.kill(serverLevel);
            }
        }

        if (!this.level().isClientSide()) {
            if (this.trapTriggerTick <= 0) {
                this.trapTriggerTick = 30;
            } else if (this.isTrapState()) {
                --this.trapTriggerTick;
            }
            if (--this.noTouchTick <= 0) {
                if (this.isTrapState() && this.isTrapTrigger()) {
                    this.setTrapTrigger(false);
                }
            }
            if (this.level().isDarkOutside() && !this.isTrapState()) {
                this.setTrapState(true);
            } else if (this.level().isBrightOutside() && this.isTrapState()) {
                this.setTrapState(false);
                this.setTrapTrigger(false);
            }

        } else {
            if (!this.isTrapTrigger() && this.isTrapState()) {
                this.deactiveAnimation();
            } else if (this.isTrapTrigger() && this.isTrapState()) {
                this.activeAnimation();
            } else if (!this.isTrapState()) {
                this.activeAnimation();
            }
        }
    }

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
     * @return Whether an entity has trap Trigger, as a {@link Boolean}.
     */
    public boolean isTrapTrigger() {
        return this.getEntityData().get(DATA_TRAP_TRIGGER_ID);
    }

    /**
     * Sets whether an entity has trap Trigger.
     *
     * @param trapState The {@link Boolean} value.
     */
    public void setTrapTrigger(boolean trapState) {
        this.getEntityData().set(DATA_TRAP_TRIGGER_ID, trapState);
    }


    /**
     * @return Whether an entity is trap, as a {@link Boolean}.
     */
    public boolean isTrapState() {
        return this.getEntityData().get(DATA_TRAP_ID);
    }

    /**
     * Sets whether an entity is trap.
     *
     * @param trapState The {@link Boolean} value.
     */
    public void setTrapState(boolean trapState) {
        this.getEntityData().set(DATA_TRAP_ID, trapState);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_CARRION_SPROUT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_CARRION_SPROUT_DEATH.get();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return this.distanceTo(entity) <= 8.0 && super.hasLineOfSight(entity);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
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
    public void addAdditionalSaveData(ValueOutput output) {
        output.putBoolean("PlayerGrown", this.isPlayerGrown());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        this.setPlayerGrown(input.getBooleanOr("PlayerGrown", false));
    }
}

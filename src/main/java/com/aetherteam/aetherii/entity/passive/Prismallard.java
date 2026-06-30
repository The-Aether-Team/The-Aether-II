package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.goal.LookAtThreatGoal;
import com.aetherteam.aetherii.entity.ai.navigator.FloatWaterPathNavigation;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Prismallard extends AetherAnimal {
    private static final EntityDimensions BABY_DIMENSIONS = EntityDimensions.scalable(0.3F, 0.4F);

    private static final EntityDataAccessor<Boolean> DATA_THREAT = SynchedEntityData.defineId(Prismallard.class, EntityDataSerializers.BOOLEAN);

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    public float flapping = 1.0F;
    private float nextFlap = 1.0F;
    public int eggTime;
    private float oDisplayScale;
    private float displayScale;

    public Prismallard(EntityType<? extends Prismallard> type, Level level) {
        super(type, level);
        this.eggTime = this.random.nextInt(6000) + 6000;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_THREAT, false);
    }

    public void setThreat(boolean threat) {
        this.entityData.set(DATA_THREAT, threat);
    }

    public boolean isThreat() {
        return this.entityData.get(DATA_THREAT);
    }

    /**
     * Navigation for falling entities.
     *
     * @param level The {@link Level}.
     * @return The {@link PathNavigation} class.
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FloatWaterPathNavigation(this, level);
    }

    /**
     * @return A {@link Float} for the calculated movement speed, both when mounted and not mounted.
     */
    @Override
    public float getFlyingSpeed() {
        if (this.isEffectiveAi() && !this.onGround()) {
            return this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
        } else {
            return super.getFlyingSpeed();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(AetherIITags.Items.PRISMALLARD_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new LookAtThreatGoal(this, Monster.class, 8.0F));
        this.goalSelector.addGoal(6, new LookAtThreatGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.85F;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return this.isBaby() ? 0.3F : super.getStandingEyeHeight(pose, size);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = this.flapSpeed + (this.onGround() || this.isInWater() ? -1.0F : 2.0F) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && !this.isInWater() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < 0.0) {
            this.setDeltaMovement(movement.multiply(1.0, 0.6, 1.0));
        }

        this.flap = this.flap + this.flapping * 2.0F;
        if (this.level() instanceof ServerLevel level && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.spawnAtLocation(AetherIIItems.PRISMALLARD_EGG.get());
            this.playSound(AetherIISoundEvents.ENTITY_PRISMALLARD_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.gameEvent(GameEvent.ENTITY_PLACE);

            this.eggTime = this.random.nextInt(6000) + 6000;
        }
        if (this.level().isClientSide()) {
            this.oDisplayScale = this.displayScale;
            if (this.isThreat()) {
                this.displayScale = Mth.clamp(this.displayScale + 0.1F, 0, 1F);
            } else {
                this.displayScale = Mth.clamp(this.displayScale - 0.1F, 0, 1F);

            }
        }
    }

    public float getDisplayAnimationScale(float a) {
        return Mth.lerp(a, this.oDisplayScale, this.displayScale);
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.playSound(AetherIISoundEvents.ENTITY_PRISMALLARD_FLAP.get(), 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(AetherIISoundEvents.ENTITY_PRISMALLARD_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_PRISMALLARD_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_PRISMALLARD_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_PRISMALLARD_DEATH.get();
    }

    public Prismallard getBreedOffspring(ServerLevel level, AgeableMob partner) {
        Prismallard baby = AetherIIEntityTypes.PRISMALLARD.get().create(level);
        return baby;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData groupData, CompoundTag dataTag) {
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData, dataTag);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }


    public static boolean checkPrismallardSpawnRules(EntityType<Prismallard> animal, LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        do {
            checkPos.move(Direction.UP);
        }
        while (level.getFluidState(checkPos).is(FluidTags.WATER));

        return level.getBlockState(checkPos).isAir() && level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(AetherIITags.Items.PRISMALLARD_FOOD);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("EggLayTime")) {
            this.eggTime = tag.getInt("EggLayTime");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("EggLayTime", this.eggTime);
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).yBodyRot = this.yBodyRot;
        }
    }
}

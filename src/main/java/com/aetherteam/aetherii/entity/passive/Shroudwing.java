package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIShroudwingVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.ai.controller.ShroudwingMoveControl;
import com.aetherteam.aetherii.entity.ai.navigator.FlyAndGroundInsectPathNavigation;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import com.aetherteam.aetherii.entity.variant.SpawnContext;
import com.aetherteam.aetherii.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;

public class Shroudwing extends Insect {
    private static final EntityDataAccessor<Holder<ShroudwingVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Shroudwing.class, AetherIIDataSerializers.SHROUDWING_VARIANT.get());

    public static int LAND_EVENT = 101;
    public static int TAKEOFF_EVENT = 102;
    private int fullyFlyTick;

    public AnimationState flyingAnimationState = new AnimationState();
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState walkAnimationState = new AnimationState();
    public AnimationState takeoffAnimationState = new AnimationState();

    public Shroudwing(EntityType<? extends Shroudwing> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new ShroudwingMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new RandomWalkAroundGoal(this));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, this.level().registryAccess().registryOrThrow(AetherIIRegistries.SHROUDWING_VARIANT).getHolder(AetherIIShroudwingVariants.SCARAB).orElseThrow());
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyAndGroundInsectPathNavigation flyingpathnavigation = new FlyAndGroundInsectPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        return flyingpathnavigation;
    }

    @Override
    public void setRestWithAnimation(boolean rest) {
        super.setRestWithAnimation(rest);
        if (rest) {
            this.level().broadcastEntityEvent(this, (byte) LAND_EVENT);
        } else {
            this.level().broadcastEntityEvent(this, (byte) TAKEOFF_EVENT);
            this.fullyFlyTick = 21;
        }

        if (!this.level().isClientSide()) {
            this.getNavigation().stop();
        }
    }

    @Override
    public void stopRest() {
        super.stopRest();
    }

    //using to adjust fly animation
    public boolean isFullyFlying() {
        return this.fullyFlyTick <= 0 && !this.isRest();
    }

    public boolean shouldStayGround() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            //check when loaded insect are rest in first tick. set the default animation
            if (this.tickCount < 2) {
                if (this.isRest()) {
                    this.landAnimationState.stop();
                    this.walkAnimationState.startIfStopped(this.tickCount);
                } else {
                    this.takeoffAnimationState.stop();
                    this.flyingAnimationState.startIfStopped(this.tickCount);
                }
            } else {
                if (this.landAnimationState.isStarted() && this.getAnimationTime(this.landAnimationState) >= 1417) {
                    this.landAnimationState.stop();
                    this.walkAnimationState.startIfStopped(this.tickCount);
                } else if (this.takeoffAnimationState.isStarted() && this.getAnimationTime(this.takeoffAnimationState) >= 2167F) {
                    this.takeoffAnimationState.stop();
                    this.flyingAnimationState.startIfStopped(this.tickCount);
                } else if (!this.walkAnimationState.isStarted() && !this.flyingAnimationState.isStarted() && !this.landAnimationState.isStarted() && !this.takeoffAnimationState.isStarted()) {
                    this.landAnimationState.stop();
                    this.flyingAnimationState.start(this.tickCount);
                }
            }
        } else {
            if (!this.isRest() && !this.isFullyFlying()) {
                this.fullyFlyTick--;
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == LAND_EVENT) {
            this.flyingAnimationState.stop();
            this.landAnimationState.start(this.tickCount);
        } else if (id == TAKEOFF_EVENT) {
            this.walkAnimationState.stop();
            this.takeoffAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        if (spawnData instanceof ShroudwingGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<ShroudwingVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIRegistries.SHROUDWING_VARIANT);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    public Holder<ShroudwingVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<ShroudwingVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.getVariant().unwrapKey().ifPresent(key -> tag.putString("variant", key.location().toString()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("variant")) {
            ResourceKey<ShroudwingVariant> key = ResourceKey.create(AetherIIRegistries.SHROUDWING_VARIANT, new ResourceLocation(tag.getString("variant")));
            this.level().registryAccess().registryOrThrow(AetherIIRegistries.SHROUDWING_VARIANT).getHolder(key).ifPresent(this::setVariant);
        }
    }

    private long getAnimationTime(AnimationState animationState) {
        animationState.updateTime(this.tickCount, 1.0F);
        return animationState.getAccumulatedTime();
    }

    public static class ShroudwingGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<ShroudwingVariant> type;

        public ShroudwingGroupData(Holder<ShroudwingVariant> type) {
            super(false);
            this.type = type;
        }
    }

    public static class RandomWalkAroundGoal extends WaterAvoidingRandomStrollGoal {
        private final Insect insect;

        public RandomWalkAroundGoal(Insect insect) {
            super(insect, 1.0F);
            this.insect = insect;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.insect.isRest() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.insect.isRest() && super.canContinueToUse();
        }

    }
}

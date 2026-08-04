package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBirdVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.controller.BirdMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.FleeRainGoal;
import com.aetherteam.aetherii.entity.ai.goal.FlyingLookGoal;
import com.aetherteam.aetherii.entity.variant.BirdVariant;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Optional;

public class Bird extends Insect {
    private static final EntityDataAccessor<Holder<BirdVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Bird.class, AetherIIDataSerializers.BIRD_VARIANT.get());
    public static int LAND_EVENT = 101;
    public static int TAKE_OFF_EVENT = 102;
    public AnimationState landAnimationState = new AnimationState();
    public AnimationState takeOffAnimationState = new AnimationState();

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public Bird(EntityType<? extends Bird> entityType, Level level) {
        super(entityType, level);
    }

    public Bird(Level level) {
        super(AetherIIEntityTypes.BIRD.get(), level);
        this.moveControl = new BirdMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0, 1.1, livingEntity -> {
            return !livingEntity.isShiftKeyDown() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity);
        }) {
            @Override
            public void start() {
                super.start();
                stopRest();
            }
        });
        this.goalSelector.addGoal(4, new FleeRainGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new RandomBirdFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new FlyingLookGoal(this));
    }

    @Override
    public void travel(Vec3 p_415638_) {
        if (this.isRest()) {
            super.travel(p_415638_);
        } else {
            this.travelFlying(p_415638_, 0.02F);
        }
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIBirdVariants.CHONK_GOLDBILL));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (spawnData instanceof BirdGroupData groupData) {
            this.setVariant(groupData.type);
        } else {
            Optional<? extends Holder<BirdVariant>> optional = VariantUtils.selectVariantToSpawn(SpawnContext.create(level, this.blockPosition()), AetherIIRegistries.BIRD_VARIANT);
            optional.ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    public Holder<BirdVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<BirdVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (float) (!this.isRest() && !this.isPassenger() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.isRest() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        this.flap += this.flapping * 2.0F;
    }

    @Override
    protected void onFlap() {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        VariantUtils.writeVariant(valueOutput, this.getVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        VariantUtils.readVariant(valueInput, AetherIIRegistries.BIRD_VARIANT).ifPresent(this::setVariant);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_218498_) {
        if (DATA_REST.equals(p_218498_)) {
            if (this.isRest()) {
                this.takeOffAnimationState.stop();
                this.landAnimationState.start(this.tickCount);
            } else {
                this.landAnimationState.stop();
                this.takeOffAnimationState.start(this.tickCount);
            }
        }

        super.onSyncedDataUpdated(p_218498_);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == LAND_EVENT) {
            this.takeOffAnimationState.stop();
            this.landAnimationState.start(this.tickCount);
        } else if (id == TAKE_OFF_EVENT) {
            this.landAnimationState.stop();
            this.takeOffAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public static class BirdGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<BirdVariant> type;

        public BirdGroupData(Holder<BirdVariant> type) {
            super(false);
            this.type = type;
        }
    }

    public enum BirdType implements StringRepresentable {
        FINCH,
        MACAW,
        CHONK,
        PHEASANT,
        WARBLER;

        public static final Codec<BirdType> CODEC = StringRepresentable.fromEnum(BirdType::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public static class RandomBirdFloatAroundGoal extends Goal {
        private final Insect insect;

        public RandomBirdFloatAroundGoal(Insect insect) {
            this.insect = insect;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.insect.getNavigation().isDone() && !this.insect.isRest();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            this.randomFly();
        }

        private void randomFly() {
            RandomSource random = this.insect.getRandom();
            double d0 = this.insect.getX() + (random.nextFloat() * 2.0F - 1.0F) * 10.0F;
            double d1 = this.insect.getY() + (random.nextFloat() * 2.0F - 1.0F) * 10.0F;
            double d2 = this.insect.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 10.0F;
            this.insect.getNavigation().moveTo(d0, d1, d2, 1.0);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}

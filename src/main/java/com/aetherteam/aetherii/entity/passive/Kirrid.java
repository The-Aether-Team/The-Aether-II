package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Kirrid extends AetherAnimal {
    private static final EntityDataAccessor<Boolean> DATA_HAS_HORN = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);

    protected static final ImmutableList<SensorType<? extends Sensor<? super Kirrid>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
            MemoryModuleType.LONG_JUMP_MID_JUMP,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.RAM_COOLDOWN_TICKS,
            MemoryModuleType.RAM_TARGET,
            MemoryModuleType.IS_PANICKING
    );

    public AnimationState jumpAnimationState = new AnimationState();

    public Kirrid(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.getPose() == Pose.LONG_JUMPING) {
                this.jumpAnimationState.start(this.tickCount);
            } else {
                this.jumpAnimationState.stop();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAS_HORN, true);
    }

    public boolean hasHorn() {
        return this.entityData.get(DATA_HAS_HORN);
    }


    public void setHorn(boolean horn) {
        this.entityData.set(DATA_HAS_HORN, horn);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Horn", this.hasHorn());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setHorn(pCompound.getBoolean("Horn"));
    }

    @Override
    protected Brain.Provider<Kirrid> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return KirridAi.makeBrain(this.brainProvider().makeBrain(pDynamic));
    }

    @Override
    public Brain<Kirrid> getBrain() {
        return (Brain<Kirrid>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("kirridBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("kirridActivityUpdate");
        KirridAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor pLevel,
            DifficultyInstance pDifficulty,
            MobSpawnType pReason,
            @javax.annotation.Nullable SpawnGroupData pSpawnData,
            @javax.annotation.Nullable CompoundTag pDataTag
    ) {
        RandomSource randomsource = pLevel.getRandom();
        KirridAi.initMemories(this, randomsource);
        this.ageBoundaryReached();

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }


    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(AetherIITags.Items.KIRRID_TEMPTATION_ITEMS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Kirrid kirrid = AetherIIEntityTypes.KIRRID.get().create(pLevel);
        KirridAi.initMemories(kirrid, this.random);
        return kirrid;
    }

    public boolean dropHorn() {
        return true;
    }
}

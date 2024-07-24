package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.brain.BurrukaiAi;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.Nullable;

public class Burrukai extends AetherAnimal implements IShearable {
    protected static final ImmutableList<SensorType<? extends Sensor<? super Burrukai>>> SENSOR_TYPES = ImmutableList.of(
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
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY
    );

    private final EntityType<? extends Burrukai> variantType;

    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState rushAnimationState = new AnimationState();

    public Burrukai(EntityType<? extends Burrukai> type, Level level) {
        super(type, level);
        this.variantType = type;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.ramAnimationState.start(this.tickCount);
        } else if (pId == 62) {
            this.ramAnimationState.stop();
        } else if (pId == 64) {
            this.rushAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected Brain.Provider<Burrukai> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return BurrukaiAi.makeBrain(this.variantType, this, this.brainProvider().makeBrain(pDynamic));
    }

    @Override
    public Brain<Burrukai> getBrain() {
        return (Brain<Burrukai>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("burrukaiBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("burrukaiActivityUpdate");
        BurrukaiAi.updateActivity(this);
        this.level().getProfiler().pop();
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
            @Nullable SpawnGroupData pSpawnData
    ) {
        RandomSource randomsource = pLevel.getRandom();
        BurrukaiAi.initMemories(this, randomsource);
        this.ageBoundaryReached();

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (this.level().isClientSide) {
            return false;
        } else {
            if (flag && pSource.getEntity() instanceof LivingEntity) {
                BurrukaiAi.maybeRetaliate(this, (LivingEntity) pSource.getEntity());
            }

            return flag;
        }
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18.0)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0F)
                .add(Attributes.ARMOR, 4.0);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(AetherIITags.Items.BURRUKAI_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Burrukai burrukai = this.variantType.create(pLevel);
        BurrukaiAi.initMemories(burrukai, this.random);
        return burrukai;
    }
}

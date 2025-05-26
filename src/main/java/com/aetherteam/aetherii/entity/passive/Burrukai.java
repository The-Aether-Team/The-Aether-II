package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.brain.BurrukaiAi;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
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
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Burrukai extends AetherAnimal {
    public static int RAM_START_EVENT = 100;
    public static int RAM_STOP_EVENT = 101;

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

    public Burrukai(EntityType<? extends Burrukai> type, Level level) {
        super(type, level);
        this.variantType = type;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0F)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        BurrukaiAi.initMemories(this, level.getRandom());
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected Brain.Provider<Burrukai> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return BurrukaiAi.makeBrain(this.variantType, this, this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Burrukai> getBrain() {
        return (Brain<Burrukai>) super.getBrain();
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        ProfilerFiller profiler = Profiler.get();

        profiler.push("burrukaiBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        profiler.pop();

        profiler.push("burrukaiActivityUpdate");
        BurrukaiAi.updateActivity(this);
        profiler.pop();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == RAM_START_EVENT) {
            this.ramAnimationState.start(this.tickCount);
        } else if (id == RAM_STOP_EVENT) {
            this.ramAnimationState.stop();
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        boolean flag = super.hurtServer(serverLevel, source, amount);
        if (this.level().isClientSide()) {
            return false;
        } else {
            if (flag && source.getEntity() instanceof LivingEntity livingEntity) {
                BurrukaiAi.maybeRetaliate(serverLevel, this, livingEntity);
            }
            return flag;
        }
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.BURRUKAI_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_BURRUKAI_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_BURRUKAI_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_BURRUKAI_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_BURRUKAI_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        EntityType<? extends Burrukai> variant = level.getRandom().nextBoolean() ? this.variantType : ((Burrukai) otherParent).variantType;
        Burrukai child = variant.create(level, EntitySpawnReason.BREEDING);
        if (child != null) {
            BurrukaiAi.initMemories(child, this.random);
        }
        return child;
    }
}

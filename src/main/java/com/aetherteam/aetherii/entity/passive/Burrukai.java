package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.brain.BurrukaiAi;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.common.ForgeMod;

public class Burrukai extends AetherAnimal {
    public static int RAM_START_EVENT = 100;
    public static int RAM_STOP_EVENT = 101;

    private final EntityType<? extends Burrukai> variantType;

    public AnimationState ramAnimationState = new AnimationState();

    public Burrukai(EntityType<? extends Burrukai> type, Level level) {
        super(type, level);
        this.variantType = type;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0F)
                .add(Attributes.ARMOR, 6.0)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        BurrukaiAi.initMemories(this, level.getRandom());
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    protected Brain<Burrukai> makeBrain(Dynamic<?> dynamic) {
        return BurrukaiAi.makeBrain(this, dynamic);
    }


    @Override
    public Brain<Burrukai> getBrain() {
        return (Brain<Burrukai>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();

        profiler.push("burrukaiBrain");
        this.getBrain().tick(serverLevel, this);
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
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (this.level().isClientSide()) {
            return false;
        } else {
            if (flag && this.level() instanceof ServerLevel serverLevel && source.getEntity() instanceof LivingEntity livingEntity) {
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
        Burrukai child = variant.create(level);
        if (child != null) {
            BurrukaiAi.initMemories(child, this.random);
        }
        return child;
    }
}

package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.client.sound.ClientSoundHooks;
import com.aetherteam.aetherii.entity.DiggingMob;
import com.aetherteam.aetherii.entity.ai.brain.TaegoreAi;
import com.aetherteam.aetherii.entity.ai.brain.memory.AetherIIMemoryModuleTypes;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.common.ForgeMod;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class Taegore extends AetherAnimal implements DiggingMob {
    public static int SEARCHING_EVENT = 100;
    public static int DIGGING_START_EVENT = 101;
    public static int DIGGING_TICK_EVENT = 102;
    public static int DIGGING_STOP_EVENT = 103;

    private final EntityType<? extends Taegore> variantType;

    public AnimationState digAnimationState = new AnimationState();
    public AnimationState digStartAnimationState = new AnimationState();
    public AnimationState digEndAnimationState = new AnimationState();

    public Taegore(EntityType<? extends Taegore> type, Level level) {
        super(type, level);
        this.variantType = type;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(ForgeMod.STEP_HEIGHT_ADDITION.get(), 1.0);
    }

    @Override
    protected Brain<Taegore> makeBrain(Dynamic<?> dynamic) {
        return TaegoreAi.makeBrain(this, dynamic);
    }

    @Override
    public Brain<Taegore> getBrain() {
        return (Brain<Taegore>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();

        profiler.push("taegoreBrain");
        this.getBrain().tick(serverLevel, this);
        profiler.pop();

        profiler.push("taegoreActivityUpdate");
        TaegoreAi.updateActivity(this);
        profiler.pop();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == SEARCHING_EVENT) {
            this.playSearchingSound();
        } else if (id == DIGGING_START_EVENT) {
            this.digStartAnimationState.start(this.tickCount);
            ClientSoundHooks.playDiggingSoundInstance(this, AetherIISoundEvents.ENTITY_TAEGORE_DIGGING.get());
        } else if (id == DIGGING_TICK_EVENT) {
            if (this.getAnimationTime(this.digStartAnimationState) >= 3000) {
                this.digStartAnimationState.stop();
                this.digAnimationState.startIfStopped(this.tickCount);
                this.emitDiggingParticles(this.digAnimationState);
            }
        } else if (id == DIGGING_STOP_EVENT) {
            this.digAnimationState.stop();
            this.digEndAnimationState.start(this.tickCount);
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), AetherIISoundEvents.ENTITY_TAEGORE_DIGGING_STOP.get(), this.getSoundSource(), 1.0F, 1.0F, false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public boolean canSearch() {
        return !this.isTempted() && !this.isPanicking() && !this.isInWater() && !this.isInLove() && this.onGround() && !this.isPassenger() && !this.isLeashed();
    }

    public boolean canDig() {
        return !this.isPanicking() && !this.isTempted() && !this.isBaby() && !this.isInWater() && this.onGround() && !this.isPassenger() && this.canDig(this.getHeadBlock().below());
    }

    public boolean canDig(BlockPos pos) {
        return this.level().getBlockState(pos).is(AetherIITags.Blocks.TAEGORE_DIGGABLE_BLOCK)
                && this.getExploredPositions().noneMatch((globalPos) -> GlobalPos.of(this.level().dimension(), pos).equals(globalPos))
                && Optional.ofNullable(this.getNavigation().createPath(pos, 1)).map(Path::canReach).orElse(false);
    }

    public boolean isTempted() {
        return this.getBrain().getMemory(MemoryModuleType.IS_TEMPTED).orElse(false);
    }

    public boolean isPanicking() {
        return this.getBrain().getMemory(MemoryModuleType.IS_PANICKING).orElse(false);
    }

    public boolean isSearching() {
        return this.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_SEARCH_TARGET.get()).isPresent();
    }

    public boolean isDigging() {
        return this.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_DIGGING.get()).orElse(false);
    }

    public Stream<GlobalPos> getExploredPositions() {
        return this.getBrain().getMemory(AetherIIMemoryModuleTypes.TAEGORE_EXPLORED_POSITIONS.get()).stream().flatMap(Collection::stream);
    }

    public BlockPos getHeadBlock() {
        Vec3 vec3 = this.getHeadPosition();
        return BlockPos.containing(vec3.x(), this.getY() + (double) 0.2F, vec3.z());
    }

    public Vec3 getHeadPosition() {
        return this.position().add(this.getLookAngle().scale(1.5 / this.getLookAngle().length()));
    }

    @Override
    public boolean canPlayDiggingSound() {
        return this.isSearching() || this.isDigging();
    }

    private void playSearchingSound() {
        if (this.level().isClientSide() && this.tickCount % 20 == 0) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), AetherIISoundEvents.ENTITY_TAEGORE_SEARCHING.get(), this.getSoundSource(), 1.0F, 1.0F, false);
        }
    }

    private void emitDiggingParticles(AnimationState animationState) {
        long animationTime = this.getAnimationTime(animationState);
        boolean flag = (animationTime % 4500 > 250 && animationTime % 4500 < 750)
                || (animationTime % 4500 > 1500 && animationTime % 4500 < 1750)
                || (animationTime % 4500 > 3000 && animationTime % 4500 < 3250);
        if (flag) {
            Vec3 vecPos = this.getHeadPosition();
            BlockPos pos = this.getHeadBlock();
            BlockState state = this.level().getBlockState(pos.below());
            if (state.getRenderShape() != RenderShape.INVISIBLE) {
                for (int i = 0; i < 30; ++i) {
                    Vec3 vec3 = Vec3.atCenterOf(pos).add(0.0F, -0.65F, 0.0F);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), vecPos.x, vec3.y, vecPos.z, 0.0F, 0.0F, 0.0F);
                }
                if (this.tickCount % 10 == 0) {
                    this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), state.getSoundType(this.level(), pos.below(), this).getHitSound(), this.getSoundSource(), 0.5F, 0.5F, false);
                }
            }
        }
        if (this.tickCount % 10 == 0) {
            this.level().gameEvent(GameEvent.ENTITY_INTERACT, this.getHeadBlock(), GameEvent.Context.of(this));
        }
    }

    private long getAnimationTime(AnimationState animationState) {
        animationState.updateTime(this.tickCount, 1.0F);
        return animationState.getAccumulatedTime();
    }

    @Override
    public int getMaxHeadYRot() {
        return 30;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.TAEGORE_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.isSearching() && !this.isDigging()) {
            return AetherIISoundEvents.ENTITY_TAEGORE_AMBIENT.get();
        }
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_TAEGORE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_TAEGORE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_TAEGORE_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        EntityType<? extends Taegore> variant = level.getRandom().nextBoolean() ? this.variantType : ((Taegore) otherParent).variantType;
        return variant.create(level);
    }

}

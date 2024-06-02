package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Kirrid extends AetherAnimal implements IShearable {
    private static final EntityDataAccessor<Boolean> DATA_HAS_PLATE = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_WOOL = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);

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
            AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(),
            AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(),
            MemoryModuleType.IS_PANICKING
    );

    private int woolGrowTime = -1;
    private int plateGrowTime = 0;

    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();

    public Kirrid(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.moveControl = new KirridMoveControl(this);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_POSE.equals(pKey)) {
            Pose pose = this.getPose();
            if (pose == Pose.LONG_JUMPING) {
                this.jumpAnimationState.start(this.tickCount);
            } else {
                this.jumpAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.ramAnimationState.start(this.tickCount);
        } else if (pId == 62) {
            this.ramAnimationState.stop();
        } else if (pId == 64) {
            this.eatAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HAS_PLATE, true);
        this.entityData.define(DATA_HAS_WOOL, true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() ? super.getMaxFallDistance() : 14;
    }

    private void midairJump() {
        Vec3 motion = this.getDeltaMovement();
        this.setDeltaMovement(new Vec3(motion.x(), 0.25, motion.z()));
    }

    /**
     * @return A {@link Float} for the midair speed of this entity.
     */
    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
    }


    @Override
    public void ate() {
        super.ate();
        if (this.woolGrowTime == -1) {
            this.woolGrowTime = 0;
        } else {
            this.woolGrowTime += this.random.nextInt(30) + 30;
        }
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    public boolean hasPlate() {
        return this.entityData.get(DATA_HAS_PLATE);
    }


    public void setPlate(boolean horn) {
        this.entityData.set(DATA_HAS_PLATE, horn);
    }

    public boolean hasWool() {
        return this.entityData.get(DATA_HAS_WOOL);
    }


    public void setWool(boolean wool) {
        this.entityData.set(DATA_HAS_WOOL, wool);
    }

    @Override
    public boolean isShearable(ItemStack item, Level level, BlockPos pos) {
        return this.hasWool();
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        if (this.hasWool()) {
            return this.getType().getDefaultLootTable();
        } else {
            return AetherIILoot.KIRRID_FUR;
        }
    }

    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);

        this.setWool(false);
        return List.of(new ItemStack(AetherIIBlocks.CLOUDWOOL, 1 + this.random.nextInt(2)));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("HasPlate", this.hasPlate());
        pCompound.putBoolean("HasWool", this.hasWool());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setPlate(pCompound.getBoolean("HasPlate"));
        this.setWool(pCompound.getBoolean("HasWool"));
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
    public void tick() {
        super.tick();
        this.handleFallSpeed();
    }

    /**
     * Makes this entity fall slowly.
     */
    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(NeoForgeMod.ENTITY_GRAVITY.value());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("kirridBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("kirridActivityUpdate");
        KirridAi.updateActivity(this);
        this.level().getProfiler().pop();

        if (this.woolGrowTime >= 2400) {
            this.setWool(true);
            this.woolGrowTime = -1;
        } else if (woolGrowTime >= 0) {
            this.woolGrowTime++;
        }

        if (this.plateGrowTime >= 6000) {
            this.setPlate(true);
            this.plateGrowTime = 0;
        } else if (!this.hasPlate()) {
            this.plateGrowTime++;
        }

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

    public boolean dropPlate() {
        if (this.random.nextFloat() < 0.01F) {
            this.setPlate(false);
            return true;
        }
        return false;
    }

    /**
     * Handles jumping movement for the Aerbunny.
     */
    public static class KirridMoveControl extends MoveControl {
        private final Kirrid kirrid;

        public KirridMoveControl(Kirrid kirrid) {
            super(kirrid);
            this.kirrid = kirrid;
        }

        @Override
        public void tick() {
            super.tick();
            if (this.hasWanted() && this.kirrid.zza != 0) {
                if (!this.kirrid.onGround() && this.kirrid.getPose() == Pose.LONG_JUMPING) {
                    int x = Mth.floor(this.kirrid.getX());
                    int y = Mth.floor(this.kirrid.getBoundingBox().minY);
                    int z = Mth.floor(this.kirrid.getZ());
                    if (this.checkForSurfaces(this.kirrid.level(), x, y, z) && !this.kirrid.horizontalCollision) {
                        this.kirrid.midairJump();
                    }
                }
            }
        }

        private boolean checkForSurfaces(Level level, int x, int y, int z) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
            if (level.getBlockState(pos.setY(y - 1)).isAir()) {
                return false;
            }
            return level.getBlockState(pos.setY(y + 2)).isAir() && level.getBlockState(pos.setY(y + 1)).isAir();
        }
    }
}

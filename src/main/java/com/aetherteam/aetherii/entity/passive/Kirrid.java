package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.navigator.KirridPathNavigation;
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
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.ai.control.JumpControl;
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
import net.minecraft.world.level.pathfinder.Path;
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

    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;

    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();

    public Kirrid(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.moveControl = new KirridMoveControl(this);
        this.jumpControl = new KirridJumpControl(this);
        this.setSpeedModifier(0.0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_POSE.equals(pKey)) {
            Pose pose = this.getPose();
            if (pose == Pose.LONG_JUMPING) {
                this.jumpAnimationState.start(this.tickCount);
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
        return new KirridPathNavigation(this, level);
    }


    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() ? super.getMaxFallDistance() : 14;
    }

    @Override
    protected float getJumpPower() {
        float f = 0.5F;
        if (this.horizontalCollision || this.moveControl.hasWanted() && this.moveControl.getWantedY() > this.getY() + 0.5) {
            f = 0.5F;
            if (this.moveControl.hasWanted() && this.moveControl.getWantedY() > this.getY() + 1.5) {
                f = 1.5F;
            }
        }

        Path path = this.navigation.getPath();
        if (path != null && !path.isDone()) {
            Vec3 vec3 = path.getNextEntityPos(this);
            if (vec3.y > this.getY() + 0.5) {
                f = 0.5F;
            }
            if (vec3.y > this.getY() + 1.5) {
                f = 1.5F;
            }
        }

        return f + this.getJumpBoostPower();
    }
    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0) {
            double d1 = this.getDeltaMovement().horizontalDistanceSqr();
            if (d1 < 0.01) {
                this.moveRelative(0.1F, new Vec3(0.0, 0.0, 1.0));
            }
        }
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
        pCompound.putInt("PlateGrowTime", this.plateGrowTime);
        pCompound.putInt("WoolGrowTime", this.woolGrowTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setPlate(pCompound.getBoolean("HasPlate"));
        this.setWool(pCompound.getBoolean("HasWool"));
        this.plateGrowTime = pCompound.getInt("PlateGrowTime");
        this.woolGrowTime = pCompound.getInt("WoolGrowTime");
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

    @Override
    protected float getFlyingSpeed() {
        return this.getControllingPassenger() instanceof Player ? super.getFlyingSpeed() : this.getSpeed() * 0.1F;
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

        if (this.jumpDelayTicks > 0) {
            --this.jumpDelayTicks;
        }

        if (this.onGround()) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }
            KirridJumpControl kirridJumpControl = (KirridJumpControl) this.jumpControl;
            if (!kirridJumpControl.wantJump()) {
                if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
                    Path path = this.navigation.getPath();
                    Vec3 vec3 = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && !path.isDone()) {
                        vec3 = path.getNextEntityPos(this);
                    }

                    this.facePoint(vec3.x, vec3.z);
                    this.startJumping();
                }
            } else if (!kirridJumpControl.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround();

        super.customServerAiStep();
    }

    private void facePoint(double pX, double pZ) {
        this.setYRot((float) (Mth.atan2(pZ - this.getZ(), pX - this.getX()) * 180.0F / (float) Math.PI) - 90.0F);
    }

    private void enableJumpControl() {
        ((KirridJumpControl) this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((KirridJumpControl) this.jumpControl).setCanJump(false);
    }

    private void setLandingDelay() {
        if (this.moveControl.getSpeedModifier() < 0.6) {
            this.jumpDelayTicks = 10;
        } else {
            this.jumpDelayTicks = 1;
        }
    }

    private void checkLandingDelay() {
        this.setLandingDelay();
        this.disableJumpControl();
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


    protected SoundEvent getJumpSound() {
        return SoundEvents.GOAT_LONG_JUMP;
    }

    public void setSpeedModifier(double pSpeedModifier) {
        this.getNavigation().setSpeedModifier(pSpeedModifier);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), pSpeedModifier);
    }

    @Override
    public void setJumping(boolean pJumping) {
        super.setJumping(pJumping);
        if (pJumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    public static class KirridJumpControl extends JumpControl {
        private final Kirrid kirrid;
        private boolean canJump;

        public KirridJumpControl(Kirrid pkirrid) {
            super(pkirrid);
            this.kirrid = pkirrid;
        }

        public boolean wantJump() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean pCanJump) {
            this.canJump = pCanJump;
        }

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        @Override
        public void tick() {
            if (this.jump) {
                this.kirrid.startJumping();
                this.jump = false;
            }
        }
    }

    /**
     * Handles jumping movement for the Aerbunny.
     */
    public static class KirridMoveControl extends MoveControl {
        private final Kirrid kirrid;

        private double nextJumpSpeed;

        public KirridMoveControl(Kirrid kirrid) {
            super(kirrid);
            this.kirrid = kirrid;
        }

        @Override
        public void tick() {
            if (this.kirrid.onGround() && !this.kirrid.jumping && !((KirridJumpControl) this.kirrid.jumpControl).wantJump()) {
                this.kirrid.setSpeedModifier(0.0);
            } else if (this.hasWanted()) {
                this.kirrid.setSpeedModifier(this.nextJumpSpeed);
            }
            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        @Override
        public void setWantedPosition(double pX, double pY, double pZ, double pSpeed) {
            if (this.kirrid.isInWater()) {
                pSpeed = 1.5;
            }

            super.setWantedPosition(pX, pY, pZ, pSpeed);
            if (pSpeed > 0.0) {
                this.nextJumpSpeed = pSpeed;
            }
        }
    }
}

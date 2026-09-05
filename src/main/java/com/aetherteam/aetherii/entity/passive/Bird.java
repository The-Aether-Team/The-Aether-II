package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBirdVariants;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.variant.BirdVariant;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;

public class Bird extends PathfinderMob implements FlyingAnimal {
    private static final EntityDataAccessor<Holder<BirdVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(Bird.class, AetherIIDataSerializers.BIRD_VARIANT.get());

    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public Bird(EntityType<? extends Bird> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, -1.0F);
        this.setPathfindingMalus(PathType.FIRE, -1.0F);
    }

    public Bird(Level level) {
        super(AetherIIEntityTypes.BIRD.get(), level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(2, new BirdWanderGoal(this, 1.0F));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        return flyingPathNavigation;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 6.0F).add(Attributes.FLYING_SPEED, 1.2F).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), AetherIIBirdVariants.CHONK_GOLDBILL));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        if (DATA_VARIANT_ID.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
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

    @Override
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += (!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping *= 0.9F;
        Vec3 movement = this.getDeltaMovement();
        if (!this.onGround() && movement.y < 0.0F) {
            this.setDeltaMovement(movement.multiply(1.0F, 0.6, 1.0F));
        }

        this.flap += this.flapping * 2.0F;
    }

    @Override
    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    @Override
    protected void onFlap() {
        this.playSound(this.getVariant().value().type().getFlySound(), 0.15F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    protected void checkFallDamage(double ya, boolean onGround, BlockState onState, BlockPos pos) { }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void doPush(Entity entity) {
        if (!(entity instanceof Player)) {
            super.doPush(entity);
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return this.getVariant().value().type().getAmbientSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.getVariant().value().type().getHurtSound();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.getVariant().value().type().getDeathSound();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        this.playSound(this.getVariant().value().type().getStepSound(), 0.15F, 1.0F);
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        return this.getVariant().value().type().getDimensions();
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

    public Holder<BirdVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<BirdVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    public enum BirdType implements StringRepresentable {
        CHONK(EntityDimensions.scalable(0.65F, 0.75F).withEyeHeight(0.5F), SoundEvents.PARROT_FLY, SoundEvents.PARROT_AMBIENT, SoundEvents.PARROT_HURT, SoundEvents.PARROT_DEATH, SoundEvents.PARROT_STEP),
        FINCH(EntityDimensions.scalable(0.35F, 0.45F).withEyeHeight(0.28F), SoundEvents.PARROT_FLY, SoundEvents.PARROT_AMBIENT, SoundEvents.PARROT_HURT, SoundEvents.PARROT_DEATH, SoundEvents.PARROT_STEP),
        MACAW(EntityDimensions.scalable(0.5F, 0.9F).withEyeHeight(0.6F), SoundEvents.PARROT_FLY, SoundEvents.PARROT_AMBIENT, SoundEvents.PARROT_HURT, SoundEvents.PARROT_DEATH, SoundEvents.PARROT_STEP),
        PHEASANT(EntityDimensions.scalable(0.65F, 0.95F).withEyeHeight(0.75F), SoundEvents.PARROT_FLY, SoundEvents.PARROT_AMBIENT, SoundEvents.PARROT_HURT, SoundEvents.PARROT_DEATH, SoundEvents.PARROT_STEP),
        WARBLER(EntityDimensions.scalable(0.65F, 0.7F).withEyeHeight(0.58F), SoundEvents.PARROT_FLY, SoundEvents.PARROT_AMBIENT, SoundEvents.PARROT_HURT, SoundEvents.PARROT_DEATH, SoundEvents.PARROT_STEP);

        public static final Codec<BirdType> CODEC = StringRepresentable.fromEnum(BirdType::values);

        private final EntityDimensions dimensions;
        private final SoundEvent flySound;
        private final SoundEvent ambientSound;
        private final SoundEvent hurtSound;
        private final SoundEvent deathSound;
        private final SoundEvent stepSound;

        BirdType(EntityDimensions dimensions, SoundEvent flySound, SoundEvent ambientSound, SoundEvent hurtSound, SoundEvent deathSound, SoundEvent stepSound) {
            this.dimensions = dimensions;
            this.flySound = flySound;
            this.ambientSound = ambientSound;
            this.hurtSound = hurtSound;
            this.deathSound = deathSound;
            this.stepSound = stepSound;
        }

        public EntityDimensions getDimensions() {
            return this.dimensions;
        }

        public SoundEvent getFlySound() {
            return this.flySound;
        }

        public SoundEvent getAmbientSound() {
            return this.ambientSound;
        }

        public SoundEvent getHurtSound() {
            return this.hurtSound;
        }

        public SoundEvent getDeathSound() {
            return this.deathSound;
        }

        public SoundEvent getStepSound() {
            return this.stepSound;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public static class BirdGroupData extends AgeableMob.AgeableMobGroupData {
        public final Holder<BirdVariant> type;

        public BirdGroupData(Holder<BirdVariant> type) {
            super(false);
            this.type = type;
        }
    }

    private static class BirdWanderGoal extends WaterAvoidingRandomFlyingGoal {
        public BirdWanderGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        protected Vec3 getPosition() { //todo improve navigational distance
            Vec3 pos = null;
            if (this.mob.isInWater()) {
                pos = LandRandomPos.getPos(this.mob, 15, 15);
            }
            if (this.mob.getRandom().nextFloat() >= this.probability) {
                pos = this.getTreePos();
            }
            return pos == null ? super.getPosition() : pos;
        }

        private Vec3 getTreePos() {
            BlockPos mobPos = this.mob.blockPosition();
            BlockPos.MutableBlockPos abovePos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

            for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0F), Mth.floor(this.mob.getY() - 6.0F), Mth.floor(this.mob.getZ() - 3.0F), Mth.floor(this.mob.getX() + 3.0F), Mth.floor(this.mob.getY() + 6.0F), Mth.floor(this.mob.getZ() + 3.0F))) {
                if (!mobPos.equals(pos)) {
                    BlockState state = this.mob.level().getBlockState(belowPos.setWithOffset(pos, Direction.DOWN));
                    boolean canSitOn = state.getBlock() instanceof LeavesBlock || state.is(BlockTags.LOGS);
                    if (canSitOn && this.mob.level().isEmptyBlock(pos) && this.mob.level().isEmptyBlock(abovePos.setWithOffset(pos, Direction.UP))) {
                        return Vec3.atBottomCenterOf(pos);
                    }
                }
            }

            return null;
        }
    }
}

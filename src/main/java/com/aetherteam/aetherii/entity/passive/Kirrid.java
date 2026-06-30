package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.navigator.KirridPathNavigation;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.IForgeShearable;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Kirrid extends AetherAnimal implements Shearable, IForgeShearable {
    public static int JUMP_START_EVENT = 100;
    public static int RAM_START_EVENT = 101;
    public static int RAM_STOP_EVENT = 102;
    public static int EAT_START_EVENT = 103;

    private static final EntityDataAccessor<Optional<KirridColor>> DATA_WOOL_COLOR_ID = SynchedEntityData.defineId(Kirrid.class, AetherIIDataSerializers.OPTIONAL_KIRRID_COLOR.get());
    private static final EntityDataAccessor<Boolean> DATA_HAS_PLATE_ID = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHEARED_ID = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);

    private final EntityType<? extends Kirrid> variantType;

    private int woolGrowTime = -1;
    private int plateGrowTime = 0;

    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;

    public AnimationState jumpAnimationState = new AnimationState();
    public AnimationState ramAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();

    public static int getDecimalColor(KirridColor color) {
        return KirridColor.DECIMAL_COLOR_BY_KIRRID_COLOR.get(color);
    }

    public Kirrid(EntityType<? extends Kirrid> type, Level level) {
        super(type, level);
        this.variantType = type;
        this.moveControl = new KirridMoveControl(this);
        this.jumpControl = new KirridJumpControl(this);
        this.setSpeedModifier(0.0);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.26);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag dataTag) {
        RandomSource randomsource = level.getRandom();
        KirridAi.initMemories(this, randomsource);
        this.ageBoundaryReached();
        this.setColor(getRandomKirridColor(randomsource, this));

        return super.finalizeSpawn(level, difficulty, reason, pSpawnData, dataTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_WOOL_COLOR_ID, Optional.empty());
        this.entityData.define(DATA_HAS_PLATE_ID, true);
        this.entityData.define(DATA_SHEARED_ID, false);
    }

    @Override
    protected Brain<Kirrid> makeBrain(Dynamic<?> dynamic) {
        return KirridAi.makeBrain(this, dynamic);
    }

    @Override
    public Brain<Kirrid> getBrain() {
        return (Brain<Kirrid>) super.getBrain();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        KirridPathNavigation kirridPathNavigation = new KirridPathNavigation(this, level);
        kirridPathNavigation.setCanFloat(true);
        return kirridPathNavigation;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == JUMP_START_EVENT) {
            this.spawnSprintParticle();
            this.jumpAnimationState.start(this.tickCount);
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else if (id == RAM_START_EVENT) {
            this.ramAnimationState.start(this.tickCount);
        } else if (id == RAM_STOP_EVENT) {
            this.ramAnimationState.stop();
        } else if (id == EAT_START_EVENT) {
            this.eatAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.handleFallSpeed();
        if (this.level().isClientSide()) {
            if (this.onGround() && this.jumpDuration <= 7) {
                this.jumpAnimationState.ifStarted(AnimationState::stop);
            }
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

    @Override
    protected void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level();
        ProfilerFiller profiler = this.level().getProfiler();

        profiler.push("kirridBrain");
        this.getBrain().tick(serverLevel, this);
        profiler.pop();

        profiler.push("kirridActivityUpdate");
        KirridAi.updateActivity(this);
        profiler.pop();

        if (this.woolGrowTime >= 2400) {
            this.setSheared(false);
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
            KirridJumpControl kirridJumpControl = (KirridJumpControl) this.getJumpControl();
            if (!kirridJumpControl.wantJump()) {
                if (this.getMoveControl().hasWanted() && this.jumpDelayTicks == 0) {
                    Path path = this.getNavigation().getPath();
                    Vec3 vec3 = new Vec3(this.getMoveControl().getWantedX(), this.getMoveControl().getWantedY(), this.getMoveControl().getWantedZ());
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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() instanceof DyeItem dyeItem) {
            DyeColor dyeColor = dyeItem.getDyeColor();
            KirridColor kirridColor = KirridColor.KIRRID_COLOR_BY_DYE.get(dyeColor);
            if (this.getColor().isEmpty() || this.getColor().get() != kirridColor) {
                player.swing(hand);
                if (!player.level().isClientSide()) {
                    this.setColor(Optional.of(kirridColor));
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
            }
        } else if (itemStack.is(AetherIIItems.WATER_VIAL.get()) || (itemStack.is(Items.POTION) && PotionUtils.getPotion(itemStack) == Potions.WATER)) {
            if (!this.getColor().isEmpty()) {
                player.swing(hand);
                if (!player.level().isClientSide()) {
                    this.setColor(Optional.empty());
                    if (!player.getAbilities().instabuild) {
                        Item result = itemStack.is(AetherIIItems.WATER_VIAL.get()) ? AetherIIItems.SCATTERGLASS_VIAL.get() : Items.GLASS_BOTTLE;
                        player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(result)));
                    }
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune) {
        level.playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
            this.setSheared(true);
            return this.createShearingDrops();
        }
        return Collections.emptyList();
    }

    @Override
    public void shear(SoundSource soundSource) {
        this.level().playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), soundSource, 1.0F, 1.0F);
        this.spawnShearingDrops(this.createShearingDrops());
        this.setSheared(true);
    }

    private List<ItemStack> createShearingDrops() {
        int count = 1 + this.getRandom().nextInt(3);
        ItemLike wool = this.getColor().map(KirridColor::getWool).orElseGet(() -> AetherIIBlocks.CLOUDWOOL.get());
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            items.add(new ItemStack(wool));
        }
        return items;
    }

    private void spawnShearingDrops(List<ItemStack> drops) {
        for (ItemStack item : drops) {
            for (int i = 0; i < item.getCount(); ++i) {
                ItemEntity drop = this.spawnAtLocation(item.copyWithCount(1), 1.0F);
                if (drop != null) {
                    drop.setDeltaMovement(drop.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, this.getRandom().nextFloat() * 0.05F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F));
                }
            }
        }
    }

    @Override
    public void ate() {
        super.ate();
        if (this.woolGrowTime == -1) {
            this.woolGrowTime = 0;
        } else {
            this.woolGrowTime += this.getRandom().nextInt(30) + 30;
        }
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping && this.getJumpSound() != null && !this.isInWater() /*&& !this.isInFluidType()*/) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        double speedModifier = this.getMoveControl().getSpeedModifier();
        if (speedModifier > 0.0) {
            double movementLengthSqr = this.getDeltaMovement().horizontalDistanceSqr();
            if (movementLengthSqr < 0.01) {
                this.moveRelative(0.1F, new Vec3(0.0, 0.0, 1.0));
            }
        }
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) JUMP_START_EVENT);
        }
    }

    @Override
    public float getJumpPower() {
        float f = 0.5F;
        if (this.horizontalCollision || this.getMoveControl().hasWanted() && this.getMoveControl().getWantedY() > this.getY() + 0.5) {
            if (this.getMoveControl().hasWanted() && this.getMoveControl().getWantedY() > this.getY() + 1.5) {
                f = 0.65F;
            }
        }

        Path path = this.getNavigation().getPath();
        if (path != null && !path.isDone()) {
            Vec3 vec3 = path.getNextEntityPos(this);
            if (vec3.y > this.getY() + 0.5) {
                f = 0.5F;
            }
            if (vec3.y > this.getY() + 1.5) {
                f = 0.65F;
            }
        }

        return f + this.getJumpBoostPower();
    }

    private void enableJumpControl() {
        ((KirridJumpControl) this.getJumpControl()).setCanJump(true);
    }

    private void disableJumpControl() {
        ((KirridJumpControl) this.getJumpControl()).setCanJump(false);
    }

    /**
     * Makes this entity fall slowly.
     */
    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.min(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
            }
        }
    }

    @Override
    protected float getFlyingSpeed() {
        return this.getControllingPassenger() instanceof Player ? super.getFlyingSpeed() : this.getSpeed() * 0.1F;
    }

    private void setLandingDelay() {
        if (this.getMoveControl().getSpeedModifier() < 0.6) {
            this.jumpDelayTicks = 10;
        } else {
            this.jumpDelayTicks = 1;
        }
    }

    private void checkLandingDelay() {
        this.setLandingDelay();
        this.disableJumpControl();
    }

    public boolean dropPlate() {
        if (this.getRandom().nextFloat() < 0.01F) {
            this.setPlate(false);
            return true;
        }
        return false;
    }

    public void setSpeedModifier(double speedModifier) {
        this.getNavigation().setSpeedModifier(speedModifier);
        this.getMoveControl().setWantedPosition(this.getMoveControl().getWantedX(), this.getMoveControl().getWantedY(), this.getMoveControl().getWantedZ(), speedModifier);
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
        return this.readyForShearing();
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(DATA_SHEARED_ID, sheared);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_SHEARED_ID);
    }

    public void setPlate(boolean horn) {
        this.entityData.set(DATA_HAS_PLATE_ID, horn);
    }

    public boolean hasPlate() {
        return this.entityData.get(DATA_HAS_PLATE_ID);
    }

    public Optional<KirridColor> getColor() {
        return this.getEntityData().get(DATA_WOOL_COLOR_ID);
    }

    public void setColor(Optional<KirridColor> color) {
        this.getEntityData().set(DATA_WOOL_COLOR_ID, color);
    }

    public static Optional<KirridColor> getRandomKirridColor(RandomSource random, Kirrid kirrid) {
        if (kirrid.variantType == AetherIIEntityTypes.HIGHFIELDS_KIRRID.get()) {
            int i = random.nextInt(100);
            if (i < 5) {
                return Optional.of(KirridColor.WHITE);
            } else if (i < 10) {
                return Optional.of(KirridColor.BROWN);
            } else if (i < 15) {
                return Optional.of(KirridColor.BLACK);
            }  else {
                return random.nextInt(500) == 0 ? Optional.of(KirridColor.LIME) : Optional.empty();
            }
        } else if (kirrid.variantType == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
            int i = random.nextInt(100);
            if (i < 5) {
                return Optional.of(KirridColor.GRAY);
            } else if (i < 10) {
                return Optional.of(KirridColor.LIME);
            } else if (i < 15) {
                return Optional.of(KirridColor.GREEN);
            }  else {
                return random.nextInt(500) == 0 ? Optional.of(KirridColor.PURPLE) : Optional.of(KirridColor.LIGHT_BLUE);
            }
        } else if (kirrid.variantType == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
            int i = random.nextInt(100);
            if (i < 5) {
                return Optional.of(KirridColor.BROWN);
            } else if (i < 10) {
                return Optional.of(KirridColor.MAGENTA);
            } else if (i < 15) {
                return Optional.of(KirridColor.PINK);
            }  else {
                return random.nextInt(500) == 0 ? Optional.of(KirridColor.BLUE) : Optional.of(KirridColor.WHITE);
            }
        }
        return Optional.empty();
    }

    private void facePoint(double pX, double pZ) {
        this.setYRot((float) (Mth.atan2(pZ - this.getZ(), pX - this.getX()) * 180.0F / (float) Math.PI) - 90.0F);
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() || this.fallDistance < 5 ? 3 : 14;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(AetherIITags.Items.KIRRID_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_KIRRID_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_KIRRID_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_KIRRID_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_KIRRID_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    protected SoundEvent getJumpSound() {
        return AetherIISoundEvents.ENTITY_KIRRID_JUMP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        EntityType<? extends Kirrid> variant = level.getRandom().nextBoolean() ? this.variantType : ((Kirrid) otherParent).variantType;
        Kirrid baby = variant.create(level);
        if (baby != null) {
            KirridAi.initMemories(baby, this.getRandom());
            Optional<KirridColor> parent1DyeColor = this.getColor();
            Optional<KirridColor> parent2DyeColor = ((Kirrid) otherParent).getColor();
            if (parent1DyeColor.isEmpty() && parent2DyeColor.isEmpty()) {
                baby.setColor(Optional.empty());
            } else if (parent1DyeColor.isPresent() && parent2DyeColor.isEmpty()) {
                baby.setColor(parent1DyeColor);
            } else if (parent1DyeColor.isEmpty() && parent2DyeColor.isPresent()) {
                baby.setColor(parent2DyeColor);
            } else {
                baby.setColor(Optional.of(this.getOffspringColor(parent1DyeColor.get(), parent2DyeColor.get())));
            }
        }
        return baby;
    }

    private KirridColor getOffspringColor(KirridColor parent1, KirridColor parent2) {
        DyeColor dyeColor1 = parent1.getDyeColor();
        DyeColor dyeColor2 = parent2.getDyeColor();
        CraftingContainer craftingContainer = makeContainer(dyeColor1, dyeColor2);
        DyeColor result = this.level()
                .getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, craftingContainer, this.level())
                .map(recipe -> recipe.assemble(craftingContainer, this.level().registryAccess()))
                .map(ItemStack::getItem)
                .filter(DyeItem.class::isInstance)
                .map(DyeItem.class::cast)
                .map(DyeItem::getDyeColor)
                .orElseGet(() -> this.level().random.nextBoolean() ? dyeColor1 : dyeColor2);
        return KirridColor.KIRRID_COLOR_BY_DYE.get(result);
    }

    private static CraftingContainer makeContainer(DyeColor dyeColor1, DyeColor dyeColor2) {
        CraftingContainer craftingContainer = new TransientCraftingContainer(new KirridContainer(null, -1), 2, 1);
        craftingContainer.setItem(0, new ItemStack(DyeItem.byColor(dyeColor1)));
        craftingContainer.setItem(1, new ItemStack(DyeItem.byColor(dyeColor2)));
        return craftingContainer;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("HasPlate", this.hasPlate());
        tag.putBoolean("Sheared", this.isSheared());
        tag.putInt("PlateGrowTime", this.plateGrowTime);
        tag.putInt("WoolGrowTime", this.woolGrowTime);
        if (this.getColor().isPresent()) {
            tag.putInt("Color", this.getColor().get().id());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setPlate(!tag.contains("HasPlate") || tag.getBoolean("HasPlate"));
        this.setSheared(tag.contains("Sheared") && tag.getBoolean("Sheared"));
        this.plateGrowTime = tag.contains("PlateGrowTime") ? tag.getInt("PlateGrowTime") : 0;
        this.woolGrowTime = tag.contains("WoolGrowTime") ? tag.getInt("WoolGrowTime") : 0;
        if (tag.contains("Color")) {
            this.setColor(Optional.of(KirridColor.BY_ID.apply(tag.getInt("Color"))));
        }
    }

    public enum KirridColor implements StringRepresentable {
        WHITE(0, 16777215, DyeColor.WHITE, AetherIIBlocks.WHITE_CLOUDWOOL),
        ORANGE(1, 16760199, DyeColor.ORANGE, AetherIIBlocks.ORANGE_CLOUDWOOL),
        MAGENTA(2, 14989818, DyeColor.MAGENTA, AetherIIBlocks.MAGENTA_CLOUDWOOL),
        LIGHT_BLUE(3, 12041207, DyeColor.LIGHT_BLUE, AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL),
        YELLOW(4, 16768903, DyeColor.YELLOW, AetherIIBlocks.YELLOW_CLOUDWOOL),
        LIME(5, 12317344, DyeColor.LIME, AetherIIBlocks.LIME_CLOUDWOOL),
        PINK(6, 16759510, DyeColor.PINK, AetherIIBlocks.PINK_CLOUDWOOL),
        GRAY(7, 8028561, DyeColor.GRAY, AetherIIBlocks.GRAY_CLOUDWOOL),
        LIGHT_GRAY(8, 13947085, DyeColor.LIGHT_GRAY, AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL),
        CYAN(9, 10741468, DyeColor.CYAN, AetherIIBlocks.CYAN_CLOUDWOOL),
        PURPLE(10, 10255815, DyeColor.PURPLE, AetherIIBlocks.PURPLE_CLOUDWOOL),
        BLUE(11, 6846906, DyeColor.BLUE, AetherIIBlocks.BLUE_CLOUDWOOL),
        BROWN(12, 7230555, DyeColor.BROWN, AetherIIBlocks.BROWN_CLOUDWOOL),
        GREEN(13, 8497266, DyeColor.GREEN, AetherIIBlocks.GREEN_CLOUDWOOL),
        RED(14, 11230822, DyeColor.RED, AetherIIBlocks.RED_CLOUDWOOL),
        BLACK(15, 3093053, DyeColor.BLACK, AetherIIBlocks.BLACK_CLOUDWOOL);

        public static final IntFunction<Kirrid.KirridColor> BY_ID = ByIdMap.continuous(Kirrid.KirridColor::id, Kirrid.KirridColor.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StringRepresentable.EnumCodec<KirridColor> CODEC = StringRepresentable.fromEnum(KirridColor::values);
        public static final StreamCodec<ByteBuf, Kirrid.KirridColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Kirrid.KirridColor::id);

        public static final Map<DyeColor, Kirrid.KirridColor> KIRRID_COLOR_BY_DYE = Maps.<DyeColor, Kirrid.KirridColor>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color.dyeColor, color -> color)));

        public static final Map<Kirrid.KirridColor, Integer> DECIMAL_COLOR_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, Integer>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, color -> color.color)));
        public static final Map<Kirrid.KirridColor, DyeColor> DYE_COLOR_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, DyeColor>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, color -> color.dyeColor)));
        public static final Map<Kirrid.KirridColor, ItemLike> CLOUDWOOL_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, ItemLike>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, KirridColor::getWool)));

        private final int id;
        private final int color;
        private final DyeColor dyeColor;
        private final Supplier<? extends ItemLike> wool;

        KirridColor(int id, int color, DyeColor dyeColor, Supplier<? extends ItemLike> wool) {
            this.id = id;
            this.color = color;
            this.dyeColor = dyeColor;
            this.wool = wool;
        }

        public int getColor() {
            return this.color;
        }

        public DyeColor getDyeColor() {
            return this.dyeColor;
        }

        public ItemLike getWool() {
            return this.wool.get();
        }

        public int id() {
            return id;
        }

        @Override
        public String getSerializedName() {
            return this.name();
        }
    }

    private static class KirridContainer extends AbstractContainerMenu {
        private KirridContainer(@Nullable MenuType<?> menuType, int containerId) {
            super(menuType, containerId);
        }

        @Override
        public ItemStack quickMoveStack(Player player, int index) {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean stillValid(Player player) {
            return false;
        }
    }

    public static class KirridJumpControl extends JumpControl {
        private final Kirrid kirrid;
        private boolean canJump;

        public KirridJumpControl(Kirrid kirrid) {
            super(kirrid);
            this.kirrid = kirrid;
        }

        public boolean wantJump() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJump) {
            this.canJump = canJump;
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
        public void setWantedPosition(double x, double y, double z, double speed) {
            if (this.kirrid.isInWater()) {
                speed = 1.5;
            }

            super.setWantedPosition(x, y, z, speed);
            if (speed > 0.0) {
                this.nextJumpSpeed = speed;
            }
        }
    }
}

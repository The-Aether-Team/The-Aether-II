package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.brain.KirridAi;
import com.aetherteam.aetherii.entity.ai.memory.AetherIIMemoryModuleTypes;
import com.aetherteam.aetherii.entity.ai.navigator.KirridPathNavigation;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class Kirrid extends AetherAnimal implements Shearable, IShearable {
    private static final EntityDataAccessor<Optional<KirridColor>> DATA_WOOL_COLOR_ID = SynchedEntityData.defineId(Kirrid.class, AetherIIDataSerializers.OPTIONAL_KIRRID_COLOR.get());
    private static final EntityDataAccessor<Boolean> DATA_HAS_PLATE_ID = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHEARED_ID = SynchedEntityData.defineId(Kirrid.class, EntityDataSerializers.BOOLEAN);

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
            AetherIIMemoryModuleTypes.KIRRID_BATTLE_TARGET.get(),
            AetherIIMemoryModuleTypes.EAT_GRASS_COOLDOWN.get(),
            MemoryModuleType.IS_PANICKING
    );

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

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor pLevel,
            DifficultyInstance pDifficulty,
            MobSpawnType pReason,
            @javax.annotation.Nullable SpawnGroupData pSpawnData
    ) {
        RandomSource randomsource = pLevel.getRandom();
        KirridAi.initMemories(this, randomsource);
        this.ageBoundaryReached();
        this.setColor(getRandomKirridColor(randomsource, this));

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_WOOL_COLOR_ID, Optional.empty());
        builder.define(DATA_HAS_PLATE_ID, true);
        builder.define(DATA_SHEARED_ID, false);
    }

    @Override
    protected Brain.Provider<Kirrid> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return KirridAi.makeBrain(this.variantType, this.brainProvider().makeBrain(pDynamic));
    }

    @Override
    public Brain<Kirrid> getBrain() {
        return (Brain<Kirrid>) super.getBrain();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new KirridPathNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.handleFallSpeed();
        if (this.level().isClientSide()) {
            if (this.onGround() && this.jumpDuration <= 7) {
                this.jumpAnimationState.ifStarted(animationState -> {
                    animationState.stop();
                });
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
        this.level().getProfiler().push("kirridBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("kirridActivityUpdate");
        KirridAi.updateActivity(this);
        this.level().getProfiler().pop();

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

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof DyeItem dyeItem) {
            DyeColor dyeColor = dyeItem.getDyeColor();
            KirridColor kirridColor = KirridColor.KIRRID_COLOR_BY_DYE.get(dyeColor);
            if (this.getColor().isEmpty() || this.getColor().get() != kirridColor) {
                player.swing(hand);
                if (!player.level().isClientSide()) {
                    this.setColor(Optional.of(kirridColor));
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos) {
        level.playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
            this.setSheared(true);
            int i = 1;
            i += this.getRandom().nextInt(3);

            List<ItemStack> items = new ArrayList<>();
            for (int j = 0; j < i; ++j) {
                ItemLike itemLike = AetherIIBlocks.CLOUDWOOL.asItem();
                if (this.getColor().isPresent()) {
                    itemLike = KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.get(this.getColor().get());
                }
                items.add(new ItemStack(itemLike));
            }
            return items;
        }
        return Collections.emptyList();
    }

    @Override
    public void shear(SoundSource source) {
        this.level().playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), source, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1;
        i += this.getRandom().nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemLike itemLike = AetherIIBlocks.CLOUDWOOL.asItem();
            if (this.getColor().isPresent()) {
                itemLike = KirridColor.CLOUDWOOL_BY_KIRRID_COLOR.get(this.getColor().get());
            }
            ItemEntity itementity = this.spawnAtLocation(itemLike, 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, this.getRandom().nextFloat() * 0.05F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F));
            }
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 1) {
            this.spawnSprintParticle();
            this.jumpAnimationState.start(this.tickCount);
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else if (pId == 61) {
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

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    public void setJumping(boolean pJumping) {
        super.setJumping(pJumping);
        if (pJumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0) {
            double d1 = this.getDeltaMovement().horizontalDistanceSqr();
            if (d1 < 0.01) {
                this.moveRelative(0.1F, new Vec3(0.0, 0.0, 1.0));
            }
        }
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 1);
        }
    }

    @Override
    protected float getJumpPower() {
        float f = 0.5F;
        if (this.horizontalCollision || this.moveControl.hasWanted() && this.moveControl.getWantedY() > this.getY() + 0.5) {
            f = 0.5F;
            if (this.moveControl.hasWanted() && this.moveControl.getWantedY() > this.getY() + 1.5) {
                f = 0.65F;
            }
        }

        Path path = this.navigation.getPath();
        if (path != null && !path.isDone()) {
            Vec3 vec3 = path.getNextEntityPos(this);
            if (vec3.y > this.getY() + 0.5) {
                f = 0.5F;
            }
            if (vec3.y > this.getY() + 1.5) {
                f = 0.65F;
            }
        }

        return super.getJumpPower(f / 0.42F);
    }

    private void enableJumpControl() {
        ((KirridJumpControl) this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((KirridJumpControl) this.jumpControl).setCanJump(false);
    }

    /**
     * Makes this entity fall slowly.
     */
    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
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

    public boolean dropPlate() {
        if (this.random.nextFloat() < 0.01F) {
            this.setPlate(false);
            return true;
        }
        return false;
    }

    public void setSpeedModifier(double pSpeedModifier) {
        this.getNavigation().setSpeedModifier(pSpeedModifier);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), pSpeedModifier);
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.GOAT_LONG_JUMP;
    }

    @Override
    public boolean isShearable(Player player, ItemStack item, Level world, BlockPos pos) {
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
        return this.onGround() || this.fallDistance < 5 ? 5 : 14;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(AetherIITags.Items.KIRRID_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Kirrid parent = (Kirrid) pOtherParent;
        Kirrid baby = this.variantType.create(pLevel);
        if (baby != null) {
            KirridAi.initMemories(baby, this.random);
            baby.setColor(this.getOffspringColor(this, parent));
        }
        return baby;
    }

    private Optional<KirridColor> getOffspringColor(Animal parent1, Animal parent2) {
        Optional<KirridColor> color1 = ((Kirrid) parent1).getColor();
        Optional<KirridColor> color2 = ((Kirrid) parent2).getColor();
        if (color1.isEmpty() && color2.isEmpty()) {
            return Optional.empty();
        } else if (color1.isPresent() && color2.isEmpty()) {
            return color1;
        } else if (color1.isEmpty() && color2.isPresent()) {
            return color2;
        } else {
            CraftingInput craftinginput = makeCraftInput(color1.get().getDyeColor(), color2.get().getDyeColor());
            return Optional.of(this.level()
                    .getRecipeManager()
                    .getRecipeFor(RecipeType.CRAFTING, craftinginput, this.level())
                    .map(p_352802_ -> p_352802_.value().assemble(craftinginput, this.level().registryAccess()))
                    .map(ItemStack::getItem)
                    .filter(DyeItem.class::isInstance)
                    .map(DyeItem.class::cast)
                    .map(DyeItem::getDyeColor)
                    .map(KirridColor.KIRRID_COLOR_BY_DYE::get)
                    .orElseGet(() -> this.level().random.nextBoolean() ? color1.get(): color2.get()));
        }
    }

    private static CraftingInput makeCraftInput(DyeColor pColor1, DyeColor pColor2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(pColor1)), new ItemStack(DyeItem.byColor(pColor2))));
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        if (this.isSheared()) {
            return this.getType().getDefaultLootTable();
        } else if (this.getColor().isEmpty()) {
            if (this.variantType == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
                return AetherIILoot.ENTITIES_ARCTIC_KIRRID_PLAIN;
            } else if (this.variantType == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
                return AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PLAIN;
            } else {
                return AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PLAIN;
            }
        } else {
            if (this.variantType == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
                return switch (this.getColor().get()) {
                    case WHITE -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_WHITE;
                    case ORANGE -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_ORANGE;
                    case MAGENTA -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_MAGENTA;
                    case LIGHT_BLUE -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIGHT_BLUE;
                    case YELLOW -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_YELLOW;
                    case LIME -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIME;
                    case PINK -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_PINK;
                    case GRAY -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_GRAY;
                    case LIGHT_GRAY -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_LIGHT_GRAY;
                    case CYAN -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_CYAN;
                    case PURPLE -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_PURPLE;
                    case BLUE -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_BLUE;
                    case BROWN -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_BROWN;
                    case GREEN -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_GREEN;
                    case RED -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_RED;
                    case BLACK -> AetherIILoot.ENTITIES_ARCTIC_KIRRID_BLACK;
                };
            } else if (this.variantType == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
                return switch (this.getColor().get()) {
                    case WHITE -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_WHITE;
                    case ORANGE -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_ORANGE;
                    case MAGENTA -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_MAGENTA;
                    case LIGHT_BLUE -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIGHT_BLUE;
                    case YELLOW -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_YELLOW;
                    case LIME -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIME;
                    case PINK -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PINK;
                    case GRAY -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_GRAY;
                    case LIGHT_GRAY -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_LIGHT_GRAY;
                    case CYAN -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_CYAN;
                    case PURPLE -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_PURPLE;
                    case BLUE -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BLUE;
                    case BROWN -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BROWN;
                    case GREEN -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_GREEN;
                    case RED -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_RED;
                    case BLACK -> AetherIILoot.ENTITIES_MAGNETIC_KIRRID_BLACK;
                };
            } else {
                return switch (this.getColor().get()) {
                    case WHITE -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_WHITE;
                    case ORANGE -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_ORANGE;
                    case MAGENTA -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_MAGENTA;
                    case LIGHT_BLUE -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIGHT_BLUE;
                    case YELLOW -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_YELLOW;
                    case LIME -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIME;
                    case PINK -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PINK;
                    case GRAY -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_GRAY;
                    case LIGHT_GRAY -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_LIGHT_GRAY;
                    case CYAN -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_CYAN;
                    case PURPLE -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_PURPLE;
                    case BLUE -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BLUE;
                    case BROWN -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BROWN;
                    case GREEN -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_GREEN;
                    case RED -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_RED;
                    case BLACK -> AetherIILoot.ENTITIES_HIGHFIELDS_KIRRID_BLACK;
                };
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("HasPlate", this.hasPlate());
        pCompound.putBoolean("Sheared", this.isSheared());
        pCompound.putInt("PlateGrowTime", this.plateGrowTime);
        pCompound.putInt("WoolGrowTime", this.woolGrowTime);
        if (this.getColor().isPresent()) {
            pCompound.putInt("Color", this.getColor().get().id());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setPlate(pCompound.getBoolean("HasPlate"));
        this.setSheared(pCompound.getBoolean("Sheared"));
        this.plateGrowTime = pCompound.getInt("PlateGrowTime");
        this.woolGrowTime = pCompound.getInt("WoolGrowTime");
        if (pCompound.contains("Color")) {
            this.setColor(Optional.of(KirridColor.BY_ID.apply(pCompound.getInt("Color"))));
        }
    }

    public enum KirridColor {
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
        public static final StreamCodec<ByteBuf, Kirrid.KirridColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Kirrid.KirridColor::id);

        public static final Map<DyeColor, Kirrid.KirridColor> KIRRID_COLOR_BY_DYE = Maps.<DyeColor, Kirrid.KirridColor>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color.dyeColor, color -> color)));

        private static final Map<Kirrid.KirridColor, Integer> DECIMAL_COLOR_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, Integer>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, color -> color.color)));
        private static final Map<Kirrid.KirridColor, DyeColor> DYE_COLOR_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, DyeColor>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, color -> color.dyeColor)));
        private static final Map<Kirrid.KirridColor, ItemLike> CLOUDWOOL_BY_KIRRID_COLOR = Maps.<Kirrid.KirridColor, ItemLike>newEnumMap(Arrays.stream(Kirrid.KirridColor.values()).collect(Collectors.toMap(color -> color, color -> color.wool)));

        private final int id;
        private final int color;
        private final DyeColor dyeColor;
        private final ItemLike wool;

        KirridColor(int id, int color, DyeColor dyeColor, ItemLike wool) {
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
            return this.wool;
        }

        public int id() {
            return id;
        }
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

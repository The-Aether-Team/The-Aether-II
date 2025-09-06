package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.ai.brain.MoaAi;
import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.inventory.menu.GuidebookEquipmentMenu;
import com.aetherteam.aetherii.inventory.menu.provider.ExtraDataMenuProvider;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaEggType;
import com.aetherteam.aetherii.item.miscellaneous.MoaFeedItem;
import com.aetherteam.aetherii.item.miscellaneous.MoaSaddlebagItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class Moa extends MountableAnimal implements ContainerListener, HasCustomInventoryScreen {
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_MOA_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final EntityDataAccessor<String> DATA_FEATHER_SHAPE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_KERATIN_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_EYE_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_FEATHER_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Boolean> DATA_HUNGRY = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_AMOUNT_FED = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_RIDER_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_LAST_RIDER_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_STAMINA = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SITTING = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_FOLLOWING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    private static final EntityDataAccessor<ItemStack> DATA_SADDLE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_SADDLEBAG = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.ITEM_STACK);

    private SimpleContainer inventory;

    private int jumpCooldown;
    private int flapCooldown;
    private int staminaHealCooldown;

    private float flap;
    private float flapO;

    private int eggTime = this.getEggTime();

    public Moa(EntityType<? extends Moa> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_OTHER, -1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_OTHER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, -1.0F);
        this.createInventory();
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.STEP_HEIGHT, 1)
                .add(Attributes.FOLLOW_RANGE, 6.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0)
                .add(AetherIIAttributes.MOA_STRENGTH)
                .add(AetherIIAttributes.MOA_STAMINA)
                .add(AetherIIAttributes.MOA_SPEED);
    }

    /**
     * Sets up Moas when spawned.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link EntitySpawnReason} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @javax.annotation.Nullable SpawnGroupData spawnData) {
        this.generateMoaReference(); //todo: 1.21 tag passing into this method was removed.

        if (reason != EntitySpawnReason.NATURAL) {
            Moa.KeratinColor keratinColor = Moa.KeratinColor.getRandom(this.getRandom());
            Moa.EyeColor eyeColor = Moa.EyeColor.getRandom(this.getRandom());
            Moa.FeatherColor featherColor = Moa.FeatherColor.getRandom(this.getRandom());
            Moa.FeatherShape featherShape = Moa.FeatherShape.getRandom(this.getRandom());
            this.setKeratinColor(keratinColor.getSerializedName());
            this.setEyeColor(eyeColor.getSerializedName());
            this.setFeatherColor(featherColor.getSerializedName());
            this.setFeatherShape(featherShape.getSerializedName());
        }
        if (spawnData == null) { // Disallow baby Moas from spawning in spawn groups.
            spawnData = new AgeableMob.AgeableMobGroupData(false);
        }
        if (reason == EntitySpawnReason.STRUCTURE) {
            //set moa home when spawn in nest
            MoaAi.initMoaHomeMemories(this, this.random);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MOA_REFERENCE, Optional.empty());
        builder.define(DATA_FEATHER_SHAPE, FeatherShape.CURVED.getSerializedName());
        builder.define(DATA_KERATIN_COLOR, KeratinColor.GRAY.getSerializedName());
        builder.define(DATA_EYE_COLOR, EyeColor.BLUE.getSerializedName());
        builder.define(DATA_FEATHER_COLOR, FeatherColor.LIGHT_BLUE.getSerializedName());
        builder.define(DATA_RIDER_REFERENCE, Optional.empty());
        builder.define(DATA_LAST_RIDER_REFERENCE, Optional.empty());
        builder.define(DATA_REMAINING_STAMINA, 0);
        builder.define(DATA_HUNGRY, false);
        builder.define(DATA_AMOUNT_FED, 0);
        builder.define(DATA_PLAYER_GROWN, false);
        builder.define(DATA_SITTING, false);
        builder.define(DATA_FOLLOWING_ID, Optional.empty());
        builder.define(DATA_SADDLE, ItemStack.EMPTY);
        builder.define(DATA_SADDLEBAG, ItemStack.EMPTY);
    }

    /**
     * Refreshes the Moa's bounding box dimensions.
     *
     * @param dataAccessor The {@link EntityDataAccessor} for the entity.
     */
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        if (DATA_SITTING.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    @Override
    protected Brain.Provider<Moa> brainProvider() {
        return Brain.provider(MoaAi.MEMORY_TYPES, MoaAi.SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return MoaAi.makeBrain(this, this.brainProvider().makeBrain(pDynamic));
    }

    @Override
    public Brain<Moa> getBrain() {
        return (Brain<Moa>) super.getBrain();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    public void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.syncToClients();
    }

    @Override
    public void containerChanged(Container container) {
        boolean isSaddled = this.isSaddled();
        this.syncToClients();
    }

    @Override
    protected Holder<SoundEvent> getEquipSound(EquipmentSlot p_397157_, ItemStack p_397978_, Equippable p_397221_) {
        return (Holder<SoundEvent>) (p_397157_ == EquipmentSlot.SADDLE ? AetherIISoundEvents.ENTITY_MOA_SADDLE : super.getEquipSound(p_397157_, p_397978_, p_397221_));
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("kirridBrain");
        this.getBrain().tick(serverLevel, this);
        profiler.pop();
        profiler.push("kirridActivityUpdate");
        MoaAi.updateActivity(this);
        profiler.pop();
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource pSource, float pAmount) {
        boolean flag = super.hurtServer(serverLevel, pSource, pAmount);
        if (this.level().isClientSide) {
            return false;
        } else {
            if (flag && pSource.getEntity() instanceof LivingEntity) {
                MoaAi.maybeRetaliate(serverLevel, this, (LivingEntity) pSource.getEntity());
            }

            return flag;
        }
    }

    /**
     * Handles wing animation.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        //this.animateWings();

        /*if (this.getControllingPassenger() instanceof Player player) {
            if (player.getData(AetherIIDataAttachments.PLAYER).isJumping() && !this.onClimbable() && this.tryToStartFallFlying()) {
            }
//            else if (player.getData(AetherIIDataAttachments.PLAYER).isJumping() && !this.tryToStartFallFlying()) {
//                this.stopFallFlying();
//            }
        }*/
    }

    @Override
    protected void updateFallFlying() {
        this.checkFallDistanceAccumulation();
        if (!this.canGlide()) {
            this.setSharedFlag(7, false);
        }
    }

    public boolean tryToStartFallFlying() {
        if (!this.isFallFlying() && this.canGlide() && !this.isInWater()) {
            this.startFallFlying();
            return true;
        } else {
            return false;
        }
    }

    public void changeFlyMode() {
        if (!this.isFallFlying()) {
            this.tryToStartFallFlying();
        } else {
            this.stopFallFlying();
        }
        this.playSound(AetherIISoundEvents.ENTITY_MOA_FLAP.get());
    }

    public void startFallFlying() {
        this.setSharedFlag(7, true);
    }

    public void stopFallFlying() {
        this.setSharedFlag(7, true);
        this.setSharedFlag(7, false);
    }

    @Override
    protected boolean canGlide() {
        return !this.onGround() && this.isPlayerGrown() && this.getControllingPassenger() instanceof Player;
    }

    /**
     * Handles Moa behavior.
     */
    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            if (!this.isFallFlying()) {
                double max = this.isVehicle() ? -0.04 : -0.1;
                double fallSpeed = Math.min(gravity.getValue() * -0.5, max); // Entity isn't allowed to fall too slowly from gravity.
                if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
                    this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                    this.hasImpulse = true;
                    this.setEntityOnGround(false);
                }
            }
        }
        if (this.getRemainingStamina() < this.getMaxStamina()) {
            if (this.getStaminaHealCooldown() > 0) {
                this.setStaminaHealCooldown(this.getStaminaHealCooldown() - 1);
            } else {
                this.setRemainingStamina(this.getRemainingStamina() + 1);
                this.setStaminaHealCooldown(300);
            }
        }
        if (this.getJumpCooldown() > 0) { // Handles jump reset behavior.
            this.setJumpCooldown(this.getJumpCooldown() - 1);
            this.setPlayerJumped(false);
        } else if (this.getJumpCooldown() == 0) {
            this.setMountJumping(false);
        }

        // Handles egg laying.
        if (!this.level().isClientSide() && this.isAlive()) {
            if (this.getRandom().nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
            if (!this.isBaby() && this.getRandom().nextInt(2500) == 0) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    ItemStack featherStack = new ItemStack(AetherIIItems.MOA_FEATHER.get());
                    featherStack.set(AetherIIDataComponents.FEATHER_COLOR, FeatherColor.valueOf(this.getFeatherColor().toUpperCase(Locale.ROOT)));
                    this.spawnAtLocation(serverLevel, featherStack);
                }
            }
            //TODO MOA EGG LAY
//            if (!this.isBaby() && this.getPassengers().isEmpty() && --this.eggTime <= 0) {
//                MoaType moaType = this.getMoaType();
//                if (moaType != null && this.getBrain().hasMemoryValue(MemoryModuleType.HOME) && this.getBrain().getMemory(MemoryModuleType.HOME).get().pos().distManhattan(this.blockPosition()) <= 3) {
//                    if (this.onGround() && this.getBlockStateOn().is(AetherIITags.Blocks.MOA_HATCH_BLOCK)) {
//                        EggLayEvent eggLayEvent = AetherIIEventDispatch.onLayEgg(this, AetherIISoundEvents.ENTITY_MOA_EGG.get(), 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F, this.getMoaType().egg());
//                        if (!eggLayEvent.isCanceled()) {
//                            if (eggLayEvent.getSound() != null) {
//                                this.playSound(eggLayEvent.getSound(), eggLayEvent.getVolume(), eggLayEvent.getPitch());
//                            }
//                            if (eggLayEvent.getItem() != null) {
//                                this.level().setBlock(this.blockPosition(), Block.byItem(eggLayEvent.getItem().getItem()).defaultBlockState(), 3);
//                            }
//                        }
//                    }
//                }
//                this.eggTime = this.getEggTime();
//            }
        }

        // Handles baby hunger.
        if (this.isBaby()) {
            if (!this.isHungry()) {
                if (!this.level().isClientSide()) {
                    if (this.getRandom().nextInt(2000) == 0) {
                        this.setHungry(true);
                    }
                }
            } else {
                if (this.getRandom().nextInt(10) == 0) {
                    this.level().addParticle(AetherIIParticleTypes.MOA_HUNGRY.get(), this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
                }
            }
        } else {
            this.setHungry(false);
            this.setAmountFed(0);
        }

        // Handles rider tracking.
        if (this.getControllingPassenger() instanceof Player player) {
            if (this.getRider() == null) {
                this.setRider(new EntityReference<>(player.getUUID()));
            }
//            if (!this.isEntityOnGround()) {
//                if (!this.isFallFlying()) {
//                    this.setSharedFlag(7, true);
//                }
//            } else {
//                if (this.isFallFlying()) {
//                    this.setSharedFlag(7, false);
//                }
//            }
        } else {
            if (this.getRider() != null) {
                this.setRider(null);
            }
//            if (this.isFallFlying()) {
//                this.setSharedFlag(7, false);
//            }
        }

        // Handles flap cooldown for sounds.
        if (this.getFlapCooldown() > 0) {
            this.setFlapCooldown(this.getFlapCooldown() - 1);
        } else if (this.getFlapCooldown() == 0) {
            if (!this.onGround()) {
                this.level().playSound(null, this, AetherIISoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.getRandom().nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.getRandom().nextFloat(), 0.0F, 0.3F));
                this.setFlapCooldown(15);
            }
        }

        if (this.level().isClientSide()) {
            this.flapO = this.flap;
            if (!this.onGround()) {
                this.flap = Mth.clamp(this.flap + 0.2F, 0, 1F);
            } else {
                this.flap = Mth.clamp(this.flap - 0.2F, 0, 1F);
            }
        }
        this.checkFallDistanceAccumulation(); // Resets the Moa's fall distance.
    }

    public float getFlyAmount(float pPartialTicks) {
        return Mth.lerp(pPartialTicks, this.flapO, this.flap);
    }

    /**
     * Tracks the last rider and Moa Skin data when a player mounts a Moa.
     *
     * @param passenger The passenger {@link Entity}.
     */
    @Override
    protected void addPassenger(Entity passenger) {
        if (passenger instanceof Player player) {
            this.generateMoaReference();
            if (this.getLastRider() == null || !this.getLastRider().matches(player)) {
                this.setLastRider(new EntityReference<>(player.getUUID()));
            }
        }
        super.addPassenger(passenger);
    }

    /**
     * Handles travel movement and entity rotations.
     *
     * @param vector The {@link Vec3} for travel movement.
     */
    @Override
    public void travel(Vec3 vector) {
        if (!this.isSitting()) {
            super.travel(vector);
        } else {
            if (this.isAlive()) {
                LivingEntity entity = this.getControllingPassenger();
                if (this.isVehicle() && this.isSaddled() && entity != null) {
                    EntityUtil.copyRotations(this, entity);
                    if (this.isLocalInstanceAuthoritative()) {
                        this.travelWithInput(new Vec3(0, vector.y(), 0));
                        this.lerpHeadSteps = 0;
                    } else {
                        this.calculateEntityAnimation(false);
                        this.setDeltaMovement(Vec3.ZERO);
                    }
                } else {
                    this.travelWithInput(new Vec3(0, vector.y(), 0));
                }
            }
        }
    }

    /**
     * Handles cooldowns, remaining stamina, and particles when jumping.
     *
     * @param mob The jumping {@link Mob}.
     */
    @Override
    public void onJump(Mob mob) {
        super.onJump(mob);
        this.setJumpCooldown(10);
        if (!this.onGround()) {
            this.setStaminaHealCooldown(300);
            this.setRemainingStamina(this.getRemainingStamina() - 1);
            this.spawnExplosionParticle();
            if (this.getControllingPassenger() instanceof Player && this.isFallFlying()) {
                Vec3 vec31 = this.getLookAngle();
                Vec3 vec32 = this.getDeltaMovement();
                this.setDeltaMovement(vec32.add(vec31.x * 0.1D + (vec31.x * 1.5D - vec32.x) * 0.5D, vec31.y * 0.1D + (vec31.y * 1.5D - vec32.y) * 0.5D, vec31.z * 0.1D + (vec31.z * 1.5D - vec32.z) * 0.5D).scale(1.5));
            }
        }
        this.setFlapCooldown(0); // Causes the flap sound to be played in Moa#riderTick().
    }

    @Override
    public Vec3 getLookAngle() {
        if (this.getControllingPassenger() instanceof Player player) {
            return player.getLookAngle();
        }
        return super.getLookAngle();
    }

    /**
     * Various interaction behaviors for Moas.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(AetherIIItems.MOA_EGG)) {
            if (!this.level().isClientSide()) {
                if (player.hasInfiniteMaterials()) {
                    MoaEggType type = itemStack.get(AetherIIDataComponents.MOA_EGG_TYPE);
                    if (type != null) {
                        Moa moa = AetherIIEntityTypes.MOA.get().create(this.level(), EntitySpawnReason.SPAWN_ITEM_USE);
                        if (moa != null) {
                            Vec3 vec3 = this.blockPosition().getCenter();
                            moa.setBaby(false);
                            moa.setPlayerGrown(true);
                            moa.setKeratinColor(type.keratinColor().getSerializedName());
                            moa.setEyeColor(type.eyeColor().getSerializedName());
                            moa.setFeatherColor(type.featherColor().getSerializedName());
                            moa.setFeatherShape(type.featherShape().getSerializedName());
                            moa.snapTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(this.getRandom().nextFloat() * 360.0F), 0.0F);
                            this.level().addFreshEntity(moa);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        } else {
            if (this.isPlayerGrown() && player.isShiftKeyDown()) {
//                this.setSitting(!this.isSitting()); //todo
                if (!this.level().isClientSide()) {
                    this.openMenu(player);
                }
                return InteractionResult.SUCCESS;
            } else if (!this.level().isClientSide() && this.isPlayerGrown() && this.isBaby() && this.isHungry() && this.getAmountFed() < 3 && itemStack.is(AetherIITags.Items.MOA_FOOD)) { // Feeds a hungry baby Moa.
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.setAmountFed(this.getAmountFed() + 1);
                switch (this.getAmountFed()) {
                    case 0 -> this.setAge(-24000);
                    case 1 -> this.setAge(-16000);
                    case 2 -> this.setAge(-8000);
                    case 3 -> this.setBaby(false);
                }
                if (this.getAmountFed() > 3 && !this.isBaby()) {
                    this.setBaby(false);
                }
                this.setHungry(false);
                //PacketDistributor.sendToAll(new MoaInteractPacket(player.getId(), hand == InteractionHand.MAIN_HAND)); // Packet necessary to play animation because this code segment is server-side only, so no animations.
                return InteractionResult.CONSUME;
            } else if (this.isPlayerGrown() && !this.isBaby() && this.getHealth() < this.getMaxHealth() && itemStack.is(AetherIITags.Items.MOA_FOOD)) { // Heals a tamed Moa.
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.heal(5.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.level().isClientSide() && (!this.isVehicle() || this.hasPassenger(player)) && this.isPlayerGrown()) {
            this.openMenu(player);
        }
    }

    private void openMenu(Player player) {
        player.openMenu(new ExtraDataMenuProvider(
                (id, inventory, user) -> new GuidebookEquipmentMenu(id, inventory, this),
                (menu, buffer) -> ByteBufCodecs.INT.encode(buffer, this.getId()),
                Component.translatable("gui.aether_ii.guidebook.equipment.title")));
    }

    @Override
    protected void dropEquipment(ServerLevel serverLevel) {
        super.dropEquipment(serverLevel);
        if (this.getInventory() != null) {
            for (int i = 1; i < this.getInventory().getContainerSize(); i++) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    this.spawnAtLocation(serverLevel, itemstack);
                }
            }
        }
    }

    @Override
    protected void dropSaddle(ServerLevel serverLevel) {
        this.spawnAtLocation(serverLevel, this.getInventory().getItem(0));
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.getFirstPassenger() instanceof Player player) {
            if (player.containerMenu instanceof GuidebookEquipmentMenu) {
                player.closeContainer();
            }
        }
        super.remove(reason);
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 20; ++i) {
            EntityUtil.spawnMovementExplosionParticles(this);
        }
    }

    /**
     * Generates a {@link UUID} for this Moa; used for Moa Skin tracking.
     */
    public void generateMoaReference() {
        if (this.getMoaReference() == null) {
            this.setMoaReference(new EntityReference<>(UUID.randomUUID()));
        }
    }

    /**
     * @return The {@link UUID} for this Moa.
     */
    @Nullable
    public EntityReference<LivingEntity> getMoaReference() {
        return this.getEntityData().get(DATA_MOA_REFERENCE).orElse(null);
    }

    /**
     * Sets this Moa's {@link UUID}.
     *
     * @param reference THe {@link UUID}.
     */
    private void setMoaReference(@Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_MOA_REFERENCE, Optional.ofNullable(reference));
    }

    public String getFeatherShape() {
        return this.entityData.get(DATA_FEATHER_SHAPE);
    }

    public void setFeatherShape(String shape) {
        this.entityData.set(DATA_FEATHER_SHAPE, shape);
    }

    public String getKeratinColor() {
        return this.entityData.get(DATA_KERATIN_COLOR);
    }

    public void setKeratinColor(String color) {
        this.entityData.set(DATA_KERATIN_COLOR, color);
    }

    public String getEyeColor() {
        return this.entityData.get(DATA_EYE_COLOR);
    }

    public void setEyeColor(String color) {
        this.entityData.set(DATA_EYE_COLOR, color);
    }

    public String getFeatherColor() {
        return this.entityData.get(DATA_FEATHER_COLOR);
    }

    public void setFeatherColor(String color) {
        this.entityData.set(DATA_FEATHER_COLOR, color);
    }

    /**
     * @return The {@link UUID} of the current rider of this Moa.
     */
    @javax.annotation.Nullable
    public EntityReference<LivingEntity> getRider() {
        return this.getEntityData().get(DATA_RIDER_REFERENCE).orElse(null);
    }

    /**
     * Sets the current rider of this Moa.
     *
     * @param reference The {@link UUID}.
     */
    public void setRider(@javax.annotation.Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_RIDER_REFERENCE, Optional.ofNullable(reference));
    }

    /**
     * @return The {@link UUID} of the last rider of this Moa (including the current rider).
     */
    @javax.annotation.Nullable
    public EntityReference<LivingEntity> getLastRider() {
        return this.getEntityData().get(DATA_LAST_RIDER_REFERENCE).orElse(null);
    }

    /**
     * Sets the last rider of this Moa (including the current rider).
     *
     * @param reference The {@link UUID}.
     */
    public void setLastRider(@javax.annotation.Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_LAST_RIDER_REFERENCE, Optional.ofNullable(reference));
    }

    /**
     * @return The {@link Integer} value for the remaining stamina.
     */
    public int getRemainingStamina() {
        return this.getEntityData().get(DATA_REMAINING_STAMINA);
    }

    /**
     * Sets the remaining stamina.
     *
     * @param remainingStamina The {@link Integer} value.
     */
    public void setRemainingStamina(int remainingStamina) {
        this.getEntityData().set(DATA_REMAINING_STAMINA, remainingStamina);
    }

    /**
     * @return Whether this Moa is hungry, as a {@link Boolean}.
     */
    public boolean isHungry() {
        return this.getEntityData().get(DATA_HUNGRY);
    }

    /**
     * Sets whether this Moa is hungry.
     *
     * @param hungry The {@link Boolean} value.
     */
    public void setHungry(boolean hungry) {
        this.getEntityData().set(DATA_HUNGRY, hungry);
    }

    /**
     * @return The {@link Integer} value for how many times this Moa has been fed.
     */
    public int getAmountFed() {
        return this.getEntityData().get(DATA_AMOUNT_FED);
    }

    /**
     * Sets the amount of times this Moa has been fed.
     *
     * @param amountFed The {@link Integer} value.
     */
    public void setAmountFed(int amountFed) {
        this.getEntityData().set(DATA_AMOUNT_FED, amountFed);
    }

    /**
     * @return Whether this Moa was raised by the player, as a {@link Boolean}.
     */
    public boolean isPlayerGrown() {
        return this.getEntityData().get(DATA_PLAYER_GROWN);
    }

    /**
     * Sets whether this Moa was raised by the player.
     *
     * @param playerGrown The {@link Boolean} value.
     */
    public void setPlayerGrown(boolean playerGrown) {
        this.getEntityData().set(DATA_PLAYER_GROWN, playerGrown);
    }

    /**
     * @return Whether this Moa is sitting, as a {@link Boolean}.
     */
    public boolean isSitting() {
        return this.getEntityData().get(DATA_SITTING);
    }

    /**
     * Sets whether this Moa is sitting.
     *
     * @param isSitting The {@link Boolean} value.
     */
    public void setSitting(boolean isSitting) {
        this.getEntityData().set(DATA_SITTING, isSitting);
    }

    /**
     * @return Whether this Moa is following the player, as a {@link Boolean}.
     */
    @javax.annotation.Nullable
    public EntityReference<LivingEntity> getFollowing() {
        return this.getEntityData().get(DATA_FOLLOWING_ID).orElse(null);
    }

    /**
     * Sets whether this Moa is following the player.
     *
     * @param reference The {@link Boolean} value.
     */
    public void setFollowing(@javax.annotation.Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_FOLLOWING_ID, Optional.ofNullable(reference));
    }

    public ItemStack getSaddleStack() {
        return this.getEntityData().get(DATA_SADDLE);
    }

    public void setSaddleStack(ItemStack itemStack) {
        this.getEntityData().set(DATA_SADDLE, itemStack);
    }

    public ItemStack getSaddlebagStack() {
        return this.getEntityData().get(DATA_SADDLEBAG);
    }

    public void setSaddlebagStack(ItemStack itemStack) {
        this.getEntityData().set(DATA_SADDLEBAG, itemStack);
    }

    public int getSaddlebagRowSize() {
        return this.getSaddlebagStack().getItem() instanceof MoaSaddlebagItem saddlebagItem ? saddlebagItem.getRowSize() : 0;
    }

    /**
     * @return The {@link Integer} value for how long until the Moa can jump again.
     */
    public int getJumpCooldown() {
        return this.jumpCooldown;
    }

    /**
     * Sets how long until the Moa can jump again.
     *
     * @param jumpCooldown The {@link Integer} value.
     */
    public void setJumpCooldown(int jumpCooldown) {
        this.jumpCooldown = jumpCooldown;
    }

    /**
     * @return The {@link Integer} value for how long until the Moa can play the flap sound effect again.
     */
    public int getFlapCooldown() {
        return this.flapCooldown;
    }

    /**
     * Sets how long until the Moa can play the flap sound effect again.
     *
     * @param flapCooldown The {@link Integer} value.
     */
    public void setFlapCooldown(int flapCooldown) {
        this.flapCooldown = flapCooldown;
    }

    /**
     * @return The {@link Integer} value for how long until the Moa can heal stamina again.
     */
    public int getStaminaHealCooldown() {
        return staminaHealCooldown;
    }

    /**
     * Sets how long until the Moa can heal stamina again.
     *
     * @param staminaHealCooldown The {@link Integer} value.
     */
    public void setStaminaHealCooldown(int staminaHealCooldown) {
        this.staminaHealCooldown = staminaHealCooldown;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_MOA_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_MOA_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_MOA_DEATH.get();
    }

    @Override
    protected SoundEvent getSaddledSound() {
        return AetherIISoundEvents.ENTITY_MOA_SADDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_MOA_STEP.get(), 0.15F, 1.0F);
    }

    /**
     * @return The {@link Integer} for the maximum amount of jumps from the {@link MoaType}.
     */
    public int getMaxStamina() {
        return this.getAttribute(AetherIIAttributes.MOA_STAMINA) != null ? (int) this.getAttribute(AetherIIAttributes.MOA_STAMINA).getValue() : 3;
    }

    /**
     * @return The {@link Integer} for how long until an egg is laid.
     */
    public int getEggTime() {
        return this.random.nextInt(6000) + 6000;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    /**
     * Makes player-raised Moas immune to Inebriation.
     *
     * @param effect The {@link MobEffectInstance} to check whether this mob is affected by.
     * @return Whether the mob is affected.
     */
    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return (effect.getEffect().value() != AetherIIEffects.TOXIN.get() || !this.isPlayerGrown()) && super.canBeAffected(effect);
    }

    /**
     * @return The {@link Float} for the movement speed from the {@link MoaType}.
     */
    @Override
    public float getSpeed() {
        if (this.isVehicle() && this.isSaddled()) {
            return this.getSteeringSpeed();
        } else {
            return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        }
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    /**
     * @return A {@link Boolean} for whether the Moa can jump, determined by remaining stamina and jump cooldown.
     */
    @Override
    public boolean canJump() {
        return this.getRemainingStamina() > 0 && this.getJumpCooldown() == 0;
    }

    public void equipSaddle(ItemStack stack) {
        this.getInventory().setItem(0, stack);
    }


    public boolean isSaddleable() {
        return !this.isBaby() && this.isPlayerGrown();
    }

    protected void syncToClients() {
        if (!this.level().isClientSide()) {
            this.setSaddled(!this.inventory.getItem(0).isEmpty());
            this.setSaddleStack(this.inventory.getItem(0));
            this.setSaddlebagStack(this.inventory.getItem(1));
        }
    }

    /**
     * @see MountableMob#getMountJumpStrength()
     */
    @Override
    public double getMountJumpStrength() {
        float f = (float) (this.getAttributeValue(AetherIIAttributes.MOA_STRENGTH) * 0.01F);
        return this.onGround() ? 0.95 + f : 0.90 + f;
    }

    /**
     * @return The {@link Float} for the steering speed.
     */
    @Override
    public float getSteeringSpeed() {
        Entity entity = this.getControllingPassenger();
        float f = entity != null && entity.isSprinting() ? (float) (this.getAttributeValue(AetherIIAttributes.MOA_SPEED) * 0.1F) : 0;

        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.35F + f;
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        if (this.hasControllingPassenger()) {
            float f = Math.min(pPartialTick * 1.0F, 1.0F);
            this.walkAnimation.update(f, pPartialTick, 0.4F);
        } else {
            super.updateWalkAnimation(pPartialTick);
        }
    }

    /**
     * @return A {@link Float} for the calculated movement speed, both when mounted and not mounted.
     */
    @Override
    public float getFlyingSpeed() {
        if (this.isVehicle() && this.isSaddled()) {
            return this.getSteeringSpeed() * 0.35F;
        } else {
            return this.getSteeringSpeed() * 0.025F;
        }
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() || this.fallDistance < 5 ? super.getMaxFallDistance() : 14;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        double base = -0.90;
        double back = 0.3;
        return this.isSitting()
                ? super.getPassengerRidingPosition(entity).add(back * Mth.cos((entity.getYRot() - 90) * Mth.DEG_TO_RAD), base + 0.75, back * Mth.sin((entity.getYRot() - 90) * Mth.DEG_TO_RAD))
                : super.getPassengerRidingPosition(entity).add(back * Mth.cos((entity.getYRot() - 90) * Mth.DEG_TO_RAD), base, back * Mth.sin((entity.getYRot() - 90) * Mth.DEG_TO_RAD));
    }

    /**
     * @return The float for the Moa's hitbox scaling. Set to a flat value, as Moa hitbox scaling is handled by {@link Moa#getDimensions(Pose)}.
     */

    @Override
    public float getAgeScale() {
        return 1.0F;
    }

    /**
     * Handles the hitbox size for Moas. The height is scaled down whether the Moa is sitting or is a baby.
     *
     * @param pose The {@link Pose} to get dimensions for.
     * @return The {@link EntityDimensions}.
     */
    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        if (this.isSitting()) {
            dimensions = dimensions.scale(1.0F, 0.5F);
        }
        if (this.isBaby()) {
            dimensions = dimensions.scale(0.5F, 0.5F);
        }
        return dimensions;
    }

    @javax.annotation.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return null;
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    /**
     * Only allow modifying the Moa's age if its being set to one of the manually specified baby values (% -8000) or as grown up (0).
     *
     * @param age The {@link Integer} value for the age.
     */
    @Override
    public void setAge(int age) {
        if (age % -8000 == 0 || (age == 0 && this.getAmountFed() >= 3)) {
            super.setAge(age);
        }
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public int getInventorySize() {
        return 30;
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack moaEggItem = new ItemStack(AetherIIItems.MOA_EGG.get());
        KeratinColor keratinColor = KeratinColor.valueOf(this.getKeratinColor().toUpperCase(Locale.ROOT));
        EyeColor eyeColor = EyeColor.valueOf(this.getEyeColor().toUpperCase(Locale.ROOT));
        FeatherColor featherColor = FeatherColor.valueOf(this.getFeatherColor().toUpperCase(Locale.ROOT));
        FeatherShape featherShape = FeatherShape.valueOf(this.getFeatherShape().toUpperCase(Locale.ROOT));
        moaEggItem.set(AetherIIDataComponents.MOA_EGG_TYPE, new MoaEggType(keratinColor, eyeColor, featherColor, featherShape));
        return moaEggItem;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (this.getMoaReference() != null) {
            output.store("MoaUUID", EntityReference.codec(), this.getMoaReference());
        }
        output.putBoolean("IsBaby", this.isBaby());
        output.putString("FeatherShape", this.getFeatherShape());
        output.putString("KeratinColor", this.getKeratinColor());
        output.putString("EyeColor", this.getEyeColor());
        output.putString("FeatherColor", this.getFeatherColor());
        if (this.getRider() != null) {
            output.store("Rider", EntityReference.codec(), this.getRider());
        }
        if (this.getLastRider() != null) {
            output.store("LastRider", EntityReference.codec(), this.getLastRider());
        }
        output.putInt("StaminaHealCooldown", this.getStaminaHealCooldown());
        output.putInt("RemainingStamina", this.getRemainingStamina());
        output.putBoolean("Hungry", this.isHungry());
        output.putInt("AmountFed", this.getAmountFed());
        output.putBoolean("PlayerGrown", this.isPlayerGrown());
        output.putBoolean("Sitting", this.isSitting());
        if (this.getFollowing() != null) {
            output.store("Following", EntityReference.codec(), this.getFollowing());
        }

        output.store("SaddleItem", ItemStack.OPTIONAL_CODEC, this.getSaddleStack());
        output.store("SaddlebagsItem", ItemStack.OPTIONAL_CODEC, this.getSaddlebagStack());

        if (!this.getInventory().getItem(2).isEmpty()) {
            output.store("FeedItem", ItemStack.OPTIONAL_CODEC, this.inventory.getItem(2));
        }

        ValueOutput.TypedOutputList<ItemStackWithSlot> list = output.list("SaddlebagItems", ItemStackWithSlot.CODEC);
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack itemStack = this.inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                list.add(new ItemStackWithSlot(i, itemStack));
            }
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("MoaUUID", EntityReference.<LivingEntity>codec()).ifPresent(this::setMoaReference);
        this.setBaby(input.getBooleanOr("IsBaby", false));
        input.getString("FeatherShape").filter((string) -> Arrays.stream(FeatherShape.values()).map(FeatherShape::getSerializedName).anyMatch((s) -> s.equals(string))).ifPresent(this::setFeatherShape);
        input.getString("KeratinColor").filter((string) -> Arrays.stream(KeratinColor.values()).map(KeratinColor::getSerializedName).anyMatch((s) -> s.equals(string))).ifPresent(this::setKeratinColor);
        input.getString("EyeColor").filter((string) -> Arrays.stream(EyeColor.values()).map(EyeColor::getSerializedName).anyMatch((s) -> s.equals(string))).ifPresent(this::setEyeColor);
        input.getString("FeatherColor").filter((string) -> Arrays.stream(FeatherColor.values()).map(FeatherColor::getSerializedName).anyMatch((s) -> s.equals(string))).ifPresent(this::setFeatherColor);
        input.read("Rider", EntityReference.<LivingEntity>codec()).ifPresent(this::setRider);
        input.read("LastRider", EntityReference.<LivingEntity>codec()).ifPresent(this::setLastRider);
        input.getInt("StaminaHealCooldown").ifPresent(this::setStaminaHealCooldown);
        input.getInt("RemainingStamina").ifPresent(this::setRemainingStamina);
        this.setHungry(input.getBooleanOr("Hungry", false));
        input.getInt("AmountFed").ifPresent(this::setAmountFed);
        this.setPlayerGrown(input.getBooleanOr("PlayerGrown", false));
        this.setSitting(input.getBooleanOr("Sitting", false));
        input.read("Following", EntityReference.<LivingEntity>codec()).ifPresent(this::setFollowing);

        input.read("SaddleItem", ItemStack.OPTIONAL_CODEC).filter((stack) -> stack.is(AetherIIItems.MOA_SADDLE.get())).ifPresent((stack) -> this.getInventory().setItem(0, stack));
        input.read("SaddlebagsItem", ItemStack.OPTIONAL_CODEC).filter((stack) -> stack.getItem() instanceof MoaSaddlebagItem).ifPresent((stack) -> this.getInventory().setItem(1, stack));
        input.read("FeedItem", ItemStack.OPTIONAL_CODEC).filter((stack) -> stack.getItem() instanceof MoaFeedItem).ifPresent((stack) -> this.getInventory().setItem(2, stack));

        for (ItemStackWithSlot stackWithSlot : input.listOrEmpty("SaddlebagItems", ItemStackWithSlot.CODEC)) {
            if (stackWithSlot.isValidInContainer(this.inventory.getContainerSize())) {
                this.inventory.setItem(stackWithSlot.slot(), stackWithSlot.stack());
            }
        }
    }

    public enum KeratinColor implements StringRepresentable {
        BLUE,
        BROWN,
        GREEN,
        GRAY,
        RED;

        public static final Codec<KeratinColor> CODEC = StringRepresentable.fromValues(KeratinColor::values);
        public static final StreamCodec<FriendlyByteBuf, KeratinColor> STREAM_CODEC = StreamCodec.of((byteBuf, keratinColor) -> byteBuf.writeUtf(keratinColor.name()), (byteBuf) -> KeratinColor.valueOf(byteBuf.readUtf()));

        public static KeratinColor getRandom(RandomSource random) {
            return KeratinColor.values()[random.nextInt(KeratinColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum EyeColor implements StringRepresentable {
        BLUE,
        GREEN,
        YELLOW;

        public static final Codec<EyeColor> CODEC = StringRepresentable.fromValues(EyeColor::values);
        public static final StreamCodec<FriendlyByteBuf, EyeColor> STREAM_CODEC = StreamCodec.of((byteBuf, eyeColor) -> byteBuf.writeUtf(eyeColor.name()), (byteBuf) -> EyeColor.valueOf(byteBuf.readUtf()));

        public static EyeColor getRandom(RandomSource random) {
            return EyeColor.values()[random.nextInt(EyeColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum FeatherColor implements StringRepresentable {
        BLACK,
        BLOOMING_RED,
        BLUE,
        BROWN,
        CLASSIC_BLACK,
        CYAN,
        GRAY,
        GREEN,
        LIGHT_BLUE,
        LIGHT_GRAY,
        LIME,
        MAGENTA,
        ORANGE,
        PINK,
        PURPLE,
        RED,
        WHITE,
        YELLOW;

        public static final Codec<FeatherColor> CODEC = StringRepresentable.fromValues(FeatherColor::values);
        public static final StreamCodec<FriendlyByteBuf, FeatherColor> STREAM_CODEC = StreamCodec.of((byteBuf, featherColor) -> byteBuf.writeUtf(featherColor.name()), (byteBuf) -> FeatherColor.valueOf(byteBuf.readUtf()));

        public static FeatherColor getRandom(RandomSource random) {
            return FeatherColor.values()[random.nextInt(FeatherColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum FeatherShape implements StringRepresentable {
        CURVED(1.0, 1.0, 1.0),
        FLAT(1.0, 1.0, 1.0),
        POINTED(1.0, 1.0, 1.0);

        public static final Codec<FeatherShape> CODEC = StringRepresentable.fromValues(FeatherShape::values);
        public static final StreamCodec<FriendlyByteBuf, FeatherShape> STREAM_CODEC = StreamCodec.of((byteBuf, featherShape) -> byteBuf.writeUtf(featherShape.name()), (byteBuf) -> FeatherShape.valueOf(byteBuf.readUtf()));

        private final double speed;
        private final double stamina;
        private final double strength;

        FeatherShape(double speed, double stamina, double strength) {
            this.speed = speed;
            this.stamina = stamina;
            this.strength = strength;
        }

        public double getSpeed() {
            return this.speed;
        }

        public double getStamina() {
            return this.stamina;
        }

        public double getStrength() {
            return this.strength;
        }

        public static FeatherShape getRandom(RandomSource random) {
            return FeatherShape.values()[random.nextInt(FeatherShape.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}

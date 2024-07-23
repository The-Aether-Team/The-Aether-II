package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.api.entity.MoaFeatherShape;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIMoaFeatherShapes;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.ai.brain.MoaAi;
import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class Moa extends MountableAnimal {
    private static final EntityDataAccessor<Optional<UUID>> DATA_MOA_UUID_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Holder<MoaFeatherShape>> DATA_FEATHER_SHAPE_ID = SynchedEntityData.defineId(Moa.class, AetherIIDataSerializers.MOA_FEATHER_SHAPE.value());
    private static final EntityDataAccessor<Integer> DATA_KERATIN_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_EYE_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_FEATHER_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> DATA_RIDER_UUID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> DATA_LAST_RIDER_UUID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_JUMPS_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_HUNGRY_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_AMOUNT_FED_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SITTING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> DATA_FOLLOWING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UUID);

    protected static final ImmutableList<SensorType<? extends Sensor<? super Moa>>> SENSOR_TYPES = ImmutableList.of(
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
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.HOME
    );

    private int jumpCooldown;
    private int flapCooldown;

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
    }


    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MOA_UUID_ID, Optional.empty());
        builder.define(DATA_FEATHER_SHAPE_ID, AetherIIMoaFeatherShapes.getRegistry(this.registryAccess()).getHolderOrThrow(AetherIIMoaFeatherShapes.CURVED));
        builder.define(DATA_KERATIN_COLOR, KeratinColor.TEMPEST.getColor());
        builder.define(DATA_EYE_COLOR, EyeColor.PORTAGE.getColor());
        builder.define(DATA_FEATHER_COLOR, FeatherColor.BLUE.getColor());
        builder.define(DATA_RIDER_UUID, Optional.empty());
        builder.define(DATA_LAST_RIDER_UUID, Optional.empty());
        builder.define(DATA_REMAINING_JUMPS_ID, 0);
        builder.define(DATA_HUNGRY_ID, false);
        builder.define(DATA_AMOUNT_FED_ID, 0);
        builder.define(DATA_PLAYER_GROWN_ID, false);
        builder.define(DATA_SITTING_ID, false);
        builder.define(DATA_FOLLOWING_ID, Optional.empty());
    }

    @Override
    protected Brain.Provider<Moa> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
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
    protected void customServerAiStep() {
        this.level().getProfiler().push("kirridBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("kirridActivityUpdate");
        MoaAi.updateActivity(this);
        this.level().getProfiler().pop();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (this.level().isClientSide) {
            return false;
        } else {
            if (flag && pSource.getEntity() instanceof LivingEntity) {
                MoaAi.maybeRetaliate(this, (LivingEntity) pSource.getEntity());
            }

            return flag;
        }
    }

    /**
     * Sets up Moas when spawned.
     *
     * @param level      The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason     The {@link MobSpawnType} reason.
     * @param spawnData  The {@link SpawnGroupData}.
     * @param tag        The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnData) {
        this.generateMoaUUID(); //todo: 1.21 tag passing into this method was removed.

        if (reason != MobSpawnType.NATURAL) {
            Moa.KeratinColor keratinColor = Moa.KeratinColor.getRandom(this.getRandom());
            Moa.EyeColor eyeColor = Moa.EyeColor.getRandom(this.getRandom());
            Moa.FeatherColor featherColor = Moa.FeatherColor.getRandom(this.getRandom());
            this.setKeratinColor(keratinColor.getColor());
            this.setEyeColor(eyeColor.getColor());
            this.setFeatherColor(featherColor.getColor());
        }


//        if (tag != null) { // Applies NBT when spawned from incubation.
//            if (tag.contains("IsBaby")) {
//                this.setBaby(tag.getBoolean("IsBaby"));
//            }
//            if (tag.contains("MoaType")) {
//                ResourceKey<MoaType> moaTypeKey = AetherIIMoaTypes.getResourceKey(level.registryAccess(), tag.getString("MoaType"));
//                if (moaTypeKey != null) {
//                    this.setMoaTypeByKey(moaTypeKey);
//                }
//            }
//            if (tag.contains("Hungry")) {
//                this.setHungry(tag.getBoolean("Hungry"));
//            }
//            if (tag.contains("PlayerGrown")) {
//                this.setPlayerGrown(tag.getBoolean("PlayerGrown"));
//            }
//        }
        if (spawnData == null) { // Disallow baby Moas from spawning in spawn groups.
            spawnData = new AgeableMob.AgeableMobGroupData(false);
        }
//        if (this.getMoaType() == null) { // A random Moa Type to set during natural spawning.
//            MoaType moaType = AetherIIMoaTypes.getWeightedChance(level.registryAccess(), this.getRandom());
//            ResourceKey<MoaType> moaTypeKey = AetherIIMoaTypes.getResourceKey(level.registryAccess(), moaType);
//            if (moaTypeKey != null) {
//                this.setMoaTypeByKey(moaTypeKey);
//            }
//        }
//        if (this.getMoaType() == null) {
//            this.setMoaTypeByKey(AetherIIMoaTypes.BLUE);
//        }
        if (reason == MobSpawnType.STRUCTURE) {
            //set moa home when spawn in nest
            MoaAi.initMoaHomeMemories(this, this.random);
        }
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    /**
     * Refreshes the Moa's bounding box dimensions.
     *
     * @param dataAccessor The {@link EntityDataAccessor} for the entity.
     */
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        if (DATA_SITTING_ID.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    /**
     * Handles wing animation.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        //this.animateWings();
    }

    /**
     * Handles Moa behavior.
     */
    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double max = this.isVehicle() ? -0.5 : -0.1;
            double fallSpeed = Math.max(gravity.getValue() * -1.25, max); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
                this.setEntityOnGround(false);
            }
        }
        if (this.onGround()) { // Reset jumps when the Moa is on the ground.
            this.setRemainingJumps(this.getMaxJumps());
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
                    this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
                }
            }
        } else {
            this.setHungry(false);
            this.setAmountFed(0);
        }

        // Handles rider tracking.
        if (this.getControllingPassenger() instanceof Player player) {
            if (this.getRider() == null) {
                this.setRider(player.getUUID());
            }
        } else {
            if (this.getRider() != null) {
                this.setRider(null);
            }
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
        this.checkSlowFallDistance(); // Resets the Moa's fall distance.
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
            this.generateMoaUUID();
            if (this.getLastRider() == null || this.getLastRider() != player.getUUID()) {
                this.setLastRider(player.getUUID());
            }
            /*if (!player.level().isClientSide()) {
                player.getData(AetherDataAttachments.AETHER_PLAYER).setSynched(player.getId(), INBTSynchable.Direction.CLIENT, "setLastRiddenMoa", this.getMoaUUID()); // Tracks the player as having last ridden this Moa.
                Map<UUID, MoaData> userSkinsData = ServerPerkData.MOA_SKIN_INSTANCE.getServerPerkData(player.getServer());
                if (userSkinsData.containsKey(this.getLastRider())) { // Tracks a Moa Skin as being tied to this Moa and this passenger.
                    ServerPerkData.MOA_SKIN_INSTANCE.applyPerkWithVerification(player.getServer(), this.getLastRider(), new MoaData(this.getMoaUUID(), userSkinsData.get(this.getLastRider()).moaSkin()));
                }
            }*/
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
                    if (this.isControlledByLocalInstance()) {
                        this.travelWithInput(new Vec3(0, vector.y(), 0));
                        this.lerpSteps = 0;
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
     * Handles cooldowns, remaining jumps, and particles when jumping.
     *
     * @param mob The jumping {@link Mob}.
     */
    @Override
    public void onJump(Mob mob) {
        super.onJump(mob);
        this.setJumpCooldown(10);
        if (!this.onGround()) {
            this.setRemainingJumps(this.getRemainingJumps() - 1);
            this.spawnExplosionParticle();
        }
        this.setFlapCooldown(0); // Causes the flap sound to be played in Moa#riderTick().
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
        if (this.isPlayerGrown() && player.isShiftKeyDown()) {
            this.setSitting(!this.isSitting());

            return InteractionResult.sidedSuccess(this.level().isClientSide());
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
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 20; ++i) {
            EntityUtil.spawnMovementExplosionParticles(this);
        }
    }

    /**
     * Generates a {@link UUID} for this Moa; used for Moa Skin tracking.
     */
    public void generateMoaUUID() {
        if (this.getMoaUUID() == null) {
            this.setMoaUUID(UUID.randomUUID());
        }
    }

    /**
     * @return The {@link UUID} for this Moa.
     */
    @javax.annotation.Nullable
    public UUID getMoaUUID() {
        return this.getEntityData().get(DATA_MOA_UUID_ID).orElse(null);
    }

    /**
     * Sets this Moa's {@link UUID}.
     *
     * @param uuid THe {@link UUID}.
     */
    private void setMoaUUID(@javax.annotation.Nullable UUID uuid) {
        this.getEntityData().set(DATA_MOA_UUID_ID, Optional.ofNullable(uuid));
    }

    public Holder<MoaFeatherShape> getFeatherShape() {
        return this.entityData.get(DATA_FEATHER_SHAPE_ID);
    }

    public void setFeatherShape(Holder<MoaFeatherShape> shape) {
        this.entityData.set(DATA_FEATHER_SHAPE_ID, shape);
    }

    public int getKeratinColor() {
        return this.entityData.get(DATA_KERATIN_COLOR);
    }

    public void setKeratinColor(int color) {
        this.entityData.set(DATA_KERATIN_COLOR, color);
    }

    public int getEyeColor() {
        return this.entityData.get(DATA_EYE_COLOR);
    }

    public void setEyeColor(int color) {
        this.entityData.set(DATA_EYE_COLOR, color);
    }

    public int getFeatherColor() {
        return this.entityData.get(DATA_FEATHER_COLOR);
    }

    public void setFeatherColor(int color) {
        this.entityData.set(DATA_FEATHER_COLOR, color);
    }

    /**
     * @return The {@link UUID} of the current rider of this Moa.
     */
    @javax.annotation.Nullable
    public UUID getRider() {
        return this.getEntityData().get(DATA_RIDER_UUID).orElse(null);
    }

    /**
     * Sets the current rider of this Moa.
     *
     * @param uuid The {@link UUID}.
     */
    public void setRider(@javax.annotation.Nullable UUID uuid) {
        this.getEntityData().set(DATA_RIDER_UUID, Optional.ofNullable(uuid));
    }

    /**
     * @return The {@link UUID} of the last rider of this Moa (including the current rider).
     */
    @javax.annotation.Nullable
    public UUID getLastRider() {
        return this.getEntityData().get(DATA_LAST_RIDER_UUID).orElse(null);
    }

    /**
     * Sets the last rider of this Moa (including the current rider).
     *
     * @param uuid The {@link UUID}.
     */
    public void setLastRider(@javax.annotation.Nullable UUID uuid) {
        this.getEntityData().set(DATA_LAST_RIDER_UUID, Optional.ofNullable(uuid));
    }

    /**
     * @return The {@link Integer} value for the remaining jumps.
     */
    public int getRemainingJumps() {
        return this.getEntityData().get(DATA_REMAINING_JUMPS_ID);
    }

    /**
     * Sets the remaining jumps.
     *
     * @param remainingJumps The {@link Integer} value.
     */
    public void setRemainingJumps(int remainingJumps) {
        this.getEntityData().set(DATA_REMAINING_JUMPS_ID, remainingJumps);
    }

    /**
     * @return Whether this Moa is hungry, as a {@link Boolean}.
     */
    public boolean isHungry() {
        return this.getEntityData().get(DATA_HUNGRY_ID);
    }

    /**
     * Sets whether this Moa is hungry.
     *
     * @param hungry The {@link Boolean} value.
     */
    public void setHungry(boolean hungry) {
        this.getEntityData().set(DATA_HUNGRY_ID, hungry);
    }

    /**
     * @return The {@link Integer} value for how many times this Moa has been fed.
     */
    public int getAmountFed() {
        return this.getEntityData().get(DATA_AMOUNT_FED_ID);
    }

    /**
     * Sets the amount of times this Moa has been fed.
     *
     * @param amountFed The {@link Integer} value.
     */
    public void setAmountFed(int amountFed) {
        this.getEntityData().set(DATA_AMOUNT_FED_ID, amountFed);
    }

    /**
     * @return Whether this Moa was raised by the player, as a {@link Boolean}.
     */
    public boolean isPlayerGrown() {
        return this.getEntityData().get(DATA_PLAYER_GROWN_ID);
    }

    /**
     * Sets whether this Moa was raised by the player.
     *
     * @param playerGrown The {@link Boolean} value.
     */
    public void setPlayerGrown(boolean playerGrown) {
        this.getEntityData().set(DATA_PLAYER_GROWN_ID, playerGrown);
    }

    /**
     * @return Whether this Moa is sitting, as a {@link Boolean}.
     */
    public boolean isSitting() {
        return this.getEntityData().get(DATA_SITTING_ID);
    }

    /**
     * Sets whether this Moa is sitting.
     *
     * @param isSitting The {@link Boolean} value.
     */
    public void setSitting(boolean isSitting) {
        this.getEntityData().set(DATA_SITTING_ID, isSitting);
    }

    /**
     * @return Whether this Moa is following the player, as a {@link Boolean}.
     */
    @javax.annotation.Nullable
    public UUID getFollowing() {
        return this.getEntityData().get(DATA_FOLLOWING_ID).orElse(null);
    }

    /**
     * Sets whether this Moa is following the player.
     *
     * @param uuid The {@link Boolean} value.
     */
    public void setFollowing(@javax.annotation.Nullable UUID uuid) {
        this.getEntityData().set(DATA_FOLLOWING_ID, Optional.ofNullable(uuid));
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
    public int getMaxJumps() {
        return 3;
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
        return (effect.getEffect() != AetherIIEffects.TOXIN.get() || !this.isPlayerGrown()) && super.canBeAffected(effect);
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

    /**
     * @return A {@link Boolean} for whether the Moa can jump, determined by remaining jumps and jump cooldown.
     */
    @Override
    public boolean canJump() {
        return this.getRemainingJumps() > 0 && this.getJumpCooldown() == 0;
    }

    @Override
    public boolean isSaddleable() {
        return super.isSaddleable() && this.isPlayerGrown();
    }

    /**
     * @see MountableMob#getMountJumpStrength()
     */
    @Override
    public double getMountJumpStrength() {
        return this.onGround() ? 0.95 : 0.90;
    }

    /**
     * @return The {@link Float} for the steering speed from the {@link MoaType}.
     */
    @Override
    public float getSteeringSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.15F;
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        if (this.hasControllingPassenger()) {
            float f = Math.min(pPartialTick * 1.0F, 1.0F);
            this.walkAnimation.update(f, 0.4F);
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
            return this.getSteeringSpeed() * 0.45F;
        } else {
            return this.getSteeringSpeed() * 0.025F;
        }
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() ? super.getMaxFallDistance() : 14;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return this.isSitting() ? super.getPassengerRidingPosition(entity).add(0, -0.575, 0) : super.getPassengerRidingPosition(entity).add(0, -0.65, 0);
    }

    /**
     * @return The float for the Moa's hitbox scaling. Set to a flat value, as Moa hitbox scaling is handled by {@link Moa#getDimensions(Pose)}.
     */
    @Override
    public float getScale() {
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
            dimensions = dimensions.scale(1.0F, 0.5F);
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

    /**
     * @return A Moa Egg {@link ItemStack} corresponding to the Moa's {@link MoaType}.
     */

    //TODO MOA EGG
    /*@Nullable
    @Override
    public ItemStack getPickResult() {
        MoaEggItem moaEggItem = MoaEggItem.byId(this.getMoaTypeKey());
        return moaEggItem == null ? null : new ItemStack(moaEggItem);
    }*/

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.getMoaUUID() != null) {
            tag.putUUID("MoaUUID", this.getMoaUUID());
        }
        tag.putBoolean("IsBaby", this.isBaby());
        tag.putString("FeatherShape", this.getFeatherShape().unwrapKey().orElse(AetherIIMoaFeatherShapes.CURVED).location().toString());
        tag.putInt("KeratinColor", this.getKeratinColor());
        tag.putInt("EyeColor", this.getEyeColor());
        tag.putInt("FeatherColor", this.getFeatherColor());
        if (this.getRider() != null) {
            tag.putUUID("Rider", this.getRider());
        }
        if (this.getLastRider() != null) {
            tag.putUUID("LastRider", this.getLastRider());
        }
        tag.putInt("RemainingJumps", this.getRemainingJumps());
        tag.putBoolean("Hungry", this.isHungry());
        tag.putInt("AmountFed", this.getAmountFed());
        tag.putBoolean("PlayerGrown", this.isPlayerGrown());
        tag.putBoolean("Sitting", this.isSitting());
        if (this.getFollowing() != null) {
            tag.putUUID("Following", this.getFollowing());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("MoaUUID")) {
            this.setMoaUUID(tag.getUUID("MoaUUID"));
        }
        if (tag.contains("IsBaby")) {
            this.setBaby(tag.getBoolean("IsBaby"));
        }
        Optional.ofNullable(ResourceLocation.tryParse(tag.getString("FeatherShape")))
                .map(location -> ResourceKey.create(AetherIIMoaFeatherShapes.MOA_FEATHER_SHAPE_REGISTRY_KEY, location))
                .flatMap((key) -> AetherIIMoaFeatherShapes.getRegistry(this.registryAccess()).getHolder(key))
                .ifPresent(this::setFeatherShape);
        if (tag.contains("KeratinColor")) {
            this.setKeratinColor(tag.getInt("KeratinColor"));
        }
        if (tag.contains("EyeColor")) {
            this.setEyeColor(tag.getInt("EyeColor"));
        }
        if (tag.contains("FeatherColor")) {
            this.setFeatherColor(tag.getInt("FeatherColor"));
        }
        if (tag.hasUUID("Rider")) {
            this.setRider(tag.getUUID("Rider"));
        }
        if (tag.hasUUID("LastRider")) {
            this.setLastRider(tag.getUUID("LastRider"));
        }
        if (tag.contains("RemainingJumps")) {
            this.setRemainingJumps(tag.getInt("RemainingJumps"));
        }
        if (tag.contains("Hungry")) {
            this.setHungry(tag.getBoolean("Hungry"));
        }
        if (tag.contains("AmountFed")) {
            this.setAmountFed(tag.getInt("AmountFed"));
        }
        if (tag.contains("PlayerGrown")) {
            this.setPlayerGrown(tag.getBoolean("PlayerGrown"));
        }
        if (tag.contains("Sitting")) {
            this.setSitting(tag.getBoolean("Sitting"));
        }
        if (tag.contains("Following")) {
            this.setFollowing(tag.getUUID("Following"));
        }
    }

    public enum KeratinColor implements StringRepresentable {
        SKY_BLUE(9946827),
        DEEP_SKY(7437951),
        SAND(8355441),
        MIDNIGHT_SKY(3489636),
        SWAMP(5727305),
        ROYAL(4802916),
        TEMPEST(6572361);

        private final int color;

        KeratinColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static KeratinColor getRandom(RandomSource random) {
            return KeratinColor.values()[random.nextInt(KeratinColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum EyeColor implements StringRepresentable {
        ALTO(14277081),
        STRAW(14269583),
        WINTER_HAZEL(14274191),
        GOSSIP(11662240),
        MINT(10548161),
        ICE(10548211),
        PERANO(10535411),
        PORTAGE(11641075);

        private final int color;

        EyeColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static EyeColor getRandom(RandomSource random) {
            return EyeColor.values()[random.nextInt(EyeColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public enum FeatherColor implements StringRepresentable {
        BLACK(1710618),
        GREY(4210752),
        WHITE(15132390),
        IROKO(5060642),
        ORANGE(14261855),
        LIBSON_BROWN(5065003),
        YELLOW(14272093),
        MALLARD(4148534),
        LIME_GREEN(12645275),
        EVERGLADE(3165499),
        GREEN(7992218),
        PLANTATION(3558733),
        SKY_BLUE(178779123),
        CLOUD_BURST(3554893),
        BLUE(10538483),
        PORT_GORE(3814989),
        PURPLE(11509491),
        BOSSANOVA(4732493),
        PINK(14785011),
        LIVID_BROWN(5060163),
        PUSE(15968982),
        DEEP_RED(5055010),
        RED(15944009);

        private final int color;

        FeatherColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return this.color;
        }

        public static FeatherColor getRandom(RandomSource random) {
            return FeatherColor.values()[random.nextInt(FeatherColor.values().length)];
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}

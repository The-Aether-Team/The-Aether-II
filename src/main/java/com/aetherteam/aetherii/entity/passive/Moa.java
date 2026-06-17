package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.api.entity.CustomPickItemEntity;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
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
import com.aetherteam.aetherii.item.components.MoaVariant;
import com.aetherteam.aetherii.item.miscellaneous.MoaFeedItem;
import com.aetherteam.aetherii.item.miscellaneous.MoaSaddlebagItem;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Moa extends MountableAetherAnimal implements ContainerListener, HasCustomInventoryScreen, OwnableEntity, CustomPickItemEntity, PlayerRideableJumping {
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_MOA_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    protected static final EntityDataAccessor<Integer> DATA_FEATHER_SHAPE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_KERATIN_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_EYE_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_FEATHER_COLOR = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> DATA_HUNGRY = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_AMOUNT_FED_POINT = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_PLAYER_GROWN = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_SHEARING_TIME = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_RIDER_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_LAST_RIDER_REFERENCE = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    protected static final EntityDataAccessor<Integer> DATA_REMAINING_STAMINA = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_SITTING = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_FOLLOWING_ID = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    protected static final EntityDataAccessor<ItemStack> DATA_SADDLEBAG = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.ITEM_STACK);

    protected static final EntityDataAccessor<OptionalInt> DATA_SPECIAL_VARIANT = SynchedEntityData.defineId(Moa.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
    private static final int MOA_FEEDING_TICK = 8000;

    private SimpleContainer inventory;

    private int jumpCooldown;
    private int flapCooldown;
    private int staminaHealCooldown;

    private float flap;
    private float flapO;

    private int flyTick;
    private int hungryTick;
    private int feedingTimeCount;
    private int feedingCooldown;

    private float playerJumpPendingScale;

    private int eggTime = this.getEggTime();

    public Moa(EntityType<? extends Moa> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.FIRE, -1.0F);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.ON_TOP_OF_POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DAMAGING, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, -1.0F);
        this.createInventory();
        this.setFeedingCooldown();
        this.hungryTick = MOA_FEEDING_TICK;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.STEP_HEIGHT, 1.0)
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        this.generateMoaReference(); //todo: 1.21 tag passing into this method was removed.

        if (reason != EntitySpawnReason.NATURAL) {
            this.setKeratinColor(KeratinColor.getRandom(this.getRandom(), false));
            this.setEyeColor(EyeColor.getRandom(this.getRandom(), false));
            this.setFeatherColor(FeatherColor.getRandom(this.getRandom(), false));
            this.setFeatherShape(FeatherShape.getRandom(this.getRandom(), false));
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
        builder.define(DATA_FEATHER_SHAPE, FeatherShape.DEFAULT.id());
        builder.define(DATA_KERATIN_COLOR, KeratinColor.DEFAULT.id());
        builder.define(DATA_EYE_COLOR, EyeColor.DEFAULT.id());
        builder.define(DATA_FEATHER_COLOR, FeatherColor.DEFAULT.id());
        builder.define(DATA_RIDER_REFERENCE, Optional.empty());
        builder.define(DATA_LAST_RIDER_REFERENCE, Optional.empty());
        builder.define(DATA_REMAINING_STAMINA, 0);
        builder.define(DATA_HUNGRY, false);
        builder.define(DATA_AMOUNT_FED_POINT, 0);
        builder.define(DATA_PLAYER_GROWN, false);
        builder.define(DATA_SHEARING_TIME, 0);
        builder.define(DATA_SITTING, false);
        builder.define(DATA_FOLLOWING_ID, Optional.empty());
        builder.define(DATA_SADDLEBAG, ItemStack.EMPTY);
        builder.define(DATA_SPECIAL_VARIANT, OptionalInt.empty());
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
    protected Brain<Moa> makeBrain(Brain.Packed packedBrain) {
        return MoaAi.BRAIN_PROVIDER.makeBrain(this, packedBrain);
    }

    @SuppressWarnings("unchecked")
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
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.syncToClients();
    }

    @Override
    public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {
        this.syncToClients();
    }

    @Override
    public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int i1) {
        this.syncToClients();
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
        if (this.level().isClientSide()) {
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

        if (!this.onGround()) {
            if (++this.flyTick > 40) {
                if (this.getControllingPassenger() != null && this.getControllingPassenger().isSprinting()) {
                    this.getControllingPassenger().setSprinting(false);
                }
            }
        } else {
            this.flyTick = 0;
        }

        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            if (!this.isFallFlying() && (!this.isVehicle() || this.fallDistance > 3.0)) {
                double max = this.isVehicle() ? -0.04 : -0.1;
                double fallSpeed = Math.min(gravity.getValue() * -0.5, max); // Entity isn't allowed to fall too slowly from gravity.
                if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
                    this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                    this.needsSync = true;
                    this.setEntityOnGround(false);
                }
            }
        }
        if (this.getRemainingStamina() < this.getMaxStamina()) {
            if (this.getStaminaHealCooldown() > 0) {
                int heal = this.onGround() ? 12 : 1;
                this.setStaminaHealCooldown(this.getStaminaHealCooldown() - heal);
            } else {
                this.setRemainingStamina(this.getRemainingStamina() + 1);
                this.setStaminaHealCooldown(300);
            }
        }
        if (this.getJumpCooldown() > 0) { // Handles jump reset behavior.
            this.setJumpCooldown(this.getJumpCooldown() - 1);
//            this.setPlayerJumped(false); //todo
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
                    if (--this.feedingCooldown <= 0) {
                        this.hungryTick = MOA_FEEDING_TICK;
                        this.setHungry(true);
                    }
                }
            } else {

                if (this.hungryTick > MOA_FEEDING_TICK / 2) {
                    if (this.getRandom().nextInt(10) == 0) {

                        this.level().broadcastEntityEvent(this, (byte) 42);
                    }
                } else {
                    if (this.getRandom().nextInt(10) == 0) {
                        this.level().broadcastEntityEvent(this, (byte) 13);

                    }
                }
                if (this.hungryTick > 0) {
                    this.hungryTick--;
                } else {
                    //missed feeding moa
                    this.setHungry(false);
                    this.feedingTimeCount++;
                    this.setFeedingCooldown();
                    switch (this.feedingTimeCount) {
                        case 0 -> this.setAge(-24000);
                        case 1 -> this.setAge(-16000);
                        case 2 -> this.setAge(-8000);
                        case 3 -> this.setBaby(false);
                    }
                    if (this.feedingTimeCount > 3 && !this.isBaby()) {
                        this.setBaby(false);
                    }
                    this.hungryTick = MOA_FEEDING_TICK;

                    if (!this.level().isClientSide()) {
                        this.level().broadcastEntityEvent(this, (byte) 13);
                    }
                }
            }
        } else {
            this.setHungry(false);
            this.setAmountFed(0);
        }

        // Handles rider tracking.
        if (this.getControllingPassenger() instanceof Player player) {
            if (this.getRider() == null) {
                this.setRider(EntityReference.of(player.getUUID()));
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

        if (!this.level().isClientSide() && this.getShearingTime() > 0) {
            this.setShearingTime(this.getShearingTime() - 1);
        }
    }

    protected void addParticlesAroundSelf(ParticleOptions particleOption) {
        for (int i = 0; i < 6; i++) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleOption, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d0, d1, d2);
        }
    }

    @Override
    public void handleEntityEvent(byte p_35391_) {
        if (p_35391_ == 13) {
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
        } else if (p_35391_ == 42) {
            this.level().addParticle(AetherIIParticleTypes.MOA_HUNGRY.get(), this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), this.getY() + 1, this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth(), 0.0, 0.0, 0.0);
        } else {
            super.handleEntityEvent(p_35391_);
        }
    }

    private void setFeedingCooldown() {
        this.feedingCooldown = 2400 + this.random.nextInt(2400);
    }

    @Override
    protected boolean considersEntityAsAlly(Entity entity) {
        if (this.isPlayerGrown()) {
            LivingEntity rootOwner = this.getRootOwner();
            if (entity == rootOwner) {
                return true;
            }

            if (rootOwner != null) {
                return rootOwner.isAlliedTo(entity);
            }
        }
        return super.considersEntityAsAlly(entity);
    }

    public void die(DamageSource cause) {
        Component deathMessage = this.getCombatTracker().getDeathMessage();
        super.die(cause);
        if (this.dead && this.level() instanceof ServerLevel serverlevel) {
            if (serverlevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES)) {
                LivingEntity owner = this.getOwner();
                if (owner instanceof ServerPlayer serverplayer) {
                    serverplayer.sendSystemMessage(deathMessage);
                }
            }
        }
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
                this.setLastRider(EntityReference.of(player.getUUID()));
            }
        }
        super.addPassenger(passenger);
    }

    /**
     * Handles travel movement and entity rotations.
     *
     * @param vector The {@link Vec3} for travel movement.
     */
//    @Override //todo
//    public void travel(Vec3 vector) {
//        if (!this.isSitting()) {
//            super.travel(vector);
//        } else {
//            if (this.isAlive()) {
//                LivingEntity entity = this.getControllingPassenger();
//                if (this.isVehicle() && this.isSaddled() && entity != null) {
//                    EntityUtil.copyRotations(this, entity);
//                    if (this.isLocalInstanceAuthoritative()) {
//                        this.travelWithInput(new Vec3(0, vector.y(), 0));
//                        this.lerpHeadSteps = 0;
//                    } else {
//                        this.calculateEntityAnimation(false);
//                        this.setDeltaMovement(Vec3.ZERO);
//                    }
//                } else {
//                    this.travelWithInput(new Vec3(0, vector.y(), 0));
//                }
//            }
//        }
//    }

    /**
     * Handles cooldowns, remaining stamina, and particles when jumping.
     *
     * @param mob The jumping {@link Mob}.
     */
    @Override
    protected void tickRidden(Player controller, Vec3 riddenInput) { //todo
        super.tickRidden(controller, riddenInput);
        if (this.isLocalInstanceAuthoritative()) {
            if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                this.executeRidersJump(this.playerJumpPendingScale, riddenInput);
            }
            this.playerJumpPendingScale = 0.0F;
            this.checkFallDistanceAccumulation();
        }
    }

    @Override
    public void onPlayerJump(int jumpAmount) { //todo
        if (jumpAmount < 0) {
            jumpAmount = 0;
        }
        this.playerJumpPendingScale = this.getPlayerJumpPendingScale(jumpAmount);

        if (!this.onGround()) {
            this.setStaminaHealCooldown(300);
            this.setRemainingStamina(this.getRemainingStamina() - 1);
        }

        this.setJumpCooldown(10);
        if (!this.onGround()) {
            this.spawnExplosionParticle();
            if (this.getControllingPassenger() instanceof Player && this.isFallFlying()) {
                Vec3 vec31 = this.getLookAngle();
                Vec3 vec32 = this.getDeltaMovement();
                this.setDeltaMovement(vec32.add(vec31.x * 0.1D + (vec31.x * 1.5D - vec32.x) * 0.5D, vec31.y * 0.1D + (vec31.y * 1.5D - vec32.y) * 0.5D, vec31.z * 0.1D + (vec31.z * 1.5D - vec32.z) * 0.5D).scale(0.9));
            }
        }
        this.setFlapCooldown(0); // Causes the flap sound to be played in Moa#riderTick().
    }

    @Override
    public void handleStartJump(int jumpScale) {

    }

    @Override
    public void handleStopJump() {

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
                            moa.setKeratinColor(type.keratinColor());
                            moa.setEyeColor(type.eyeColor());
                            moa.setFeatherColor(type.featherColor());
                            moa.setFeatherShape(type.featherShape());
                            moa.snapTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(this.getRandom().nextFloat() * 360.0F), 0.0F);
                            this.level().addFreshEntity(moa);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        } else if (itemStack.canPerformAction(ItemAbilities.SHEARS_HARVEST) && !this.isBaby() && this.isPlayerGrown() && this.getShearingTime() == 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
                ItemStack featherStack = new ItemStack(AetherIIItems.MOA_FEATHER.get(), 4);
                FeatherColor featherColor = this.getFeatherColor();
                var specialVariantOpt = this.getSpecialVariant();
                if (specialVariantOpt.isPresent()) {
                    var specialVariant = specialVariantOpt.get();
                    featherColor = specialVariant.getFeatherColor(this);
                    specialVariant.addDataToFeatherItem(featherStack);
                }
                featherStack.set(AetherIIDataComponents.FEATHER_COLOR, featherColor);
                this.spawnAtLocation(serverLevel, featherStack);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.playSound(SoundEvents.SHEARS_SNIP);
                this.setShearingTime(this.getRandom().nextInt(2000));
            }
            itemStack.hurtAndBreak(32, player, hand);
            return InteractionResult.SUCCESS;
        } else {
            if (this.isPlayerGrown() && player.isShiftKeyDown() && !this.isBaby()) {
//                this.setSitting(!this.isSitting()); //todo
                if (!this.level().isClientSide()) {
                    this.openMenu(player);
                }
                return InteractionResult.SUCCESS;
            } else if (this.isPlayerGrown() && !this.isBaby() && this.isSaddled() && !this.isVehicle() && !player.isSecondaryUseActive()) {
                this.doPlayerRide(player);
                return InteractionResult.SUCCESS;
            } else if (!this.level().isClientSide() && this.isPlayerGrown() && this.isBaby() && this.isHungry() && this.feedingTimeCount < 3 && itemStack.is(AetherIITags.Items.MOA_FOOD)) { // Feeds a hungry baby Moa.
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                if (this.hungryTick > MOA_FEEDING_TICK / 2) {
                    this.setAmountFed(this.getAmountFed() + 2);
                } else {
                    //bonus reduced
                    this.setAmountFed(this.getAmountFed() + 1);
                }
                this.feedingTimeCount++;
                switch (this.feedingTimeCount) {
                    case 0 -> this.setAge(-24000);
                    case 1 -> this.setAge(-16000);
                    case 2 -> this.setAge(-8000);
                    case 3 -> this.setBaby(false);
                }
                this.setFeedingCooldown();
                if (this.feedingTimeCount > 3 && !this.isBaby()) {
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
                if (player instanceof ServerPlayer serverPlayer) {
                    AetherIIAdvancementTriggers.FEED_MOA.get().trigger(serverPlayer, itemStack, this);
                }
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
            this.setMoaReference(EntityReference.of(UUID.randomUUID()));
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

    public FeatherShape getFeatherShape() {
        return FeatherShape.BY_ID.apply(this.entityData.get(DATA_FEATHER_SHAPE));
    }

    public void setFeatherShape(FeatherShape shape) {
        this.entityData.set(DATA_FEATHER_SHAPE, shape.id);
    }

    public KeratinColor getKeratinColor() {
        return KeratinColor.BY_ID.apply(this.entityData.get(DATA_KERATIN_COLOR));
    }

    public void setKeratinColor(KeratinColor color) {
        this.entityData.set(DATA_KERATIN_COLOR, color.id);
    }

    public EyeColor getEyeColor() {
        return EyeColor.BY_ID.apply(this.entityData.get(DATA_EYE_COLOR));
    }

    public void setEyeColor(EyeColor color) {
        this.entityData.set(DATA_EYE_COLOR, color.id);
    }

    public FeatherColor getFeatherColor() {
        return FeatherColor.BY_ID.apply(this.entityData.get(DATA_FEATHER_COLOR));
    }

    public void setFeatherColor(FeatherColor color) {
        this.entityData.set(DATA_FEATHER_COLOR, color.id);
    }

    public void calculatePotentialStats() {
        if (this.getAmountFed() != 4 || this.getAmountFed() != 5) {
            if (this.getAmountFed() == 0) {
                this.getAttribute(AetherIIAttributes.MOA_STAMINA).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STAMINA) - 4);
                this.getAttribute(AetherIIAttributes.MOA_SPEED).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_SPEED) - 4);
                this.getAttribute(AetherIIAttributes.MOA_STRENGTH).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STRENGTH) - 4);

            } else if (this.getAmountFed() == 1 && this.getAmountFed() == 2) {
                this.getAttribute(AetherIIAttributes.MOA_STAMINA).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STAMINA) - 2);
                this.getAttribute(AetherIIAttributes.MOA_SPEED).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_SPEED) - 2);
                this.getAttribute(AetherIIAttributes.MOA_STRENGTH).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STRENGTH) - 2);
            } else if (this.getAmountFed() == 3) {
                this.getAttribute(AetherIIAttributes.MOA_STAMINA).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STAMINA) - 1);
                this.getAttribute(AetherIIAttributes.MOA_SPEED).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_SPEED) - 1);
                this.getAttribute(AetherIIAttributes.MOA_STRENGTH).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STRENGTH) - 1);
            } else {
                this.getAttribute(AetherIIAttributes.MOA_STAMINA).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STAMINA) + 1);
                this.getAttribute(AetherIIAttributes.MOA_SPEED).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_SPEED) + 1);
                this.getAttribute(AetherIIAttributes.MOA_STRENGTH).setBaseValue(this.getAttributeBaseValue(AetherIIAttributes.MOA_STRENGTH) + 1);
            }
        }
    }

    /**
     * @return The {@link UUID} of the current rider of this Moa.
     */
    @Nullable
    public EntityReference<LivingEntity> getRider() {
        return this.getEntityData().get(DATA_RIDER_REFERENCE).orElse(null);
    }

    /**
     * Sets the current rider of this Moa.
     *
     * @param reference The {@link UUID}.
     */
    public void setRider(@Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_RIDER_REFERENCE, Optional.ofNullable(reference));
    }

    /**
     * @return The {@link UUID} of the last rider of this Moa (including the current rider).
     */
    @Nullable
    public EntityReference<LivingEntity> getLastRider() {
        return this.getEntityData().get(DATA_LAST_RIDER_REFERENCE).orElse(null);
    }

    /**
     * Sets the last rider of this Moa (including the current rider).
     *
     * @param reference The {@link UUID}.
     */
    public void setLastRider(@Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_LAST_RIDER_REFERENCE, Optional.ofNullable(reference));
    }

    /**
     * Sets the last rider of this Moa (including the current rider).
     *
     * @param lastRider The {@link LivingEntity}.
     */
    public void setLastRider(@Nullable LivingEntity lastRider) {
        this.getEntityData().set(DATA_LAST_RIDER_REFERENCE, Optional.ofNullable(lastRider).map(EntityReference::of));
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
     * @return The {@link Integer} value for how many amount of fed bonus(This will be affect growth stats).
     */
    public int getAmountFed() {
        return this.getEntityData().get(DATA_AMOUNT_FED_POINT);
    }

    /**
     * Sets the amount of fed bonus.
     *
     * @param amountFed The {@link Integer} value.
     */
    public void setAmountFed(int amountFed) {
        this.getEntityData().set(DATA_AMOUNT_FED_POINT, amountFed);
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

    public int getShearingTime() {
        return this.getEntityData().get(DATA_SHEARING_TIME);
    }

    public void setShearingTime(int shearingTime) {
        this.getEntityData().set(DATA_SHEARING_TIME, shearingTime);
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
    @Nullable
    public EntityReference<LivingEntity> getFollowing() {
        return this.getEntityData().get(DATA_FOLLOWING_ID).orElse(null);
    }

    /**
     * Sets whether this Moa is following the player.
     *
     * @param reference The {@link Boolean} value.
     */
    public void setFollowing(@Nullable EntityReference<LivingEntity> reference) {
        this.getEntityData().set(DATA_FOLLOWING_ID, Optional.ofNullable(reference));
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
    public int getAmbientSoundInterval() {
        return 580;
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
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherIISoundEvents.ENTITY_MOA_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected float nextStep() {
        return (int) this.moveDist + 4;
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

    @Nullable
    public EntityReference<LivingEntity> getOwnerReference() {
        return this.getEntityData().get(DATA_RIDER_REFERENCE).orElseGet(this::getLastRider);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        setLastRider(owner);
    }

    public void setOwnerReference(@Nullable EntityReference<LivingEntity> owner) {
        setLastRider(owner);
    }

    public void tame(Player player) {
        this.setOwner(player);
        if (player instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger(serverplayer, this);
        }
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
        return (effect.getEffect().value() != AetherIIMobEffects.TOXIN.get() || !this.isPlayerGrown()) && super.canBeAffected(effect);
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
        return (this.getRemainingStamina() > 0 && this.getJumpCooldown() == 0 || this.onGround());
    }

    public boolean isSaddleable() {
        return !this.isBaby() && this.isPlayerGrown();
    }

    protected void syncToClients() {
        if (!this.level().isClientSide()) {
            this.setSaddlebagStack(this.inventory.getItem(1));
        }
    }

    @Override
    protected float getRiddenSpeed(Player controller) { //todo
        float multiplier = controller != null && controller.isSprinting() && this.onGround() ? (float) (this.getAttributeValue(AetherIIAttributes.MOA_SPEED) * 0.1F) : 0.0F;
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.35F + multiplier;
    }

    @Override
    public float getFlyingSpeed() { //todo
        if (this.isVehicle() && this.isSaddled() && this.getControllingPassenger() instanceof Player controller) {
            if (this.onGround()) {
                return this.getRiddenSpeed(controller) * 0.2F;
            } else if (this.isFallFlying()) {
                return this.getRiddenSpeed(controller) * 0.25F;
            } else {
                return this.getRiddenSpeed(controller) * 0.2F;
            }
        } else {
            return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.025F;
        }
    }

    @Override
    protected float getMountedJumpPower(float multiplier) { //todo
        float strength = (float) (this.getAttributeValue(AetherIIAttributes.MOA_STRENGTH) * 0.01F);
        return this.onGround() ? 0.95F + strength : 0.90F + strength;
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

    @Override
    public LivingEntity getControllingPassenger() {
        return this.isSaddled() && this.getFirstPassenger() instanceof Player passenger ? passenger : super.getControllingPassenger();
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() || this.fallDistance < 5 ? 3 : 14;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale) {
        double base = 1.425;
        double back = -0.85F;
        return new Vec3(0.0F, base, back + 0.55F).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
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

    @Nullable
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
        if (age % -8000 == 0 || (age == 0 && this.feedingTimeCount > 3)) {
            super.setAge(age);
        }
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby()) {

            this.calculatePotentialStats();
        }
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    public int getInventorySize() {
        return 30;
    }

    public Optional<SpecialVariant> getSpecialVariant() {
        OptionalInt optionalInt = this.entityData.get(DATA_SPECIAL_VARIANT);
        if (!optionalInt.isEmpty()) {
            var variant = SpecialVariant.BY_ID.apply(optionalInt.getAsInt());
            if (variant.canApplyTo(this)) {
                return Optional.of(variant);
            } else {
                this.entityData.set(DATA_SPECIAL_VARIANT, optionalInt = OptionalInt.empty());
            }
        }
        for (var specialVariant : SpecialVariant.VALUES) {
            if (specialVariant.test(this)) {
                return Optional.of(specialVariant);
            }
        }
        return Optional.empty();
    }

    public MoaEggType getDefaultEggType() {
        return new MoaEggType(this.getKeratinColor(), this.getEyeColor(), this.getFeatherColor(), this.getFeatherShape());
    }

    public MoaEggType getEggType() {
        return this.getSpecialVariant().map(variant -> variant.getEggType(this)).orElseGet(this::getDefaultEggType);
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack moaEggItem = new ItemStack(AetherIIItems.MOA_EGG.get());
        moaEggItem.set(AetherIIDataComponents.MOA_EGG_TYPE, this.getEggType());
        return moaEggItem;
    }

    @Override
    public ItemStack getPickResult(ServerPlayer player, boolean includeData) {
        if (includeData) {
            ItemStack itemstack = super.getPickResult();
            if (itemstack == null) return null;
            if (itemstack.getItem() instanceof SpawnEggItem) {
                itemstack.copyFrom(AetherIIDataComponents.MOA_VARIANT, this);
            }
            return itemstack;
        } else {
            return this.getPickResult();
        }
    }

    public MoaVariant getVariant() {
        return new MoaVariant(this.getKeratinColor(), this.getEyeColor(), this.getFeatherColor(), this.getFeatherShape(), this.getSpecialVariant());
    }

    public void setVariant(MoaVariant variant) {
        this.setKeratinColor(variant.keratinColor());
        this.setEyeColor(variant.eyeColor());
        this.setFeatherColor(variant.featherColor());
        this.setFeatherShape(variant.featherShape());
        var specialVariant = variant.specialVariant();
        this.entityData.set(DATA_SPECIAL_VARIANT, specialVariant.isPresent() ? OptionalInt.of(specialVariant.get().id) : OptionalInt.empty());
    }

    @Nullable
    @Override
    public <T> T get(DataComponentType<? extends T> component) {
        return component == AetherIIDataComponents.MOA_VARIANT.get() ? castComponentValue(component, this.getVariant()) : super.get(component);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter componentGetter) {
        this.applyImplicitComponentIfPresent(componentGetter, AetherIIDataComponents.MOA_VARIANT.get());
        super.applyImplicitComponents(componentGetter);
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> component, T value) {
        if (component == AetherIIDataComponents.MOA_VARIANT.get()) {
            this.setVariant(castComponentValue(AetherIIDataComponents.MOA_VARIANT.get(), value));
            return true;
        } else {
            return super.applyImplicitComponent(component, value);
        }
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        if (this.getMoaReference() != null) {
            output.store("MoaUUID", EntityReference.codec(), this.getMoaReference());
        }
        output.putBoolean("IsBaby", this.isBaby());
        output.putString("FeatherShape", this.getFeatherShape().getSerializedName());
        output.putString("KeratinColor", this.getKeratinColor().getSerializedName());
        output.putString("EyeColor", this.getEyeColor().getSerializedName());
        output.putString("FeatherColor", this.getFeatherColor().getSerializedName());
        this.entityData.get(DATA_SPECIAL_VARIANT).ifPresentOrElse((variantId) -> {
            var variant = SpecialVariant.BY_ID.apply(variantId);
            if (variant.canApplyTo(this)) {
                output.putInt("MoaVariant", variant.id);
            } else {
                output.discard("MoaVariant");
            }
        }, () -> {
            output.discard("MoaVariant");
        });
        if (this.getRider() != null) {
            output.store("Rider", EntityReference.codec(), this.getRider());
        }
        if (this.getLastRider() != null) {
            output.store("LastRider", EntityReference.codec(), this.getLastRider());
        }
        output.putInt("StaminaHealCooldown", this.getStaminaHealCooldown());
        output.putInt("RemainingStamina", this.getRemainingStamina());
        output.putBoolean("Hungry", this.isHungry());
        output.putInt("MoaHungryTick", this.hungryTick);
        output.putInt("FeedingTimeCount", this.feedingTimeCount);
        output.putInt("FeedingCooldown", this.feedingCooldown);
        output.putInt("AmountFed", this.getAmountFed());
        output.putBoolean("PlayerGrown", this.isPlayerGrown());
        output.putBoolean("Sitting", this.isSitting());
        if (this.getFollowing() != null) {
            output.store("Following", EntityReference.codec(), this.getFollowing());
        }

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
        output.putBoolean("FlyingMode", this.isFallFlying());
        output.putInt("ShearingTime", this.getShearingTime());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("MoaUUID", EntityReference.<LivingEntity>codec()).ifPresent(this::setMoaReference);
        this.setBaby(input.getBooleanOr("IsBaby", false));
        input.getString("FeatherShape").map(FeatherShape::byName).ifPresent(this::setFeatherShape);
        input.getString("KeratinColor").map(KeratinColor::byName).ifPresent(this::setKeratinColor);
        input.getString("EyeColor").map(EyeColor::byName).ifPresent(this::setEyeColor);
        input.getString("FeatherColor").map(FeatherColor::byName).ifPresent(this::setFeatherColor);
        input.read("Rider", EntityReference.<LivingEntity>codec()).ifPresent(this::setRider);
        input.read("LastRider", EntityReference.<LivingEntity>codec()).ifPresent(this::setLastRider);
        input.getInt("StaminaHealCooldown").ifPresent(this::setStaminaHealCooldown);
        input.getInt("RemainingStamina").ifPresent(this::setRemainingStamina);
        this.setHungry(input.getBooleanOr("Hungry", false));
        this.hungryTick = input.getIntOr("MoaHungryTick", 0);
        this.feedingTimeCount = input.getIntOr("FeedingTimeCount", 0);
        input.getInt("FeedingCooldown").ifPresent(consumer -> this.feedingCooldown = flapCooldown);
        input.getInt("AmountFed").ifPresent(this::setAmountFed);
        this.setPlayerGrown(input.getBooleanOr("PlayerGrown", false));
        this.setSitting(input.getBooleanOr("Sitting", false));
        input.read("Following", EntityReference.<LivingEntity>codec()).ifPresent(this::setFollowing);

        input.read("SaddlebagsItem", ItemStack.OPTIONAL_CODEC).ifPresent((stack) -> this.inventory.setItem(1, stack.getItem() instanceof MoaSaddlebagItem ? stack : ItemStack.EMPTY));
        input.read("FeedItem", ItemStack.OPTIONAL_CODEC).ifPresent((stack) -> this.inventory.setItem(2, stack.getItem() instanceof MoaFeedItem ? stack : ItemStack.EMPTY));

        for (ItemStackWithSlot stackWithSlot : input.listOrEmpty("SaddlebagItems", ItemStackWithSlot.CODEC)) {
            if (stackWithSlot.isValidInContainer(this.inventory.getContainerSize())) {
                this.inventory.setItem(stackWithSlot.slot(), stackWithSlot.stack());
            }
        }

        input.getInt("MoaVariant").ifPresentOrElse((variantId) -> {
            var variant = SpecialVariant.BY_ID.apply(variantId);
            if (variant.canApplyTo(this)) {
                this.entityData.set(DATA_SPECIAL_VARIANT, OptionalInt.of(variant.id));
            } else {
                this.entityData.set(DATA_SPECIAL_VARIANT, OptionalInt.empty());
            }
        }, () -> {
            this.entityData.set(DATA_SPECIAL_VARIANT, OptionalInt.empty());
        });
        if (input.getBooleanOr("FlyingMode", false)) {
            this.startFallFlying();
        }
        this.setShearingTime(input.getIntOr("ShearingTime", 0));
        this.syncToClients();
    }


    public enum KeratinColor implements StringRepresentable {
        GRAY("gray", 0, false),
        BLUE("blue", 1, false),
        BROWN("brown", 2, false),
        GREEN("green", 3, false),
        RED("red", 4, false),
        BLEY("bley", 5, true),
        ;

        public static final KeratinColor DEFAULT = GRAY;

        /**
         * Alternate spelling of {@link #GRAY}
         */
        public static final KeratinColor GREY = GRAY;

        public static final StringRepresentable.EnumCodec<KeratinColor> CODEC = StringRepresentable.fromEnum(KeratinColor::values);
        static final IntFunction<KeratinColor> BY_ID = ByIdMap.continuous(KeratinColor::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, KeratinColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, KeratinColor::id);

        private final String name;
        private final int id;
        public final boolean isSpecialColor;

        private static final KeratinColor[] VALUES = values();
        private static final KeratinColor[] NORMAL_VALUES = Stream.of(VALUES).filter(KeratinColor::isNormalColor).toArray(KeratinColor[]::new);

        private KeratinColor(String name, int id, boolean isSpecialColor) {
            this.name = name;
            this.id = id;
            this.isSpecialColor = isSpecialColor;
        }

        public boolean isSpecialColor() {
            return this.isSpecialColor;
        }

        public boolean isNormalColor() {
            return !this.isSpecialColor;
        }

        public static Stream<KeratinColor> stream() {
            return Stream.of(VALUES);
        }

        public static Stream<KeratinColor> stream(boolean includeSpecial) {
            return Stream.of(includeSpecial ? VALUES : NORMAL_VALUES);
        }

        public static KeratinColor getRandom(RandomSource random) {
            return Util.getRandom(VALUES, random);
        }

        public static KeratinColor getRandom(RandomSource random, boolean includeSpecial) {
            return Util.getRandom(includeSpecial ? VALUES : NORMAL_VALUES, random);
        }

        public String getName() {
            return this.name;
        }

        /**
         * @return the KeratinColor specified by the given name or null if no such KeratinColor exists
         */
        @Nullable
        public static KeratinColor byName(@Nullable String name) {
            KeratinColor color = CODEC.byName(name);
            return color == null && "grey".equals(name) ? GRAY : color;
        }

        @Nullable
        @Contract("_,!null->!null;_,null->_")
        public static KeratinColor byName(@Nullable String name, @Nullable KeratinColor fallback) {
            KeratinColor color = KeratinColor.byName(name);
            return color != null ? color : fallback;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return this.id;
        }
    }

    public enum EyeColor implements StringRepresentable {
        BLUE("blue", 0, false),
        GREEN("green", 1, false),
        YELLOW("yellow", 2, false),
        GOLD("gold", 3, true),
        ;

        public static final EyeColor DEFAULT = BLUE;

        public static final StringRepresentable.EnumCodec<EyeColor> CODEC = StringRepresentable.fromEnum(EyeColor::values);
        static final IntFunction<EyeColor> BY_ID = ByIdMap.continuous(EyeColor::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, EyeColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, EyeColor::id);

        private final String name;
        private final int id;
        public final boolean isSpecialColor;

        private static final EyeColor[] VALUES = values();
        private static final EyeColor[] NORMAL_VALUES = Stream.of(VALUES).filter(EyeColor::isNormalColor).toArray(EyeColor[]::new);

        private EyeColor(String name, int id, boolean isSpecialColor) {
            this.name = name;
            this.id = id;
            this.isSpecialColor = isSpecialColor;
        }

        public boolean isSpecialColor() {
            return this.isSpecialColor;
        }

        public boolean isNormalColor() {
            return !this.isSpecialColor;
        }

        public static Stream<EyeColor> stream() {
            return Stream.of(VALUES);
        }

        public static Stream<EyeColor> stream(boolean includeSpecial) {
            return Stream.of(includeSpecial ? VALUES : NORMAL_VALUES);
        }

        public static EyeColor getRandom(RandomSource random) {
            return Util.getRandom(VALUES, random);
        }

        public static EyeColor getRandom(RandomSource random, boolean includeSpecial) {
            return Util.getRandom(includeSpecial ? VALUES : NORMAL_VALUES, random);
        }

        public String getName() {
            return this.name;
        }

        /**
         * @return the EyeColor specified by the given name or null if no such EyeColor exists
         */
        @Nullable
        public static EyeColor byName(@Nullable String name) {
            return CODEC.byName(name);
        }

        @Nullable
        @Contract("_,!null->!null;_,null->_")
        public static EyeColor byName(@Nullable String name, @Nullable EyeColor fallback) {
            EyeColor color = CODEC.byName(name);
            return color != null ? color : fallback;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return this.id;
        }
    }

    public enum FeatherColor implements StringRepresentable {
        LIGHT_BLUE("light_blue", 0, DyeColor.LIGHT_BLUE, false),
        BLACK("black", 1, DyeColor.BLACK, false),
        BLOOMING_RED("blooming_red", 2, DyeColor.RED, false),
        BLUE("blue", 3, DyeColor.BLUE, false),
        BROWN("brown", 4, DyeColor.BROWN, false),
        CLASSIC_BLACK("classic_black", 5, DyeColor.BLACK, false),
        CYAN("cyan", 6, DyeColor.CYAN, false),
        GRAY("gray", 7, DyeColor.GRAY, false),
        GREEN("green", 8, DyeColor.GREEN, false),
        LIGHT_GRAY("light_gray", 9, DyeColor.LIGHT_GRAY, false),
        LIME("lime", 10, DyeColor.LIME, false),
        MAGENTA("magenta", 11, DyeColor.MAGENTA, false),
        ORANGE("orange", 12, DyeColor.ORANGE, false),
        PINK("pink", 13, DyeColor.PINK, false),
        PURPLE("purple", 14, DyeColor.PURPLE, false),
        RED("red", 15, DyeColor.RED, false),
        WHITE("white", 16, DyeColor.WHITE, false),
        YELLOW("yellow", 17, DyeColor.YELLOW, false),
        DEEP_BLUE("deep_blue", 18, DyeColor.BLUE, true),
        ;

        public static final FeatherColor DEFAULT = LIGHT_BLUE;

        /**
         * Alternate spelling of {@link #GRAY}
         */
        public static final FeatherColor GREY = GRAY;
        /**
         * Alternate spelling of {@link #LIGHT_GRAY}
         */
        public static final FeatherColor LIGHT_GREY = LIGHT_GRAY;

        public static final StringRepresentable.EnumCodec<FeatherColor> CODEC = StringRepresentable.fromEnum(FeatherColor::values);
        static final IntFunction<FeatherColor> BY_ID = ByIdMap.continuous(FeatherColor::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, FeatherColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FeatherColor::id);

        private final String name;
        private final int id;
        public final DyeColor dyeColor;
        public final boolean isSpecialColor;

        private static final FeatherColor[] VALUES = values();
        private static final FeatherColor[] NORMAL_VALUES = Stream.of(VALUES).filter(FeatherColor::isNormalColor).toArray(FeatherColor[]::new);

        private static final EnumMap<DyeColor, EnumSet<FeatherColor>> BY_DYE_COLOR;

        static {
            var byDyeColor = new EnumMap<DyeColor, EnumSet<FeatherColor>>(DyeColor.class);
            java.util.function.Function<Object, EnumSet<FeatherColor>> ifAbsent = unused -> EnumSet.noneOf(FeatherColor.class);
            for (var featherColor : VALUES) {
                byDyeColor.computeIfAbsent(featherColor.dyeColor, ifAbsent).add(featherColor);
            }
            BY_DYE_COLOR = byDyeColor;
        }

        private FeatherColor(String name, int id, DyeColor dyeColor, boolean isSpecialColor) {
            this.name = name;
            this.id = id;
            this.dyeColor = dyeColor;
            this.isSpecialColor = isSpecialColor;
        }

        public boolean isSpecialColor() {
            return this.isSpecialColor;
        }

        public boolean isNormalColor() {
            return !this.isSpecialColor;
        }

        public DyeColor getDyeColor() {
            return this.dyeColor;
        }

        public static Stream<FeatherColor> stream() {
            return Stream.of(VALUES);
        }

        public static Stream<FeatherColor> stream(boolean includeSpecial) {
            return Stream.of(includeSpecial ? VALUES : NORMAL_VALUES);
        }

        public static FeatherColor getRandom(RandomSource random) {
            return Util.getRandom(VALUES, random);
        }

        public static FeatherColor getRandom(RandomSource random, boolean includeSpecial) {
            return Util.getRandom(includeSpecial ? VALUES : NORMAL_VALUES, random);
        }

        public String getName() {
            return this.name;
        }

        /**
         * @return the FeatherColor specified by the given name or null if no such FeatherColor exists
         */
        @Nullable
        public static FeatherColor byName(@Nullable String name) {
            FeatherColor color = CODEC.byName(name);
            return color != null || name == null ? color : switch (name) {
                case "grey" -> GRAY;
                case "light_grey" -> LIGHT_GRAY;
                default -> null;
            };
        }

        @Nullable
        @Contract("_,!null->!null;_,null->_")
        public static FeatherColor byName(@Nullable String name, @Nullable FeatherColor fallback) {
            FeatherColor color = FeatherColor.byName(name);
            return color != null ? color : fallback;
        }

        @Nullable
        @Contract("!null->!null;null->null")
        public static FeatherColor byDyeColor(@Nullable DyeColor dyeColor) {
            EnumSet<FeatherColor> colors = BY_DYE_COLOR.get(dyeColor);
            if (colors == null) return null;
            if (colors.size() == 1) return colors.iterator().next();
            return colors.stream().min((color1, color2) -> color1.id - color2.id).get();
        }

        @Nullable
        @Contract("!null,_->!null;null,_->null")
        public static FeatherColor byDyeColor(@Nullable DyeColor dyeColor, RandomSource random) {
            EnumSet<FeatherColor> colors = BY_DYE_COLOR.get(dyeColor);
            if (colors == null) return null;
            if (colors.size() == 1) return colors.iterator().next();
            int i = random.nextInt(colors.size());
            for (var color : colors) {
                if (i-- == 0) return color;
            }
            throw new AssertionError("Unreachable code");
        }

        public static Set<FeatherColor> ofDyeColor(DyeColor dyeColor) {
            return Collections.unmodifiableSet(BY_DYE_COLOR.get(dyeColor));
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return id;
        }
    }

    public enum FeatherShape implements StringRepresentable {
        CURVED("curved", 0, 1.0, 1.0, 1.0, false),
        FLAT("flat", 1, 1.0, 1.0, 1.0, false),
        POINTED("pointed", 2, 1.0, 1.0, 1.0, false),
        ;

        public static final FeatherShape DEFAULT = CURVED;

        public static final StringRepresentable.EnumCodec<FeatherShape> CODEC = StringRepresentable.fromEnum(FeatherShape::values);
        static final IntFunction<FeatherShape> BY_ID = ByIdMap.continuous(FeatherShape::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, FeatherShape> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, FeatherShape::id);

        private final String name;
        private final int id;
        public final double speed;
        public final double stamina;
        public final double strength;
        public final boolean isSpecialShape;

        private static final FeatherShape[] VALUES = values();
        private static final FeatherShape[] NORMAL_VALUES = Stream.of(VALUES).filter(FeatherShape::isNormalShape).toArray(FeatherShape[]::new);

        FeatherShape(String name, int id, double speed, double stamina, double strength, boolean isSpecialShape) {
            this.name = name;
            this.id = id;
            this.speed = speed;
            this.stamina = stamina;
            this.strength = strength;
            this.isSpecialShape = isSpecialShape;
        }

        public boolean isSpecialShape() {
            return this.isSpecialShape;
        }

        public boolean isNormalShape() {
            return !this.isSpecialShape;
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

        public static Stream<FeatherShape> stream() {
            return Stream.of(VALUES);
        }

        public static Stream<FeatherShape> stream(boolean includeSpecial) {
            return Stream.of(includeSpecial ? VALUES : NORMAL_VALUES);
        }

        public static FeatherShape getRandom(RandomSource random) {
            return Util.getRandom(VALUES, random);
        }

        public static FeatherShape getRandom(RandomSource random, boolean includeSpecial) {
            return Util.getRandom(includeSpecial ? VALUES : NORMAL_VALUES, random);
        }

        public String getName() {
            return this.name;
        }

        /**
         * @return the FeatherShape specified by the given name or null if no such FeatherShape exists
         */
        @Nullable
        public static FeatherShape byName(@Nullable String name) {
            return CODEC.byName(name);
        }

        @Nullable
        @Contract("_,!null->!null;_,null->_")
        public static FeatherShape byName(@Nullable String name, @Nullable FeatherShape fallback) {
            FeatherShape shape = CODEC.byName(name);
            return shape != null ? shape : fallback;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return this.id;
        }
    }

    public enum SpecialVariant implements StringRepresentable, Predicate<Moa> {
        RAPTOR("raptor", 0, "raptor", KeratinColor.BLEY, EyeColor.GOLD, FeatherColor.DEEP_BLUE, FeatherShape.CURVED) {
            @Override
            public boolean test(Moa moa) {
                if (moa.getCustomName() == null) return false;
                String customName = moa.getCustomName().getString(20);
                boolean result = switch (customName.hashCode()) {
                    case -1854343754, 387083286 -> customName.length() >= 6 && customName.length() < 20
                            && this.canApplyTo(moa);
                    default -> false;
                };
                return result;
            }

            @Override
            public boolean canApplyTo(Moa moa) {
                boolean result = moa.getFeatherShape() == FeatherShape.CURVED && switch (moa.getFeatherColor()) {
                    case BLUE:
                    case LIGHT_BLUE:
                    case DEEP_BLUE:
                        yield switch (moa.getEyeColor()) {
                            case YELLOW, GOLD -> switch (moa.getKeratinColor()) {
                                case GRAY, BLUE, BLEY -> true;
                                default -> false;
                            };
                            default -> false;
                        };
                    default:
                        yield false;
                };
                return result;
            }

            @Override
            public void addDataToFeatherItem(ItemStack feather) {
                feather.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(), List.of(true), List.of(), List.of()));
            }

            @Override
            public boolean dependsOnCustomName() {
                return true;
            }
        },
        ;

        public static final StringRepresentable.EnumCodec<SpecialVariant> CODEC = StringRepresentable.fromEnum(SpecialVariant::values);
        static final IntFunction<SpecialVariant> BY_ID = ByIdMap.continuous(SpecialVariant::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, SpecialVariant> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SpecialVariant::id);
        public static final Codec<Optional<SpecialVariant>> OPTIONAL_INT_CODEC = Codec.INT.xmap(id -> Optional.of(BY_ID.apply(id)), opt -> opt.isPresent() ? opt.get().id + 1 : 0);
        public static final Codec<SpecialVariant> INT_CODEC = Codec.INT.xmap(BY_ID::apply, SpecialVariant::id);

        private final String name;
        private final int id;
        @Nullable
        public final Identifier defaultTexture, babyTexture;
        @Nullable
        public final KeratinColor keratinColorOverride;
        @Nullable
        public final EyeColor eyeColorOverride;
        @Nullable
        public final FeatherColor featherColorOverride;
        @Nullable
        public final FeatherShape featherShapeOverride;
        @Nullable
        public final MoaEggType eggTypeOverride;

        private static final SpecialVariant[] VALUES = values();

        private SpecialVariant(String name, int id,
                               @Nullable Identifier defaultTexture, @Nullable Identifier babyTexture,
                               @Nullable KeratinColor keratinColorOverride, @Nullable EyeColor eyeColorOverride, @Nullable FeatherColor featherColorOverride, @Nullable FeatherShape featherShapeOverride,
                               @Nullable MoaEggType eggTypeOverride) {
            this.name = name;
            this.id = id;
            this.defaultTexture = defaultTexture;
            this.babyTexture = babyTexture;
            this.keratinColorOverride = keratinColorOverride;
            this.eyeColorOverride = eyeColorOverride;
            this.featherColorOverride = featherColorOverride;
            this.featherShapeOverride = featherShapeOverride;
            this.eggTypeOverride = eggTypeOverride;
        }

        private SpecialVariant(String name, int id) {
            this(name, id, null, null, null, null, null, null);
        }

        private SpecialVariant(String name, int id, String baseTextureNameNoModid) {
            this(name, id, baseTextureNameNoModid, null, null, null, null);
        }

        private SpecialVariant(String name, int id, Identifier baseTextureName) {
            this(name, id, baseTextureName, null, null, null, null);
        }

        private SpecialVariant(String name, int id,
                               @Nullable Identifier defaultTexture, @Nullable Identifier babyTexture) {
            this(name, id, defaultTexture, babyTexture, null, null, null, null);
        }

        private SpecialVariant(String name, int id, String baseTextureNameNoModid,
                               @Nullable KeratinColor keratinColorOverride, @Nullable EyeColor eyeColorOverride, @Nullable FeatherColor featherColorOverride, @Nullable FeatherShape featherShapeOverride) {
            this(name, id,
                    Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/" + baseTextureNameNoModid + ".png"), Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/moa/" + baseTextureNameNoModid + "_baby.png"),
                    keratinColorOverride, eyeColorOverride, featherColorOverride, featherShapeOverride);
        }

        private SpecialVariant(String name, int id, Identifier baseTextureName,
                               @Nullable KeratinColor keratinColorOverride, @Nullable EyeColor eyeColorOverride, @Nullable FeatherColor featherColorOverride, @Nullable FeatherShape featherShapeOverride) {
            this(name, id,
                    baseTextureName.withPath(path -> "textures/entity/mobs/moa/" + path + ".png"), baseTextureName.withPath(path -> "textures/entity/mobs/moa/" + path + "_baby.png"),
                    keratinColorOverride, eyeColorOverride, featherColorOverride, featherShapeOverride);
        }

        private SpecialVariant(String name, int id,
                               @Nullable Identifier defaultTexture, @Nullable Identifier babyTexture,
                               @Nullable KeratinColor keratinColorOverride, @Nullable EyeColor eyeColorOverride, @Nullable FeatherColor featherColorOverride, @Nullable FeatherShape featherShapeOverride) {
            this(name, id,
                    defaultTexture, babyTexture,
                    keratinColorOverride, eyeColorOverride, featherColorOverride, featherShapeOverride,
                    keratinColorOverride != null && eyeColorOverride != null && featherColorOverride != null && featherShapeOverride != null ? new MoaEggType(keratinColorOverride, eyeColorOverride, featherColorOverride, featherShapeOverride) : null);
        }

        public boolean dependsOnCustomName() {
            return false;
        }

        @Nullable
        public Identifier getDefaultTexture() {
            return this.defaultTexture;
        }

        @Nullable
        public Identifier getBabyTexture() {
            return this.babyTexture;
        }

        @Nullable
        public KeratinColor getKeratinColorOverride() {
            return keratinColorOverride;
        }

        @Nullable
        public EyeColor getEyeColorOverride() {
            return eyeColorOverride;
        }

        @Nullable
        public FeatherColor getFeatherColorOverride() {
            return featherColorOverride;
        }

        @Nullable
        public FeatherShape getFeatherShapeOverride() {
            return featherShapeOverride;
        }

        @Nullable
        public MoaEggType getEggTypeOverride() {
            return eggTypeOverride;
        }

        public boolean canApplyTo(Moa moa) {
            return true;
        }

        @Override
        public boolean test(Moa moa) {
            return false;
        }

        public void addDataToFeatherItem(ItemStack feather) {
        }

        public KeratinColor getKeratinColor(Moa moa) {
            return keratinColorOverride != null ? keratinColorOverride : moa.getKeratinColor();
        }

        public EyeColor getEyeColor(Moa moa) {
            return eyeColorOverride != null ? eyeColorOverride : moa.getEyeColor();
        }

        public FeatherColor getFeatherColor(Moa moa) {
            return featherColorOverride != null ? featherColorOverride : moa.getFeatherColor();
        }

        public FeatherShape getFeatherShape(Moa moa) {
            return featherShapeOverride != null ? featherShapeOverride : moa.getFeatherShape();
        }

        public MoaEggType getEggType(Moa moa) {
            return eggTypeOverride != null ? eggTypeOverride : moa.getDefaultEggType();
        }

        public static Stream<SpecialVariant> stream() {
            return Stream.of(VALUES);
        }

        public String getName() {
            return this.name;
        }

        /**
         * @return the Variant specified by the given name or null if no such Variant exists
         */
        @Nullable
        public static SpecialVariant byName(@Nullable String name) {
            return CODEC.byName(name);
        }

        @Nullable
        @Contract("_,!null->!null;_,null->_")
        public static SpecialVariant byName(@Nullable String name, @Nullable SpecialVariant fallback) {
            SpecialVariant variant = CODEC.byName(name);
            return variant != null ? variant : fallback;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public int id() {
            return this.id;
        }
    }
}

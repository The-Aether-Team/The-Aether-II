package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.entity.AetherIIDataSerializers;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.EntityReference;
import com.aetherteam.aetherii.entity.EntityUtil;
import com.aetherteam.aetherii.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.EntityAccessor;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.AerbunnyMessagePacket;
import com.aetherteam.aetherii.network.packet.serverbound.AerbunnyPuffPacket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import com.aetherteam.aetherii.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

public class Aerbunny extends AetherTamableAnimal {
    public static int PUFF_PARTICLE_EVENT = 100;

    private static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_PUFF_COOLDOWN_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_AFRAID_TIME_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FAST_FALLING_ID = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_COLLAR_COLOR = SynchedEntityData.defineId(Aerbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_VEHICLE_REFERENCE = SynchedEntityData.defineId(Aerbunny.class, AetherIIDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

    private static final int MAXIMUM_PUFFS = 11;

    private int puffSubtract;
    @Nullable
    private Vec3 lastPos;

    public Aerbunny(EntityType<? extends Aerbunny> type, Level level) {
        super(type, level);
        this.moveControl = new AerbunnyMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new RunWhenAfraid(this, 1.3));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(AetherIITags.Items.AERBUNNY_FOOD), false));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.1F, 10.0F, 2.0F, false));
        //this.goalSelector.addGoal(5, new TamedFollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new FallingRandomStrollGoal(this, 1.0, 80));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PUFFINESS_ID, 0);
        this.entityData.define(DATA_PUFF_COOLDOWN_ID, 0);
        this.entityData.define(DATA_AFRAID_TIME_ID, 0);
        this.entityData.define(DATA_FAST_FALLING_ID, false);
        this.entityData.define(DATA_COLLAR_COLOR, DyeColor.BLUE.getId());
        this.entityData.define(DATA_VEHICLE_REFERENCE, Optional.empty());
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == PUFF_PARTICLE_EVENT) {
            this.spawnPuffParticles();
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * Handles slow-falling, puffiness tracking, and other mount behavior.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.isFastFalling()) { // Handle slow-falling unless the Aerbunny is set to fall fast.
            this.handleFallSpeed();
        } else if (this.onGround()) {
            this.setFastFalling(false);
        }
        this.setPuffiness(this.getPuffiness() - this.puffSubtract);
        if (this.getPuffiness() > 0) {
            this.puffSubtract = 1;
        } else {
            this.puffSubtract = 0;
            this.setPuffiness(0);
        }
        this.handlePlayerInput();

        boolean blockIntersection = false;
        if (this.getVehicle() != null) {
            AABB vehicleBounds = this.getVehicle().getBoundingBox();
            BlockPos minPos = BlockPos.containing(vehicleBounds.minX, vehicleBounds.minY, vehicleBounds.minZ);
            BlockPos maxPos = BlockPos.containing(vehicleBounds.maxX, vehicleBounds.maxY, vehicleBounds.maxZ);
            for (int x = minPos.getX(); x <= maxPos.getX(); x++) {
                for (int y = minPos.getY(); y <= maxPos.getY(); y++) {
                    for (int z = minPos.getZ(); z <= maxPos.getZ(); z++) {
                        BlockPos pos = BlockPos.containing(x, y, z);
                        BlockState blockState = this.level().getBlockState(pos);
                        VoxelShape shape = blockState.getShape(this.getVehicle().level(), this.getVehicle().blockPosition());
                        for (AABB aabb : shape.toAabbs()) {
                            AABB offset = aabb.move(pos);
                            if (vehicleBounds.intersects(offset)) {
                                blockIntersection = true;
                            }
                        }
                    }
                }
            }
        }

        if (this.getVehicle() != null && (this.getVehicle().onGround() || this.getVehicle().isInWaterOrBubble() || this.getVehicle().isInLava() || blockIntersection)) { // Reset the last tracked fall position if the Aerbunny touches a surface.
            this.lastPos = null;
        }

        if (this.getVehicle() == null) {
            this.setPuffCooldown(0);
        } else if (this.getPuffCooldown() > 0) {
            this.setPuffCooldown(this.getPuffCooldown() - 1);
        }
    }

    /**
     * Handles the length of time that the Aerbunny is afraid.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getAfraidTime() > 0) {
            this.setAfraidTime(this.getAfraidTime() - 1);
        }
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
            }
        }
    }

    /**
     * Makes the vehicle player fall slowly, and handles the jump ability for the player.
     */
    private void handlePlayerInput() {
        if (this.getVehicle() instanceof Player player) {
            if (player.isSpectator()) {
                this.stopRiding();
            }

            EntityUtil.copyRotations(this, player);

            if (!player.onGround() && !player.isFallFlying()) {
                AttributeInstance playerGravity = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
                if (playerGravity != null) {
                    if (!player.getAbilities().flying && playerGravity.getValue() > 0.02 && !player.isInWater() /*&& !player.isInFluidType()*/) {  // Entity isn't allowed to fall too slowly from gravity.
                        if (!player.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS)) {
                            player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.05, 0.0));
                        }
                    }
                }

                if (this.level().isClientSide()) {
                    var data = AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER);
                    if (player.getDeltaMovement().y() <= 0.0) {
                        if (this.lastPos == null) { // Tracks the last position when the player starts falling.
                            this.lastPos = this.position();
                        }
                        // The player is only able to jump if the Aerbunny's position is below the last tracked falling position, to avoid infinite jump exploits.
                        if (!player.onGround() && data.isJumping() && player.getDeltaMovement().y() <= 0.0 && this.position().y() < this.lastPos.y() - 1.1) {
                            if (this.getPuffCooldown() <= 0) { // Also check cooldown timer.
                                player.setDeltaMovement(player.getDeltaMovement().x(), 0.125, player.getDeltaMovement().z());
                                ClientPacketDistributor.sendToServer(new AerbunnyPuffPacket(this.getId())); // Calls Aerbunny#puff() on the server.
                                this.lastPos = null;
                                this.setPuffCooldown(20);
                            }
                        }
                    }
                }
            } else if (player.isFallFlying()) { // Dismount when wearing Elytra.
                this.stopRiding();
            }
            if (player instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                serverGamePacketListenerImplAccessor.aether_ii$setAboveGroundTickCount(0);
            }
        }
    }

    /**
     * Dismounts the Aerbunny when in water.
     */
    @Override
    public void baseTick() {
        if (this.getVehicle() instanceof Player vehicle) {
            if (!vehicle.isCrouching() && this.getBoundingBox().getYsize() != 0.03F) {
                this.refreshDimensions();
            } else if (vehicle.isCrouching() && this.getBoundingBox().getYsize() == 0.03F) {
                this.refreshDimensions();
            }
        }
        super.baseTick();
        if (this.isAlive() && this.isPassenger() && this.getVehicle() != null) {
            if (this.isInWater() && this.getFluidHeight(FluidTags.WATER) > this.getFluidJumpThreshold()
                    || this.isInLava()
                /*|| this.isInFluidType((fluidType, height) -> this.canSwimInFluidType(fluidType) && height > this.getFluidJumpThreshold())*/) {
                float f = this.getJumpPower();
                if (!this.getVehicle().isShiftKeyDown()) {
                    if (!(f <= 1.0E-5F)) {
                        Vec3 vec3 = this.getVehicle().getDeltaMovement();
                        this.getVehicle().setDeltaMovement(vec3.x, Math.max(0.025, vec3.y), vec3.z);
                        if (this.getVehicle() instanceof Player player && AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER).isJumping()) {
                            player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.015, 0.0));
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles right-clicking the Aerbunny for mounting and dismounting.
     *
     * @param player The interacting {@link Player}.
     * @param hand   The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        InteractionResult result = super.mobInteract(player, hand);

        if (!result.consumesAction()) {
            result = itemStack.interactLivingEntity(player, this, hand);
            if (result.consumesAction()) {
                return result;
            }
            if (this.isTame()) {
                if (this.isOwnedBy(player)) {
                    if (item instanceof DyeItem dyeItem) {
                        DyeColor dyeColor = dyeItem.getDyeColor();
                        if (dyeColor != this.getCollarColor()) {
                            if (!this.level().isClientSide()) {
                                this.setCollarColor(dyeColor);
                                if (!player.getAbilities().instabuild) {
                                    itemStack.shrink(1);
                                }
                                this.setPersistenceRequired();
                            }
                            return InteractionResult.SUCCESS;
                        }
                    } else if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                        this.usePlayerItem(player, hand, itemStack);
                        this.heal(2.0F);
                        this.gameEvent(GameEvent.EAT, this);
                        return InteractionResult.SUCCESS;
                    }

                    if (!result.consumesAction() && player.isShiftKeyDown()) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        result = InteractionResult.SUCCESS;
                    }
                }
            } else if (itemStack.is(AetherIITags.Items.AERBUNNY_TAME_ITEMS) && this.getAfraidTime() <= 0) {
                if (!this.level().isClientSide()) {
                    this.usePlayerItem(player, hand, itemStack);
                    if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                        this.tame(player);
                        this.setOrderedToSit(true);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                    this.setPersistenceRequired();
                }

                return InteractionResult.SUCCESS;
            }

            if (this.isTame() && !this.isFood(itemStack)) {
                if (!(this.getVehicle() instanceof Player vehicle) || vehicle.equals(player)) { // Interacting player has to be the one wearing the Aerbunny.
                    // Aerbunny can be mounted/dismounted if the shift key is held and no other interaction actions succeed, but only if the Aerbunny is not inside a block.
                    if ((this.getVehicle() != null || result == InteractionResult.PASS || result == InteractionResult.FAIL) && !super.isInWall()) {
                        result = this.ridePlayer(player);
                    }
                }
            }
        }


        return result;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getType().getDimensions().makeBoundingBox(this.position()).getSize();
        if (Double.isNaN(d0)) {
            d0 = 1.0F;
        }
        d0 *= 64.0 * getViewScale();
        return distance < d0 * d0;
    }

    /**
     * Method used for both mounting and dismounting the Aerbunny to a vehicle player.
     *
     * @param player The {@link Player}.
     * @return The {@link InteractionResult}.
     */
    private InteractionResult ridePlayer(Player player) {
        if (!this.isBaby() && (!this.isTame() || this.isTame() && this.isOwnedBy(player))) {
            if (this.isPassenger()) {
                if (this.isTame()) {
                    this.setOrderedToSit(true);
                }
                // Dismount segment.
                this.stopRiding();
                this.setFastFalling(true); // Aerbunny will fall fast when dismounted.
                Vec3 playerMovement = player.getDeltaMovement();
                this.setDeltaMovement(playerMovement.x() * 5, playerMovement.y() * 0.5 + 0.5, playerMovement.z() * 5);
                this.level().playSound(player, this, AetherIISoundEvents.ENTITY_AERBUNNY_LAND.get(), SoundSource.NEUTRAL, 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            } else if (this.startRiding(player)) { // Mount segment.
                if (this.isTame()) {
                    this.setOrderedToSit(false);
                }
                if (!player.level().isClientSide()) {
                    AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).setMountedAerbunny(this);
                }
                this.level().playSound(player, this, AetherIISoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundSource.NEUTRAL, 1.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean startRiding(Entity vehicle, boolean force) {
        if (vehicle == this.getVehicle()) {
            return false;
        } else if (!((EntityAccessor) vehicle).callCouldAcceptPassenger()) {
            return false;
        } else {
            for (Entity entity = vehicle; entity.getVehicle() != null; entity = entity.getVehicle()) {
                if (entity.getVehicle() == this) {
                    return false;
                }
            }

            if (!ForgeEventFactory.canMountEntity(this, vehicle, true)) {
                return false;
            } else if (force || this.canRide(vehicle) && ((EntityAccessor) vehicle).callCanAddPassenger(this)) {
                if (this.isPassenger()) {
                    this.stopRiding();
                }

                this.setPose(Pose.STANDING);
                this.vehicle = vehicle;
                ((EntityAccessor) this.getVehicle()).callAddPassenger(this);
                this.level().gameEvent(this, GameEvent.ENTITY_MOUNT, this.getVehicle().position());
                if (this.getVehicle() instanceof Player player) {
                    this.setVehicleReference(Optional.of(EntityReference.of(player.getUUID())));
                    if (player instanceof ServerPlayer serverPlayer && !this.firstTick) {
                        PacketDistributor.sendToPlayer(serverPlayer, new AerbunnyMessagePacket());
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Stop tracking mounted Aerbunny with {@link com.aetherteam.aetherii.attachment.player.AerbunnyMountAttachment}.
     */
    @Override
    public void stopRiding() {
        if (this.getVehicle() instanceof Player player) {
            AetherIIDataAttachments.get(player, AetherIIDataAttachments.AERBUNNY_MOUNT).setMountedAerbunny(null);
        }
        if (this.getVehicleReference().isPresent()) {
            this.setVehicleReference(Optional.empty());
        }
        super.stopRiding();

        this.refreshDimensions();
    }

    /**
     * Sets the Aerbunny as afraid when hit by a player.
     *
     * @param source The {@link DamageSource}.
     * @param amount The damage amount, as a {@link Float}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && source.getEntity() instanceof Player) {
            this.setAfraidTime(100 + this.getRandom().nextInt(50));
        }
        return flag;
    }

    /**
     * Handles the small hops in the air.
     */
    protected void midairJump() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.y() < 0) {
            this.puff();
            this.level().broadcastEntityEvent(this, (byte) PUFF_PARTICLE_EVENT);
        }
        this.setDeltaMovement(new Vec3(motion.x(), 0.25, motion.z()));
    }

    /**
     * Sets the puffiness to the maximum amount, from {@link AerbunnyPuffPacket}.
     */
    public void puff() {
        if (this.level() instanceof ServerLevel) {
            this.setPuffiness(MAXIMUM_PUFFS);
        }
        this.level().playSound(null, this, AetherIISoundEvents.ENTITY_AERBUNNY_HOP.get(), SoundSource.NEUTRAL, 2.0F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
    }

    private void spawnPuffParticles() {
        for (int i = 0; i < 5; i++) {
            EntityUtil.spawnMovementExplosionParticles(this);
        }
    }

    /**
     * @return The {@link Integer} value for the puffiness, used for animation.
     */
    public int getPuffiness() {
        return this.entityData.get(DATA_PUFFINESS_ID);
    }

    /**
     * Sets the puffiness value, used for animation.
     *
     * @param puffiness The {@link Integer} value.
     */
    public void setPuffiness(int puffiness) {
        this.entityData.set(DATA_PUFFINESS_ID, puffiness);
    }

    public int getPuffCooldown() {
        return this.entityData.get(DATA_PUFF_COOLDOWN_ID);
    }

    public void setPuffCooldown(int cooldown) {
        this.entityData.set(DATA_PUFF_COOLDOWN_ID, cooldown);
    }

    /**
     * @return The {@link Integer} value for how long the Aerbunny should be afraid for.
     */
    public int getAfraidTime() {
        return this.entityData.get(DATA_AFRAID_TIME_ID);
    }

    /**
     * Sets how long the Aerbunny should be afraid for.
     *
     * @param afraidTime The {@link Integer} value.
     */
    public void setAfraidTime(int afraidTime) {
        this.entityData.set(DATA_AFRAID_TIME_ID, afraidTime);
    }

    /**
     * @return The {@link Boolean} value for whether the Aerbunny is falling fast.
     */
    public boolean isFastFalling() {
        return this.entityData.get(DATA_FAST_FALLING_ID);
    }

    /**
     * Sets whether the Aerbunny is falling fast.
     *
     * @param fastFalling The {@link Boolean} value.
     */
    public void setFastFalling(boolean fastFalling) {
        this.entityData.set(DATA_FAST_FALLING_ID, fastFalling);
    }

    /**
     * @return The {@link Integer} amount to subtract from puffiness in animation.
     */
    public int getPuffSubtract() {
        return this.puffSubtract;
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor pCollarColor) {
        this.entityData.set(DATA_COLLAR_COLOR, pCollarColor.getId());
    }

    public Optional<EntityReference<LivingEntity>> getVehicleReference() {
        return this.entityData.get(DATA_VEHICLE_REFERENCE);
    }

    public void setVehicleReference(Optional<EntityReference<LivingEntity>> reference) {
        this.entityData.set(DATA_VEHICLE_REFERENCE, reference);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_AERBUNNY_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_AERBUNNY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_AERBUNNY_DEATH.get();
    }

    /**
     * @return A {@link Float} for the midair speed of this entity.
     */
    @Override
    protected float getFlyingSpeed() {
        return this.getSpeed() * 0.216F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.AERBUNNY_FOOD);
    }

    public Vec3 getVehicleAttachmentPoint(Entity entity) {
        Vec3 vehicleAttachmentPoint = new Vec3(0.0, this.getBbHeight(), 0.0);
        if (entity instanceof Player player && player.hasPose(Pose.SLEEPING) && player.getBedOrientation() != null) {
            vehicleAttachmentPoint = vehicleAttachmentPoint.add(0, 0.1, 0.5).yRot(-player.getBedOrientation().toYRot() * Mth.DEG_TO_RAD);
        }
        return vehicleAttachmentPoint;
    }

    @Override
    public boolean canRiderInteract() {
        if (this.getVehicle() instanceof Player player && player.level().isClientSide()) {
            return AetherIIClientProxy.isAerbunnyInteractable();
        }
        return true;
    }

    /**
     * @return Whether the Aerbunny can be interacted with. The Aerbunny can only be interacted when it is within a certain range of the player's view vector,
     * to avoid bugs with displaying the player's crosshairs.
     */
    @Override
    public boolean isPickable() {
        if (this.getVehicle() instanceof Player player && player.level().isClientSide()) {
            if (!AetherIIClientProxy.isAerbunnyInteractable()) {
                return false;
            } else {
                return player.getBoundingBox().expandTowards(player.getViewVector(0.0F)).contains(this.getBoundingBox().getCenter().add(0, this.getBoundingBox().getSize() / 2, 0));
            }
        }
        return true;
    }

    /**
     * Prevents the Aerbunny from being hurt by the vehicle entity.
     *
     * @param damageSource The {@link DamageSource}.
     * @return Whether the Aerbunny is invulnerable to the damage, as a {@link Boolean}.
     */
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return (this.getVehicle() != null && damageSource.getEntity() != null && damageSource.getEntity().equals(this.getOwner())) || super.isInvulnerableTo(damageSource);
    }

    /**
     * @return A {@link Boolean} for whether the Aerbunny is checked as being in a wall, which is false when this Aerbunny is mounted to another entity.
     */
    @Override
    public boolean isInWall() {
        return !this.isPassenger() && super.isInWall();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        Aerbunny aerbunny = AetherIIEntityTypes.AERBUNNY.get().create(level);
        if (aerbunny != null) {
            if (this.getOwnerUUID() != null) {
                aerbunny.setOwnerUUID(this.getOwnerUUID());
                aerbunny.setTame(true);
                aerbunny.setOrderedToSit(true);
            }
        }

        return aerbunny;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag output) {
        super.addAdditionalSaveData(output);
        output.putInt("AfraidTime", this.getAfraidTime());
        output.putInt("CollarColor", this.getCollarColor().getId());
        if (this.getVehicleReference().isPresent()) {
            output.putUUID("VehicleUUID", this.getVehicleReference().get().uuid());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag input) {
        super.readAdditionalSaveData(input);
        this.setAfraidTime(input.getInt("AfraidTime"));
        if (input.contains("CollarColor")) {
            this.setCollarColor(DyeColor.byId(input.getInt("CollarColor")));
        }
        this.setVehicleReference(input.hasUUID("VehicleUUID") ? Optional.of(EntityReference.of(input.getUUID("VehicleUUID"))) : Optional.empty());
    }

    @Override
    public int getMaxFallDistance() {
        return 3;
    }

    /**
     * Sets a position for the Aerbunny to run away to.
     */
    public static class RunWhenAfraid extends Goal {
        private final Aerbunny aerbunny;
        private final double speedModifier;

        public RunWhenAfraid(Aerbunny aerbunny, double speedModifier) {
            this.aerbunny = aerbunny;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.getAfraidTime() > 0;
        }

        @Override
        public boolean canContinueToUse() {
            return !this.aerbunny.getNavigation().isDone() && this.aerbunny.getRandom().nextInt(20) != 0;
        }

        @Override
        public void start() {
            LivingEntity attacker = this.aerbunny.level().getNearestPlayer(this.aerbunny, 12);
            if (attacker == null) {
                return;
            }
            Vec3 position = this.aerbunny.position();
            double angle = Mth.atan2(position.x() - attacker.getX(), position.z() - attacker.getZ());
            float angleOffset = this.aerbunny.getRandom().nextFloat() * 2 - 1;
            angle += angleOffset * 0.75;
            double x = position.x() + Math.sin(angle) * 8;
            double z = position.z() + Math.cos(angle) * 8;
            boolean flag = this.aerbunny.getNavigation().moveTo(x, this.aerbunny.getY(), z, this.speedModifier);
            if (!flag) {
                this.aerbunny.getLookControl().setLookAt(attacker, 30, 30);
            }
        }

        /**
         * Spawns crying particles.
         */
        @Override
        public void tick() {
            if (this.aerbunny.level() instanceof ServerLevel serverLevel) {
                if (this.aerbunny.getRandom().nextInt(4) == 0) {
                    serverLevel.sendParticles(ParticleTypes.SPLASH, this.aerbunny.getRandomX(0.5), this.aerbunny.getRandomY(), this.aerbunny.getRandomZ(0.5), 2, 0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * Handles jumping movement for the Aerbunny.
     */
    public static class AerbunnyMoveControl extends MoveControl {
        private final Aerbunny aerbunny;

        public AerbunnyMoveControl(Aerbunny aerbunny) {
            super(aerbunny);
            this.aerbunny = aerbunny;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.JUMPING) {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.mob.onGround()) {
                    this.operation = Operation.WAIT;
                } else {
                    this.operation = Operation.MOVE_TO;
                }
            } else {
                super.tick();
            }
            if (this.aerbunny.zza != 0 && !this.aerbunny.isInSittingPose()) {
                if (this.aerbunny.onGround()) {
                    this.aerbunny.getJumpControl().jump();
                } else {
                    int x = Mth.floor(this.aerbunny.getX());
                    int y = Mth.floor(this.aerbunny.getBoundingBox().minY);
                    int z = Mth.floor(this.aerbunny.getZ());
                    if (this.checkForSurfaces(this.aerbunny.level(), x, y, z) && !this.aerbunny.horizontalCollision) {
                        this.aerbunny.midairJump();
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

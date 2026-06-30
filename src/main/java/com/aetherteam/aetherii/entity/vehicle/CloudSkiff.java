package com.aetherteam.aetherii.entity.vehicle;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.natural.AercloudBlock;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractBoatAccessor;
import com.aetherteam.aetherii.network.packet.serverbound.SkiffParticlesPacket;
import com.aetherteam.aetherii.network.packet.serverbound.SkiffSteeringPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class CloudSkiff extends Boat implements RiderSitContext {
    public static int UNFOLD_EVENT = 99;
    public static int FOLD_EVENT = 100;
    public static int PARTICLE_EVENT = 101;

    protected static final EntityDataAccessor<Boolean> DATA_ANIMATE_UNFOLD = SynchedEntityData.defineId(CloudSkiff.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_FOLD_START_TICK = SynchedEntityData.defineId(CloudSkiff.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<SteeringState> DATA_STEERING_STATE = SynchedEntityData.defineId(CloudSkiff.class, AetherIIDataSerializers.CLOUD_SKIFF_STEERING_STATE.get());
    public AnimationState unfoldAnimationState = new AnimationState();
    public AnimationState foldAnimationState = new AnimationState();
    public float steering = 0.0F;
    public float steeringO = 0.0F;
    public float wingLift = 0.0F;
    public float wingLiftO = 0.0F;

    public CloudSkiff(EntityType<CloudSkiff> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.5625F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ANIMATE_UNFOLD, false);
        this.entityData.define(DATA_FOLD_START_TICK, 0);
        this.entityData.define(DATA_STEERING_STATE, SteeringState.NONE);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == UNFOLD_EVENT) {
            this.unfoldAnimationState.start(this.tickCount);
        } else if (id == FOLD_EVENT) {
            this.foldAnimationState.start(this.tickCount);
        } else if (id == PARTICLE_EVENT) {
            this.spawnRudderParticles();
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.animateUnfold()) {
                serverLevel.broadcastEntityEvent(this, (byte) UNFOLD_EVENT);
                this.setAnimateUnfold(false);
            }
            if (this.getFoldStartTick() > 0 && this.tickCount > this.getFoldStartTick() + 10) {
                if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    this.spawnAtLocation(this.getDropItem());
                }
                this.discard();
            }
        }
        AbstractBoatAccessor accessor = (AbstractBoatAccessor) this;
        if (this.getControllingPassenger() instanceof Player || !this.level().isClientSide()) {
            if (this.level().getBlockState(this.blockPosition()).is(AetherIITags.Blocks.AERCLOUDS)) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), 0.2F, this.getDeltaMovement().z()));
            } else if (this.getBlockStateOn().is(AetherIITags.Blocks.AERCLOUDS) && this.getBlockStateOn().getBlock() instanceof AercloudBlock aercloudBlock) {
                aercloudBlock.runAercloudEffect(this.getBlockStateOn(), this.level(), this.getOnPos(), this);
            }
        }
        super.tick();

        if (this.level().isClientSide()) {
            SteeringState clientState = SteeringState.NONE;
            if (accessor.aether$getInputRight()) {
                clientState = SteeringState.RIGHT;
            } else if (accessor.aether$getInputLeft()) {
                clientState = SteeringState.LEFT;
            }
            ClientPacketDistributor.sendToServer(new SkiffSteeringPacket(this.getId(), clientState));

            this.steeringO = this.steering;
            switch (this.getSteeringState()) {
                case RIGHT -> {
                    this.steering = Mth.clamp(this.steering - 3, -45.0F, 45.0F);
                }
                case LEFT -> {
                    this.steering = Mth.clamp(this.steering + 3, -45.0F, 45.0F);
                }
                case NONE -> {
                    if (this.steering > 0) {
                        this.steering--;
                    } else if (this.steering < 0) {
                        this.steering++;
                    }
                }
            }
        }

        this.wingLiftO = this.wingLift;
        if (accessor.callGetStatus() == Status.IN_AIR || accessor.callGetStatus() == Status.UNDER_WATER) {
            this.wingLift = Mth.lerp(0.1F, this.wingLift, -0.2618F * Mth.RAD_TO_DEG);
        } else {
            this.wingLift = Mth.lerp(0.25F, this.wingLift, 0.0F);
        }

        if (this.level().isClientSide()) {
            if (accessor.aether$getInputUp() || accessor.aether$getInputRight() || accessor.aether$getInputLeft()) {
                ClientPacketDistributor.sendToServer(new SkiffParticlesPacket(this.getId()));
            }
        }
    }

    private void spawnRudderParticles() {
        Vec3 particleOffset = new Vec3(0.0, 0.0, -1.1).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        Vec3 vec3 = this.getDeltaMovement();

        if (this.isInWater()) {
            ParticleOptions splashParticle = ParticleTypes.SPLASH;
            if (this.level() instanceof ClientLevel clientLevel && clientLevel.getBiome(this.blockPosition()).is(AetherIITags.Biomes.THE_AETHER)) {
                splashParticle = AetherIIParticleTypes.SPLASH.get();
            }
            for (int i = 0; i < 20; i++) {
                this.level().addParticle(splashParticle, this.position().x() + particleOffset.x(), this.position().y(), this.position().z() + particleOffset.z(), vec3.x * -4.0, 1.5, vec3.z * -4.0);
            }
        } else {
            BlockPos pos = this.getOnPosLegacy();
            BlockState state = this.level().getBlockState(pos);
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.position().x() + particleOffset.x(), this.position().y(), this.position().z() + particleOffset.z(), vec3.x * -4.0, 1.5, vec3.z * -4.0);
        }
    }

    @Override
    public Item getDropItem() {
        return AetherIIItems.CLOUD_SKIFF.get();
    }

    @Override
    protected void destroy(DamageSource damageSource) {
        this.setFoldStartTick(this.tickCount);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.broadcastEntityEvent(this, (byte) FOLD_EVENT);
        }
    }

    @Override
    public float getGroundFriction() {
        AABB bounds = this.getBoundingBox();
        AABB expandedBounds = new AABB(bounds.minX, bounds.minY - 0.001, bounds.minZ, bounds.maxX, bounds.minY, bounds.maxZ);
        int minX = Mth.floor(expandedBounds.minX) - 1;
        int maxX = Mth.ceil(expandedBounds.maxX) + 1;
        int minY = Mth.floor(expandedBounds.minY) - 1;
        int maxY = Mth.ceil(expandedBounds.maxY) + 1;
        int minZ = Mth.floor(expandedBounds.minZ) - 1;
        int maxZ = Mth.ceil(expandedBounds.maxZ) + 1;
        VoxelShape expandedShape = Shapes.create(expandedBounds);
        float friction = 0.0F;
        int amount = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                int horizontal = (x != minX && x != maxX - 1 ? 0 : 1) + (z != minZ && z != maxZ - 1 ? 0 : 1);
                if (horizontal != 2) {
                    for (int y = minY; y < maxY; ++y) {
                        if (horizontal <= 0 || y != minY && y != maxY - 1) {
                            mutablePos.set(x, y, z);
                            BlockState blockState = this.level().getBlockState(mutablePos);
                            if (!(blockState.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level(), mutablePos, CollisionContext.of(this)).move(mutablePos.getX(), mutablePos.getY(), mutablePos.getZ()), expandedShape, BooleanOp.AND)) {
                                friction += blockState.getFriction(this.level(), mutablePos, this);
                                ++amount;
                            }
                        }
                    }
                }
            }
        }
        return friction / (float) amount;
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction callback) {
        float z = this.getSinglePassengerXOffset();
        int i = this.getPassengers().indexOf(passenger);
        if (i >= 0) {
            Vec3 offset = new Vec3(0.0F, 0.0F, i == 0 ? z - 0.125F : z + 0.55F).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
            double yOffset = this.getPassengersRidingOffset();
            if (i != 0) {
                yOffset += passenger.getMyRidingOffset();
            }
            callback.accept(passenger, this.getX() + offset.x(), this.getY() + yOffset, this.getZ() + offset.z());
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight();
    }

    @Override
    public boolean shouldRiderSit(Entity vehicle, LivingEntity passenger) {
        int i = this.getPassengers().indexOf(passenger);
        return i != 0;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        return this.position().add(0, 0.25, 0);
    }

    @Nullable
    @Override
    protected SoundEvent getPaddleSound() {
        AbstractBoatAccessor accessor = (AbstractBoatAccessor) this;
        if (accessor.callGetStatus() == Status.ON_LAND && !this.getBlockStateOn().is(AetherIITags.Blocks.AERCLOUDS)) {
            return super.getPaddleSound();
        }
        return null;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0F, 0.88F * this.getBbHeight(), 0.57F * this.getBbWidth());
    }

    @Override
    public boolean isPickable() {
        return this.getFoldStartTick() == 0;
    }

    public boolean animateUnfold() {
        return this.getEntityData().get(DATA_ANIMATE_UNFOLD);
    }

    public void setAnimateUnfold(boolean animate) {
        this.getEntityData().set(DATA_ANIMATE_UNFOLD, animate);
    }

    public int getFoldStartTick() {
        return this.getEntityData().get(DATA_FOLD_START_TICK);
    }

    public void setFoldStartTick(int tick) {
        this.getEntityData().set(DATA_FOLD_START_TICK, tick);
    }

    public SteeringState getSteeringState() {
        return this.getEntityData().get(DATA_STEERING_STATE);
    }

    public void setSteeringState(SteeringState state) {
        this.getEntityData().set(DATA_STEERING_STATE, state);
    }

    public enum SteeringState implements StringRepresentable {
        RIGHT,
        LEFT,
        NONE;

        public static final IntFunction<SteeringState> BY_ORDINAL = ByIdMap.continuous(SteeringState::ordinal, SteeringState.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StringRepresentable.EnumCodec<SteeringState> CODEC = StringRepresentable.fromEnum(SteeringState::values);
        public static final StreamCodec<ByteBuf, SteeringState> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ORDINAL, SteeringState::ordinal);

        @Override
        public String getSerializedName() {
            return this.name();
        }
    }
}

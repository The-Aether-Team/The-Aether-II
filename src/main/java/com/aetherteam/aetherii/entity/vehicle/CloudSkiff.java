package com.aetherteam.aetherii.entity.vehicle;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.natural.AercloudBlock;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HolyIslesSpecialEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractBoatAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CloudSkiff extends AbstractBoat implements RiderSitContext {
    public float steering = 0.0F;
    public float steeringO = 0.0F;
    public float wingLift = 0.2618F * Mth.RAD_TO_DEG;
    public float wingLiftO = 0.2618F * Mth.RAD_TO_DEG;

    public CloudSkiff(EntityType<CloudSkiff> entityType, Level level) {
        super(entityType, level, AetherIIItems.CLOUD_SKIFF);
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        AbstractBoatAccessor accessor = (AbstractBoatAccessor) this;
        if (this.getControllingPassenger() instanceof Player || !this.level().isClientSide()) {
            if (this.getInBlockState().is(AetherIITags.Blocks.AERCLOUDS)) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), 0.2F, this.getDeltaMovement().z()));
            } else if (this.getBlockStateOn().is(AetherIITags.Blocks.AERCLOUDS) && this.getBlockStateOn().getBlock() instanceof AercloudBlock aercloudBlock) {
                aercloudBlock.runAercloudEffect(this.getBlockStateOn(), this.level(), this.getOnPos(), this);
            }
        }
        super.tick();

        this.steeringO = this.steering;
        if (accessor.aether$getInputRight()) {
            this.steering = Math.clamp(this.steering - 3, -45.0F, 45.0F);
        } else if (accessor.aether$getInputLeft()) {
            this.steering = Math.clamp(this.steering + 3, -45.0F, 45.0F);
        } else {
            if (this.steering > 0) {
                this.steering--;
            } else if (this.steering < 0) {
                this.steering++;
            }
        }

        this.wingLiftO = this.wingLift;
        if (accessor.callGetStatus() == Status.IN_AIR || accessor.callGetStatus() == Status.UNDER_WATER) {
            this.wingLift = Mth.lerp(0.1F, this.wingLift, 0.0F);
        } else {
            this.wingLift = Mth.lerp(0.25F, this.wingLift, 0.2618F * Mth.RAD_TO_DEG);
        }

        if (accessor.aether$getInputUp() || accessor.aether$getInputRight() || accessor.aether$getInputLeft()) {
            this.spawnRudderParticles();
        }
    }

    private void spawnRudderParticles() {
        Vec3 particleOffset = new Vec3(0.0, 0.0, -1.1).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        Vec3 vec3 = this.getDeltaMovement();

        if (this.isInWater()) {
            ParticleOptions splashParticle = ParticleTypes.SPLASH;
            if (this.level() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HolyIslesSpecialEffects) {
                splashParticle = AetherIIParticleTypes.SPLASH.get();
            }
            for (int i = 0; i < 20; i++) {
                this.level().addParticle(splashParticle, this.position().x() + particleOffset.x(), this.position().y(), this.position().z() + particleOffset.z(), vec3.x * -4.0, 1.5, vec3.z * -4.0);
            }
        } else {
            BlockPos pos = this.getOnPosLegacy();
            BlockState state = this.level().getBlockState(pos);
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state, pos), this.position().x() + particleOffset.x(), this.position().y(), this.position().z() + particleOffset.z(), vec3.x * -4.0, 1.5, vec3.z * -4.0);
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
                            if (!(blockState.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockState.getCollisionShape(this.level(), mutablePos, CollisionContext.of(this)).move(mutablePos), expandedShape, BooleanOp.AND)) {
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
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        float z = this.getSinglePassengerXOffset();
        int i = this.getPassengers().indexOf(passenger);
        if (i == 0) {
            return new Vec3(0.0F, this.rideHeight(dimensions) + passenger.getVehicleAttachmentPoint(this).y(), z - 0.125F).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        } else {
            return new Vec3(0.0F, this.rideHeight(dimensions), z + 0.55F).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    protected double rideHeight(EntityDimensions entityDimensions) {
        return entityDimensions.height();
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
    public Vec3[] getQuadLeashOffsets() {
        return Leashable.createQuadLeashOffsets(this, 0.0F, 0.57F, 0.382, 0.88);
    }
}

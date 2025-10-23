package com.aetherteam.aetherii.entity.vehicle;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.AbstractBoatAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class CloudSkiff extends AbstractBoat {
    public float steering;

    public CloudSkiff(EntityType<CloudSkiff> entityType, Level level) {
        super(entityType, level, AetherIIItems.CLOUD_SKIFF);
        this.blocksBuilding = true;
    }

    @Override
    public void tick() {
        AbstractBoatAccessor accessor = (AbstractBoatAccessor) this;
        super.tick();
        if (this.level().isClientSide()) {
            if (this.getInBlockState().is(AetherIIBlocks.COLD_AERCLOUD.get())) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), 0.2F, this.getDeltaMovement().z()));
            }
        }
        if (accessor.aether$getInputRight()) {
            this.steering = Math.clamp(this.steering - 3, -30.0F, 30.0F);
        } else if (accessor.aether$getInputLeft()) {
            this.steering = Math.clamp(this.steering + 3, -30.0F, 30.0F);
        } else {
            if (this.steering > 0) {
                this.steering--;
            } else if (this.steering < 0) {
                this.steering++;
            }
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
    protected void positionRider(Entity entity, MoveFunction moveFunction) {
        super.positionRider(entity, moveFunction);
//        entity.setYBodyRot(0);
//        entity.setOldRot();
    }

    @Override
    protected double rideHeight(EntityDimensions entityDimensions) {
        return entityDimensions.height() + 0.55F;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        return this.position().add(0, 0.25, 0);
    }
}

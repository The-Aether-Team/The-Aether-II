package com.aetherteam.aetherii.entity.ai.controller;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FlyingMoveControl extends MoveControl {
    private final Mob mob;
    private int floatDuration;

    public FlyingMoveControl(Mob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            if (this.floatDuration-- <= 0) {
                this.floatDuration += this.mob.getRandom().nextInt(5) + 2;
                Vec3 vec3d = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
                vec3d = vec3d.normalize();
                if (this.canReach(vec3d)) {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3d.scale(0.07)));
                } else {
                    this.operation = Operation.WAIT;
                }
            }
        }
    }

    private boolean canReach(Vec3 delta) {
        AABB aabb = this.mob.getBoundingBox().move(delta).inflate(1.0);
        boolean isInWater = this.mob.isInWater();
        boolean isInLava = this.mob.isInLava();

        for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
            if (!this.blockTraversalPossible(this.mob.level(), blockpos, isInWater, isInLava)) {
                return false;
            }
        }

        return true;
    }

    private boolean blockTraversalPossible(BlockGetter level, BlockPos pos, boolean isInWater, boolean isInLava) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.isAir()) {
            return true;
        } else {
            BlockState blockState = level.getBlockState(pos);
            BlockState onStandingState = level.getBlockState(this.mob.blockPosition());

            FluidState fluidstate = level.getFluidState(pos);
            if (!fluidstate.isEmpty()) {
                if (fluidstate.is(FluidTags.WATER)) {
                    return isInWater;
                }

                if (fluidstate.is(FluidTags.LAVA)) {
                    return isInLava;
                }
            }

            if (blockState.is(AetherIITags.Blocks.AERCLOUDS) && !onStandingState.is(AetherIITags.Blocks.AERCLOUDS)) {
                return false;
            }

            return blockstate.getCollisionShape(level, pos).isEmpty();
        }
    }
}

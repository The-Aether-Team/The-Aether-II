package com.aetherteam.aetherii.entity.ai.controller;

import com.aetherteam.aetherii.AetherIITags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Ghast;
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

    public Vec3 extendVecForReach(Vec3 delta) {
        double x = delta.x;
        double y = delta.y;
        double z = delta.z;

        if (x > 0) {
            ++x;
        } else if (x < 0) {
            --x;
        }
        if (y > 0) {
            ++y;
        } else if (y < 0) {
            --y;
        }
        if (z > 0) {
            ++z;
        } else if (z < 0) {
            --z;
        }
        return new Vec3(x, y, z);
    }

    private boolean canReach(Vec3 delta) {
        AABB aabb = this.mob.getBoundingBox();
        AABB aabb1 = aabb.move(extendVecForReach(delta));

        for (BlockPos blockpos : BlockPos.betweenClosed(aabb1)) {
            if (!this.blockTraversalPossible(this.mob.level(), null, null, blockpos, false, false)) {
                return false;
            }
        }

        boolean flag = this.mob.isInWater();
        boolean flag1 = this.mob.isInLava();
        Vec3 vec3 = this.mob.position();
        Vec3 vec31 = vec3.add(delta);
        return BlockGetter.forEachBlockIntersectedBetween(
                vec3,
                vec31,
                aabb1,
                (pos, index) -> aabb.intersects(pos) || this.blockTraversalPossible(this.mob.level(), vec3, vec31, pos, flag, flag1)
        );
    }


    /**
     * [CODE COPY] - {@link Ghast.GhastMoveControl#blockTraversalPossible(BlockGetter, Vec3, Vec3, BlockPos, boolean, boolean)}
     */
    private boolean blockTraversalPossible(
            BlockGetter level, @Nullable Vec3 from, @Nullable Vec3 to, BlockPos pos, boolean isInLava, boolean isInWater
    ) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.isAir()) {
            return true;
        } else {
            boolean flag = from != null && to != null;
            boolean flag1 = flag
                    ? !this.mob
                       .collidedWithShapeMovingFrom(
                               from, to, blockstate.getCollisionShape(level, pos).move(new Vec3(pos)).toAabbs()
                       )
                    : blockstate.getCollisionShape(level, pos).isEmpty();


            BlockState blockState = level.getBlockState(pos);
            BlockState onStandingState = level.getBlockState(this.mob.blockPosition());

            FluidState fluidstate = level.getFluidState(pos);
            if (!fluidstate.isEmpty() && (!flag || this.mob.collidedWithFluid(fluidstate, pos, from, to))) {
                if (fluidstate.is(FluidTags.WATER)) {
                    return isInLava;
                }

                if (fluidstate.is(FluidTags.LAVA)) {
                    return isInWater;
                }
            }

            if (blockState.is(AetherIITags.Blocks.AERCLOUDS) && !onStandingState.is(AetherIITags.Blocks.AERCLOUDS)) {
                return false;
            }

            return flag1;
        }
    }
}

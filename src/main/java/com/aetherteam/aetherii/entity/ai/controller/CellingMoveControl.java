package com.aetherteam.aetherii.entity.ai.controller;

import com.aetherteam.aetherii.entity.monster.CellingMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathType;

public class CellingMoveControl extends MoveControl {
    public CellingMonster cellingMonster;

    public CellingMoveControl(CellingMonster cellingMonster) {
        super(cellingMonster);
        this.cellingMonster = cellingMonster;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            if (this.cellingMonster.getAttachFacing() == Direction.DOWN) {
                this.operation = Operation.WAIT;
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedZ - this.mob.getZ();
                double d2 = this.wantedY - this.mob.getY();

                double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                float f9 = (float) (Mth.atan2(d1, d0) * 180.0F / (float) Math.PI) - 90.0F;
                if (d3 < 2.5000003E-7F) {
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                    return;
                }
                float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));

                if (Math.abs(d2) > 0.0F && this.isWalkableUpper()) {

                    this.mob.setYya(d2 > 0.0 ? f1 : -f1);
                }

                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
               /* BlockPos blockpos = this.mob.blockPosition();
                BlockState blockstate = this.mob.level().getBlockState(blockpos);
                VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
                if (d2 > (double)this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.mob.getBbWidth())
                        || !voxelshape.isEmpty()
                        && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double)blockpos.getY()
                        && !blockstate.is(BlockTags.DOORS)
                        && !blockstate.is(BlockTags.FENCES)) {
                    this.mob.getJumpControl().jump();
                    this.operation = MoveControl.Operation.JUMPING;
                }*/
            } else {
                this.operation = Operation.WAIT;
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedZ - this.mob.getZ();
                double d2 = this.wantedY - this.mob.getY();
                double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                if (d3 < 2.5000003E-7F) {
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                    return;
                }

                float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));

                if (Math.abs(d2) > 0.0F) {

                    this.mob.setYya(d2 > 0.0 ? f1 : -f1);
                }

                this.mob.setYRot(this.cellingMonster.getAttachFacing().toYRot());
                this.mob.yBodyRot = this.mob.getYRot();
                if (Math.abs(d0) > 2.5000003E-7F) {
                    this.mob.setXxa(d0 > 0.0 ? -f1 : f1);
                }

                if (Math.abs(d1) > 2.5000003E-7F) {
                    this.mob.setZza(d1 > 0.0 ? -f1 : f1);
                }
            }
        } else {
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }

    public boolean isWalkableUpper() {
        PathNavigation pathnavigation = this.mob.getNavigation();
        if (pathnavigation != null) {
            NodeEvaluator nodeevaluator = pathnavigation.getNodeEvaluator();
            if (nodeevaluator != null
                    && nodeevaluator.getPathType(
                    this.mob, BlockPos.containing(this.mob.getX(), (double) this.mob.getBlockY() + 1, this.mob.getZ())
            )
                    != PathType.WALKABLE) {
                return false;
            }
        }

        return true;
    }
}

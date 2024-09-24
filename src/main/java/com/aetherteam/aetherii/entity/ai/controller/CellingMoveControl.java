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
        if (this.operation == Operation.STRAFE) {
            if (this.cellingMonster.getAttachFacing() == Direction.DOWN) {
                super.tick();
            } else {
                float f = (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
                float f1 = (float) this.speedModifier * f;
                float f2 = this.strafeForwards;
                float f3 = this.strafeRight;
                float f4 = Mth.sqrt(f2 * f2 + f3 * f3);
                if (f4 < 1.0F) {
                    f4 = 1.0F;
                }

                f4 = f1 / f4;
                f2 *= f4;
                f3 *= f4;
                float f5 = Mth.sin(this.mob.getYRot() * (float) (Math.PI / 180.0));
                float f6 = Mth.cos(this.mob.getYRot() * (float) (Math.PI / 180.0));
                float f7 = f2 * f6 - f3 * f5;
                float f8 = f3 * f6 + f2 * f5;
                if (!this.isWalkable(f7, f8)) {
                    this.strafeForwards = 1.0F;
                    this.strafeRight = 0.0F;
                }

                this.mob.setSpeed(f1);
                this.mob.setYya(0.0F);
                this.mob.setZza(this.strafeForwards);
                this.mob.setXxa(this.strafeRight);
                this.operation = Operation.WAIT;
            }
        } else if (this.operation == Operation.MOVE_TO) {
            if (this.cellingMonster.getAttachFacing() == Direction.DOWN) {
                super.tick();
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedZ - this.mob.getZ();
                double d2 = this.wantedY - this.mob.getY();
                float f9 = (float) (Mth.atan2(d1, d0) * 180.0F / (float) Math.PI) - 90.0F;
                this.mob.setXxa(0.0F);
                this.mob.setYya(0.0F);
                if (d2 > 0.0) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
                    this.mob.yBodyRot = this.mob.getYRot();
                }
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

                float f9 = (float) (Mth.atan2(d1, d0) * 180.0F / (float) Math.PI) - 90.0F;
                this.mob.setYRot(this.cellingMonster.getAttachFacing().toYRot());
                this.mob.yBodyRot = this.mob.getYRot();
                if (Math.abs(d0) > 0.0F) {
                    this.mob.setXxa(d0 > 0.0 ? f1 : -f1);
                }

                if (Math.abs(d1) > 0.0F) {
                    this.mob.setZza(d1 > 0.0 ? f1 : -f1);
                }
            }
        } else {
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            super.tick();
        }
    }

    private boolean isWalkable(float p_24997_, float p_24998_) {
        PathNavigation pathnavigation = this.mob.getNavigation();
        if (pathnavigation != null) {
            NodeEvaluator nodeevaluator = pathnavigation.getNodeEvaluator();
            if (nodeevaluator != null
                    && nodeevaluator.getPathType(
                    this.mob, BlockPos.containing(this.mob.getX() + (double) p_24997_, (double) this.mob.getBlockY(), this.mob.getZ() + (double) p_24998_)
            )
                    != PathType.WALKABLE) {
                return false;
            }
        }

        return true;
    }
}

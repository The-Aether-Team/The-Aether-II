package com.aetherteam.aetherii.entity.ai.controller;

import com.aetherteam.aetherii.entity.passive.Shroudwing;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class ShroudwingMoveControl extends MoveControl {
    private final Shroudwing mob;
    private int floatDuration;

    public ShroudwingMoveControl(Shroudwing mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (this.mob.isRest()) {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                this.operation = MoveControl.Operation.WAIT;
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedZ - this.mob.getZ();
                double d2 = this.wantedY - this.mob.getY();
                double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                if (d3 < 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                    return;
                }

                float f9 = (float) (Mth.atan2(d1, d0) * 180.0F / (float) Math.PI) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            } else {
                this.mob.setZza(0.0F);
            }
        } else if (this.mob.isFullyFlying()) {
            if (this.operation == Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.mob.getRandom().nextInt(5) + 2;
                    Vec3 vec3d = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
                    vec3d = vec3d.normalize();
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3d.scale(0.1)));
                }
            }
        }
    }
}

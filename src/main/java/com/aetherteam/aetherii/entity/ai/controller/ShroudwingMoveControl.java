package com.aetherteam.aetherii.entity.ai.controller;

import com.aetherteam.aetherii.entity.passive.Shroudwing;
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
            super.tick();
        } else {
            if (this.operation == Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.operation = Operation.WAIT;
                    this.floatDuration += this.mob.getRandom().nextInt(5) + 2;
                    Vec3 vec3d = new Vec3(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
                    vec3d = vec3d.normalize();
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3d.scale(0.1)));
                }
            }
        }
    }
}

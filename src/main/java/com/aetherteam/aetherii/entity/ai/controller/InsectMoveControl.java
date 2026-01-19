package com.aetherteam.aetherii.entity.ai.controller;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class InsectMoveControl extends MoveControl {
    private final Mob mob;
    private int floatDuration;

    public InsectMoveControl(Mob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
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

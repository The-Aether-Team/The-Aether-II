package com.aetherteam.aetherii.entity.ai.controller;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
                double d0 = vec3d.length();
                vec3d = vec3d.normalize();
                if (this.canReach(vec3d, Mth.ceil(d0))) {
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3d.scale(0.05)));
                } else {
                    this.operation = Operation.WAIT;
                }
            }
        }
    }

    private boolean canReach(Vec3 pos, int distance) {
        AABB aabb = this.mob.getBoundingBox();
        for (int i = 1; i < distance; ++i) {
            aabb = aabb.move(pos);
            if (!this.mob.level().noCollision(this.mob, aabb)) {
                return false;
            }
        }
        return true;
    }
}

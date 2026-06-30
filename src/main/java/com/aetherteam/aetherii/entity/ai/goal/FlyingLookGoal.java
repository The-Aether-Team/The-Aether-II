package com.aetherteam.aetherii.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingLookGoal extends Goal {
    private final Mob mob;

    public FlyingLookGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getTarget() == null) {
            Vec3 vec3d = this.mob.getDeltaMovement();
            this.mob.setYRot(-((float) Mth.atan2(vec3d.x(), vec3d.z())) * Mth.RAD_TO_DEG);
            this.mob.yBodyRot = this.mob.getYRot();
        } else {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity.distanceToSqr(this.mob) < 4096.0) {
                double x = livingEntity.getX() - this.mob.getX();
                double z = livingEntity.getZ() - this.mob.getZ();
                this.mob.setYRot(-((float) Mth.atan2(x, z)) * Mth.RAD_TO_DEG);
                this.mob.setYBodyRot(this.mob.getYRot());
            }
        }
    }
}

package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.entity.monster.Zephyr;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingLookWithAvoidAmbrosiumGoal extends FlyingLookGoal {
    private final Zephyr mob;

    public FlyingLookWithAvoidAmbrosiumGoal(Zephyr mob) {
        super(mob);
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void tick() {
        if (this.mob.getTarget() == null || this.mob.isAIAvoid()) {
            Vec3 vec3d = this.mob.getDeltaMovement();
            this.mob.setYRot(-((float) Mth.atan2(vec3d.x(), vec3d.z())) * Mth.RAD_TO_DEG);
            this.mob.yBodyRot = this.mob.getYRot();
        } else {
            super.tick();
        }
    }
}

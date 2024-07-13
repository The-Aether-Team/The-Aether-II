package com.aetherteam.aetherii.entity.ai.goal;

import com.aetherteam.aetherii.entity.passive.Taegore;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class TaegorePanicGoal extends PanicGoal {
    public TaegorePanicGoal(Taegore taegore, double speed) {
        super(taegore, speed);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 32, 6);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }
}
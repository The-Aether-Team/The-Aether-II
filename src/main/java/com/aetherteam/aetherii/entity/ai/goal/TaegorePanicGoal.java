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
        Vec3 pos = DefaultRandomPos.getPos(this.mob, 32, 6);
        if (pos == null) {
            return false;
        } else {
            this.posX = pos.x;
            this.posY = pos.y;
            this.posZ = pos.z;
            return true;
        }
    }
}
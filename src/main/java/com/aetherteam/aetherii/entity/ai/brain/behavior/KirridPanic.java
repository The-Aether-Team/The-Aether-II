package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class KirridPanic extends AnimalPanic {
    private final float speedMultiplier;

    public KirridPanic(float speed) {
        super(speed);
        this.speedMultiplier = speed;
    }

    @Override
    protected void tick(ServerLevel pLevel, PathfinderMob pOwner, long pGameTime) {
        super.tick(pLevel, pOwner, pGameTime);
        if (pOwner instanceof Kirrid kirrid) {
            kirrid.setSpeedModifier(this.speedMultiplier);
        }
    }
}

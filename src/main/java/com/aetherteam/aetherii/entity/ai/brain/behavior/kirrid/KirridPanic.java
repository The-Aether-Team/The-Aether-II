package com.aetherteam.aetherii.entity.ai.brain.behavior.kirrid;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class KirridPanic extends AnimalPanic { //todo verify this is necessary
    private final float speedMultiplier;

    public KirridPanic(float speed) {
        super(speed);
        this.speedMultiplier = speed;
    }

    @Override
    protected void tick(ServerLevel serverLevel, PathfinderMob owner, long gameTime) {
        super.tick(serverLevel, owner, gameTime);
        if (owner instanceof Kirrid kirrid) {
            kirrid.setSpeedModifier(this.speedMultiplier);
        }
    }
}

package com.aetherteam.aetherii.entity.ai.brain.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class BabyOnlyAnimalPanic<T extends PathfinderMob> extends AnimalPanic<T> {
    public BabyOnlyAnimalPanic(float speed) {
        super(speed);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T owner) {
        return owner.isBaby() && super.checkExtraStartConditions(level, owner);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, T entity, long gameTime) {
        return entity.isBaby() && super.canStillUse(level, entity, gameTime);
    }
}

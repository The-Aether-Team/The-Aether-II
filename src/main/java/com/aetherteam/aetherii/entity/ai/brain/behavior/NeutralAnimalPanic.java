package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.AetherAnimal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class NeutralAnimalPanic<T extends AetherAnimal> extends AnimalPanic {
    public NeutralAnimalPanic(float speedModifier) {
        super(speedModifier);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, PathfinderMob owner) {
        return owner.isBaby() && super.checkExtraStartConditions(serverLevel, owner);
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, PathfinderMob owner, long gameTime) {
        return owner.isBaby() && super.canStillUse(serverLevel, owner, gameTime);
    }
}

package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.AetherAnimal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class NeutralAnimalPanic<T extends AetherAnimal> extends AnimalPanic<T> {
    public NeutralAnimalPanic(float p_249921_) {
        super(p_249921_);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, T entity, long gameTime) {
        return entity.isBaby() && super.canStillUse(level, entity, gameTime);
    }
}

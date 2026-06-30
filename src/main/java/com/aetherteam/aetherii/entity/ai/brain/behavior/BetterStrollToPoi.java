package com.aetherteam.aetherii.entity.ai.brain.behavior;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import org.apache.commons.lang3.mutable.MutableLong;

public class BetterStrollToPoi {
    public static OneShot<PathfinderMob> create(MemoryModuleType<GlobalPos> globalPosMemory, float speedModifier, int closeEnoughDist, int maxDistFromPOI) {
        MutableLong mutablelong = new MutableLong(0L);
        return BehaviorBuilder.create(instance -> instance.group(instance.registered(MemoryModuleType.WALK_TARGET), instance.present(globalPosMemory))
                .apply(instance, (walkTarget, globalPos) -> (serverLevel, owner, gameTime) -> {
                    GlobalPos globalpos = instance.get(globalPos);
                    if (serverLevel.dimension() != globalpos.dimension() || !globalpos.pos().closerToCenterThan(owner.position(), maxDistFromPOI)) {
                        return false;
                    } else if (gameTime <= mutablelong.getValue()) {
                        return true;
                    } else {
                        walkTarget.set(new WalkTarget(globalpos.pos(), speedModifier, closeEnoughDist));
                        mutablelong.setValue(gameTime + 80L);
                        return true;
                    }
                })
        );
    }
}

package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FallRandomStroll {
    public static OneShot<PathfinderMob> stroll(float speedModifier) {
        return stroll(speedModifier, true);
    }

    public static OneShot<PathfinderMob> stroll(float speedModifier, boolean mayStrollFromWater) {
        return strollFlyOrSwim(
                speedModifier,
                mob -> LandRandomPos.getPos(mob, 12, mob.getMaxFallDistance()),
                mayStrollFromWater
                        ? mob -> true
                        : mob -> !mob.isInWater()
        );
    }

    private static OneShot<PathfinderMob> strollFlyOrSwim(float speedModifier, Function<PathfinderMob, Vec3> target, Predicate<PathfinderMob> canStroll) {
        return BehaviorBuilder.create(instance -> instance.group(instance.absent(MemoryModuleType.WALK_TARGET))
                .apply(instance, walkTarget -> (serverLevel, owner, gameTime) -> {
                    if (!canStroll.test(owner)) {
                        return false;
                    } else {
                        Optional<Vec3> targetPos = Optional.ofNullable(target.apply(owner));
                        walkTarget.setOrErase(targetPos.map(vec -> new WalkTarget(vec, speedModifier, 0)));
                        if (owner instanceof Kirrid kirrid) {
                            kirrid.setSpeedModifier(speedModifier);
                        }
                        return true;
                    }
                })
        );
    }
}

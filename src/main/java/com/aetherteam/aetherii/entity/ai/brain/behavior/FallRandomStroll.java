package com.aetherteam.aetherii.entity.ai.brain.behavior;

import com.aetherteam.aetherii.entity.passive.Kirrid;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FallRandomStroll {

    public static OneShot<PathfinderMob> stroll(float pSpeedModifier) {
        return stroll(pSpeedModifier, true);
    }

    public static OneShot<PathfinderMob> stroll(float pSpeedModifier, boolean pMayStrollFromWater) {
        return strollFlyOrSwim(
                pSpeedModifier, p_258601_ -> LandRandomPos.getPos(p_258601_, 12, p_258601_.getMaxFallDistance()), pMayStrollFromWater ? p_258615_ -> true : p_308642_ -> !p_308642_.isInWaterOrBubble()
        );
    }

    private static OneShot<PathfinderMob> strollFlyOrSwim(float pSpeedModifier, Function<PathfinderMob, Vec3> pTarget, Predicate<PathfinderMob> pCanStroll) {
        return BehaviorBuilder.create(
                p_258620_ -> p_258620_.group(p_258620_.absent(MemoryModuleType.WALK_TARGET))
                        .apply(p_258620_, p_258600_ -> (p_258610_, p_258611_, p_258612_) -> {
                            if (!pCanStroll.test(p_258611_)) {
                                return false;
                            } else {
                                Optional<Vec3> optional = Optional.ofNullable(pTarget.apply(p_258611_));
                                p_258600_.setOrErase(optional.map(p_258622_ -> new WalkTarget(p_258622_, pSpeedModifier, 0)));
                                if (p_258611_ instanceof Kirrid kirrid) {
                                    kirrid.setSpeedModifier(pSpeedModifier);
                                }
                                return true;
                            }
                        })
        );
    }

    @Nullable
    private static Vec3 getTargetFlyPos(PathfinderMob pMob, int pMaxDistance, int pYRange) {
        Vec3 vec3 = pMob.getViewVector(0.0F);
        return AirAndWaterRandomPos.getPos(pMob, pMaxDistance, pYRange, -2, vec3.x, vec3.z, (float) (Math.PI / 2));
    }
}

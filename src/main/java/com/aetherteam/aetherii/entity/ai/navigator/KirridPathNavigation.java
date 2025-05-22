package com.aetherteam.aetherii.entity.ai.navigator;

import com.aetherteam.aetherii.entity.ai.navigator.node.KirridNodeEvaluator;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class KirridPathNavigation extends FallPathNavigation {
    public KirridPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new KirridNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanWalkOverFences(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}

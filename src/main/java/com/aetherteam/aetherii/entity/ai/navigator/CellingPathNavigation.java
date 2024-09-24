package com.aetherteam.aetherii.entity.ai.navigator;

import com.aetherteam.aetherii.entity.ai.navigator.node.CellingNodeEvaluator;
import com.aetherteam.aetherii.entity.monster.CellingMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class CellingPathNavigation extends GroundPathNavigation {
    public final com.aetherteam.aetherii.entity.monster.CellingMonster cellingMonster;

    public CellingPathNavigation(CellingMonster mob, Level level) {
        super(mob, level);
        this.cellingMonster = mob;
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    @Override
    protected PathFinder createPathFinder(int p_26598_) {
        this.nodeEvaluator = new CellingNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanOpenDoors(false);
        this.nodeEvaluator.setCanFloat(true);
        return new PathFinder(this.nodeEvaluator, p_26598_);
    }

    @Override
    public void tick() {
        this.tick++;
        if (this.hasDelayedRecomputation) {
            this.recomputePath();
        }

        if (!this.isDone()) {
            if (this.canUpdatePath()) {
                this.followThePath();
            } else if (this.path != null && !this.path.isDone()) {
                Vec3 vec3 = this.path.getNextEntityPos(this.mob);
                if (this.mob.getBlockX() == Mth.floor(vec3.x) && this.mob.getBlockY() == Mth.floor(vec3.y) && this.mob.getBlockZ() == Mth.floor(vec3.z)) {
                    this.path.advance();
                }
            }

            DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
            if (!this.isDone()) {
                Vec3 vec31 = this.path.getNextEntityPos(this.mob);
                this.mob.getMoveControl().setWantedPosition(vec31.x, vec31.y, vec31.z, this.speedModifier);
            }
        }
    }

    @Override
    protected boolean canMoveDirectly(Vec3 p_186138_, Vec3 p_186139_) {
        return false;
    }

    @Override
    public boolean isStableDestination(BlockPos p_26608_) {
        return true;
    }

    @Override
    public void setCanFloat(boolean p_26563_) {
        this.nodeEvaluator.setCanFloat(p_26563_);
    }

    @Override
    protected boolean hasValidPathType(PathType p_326937_) {
        return super.hasValidPathType(p_326937_) || p_326937_ == PathType.OPEN;
    }
}
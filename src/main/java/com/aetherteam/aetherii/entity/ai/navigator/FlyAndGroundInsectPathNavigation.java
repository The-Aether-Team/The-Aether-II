package com.aetherteam.aetherii.entity.ai.navigator;

import com.aetherteam.aetherii.entity.ai.navigator.node.InsectNodeEvaluator;
import com.aetherteam.aetherii.entity.passive.Insect;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class FlyAndGroundInsectPathNavigation extends FlyInsectPathNavigation {
    public FlyAndGroundInsectPathNavigation(Mob p_26424_, Level p_26425_) {
        super(p_26424_, p_26425_);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new InsectNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanWalkOverFences(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    public void tick() {
        if (this.mob instanceof Insect insect && insect.isRest()) {
            this.tick++;
            if (this.hasDelayedRecomputation) {
                this.recomputePath();
            }

            if (!this.isDone()) {
                if (this.canUpdatePath()) {
                    this.followThePath();
                } else if (this.path != null && !this.path.isDone()) {
                    Vec3 vec3 = this.getTempMobPos();
                    Vec3 vec31 = this.path.getNextEntityPos(this.mob);
                    if (vec3.y > vec31.y && !this.mob.onGround() && Mth.floor(vec3.x) == Mth.floor(vec31.x) && Mth.floor(vec3.z) == Mth.floor(vec31.z)) {
                        this.path.advance();
                    }
                }

                DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
                if (!this.isDone()) {
                    Vec3 vec32 = this.path.getNextEntityPos(this.mob);
                    this.mob.getMoveControl().setWantedPosition(vec32.x, this.getGroundY(vec32), vec32.z, this.speedModifier);
                }
            }
        } else {
            super.tick();
        }
    }




    @Override
    public boolean isStableDestination(BlockPos pos) {
        return this.level.getBlockState(pos).entityCanStandOn(this.level, pos, this.mob);
    }

    @Override
    protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
        return this.mob instanceof Insect insect && insect.isRest() ? false : super.canMoveDirectly(posVec31, posVec32);
    }

    @Override
    public boolean canNavigateGround() {
        return this.mob instanceof Insect insect && insect.isRest();
    }
}

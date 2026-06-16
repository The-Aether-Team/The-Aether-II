package com.aetherteam.aetherii.entity.ai.navigator;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * A path navigator that doesn't require the entity to be on the ground to update the path.
 */
public class FallPathNavigation extends GroundPathNavigation {
    public FallPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    // Fixes falling pathfinding
    @Override
    protected void followThePath() {
        Vec3 vecPos = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
        Vec3i posNext = this.path.getNextNodePos();
        double xDist = Math.abs(this.mob.getX() - ((double) posNext.getX() + (this.mob.getBbWidth() + 1) / 2D)); // Forge: Fix MC-94054
        double yDist = Math.abs(this.mob.getY() - (double) posNext.getY());
        double zDist = Math.abs(this.mob.getZ() - ((double) posNext.getZ() + (this.mob.getBbWidth() + 1) / 2D)); // Forge: Fix MC-94054

        // This makes there be no need to rotate around a point when following a path.
        float fallDistance = this.mob.fallDistance > 3 ? 14 : 1;
        boolean isClose = xDist <= (double) this.maxDistanceToWaypoint && zDist <= (double) this.maxDistanceToWaypoint && yDist < fallDistance;
        if (isClose || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vecPos)) {
            this.path.advance();
        }

        this.doStuckDetection(vecPos);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 vecPos) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 nextPos = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!vecPos.closerThan(nextPos, 2.0)) {
                return false;
            } else if (this.canMoveDirectly(vecPos, this.path.getNextEntityPos(this.mob))) {
                return true;
            } else {
                Vec3 nextPosExtra = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 distToNextPos = nextPos.subtract(vecPos);
                Vec3 distToNextPosExtra = nextPosExtra.subtract(vecPos);
                double distLen = distToNextPos.lengthSqr();
                double distLenExtra = distToNextPosExtra.lengthSqr();
                boolean distTooFar = distLenExtra < distLen;
                boolean distTooSmall = distLen < 0.5;
                if (!distTooFar && !distTooSmall) {
                    return false;
                } else {
                    Vec3 nextPosNormalize = distToNextPos.normalize();
                    Vec3 nextPosExtraNormalize = distToNextPosExtra.normalize();
                    return nextPosExtraNormalize.dot(nextPosNormalize) < 0.0;
                }
            }
        }
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}

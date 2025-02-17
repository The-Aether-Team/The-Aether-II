package com.aetherteam.aetherii.entity.ai.navigator;

import com.aetherteam.aetherii.entity.ai.navigator.node.CellingNodeEvaluator;
import com.aetherteam.aetherii.entity.monster.CellingMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class CellingPathNavigation extends GroundPathNavigation {
    public final CellingMonster cellingMonster;

    public CellingPathNavigation(CellingMonster mob, Level level) {
        super(mob, level);
        this.cellingMonster = mob;
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }


    @Override
    public boolean canCutCorner(PathType p_326808_) {
        return this.cellingMonster.getAttachFacing() == Direction.DOWN && super.canCutCorner(p_326808_);
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
                //TODO find why 0.5 block gap has!
                this.mob.getMoveControl().setWantedPosition(vec31.x, vec31.y, vec31.z, this.speedModifier);
            }
        }
    }

    @Override
    protected void followThePath() {
        Vec3 vec3 = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75F ? this.mob.getBbWidth() / 2.0F : 0.75F - this.mob.getBbWidth() / 2.0F;
        Vec3i vec3i = this.path.getNextNodePos();

        double d0 = Math.abs(this.mob.getX() - ((double) vec3i.getX() + (this.mob.getBbWidth() + 1) / 2D)); //Forge: Fix MC-94054
        double d1 = Math.abs(this.mob.getY() - (double) vec3i.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double) vec3i.getZ() + (this.mob.getBbWidth() + 1) / 2D)); //Forge: Fix MC-94054

        //no cut out
        float fallDistance = this.mob.getMaxFallDistance();
        boolean flag = d0 <= (double) this.maxDistanceToWaypoint && d2 <= (double) this.maxDistanceToWaypoint && d1 <= this.maxDistanceToWaypoint;
        if (flag || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vec3)) {
            this.path.advance();
        }

        this.doStuckDetection(vec3);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 pVec) {
        if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vec3 = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!pVec.closerThan(vec3, 2.0)) {
                return false;
            } else if (this.canMoveDirectly(pVec, this.path.getNextEntityPos(this.mob))) {
                return true;
            } else {
                Vec3 vec31 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vec32 = vec3.subtract(pVec);
                Vec3 vec33 = vec31.subtract(pVec);
                double d0 = vec32.lengthSqr();
                double d1 = vec33.lengthSqr();
                boolean flag = d1 < d0;
                boolean flag1 = d0 < 0.5;
                if (!flag && !flag1) {
                    return false;
                } else {
                    Vec3 vec34 = vec32.normalize();
                    Vec3 vec35 = vec33.normalize();
                    return vec35.dot(vec34) < 0.0;
                }
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

    @Override
    protected Vec3 getTempMobPos() {
        if (this.cellingMonster.getAttachFacing() != Direction.DOWN) {
            return new Vec3(this.mob.getX(), this.mob.getY() + 0.5F, this.mob.getZ());
        }

        return super.getTempMobPos();
    }
}
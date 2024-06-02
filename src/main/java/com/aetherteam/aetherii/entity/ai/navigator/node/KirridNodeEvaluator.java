package com.aetherteam.aetherii.entity.ai.navigator.node;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import javax.annotation.Nullable;

public class KirridNodeEvaluator extends WalkNodeEvaluator {


    @Nullable
    protected Node findAcceptedNode(int pX, int pY, int pZ, int pVerticalDeltaLimit, double pNodeFloorLevel, Direction pDirection, BlockPathTypes pPathType) {
        Node node = null;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        double d0 = this.getFloorLevel(blockpos$mutableblockpos.set(pX, pY, pZ));
        if (d0 - pNodeFloorLevel > this.getMobJumpHeight()) {
            return null;
        } else {
            return super.findAcceptedNode(pX, pY, pZ, pVerticalDeltaLimit, pNodeFloorLevel, pDirection, pPathType);
        }
    }

    @Override
    public int getNeighbors(Node[] pOutputArray, Node pNode) {
        int i = 0;
        int j = 0;
        BlockPathTypes blockpathtypes = this.getCachedBlockType(this.mob, pNode.x, pNode.y + 1, pNode.z);
        BlockPathTypes blockpathtypes1 = this.getCachedBlockType(this.mob, pNode.x, pNode.y, pNode.z);
        if (this.mob.getPathfindingMalus(blockpathtypes) >= 0.0F && blockpathtypes1 != BlockPathTypes.STICKY_HONEY) {
            j = Mth.floor(Math.max(2.5F, this.mob.getStepHeight()));
        }

        double d0 = this.getFloorLevel(new BlockPos(pNode.x, pNode.y, pNode.z));
        Node node = this.findAcceptedNode(pNode.x, pNode.y, pNode.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isNeighborValid(node, pNode)) {
            pOutputArray[i++] = node;
        }

        Node node1 = this.findAcceptedNode(pNode.x - 1, pNode.y, pNode.z, j, d0, Direction.WEST, blockpathtypes1);
        if (this.isNeighborValid(node1, pNode)) {
            pOutputArray[i++] = node1;
        }

        Node node2 = this.findAcceptedNode(pNode.x + 1, pNode.y, pNode.z, j, d0, Direction.EAST, blockpathtypes1);
        if (this.isNeighborValid(node2, pNode)) {
            pOutputArray[i++] = node2;
        }

        Node node3 = this.findAcceptedNode(pNode.x, pNode.y, pNode.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isNeighborValid(node3, pNode)) {
            pOutputArray[i++] = node3;
        }

        Node node4 = this.findAcceptedNode(pNode.x - 1, pNode.y, pNode.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isDiagonalValid(pNode, node1, node3, node4)) {
            pOutputArray[i++] = node4;
        }

        Node node5 = this.findAcceptedNode(pNode.x + 1, pNode.y, pNode.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isDiagonalValid(pNode, node2, node3, node5)) {
            pOutputArray[i++] = node5;
        }

        Node node6 = this.findAcceptedNode(pNode.x - 1, pNode.y, pNode.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isDiagonalValid(pNode, node1, node, node6)) {
            pOutputArray[i++] = node6;
        }

        Node node7 = this.findAcceptedNode(pNode.x + 1, pNode.y, pNode.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isDiagonalValid(pNode, node2, node, node7)) {
            pOutputArray[i++] = node7;
        }

        return i;
    }


    private double getMobJumpHeight() {
        return Math.max(2.5, (double) this.mob.getStepHeight());
    }
}

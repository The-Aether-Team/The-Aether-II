package com.aetherteam.aetherii.entity.ai.navigator.node;

import com.aetherteam.aetherii.entity.passive.Insect;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class InsectNodeEvaluator extends FlyNodeEvaluator {
    private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap<>();
    private final Node[] reusableNeighbors = new Node[4];

    @Override
    public void prepare(PathNavigationRegion p_77261_, Mob p_77262_) {
        super.prepare(p_77261_, p_77262_);
    }


    @Override
    public void done() {
        this.collisionCache.clear();
        super.done();
    }

    @Override
    public int getNeighbors(Node[] p_77640_, Node p_77641_) {
        if (this.mob instanceof Insect insect && insect.isRest()) {

            int i = 0;
            int j = 0;
            BlockPathTypes pathtype = this.getCachedBlockType(this.mob, p_77641_.x, p_77641_.y + 1, p_77641_.z);
            BlockPathTypes pathtype1 = this.getCachedBlockType(this.mob, p_77641_.x, p_77641_.y, p_77641_.z);
            if (this.mob.getPathfindingMalus(pathtype) >= 0.0F && pathtype1 != BlockPathTypes.STICKY_HONEY) {
                j = Mth.floor(Math.max(1.0F, this.mob.maxUpStep()));
            }

            double d0 = this.getFloorLevel(new BlockPos(p_77641_.x, p_77641_.y, p_77641_.z));

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Node node = this.findAcceptedNode(p_77641_.x + direction.getStepX(), p_77641_.y, p_77641_.z + direction.getStepZ(), j, d0, direction, pathtype1);
                this.reusableNeighbors[direction.get2DDataValue()] = node;
                if (this.isNeighborValid(node, p_77641_)) {
                    p_77640_[i++] = node;
                }
            }

            for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                Direction direction2 = direction1.getClockWise();
                if (this.isDiagonalValid(p_77641_, this.reusableNeighbors[direction1.get2DDataValue()], this.reusableNeighbors[direction2.get2DDataValue()])) {
                    Node node1 = this.findAcceptedNode(
                            p_77641_.x + direction1.getStepX() + direction2.getStepX(),
                            p_77641_.y,
                            p_77641_.z + direction1.getStepZ() + direction2.getStepZ(),
                            j,
                            d0,
                            direction1,
                            pathtype1
                    );
                    if (this.isDiagonalValid(node1)) {
                        p_77640_[i++] = node1;
                    }
                }
            }

            return i;
        } else {
            return super.getNeighbors(p_77640_, p_77641_);
        }
    }

    @Override
    protected boolean isNeighborValid(@Nullable Node neighbor, Node node) {
        if (this.mob instanceof Insect insect && insect.isRest()) {
            return neighbor != null && !neighbor.closed && (neighbor.costMalus >= 0.0F || node.costMalus < 0.0F);
        } else {
            return super.isNeighborValid(neighbor, node);
        }
    }

    protected boolean isDiagonalValid(Node root, @Nullable Node xNode, @Nullable Node zNode) {
        if (this.mob instanceof Insect insect && insect.isRest()) {

            if (zNode == null || xNode == null || zNode.y > root.y || xNode.y > root.y) {
                return false;
            } else if (xNode.type != BlockPathTypes.WALKABLE_DOOR && zNode.type != BlockPathTypes.WALKABLE_DOOR) {
                boolean flag = zNode.type == BlockPathTypes.FENCE && xNode.type == BlockPathTypes.FENCE && this.mob.getBbWidth() < 0.5;
                return (zNode.y < root.y || zNode.costMalus >= 0.0F || flag) && (xNode.y < root.y || xNode.costMalus >= 0.0F || flag);
            } else {
                return false;
            }
        } else {
            return xNode != null && zNode != null;
        }
    }

    protected boolean isDiagonalValid(@Nullable Node node) {
        if (this.mob instanceof Insect insect && insect.isRest()) {

            if (node == null || node.closed) {
                return false;
            } else {
                return node.type == BlockPathTypes.WALKABLE_DOOR ? false : node.costMalus >= 0.0F;
            }
        } else {
            return node != null && !node.closed && node.costMalus >= 0.0F;
        }
    }

    @Nullable
    protected Node findAcceptedNode(int x, int y, int z, int verticalDeltaLimit, double nodeFloorLevel, Direction direction, BlockPathTypes pathType) {
        Node node = null;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        double d0 = this.getFloorLevel(blockpos$mutableblockpos.set(x, y, z));
        if (d0 - nodeFloorLevel > this.getMobJumpHeight()) {
            return null;
        } else {
            BlockPathTypes pathtype = this.getCachedBlockType(this.mob, x, y, z);
            float f = this.mob.getPathfindingMalus(pathtype);
            if (f >= 0.0F) {
                node = this.getNodeAndUpdateCostToMax(x, y, z, pathtype, f);
            }

            if (doesBlockHavePartialCollision(pathType) && node != null && node.costMalus >= 0.0F && !this.canReachWithoutCollision(node)) {
                node = null;
            }

            if (pathtype != BlockPathTypes.WALKABLE && (!this.isAmphibious() || pathtype != BlockPathTypes.WATER)) {
                if ((node == null || node.costMalus < 0.0F)
                        && verticalDeltaLimit > 0
                        && (pathtype != BlockPathTypes.FENCE || this.canWalkOverFences())
                        && pathtype != BlockPathTypes.UNPASSABLE_RAIL
                        && pathtype != BlockPathTypes.TRAPDOOR
                        && pathtype != BlockPathTypes.POWDER_SNOW) {
                    node = this.tryJumpOn(x, y, z, verticalDeltaLimit, nodeFloorLevel, direction, pathType, blockpos$mutableblockpos);
                } else if (!this.isAmphibious() && pathtype == BlockPathTypes.WATER && !this.canFloat()) {
                    node = this.tryFindFirstNonWaterBelow(x, y, z, node);
                } else if (pathtype == BlockPathTypes.OPEN) {
                    node = this.tryFindFirstGroundNodeBelow(x, y, z);
                } else if (doesBlockHavePartialCollision(pathtype) && node == null) {
                    node = this.getClosedNode(x, y, z, pathtype);
                }

                return node;
            } else {
                return node;
            }
        }
    }

    private Node getNodeAndUpdateCostToMax(int x, int y, int z, BlockPathTypes pathType, float malus) {
        Node node = this.getNode(x, y, z);
        node.type = pathType;
        node.costMalus = Math.max(node.costMalus, malus);
        return node;
    }

    private Node getBlockedNode(int x, int y, int z) {
        Node node = this.getNode(x, y, z);
        node.type = BlockPathTypes.BLOCKED;
        node.costMalus = -1.0F;
        return node;
    }

    private Node getClosedNode(int x, int y, int z, BlockPathTypes pathType) {
        Node node = this.getNode(x, y, z);
        node.closed = true;
        node.type = pathType;
        node.costMalus = pathType.getMalus();
        return node;
    }

    @Nullable
    private Node tryJumpOn(
            int x,
            int y,
            int z,
            int verticalDeltaLimit,
            double nodeFloorLevel,
            Direction direction,
            BlockPathTypes pathType,
            BlockPos.MutableBlockPos pos
    ) {
        Node node = this.findAcceptedNode(x, y + 1, z, verticalDeltaLimit - 1, nodeFloorLevel, direction, pathType);
        if (node == null) {
            return null;
        } else if (this.mob.getBbWidth() >= 1.0F) {
            return node;
        } else if (node.type != BlockPathTypes.OPEN && node.type != BlockPathTypes.WALKABLE) {
            return node;
        } else {
            double d0 = x - direction.getStepX() + 0.5;
            double d1 = z - direction.getStepZ() + 0.5;
            double d2 = this.mob.getBbWidth() / 2.0;
            AABB aabb = new AABB(
                    d0 - d2,
                    this.getFloorLevel(pos.set(d0, (double) (y + 1), d1)) + 0.001,
                    d1 - d2,
                    d0 + d2,
                    this.mob.getBbHeight() + this.getFloorLevel(pos.set((double) node.x, (double) node.y, (double) node.z)) - 0.002,
                    d1 + d2
            );
            return this.hasCollisions(aabb) ? null : node;
        }
    }

    private boolean hasCollisions(AABB boundingBox) {
        return this.collisionCache.computeIfAbsent(boundingBox, p_330163_ -> !this.level.noCollision(this.mob, boundingBox));
    }

    @Nullable
    private Node tryFindFirstNonWaterBelow(int x, int y, int z, @Nullable Node node) {
        y--;

        while (y > this.mob.level().getMinBuildHeight()) {
            BlockPathTypes pathtype = this.getCachedBlockType(this.mob, x, y, z);
            if (pathtype != BlockPathTypes.WATER) {
                return node;
            }

            node = this.getNodeAndUpdateCostToMax(x, y, z, pathtype, this.mob.getPathfindingMalus(pathtype));
            y--;
        }

        return node;
    }

    private Node tryFindFirstGroundNodeBelow(int x, int y, int z) {
        for (int i = y - 1; i >= this.mob.level().getMinBuildHeight(); i--) {
            if (y - i > this.mob.getMaxFallDistance()) {
                return this.getBlockedNode(x, i, z);
            }

            BlockPathTypes pathtype = this.getCachedBlockType(this.mob, x, i, z);
            float f = this.mob.getPathfindingMalus(pathtype);
            if (pathtype != BlockPathTypes.OPEN) {
                if (f >= 0.0F) {
                    return this.getNodeAndUpdateCostToMax(x, i, z, pathtype, f);
                }

                return this.getBlockedNode(x, i, z);
            }
        }

        return this.getBlockedNode(x, y, z);
    }

    private boolean canReachWithoutCollision(Node node) {
        AABB aabb = this.mob.getBoundingBox();
        Vec3 vec3 = new Vec3(
                node.x - this.mob.getX() + aabb.getXsize() / 2.0,
                node.y - this.mob.getY() + aabb.getYsize() / 2.0,
                node.z - this.mob.getZ() + aabb.getZsize() / 2.0
        );
        int i = Mth.ceil(vec3.length() / aabb.getSize());
        vec3 = vec3.scale(1.0F / i);

        for (int j = 1; j <= i; j++) {
            aabb = aabb.move(vec3);
            if (this.hasCollisions(aabb)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        if (this.mob instanceof Insect insect && insect.isRest()) {
            return getBlockPathTypeStatic(level, new BlockPos.MutableBlockPos(x, y, z));
        } else {
            return super.getBlockPathType(level, x, y, z);
        }
    }

    private static boolean doesBlockHavePartialCollision(BlockPathTypes pathType) {
        return pathType == BlockPathTypes.FENCE || pathType == BlockPathTypes.DOOR_WOOD_CLOSED || pathType == BlockPathTypes.DOOR_IRON_CLOSED;
    }

    private double getMobJumpHeight() {
        return Math.max(0.525, (double) this.mob.maxUpStep());
    }
}

package com.aetherteam.aetherii.entity.ai.navigator.node;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import javax.annotation.Nullable;
import java.util.Map;

public class CellingNodeEvaluator extends WalkNodeEvaluator {
    private final Long2ObjectMap<PathType> pathTypesByPosCache = new Long2ObjectOpenHashMap<>();

    public CellingNodeEvaluator() {
    }

    @Override
    public void prepare(PathNavigationRegion p_192959_, Mob p_192960_) {
        super.prepare(p_192959_, p_192960_);
        this.pathTypesByPosCache.clear();
    }

    @Override
    public void done() {
        super.done();
        this.pathTypesByPosCache.clear();
    }

    @Override
    public Node getStart() {
        int i;
        if (this.canFloat() && this.mob.isInWater()) {
            i = this.mob.getBlockY();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.mob.getX(), (double) i, this.mob.getZ());

            for (BlockState blockstate = this.currentContext.getBlockState(blockpos$mutableblockpos);
                 blockstate.is(Blocks.WATER);
                 blockstate = this.currentContext.getBlockState(blockpos$mutableblockpos)
            ) {
                blockpos$mutableblockpos.set(this.mob.getX(), (double) (++i), this.mob.getZ());
            }
        } else {
            i = Mth.floor(this.mob.getY() + 0.5);
        }

        BlockPos blockpos1 = BlockPos.containing(this.mob.getX(), (double) i, this.mob.getZ());
        if (!this.canStartAt(blockpos1)) {
            return super.getStartNode(blockpos1);
        }

        return super.getStartNode(blockpos1);
    }


    @Nullable
    public Target getTarget(double p_164662_, double p_164663_, double p_164664_) {
        return this.getTargetNodeAt(Mth.floor(p_164662_), Mth.floor(p_164663_ + 0.5D), Mth.floor(p_164664_));
    }

    @Override
    public int getNeighbors(Node[] p_77483_, Node p_77484_) {
        int i = 0;
        Map<Direction, Node> map = Maps.newEnumMap(Direction.class);

        for (Direction direction : Direction.values()) {
            Node node = this.findAcceptedNode(p_77484_.x + direction.getStepX(), p_77484_.y + direction.getStepY(), p_77484_.z + direction.getStepZ());
            for (Direction direction2 : Direction.values()) {
                BlockPos pos = new BlockPos(p_77484_.x + direction.getStepX() + direction2.getStepX(), p_77484_.y + direction.getStepY() + direction2.getStepY(), p_77484_.z + direction.getStepZ() + direction2.getStepZ());
                if (!this.currentContext.getBlockState(pos).getCollisionShape(this.currentContext.level(), pos).isEmpty()) {

                    map.put(direction, node);
                    break;
                }
            }
            if (this.isNodeValid(node)) {
                p_77483_[i++] = node;
            }
        }

        for (Direction direction1 : Direction.Plane.HORIZONTAL) {
            Direction direction2 = direction1.getClockWise();
            if (hasMalus(map.get(direction1)) && hasMalus(map.get(direction2))) {
                Node node1 = this.findAcceptedNode(
                        p_77484_.x + direction1.getStepX() + direction2.getStepX(), p_77484_.y, p_77484_.z + direction1.getStepZ() + direction2.getStepZ()
                );
                if (this.isNodeValid(node1)) {
                    p_77483_[i++] = node1;
                }
            }
        }

        return i;
    }

    private Node tryFindFirstGroundNodeBelow(int p_326892_, int p_326901_, int p_326809_) {
        for (int i = p_326901_ - 1; i >= this.mob.level().getMinBuildHeight(); i--) {
            if (p_326901_ - i > this.mob.getMaxFallDistance()) {
                return this.getBlockedNode(p_326892_, i, p_326809_);
            }

            PathType pathtype = this.getCachedPathType(p_326892_, i, p_326809_);
            float f = this.mob.getPathfindingMalus(pathtype);
            if (pathtype != PathType.OPEN) {
                if (f >= 0.0F) {
                    return this.getNodeAndUpdateCostToMax(p_326892_, i, p_326809_, pathtype, f);
                }

                return this.getBlockedNode(p_326892_, i, p_326809_);
            }
        }

        return this.getBlockedNode(p_326892_, p_326901_, p_326809_);
    }

    private Node getNodeAndUpdateCostToMax(int p_230620_, int p_230621_, int p_230622_, PathType p_326789_, float p_230624_) {
        Node node = this.getNode(p_230620_, p_230621_, p_230622_);
        node.type = p_326789_;
        node.costMalus = Math.max(node.costMalus, p_230624_);
        return node;
    }

    private Node getBlockedNode(int p_230628_, int p_230629_, int p_230630_) {
        Node node = this.getNode(p_230628_, p_230629_, p_230630_);
        node.type = PathType.BLOCKED;
        node.costMalus = -1.0F;
        return node;
    }

    private Node getClosedNode(int p_326935_, int p_326904_, int p_326845_, PathType p_326820_) {
        Node node = this.getNode(p_326935_, p_326904_, p_326845_);
        node.closed = true;
        node.type = p_326820_;
        node.costMalus = p_326820_.getMalus();
        return node;
    }

    protected boolean isNodeValid(@Nullable Node p_192962_) {
        return p_192962_ != null && !p_192962_.closed;
    }

    @Nullable
    protected Node findAcceptedNode(int p_263032_, int p_263066_, int p_263105_) {
        Node node = null;
        PathType pathtype = this.getCachedBlockType(p_263032_, p_263066_, p_263105_);

        float f = this.mob.getPathfindingMalus(pathtype);
        if (f >= 0.0F) {
            node = this.getNode(p_263032_, p_263066_, p_263105_);
            node.type = pathtype;
            node.costMalus = Math.max(node.costMalus, f);
        }
        if (node != null && node.type == PathType.OPEN) {
            BlockPos blockpos = new BlockPos(p_263032_, p_263066_, p_263105_);

            for (Direction direction : Direction.values()) {
                if (!this.currentContext.getBlockState(blockpos.offset(direction.getNormal())).isAir()) {
                    return node;
                }
            }

            //node = tryFindFirstGroundNodeBelow(p_263032_, p_263066_, p_263105_);
        }
        return null;
    }

    protected PathType getCachedBlockType(int p_192968_, int p_192969_, int p_192970_) {
        return this.pathTypesByPosCache
                .computeIfAbsent(
                        BlockPos.asLong(p_192968_, p_192969_, p_192970_), p_330157_ -> this.getPathType(this.currentContext, p_192968_, p_192969_, p_192970_)
                );
    }

    private static boolean hasMalus(@Nullable Node p_326813_) {
        return p_326813_ != null && p_326813_.costMalus >= 0.0F;
    }

}
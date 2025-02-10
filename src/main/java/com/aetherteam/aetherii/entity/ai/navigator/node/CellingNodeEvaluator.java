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
            map.put(direction, node);

            BlockPos pos = new BlockPos(p_77484_.x + direction.getStepX(), p_77484_.y + direction.getStepY(), p_77484_.z + direction.getStepZ());

            for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {

                if (!this.currentContext.getBlockState(blockPos).getCollisionShape(this.currentContext.level(), blockPos).isEmpty()) {
                    if (this.isNodeValid(node)) {
                        p_77483_[i++] = node;
                    }
                    break;
                }
            }



        }

        for (Direction direction1 : Direction.Plane.HORIZONTAL) {
            Direction direction2 = direction1.getClockWise();
            if (hasMalus(map.get(direction1)) && hasMalus(map.get(direction2))) {
                Node node1 = this.findAcceptedNode(
                        p_77484_.x + direction1.getStepX() + direction2.getStepX(), p_77484_.y, p_77484_.z + direction1.getStepZ() + direction2.getStepZ()
                );
                BlockPos pos = new BlockPos(p_77484_.x + direction1.getStepX() + direction2.getStepX(), p_77484_.y, p_77484_.z + direction1.getStepZ() + direction2.getStepZ());
                for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                    if (!this.currentContext.getBlockState(blockPos).getCollisionShape(this.currentContext.level(), blockPos).isEmpty()) {
                        if (this.isNodeValid(node1)) {
                            p_77483_[i++] = node1;
                            break;
                        }
                    }
                }
            }
        }

        return i;
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
        return node;
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
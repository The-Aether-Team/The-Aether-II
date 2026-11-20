package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GiantTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<GiantTrunkTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("log_block_provider").forGetter(decorator -> decorator.logState),
                    BlockStateProvider.CODEC.fieldOf("trunk_block_provider").forGetter(decorator -> decorator.trunkState),
                    IntProviders.CODEC.fieldOf("log_height").forGetter(decorator -> decorator.logHeight)
            ).apply(instance, GiantTrunkTreeDecorator::new));

    private final BlockStateProvider logState;
    private final BlockStateProvider trunkState;
    private final IntProvider logHeight;

    public GiantTrunkTreeDecorator(BlockStateProvider logState, BlockStateProvider trunkState, IntProvider logHeight) {
        this.logState = logState;
        this.trunkState = trunkState;
        this.logHeight = logHeight;
    }

    @Override
    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        List<BlockPos> logs = context.logs();
        logs.removeIf((pos) -> !level.getBlockState(pos).is(BlockTags.LOGS));
        logs = logs.subList(0, 4);

        if (logs.size() == 4) {
            BlockPos start = logs.getFirst();
            BlockPos end = null;
            for (BlockPos pos : logs) {
                if (pos.getX() != start.getX() && pos.getZ() != start.getZ() && pos.getY() == start.getY()) {
                    end = pos;
                    break;
                }
            }
            if (end != null) {
                AABB base = new AABB(start.getBottomCenter(), end.getBottomCenter());
                Vec3 baseCenter = base.getCenter();

                for (Direction out : Direction.Plane.HORIZONTAL) {
                    Map<BlockPos, BlockState> trunkPositions = new LinkedHashMap<>();

                    Direction side = random.nextBoolean() ? out.getClockWise() : out.getCounterClockWise();
                    Vec3 offsetVec = baseCenter.relative(out, 1.5).relative(side, 0.5);
                    BlockPos offsetPos = BlockPos.containing(offsetVec);

                    int logHeight = this.logHeight.sample(random);
                    int aboveTrunkHeight = logHeight + 1;
                    int logMin = random.nextInt(2) - 3;

                    for (int i = logHeight; i >= logMin; i--) {
                        BlockPos pos = offsetPos.above(i);
                        if (TreeFeature.validTreePos(level, pos)) {
                            if (i != logMin) {
                                level.setBlock(pos, this.logState.getState(level, random, pos), 1 | 2);
                            } else {
                                level.setBlock(pos, this.trunkState.getState(level, random, pos).trySetValue(TrunkBlock.TALL, true), 1 | 2);
                            }
                        } else {
                            break;
                        }
                    }

                    for (int i = logHeight + aboveTrunkHeight - 1; i >= logHeight; i--) {
                        BlockPos pos = offsetPos.above(i);
                        trunkPositions.put(pos, this.trunkState.getState(level, random, pos).trySetValue(TrunkBlock.getPropertyForDirection(out.getOpposite()), WallSide.LOW));
                    }
                    if (aboveTrunkHeight > 1) {
                        for (int i = logHeight; i >= logMin + 2; i--) {
                            BlockPos pos = offsetPos.relative(out).above(i);
                            if (TreeFeature.validTreePos(level, pos)) {
                                trunkPositions.put(pos, this.trunkState.getState(level, random, pos).trySetValue(TrunkBlock.getPropertyForDirection(out.getOpposite()), WallSide.LOW));
                            }
                        }
                    }

                    Map.Entry<BlockPos, BlockState>[] trunkEntries = trunkPositions.entrySet().toArray(Map.Entry[]::new);
                    for (int i = 1; i < trunkPositions.size(); i++) {
                        Map.Entry<BlockPos, BlockState> previousEntry = trunkEntries[i - 1];
                        Map.Entry<BlockPos, BlockState> entry = trunkEntries[i];

                        Direction directionToPrevious = Direction.getApproximateNearest(previousEntry.getKey().getCenter().subtract(entry.getKey().getCenter()));

                        if (directionToPrevious == Direction.UP) {
                            BlockState state = entry.getValue();
                            state = state.setValue(TrunkBlock.TALL, true);
                            if (previousEntry.getValue().getValue(TrunkBlock.getPropertyForDirection(out.getOpposite())) != WallSide.NONE) {
                                state = state.setValue(TrunkBlock.getPropertyForDirection(out.getOpposite()), WallSide.TALL);
                            }
                            trunkPositions.put(entry.getKey(), state);
                        }
                        if (i < trunkPositions.size() - 1) {
                            Map.Entry<BlockPos, BlockState> nextEntry = trunkEntries[i + 1];
                            Direction directionToNext = Direction.getApproximateNearest(nextEntry.getKey().getCenter().subtract(entry.getKey().getCenter()));

                            if (directionToPrevious.getAxis().isHorizontal()) {
                                trunkPositions.put(previousEntry.getKey(), previousEntry.getValue().setValue(TrunkBlock.getPropertyForDirection(directionToPrevious.getOpposite()), WallSide.LOW));
                                trunkPositions.put(entry.getKey(), entry.getValue().setValue(TrunkBlock.getPropertyForDirection(directionToPrevious), WallSide.LOW));
                            }
                            if (directionToNext.getAxis().isHorizontal()) {
                                trunkPositions.put(entry.getKey(), entry.getValue().setValue(TrunkBlock.getPropertyForDirection(directionToNext), WallSide.LOW));
                                trunkPositions.put(nextEntry.getKey(), nextEntry.getValue().setValue(TrunkBlock.getPropertyForDirection(directionToNext.getOpposite()), WallSide.LOW));
                            }
                        }
                    }

                    for (Map.Entry<BlockPos, BlockState> entry : trunkPositions.entrySet()) {
                        level.setBlock(entry.getKey(), entry.getValue(), 1 | 2);
                    }
                }
            }
        }
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.GIANT_TRUNK.get();
    }
}
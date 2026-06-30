package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SimpleTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<SimpleTrunkTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("trunk_block_provider").forGetter(decorator -> decorator.trunkState),
                    Codec.FLOAT.fieldOf("placement_chance").forGetter(decorator -> decorator.placementChance),
                    Codec.FLOAT.fieldOf("above_placement_chance").forGetter(decorator -> decorator.abovePlacementChance),
                    Codec.FLOAT.fieldOf("side_placement_chance").forGetter(decorator -> decorator.sidePlacementChance)
            ).apply(instance, SimpleTrunkTreeDecorator::new));

    private final BlockStateProvider trunkState;
    private final float placementChance;
    private final float abovePlacementChance;
    private final float sidePlacementChance;

    public SimpleTrunkTreeDecorator(BlockStateProvider trunkState, float placementChance, float abovePlacementChance, float sidePlacementChance) {
        this.trunkState = trunkState;
        this.placementChance = placementChance;
        this.abovePlacementChance = abovePlacementChance;
        this.sidePlacementChance = sidePlacementChance;
    }

    @Override
    public void place(Context context) {
        LevelSimulatedReader level = context.level();
        RandomSource random = context.random();

        if (context.logs().size() > 1) {
            BlockPos pos = context.logs().get(1);
            TrunkMap trunkMap = new TrunkMap(pos);

            int treeHeight = 0;
            int lastHeight = Integer.MIN_VALUE;
            for (BlockPos logPos : context.logs()) {
                if (logPos.getY() > lastHeight) {
                    treeHeight++;
                }
                lastHeight = logPos.getY();
            }

            if (treeHeight > 8) {
                for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
                    BlockPos trunkPos = pos;
                    if (TreeFeature.validTreePos(level, trunkPos.relative(direction)) && random.nextFloat() < this.placementChance) {
                        trunkPos = trunkPos.relative(direction);
                        trunkMap.add(trunkPos);

                        if (TreeFeature.validTreePos(level, trunkPos.above()) && treeHeight > 10 && random.nextDouble() < this.abovePlacementChance) {
                            trunkMap.add(trunkPos.above());
                        }

                        if (TreeFeature.validTreePos(level, trunkPos.below())) {
                            trunkPos = trunkPos.below();
                            trunkMap.add(trunkPos);
                        }
                    }
                }

                for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
                    if (random.nextDouble() < this.sidePlacementChance) {
                        for (int y = 1; y >= 0; y--) {
                            BlockPos trunkPos = pos.below(y);
                            if (trunkMap.get(trunkPos, direction.getNormal())) {
                                trunkPos = trunkPos.relative(direction);
                                boolean placed = false;
                                for (Direction neighborDirection : new Direction[]{direction.getClockWise(), direction.getCounterClockWise()}) {
                                    BlockPos neighborPos = trunkPos.relative(neighborDirection);
                                    if (TreeFeature.validTreePos(level, neighborPos) && !trunkMap.get(neighborPos, neighborDirection.getClockWise().getNormal()) && !trunkMap.get(neighborPos, neighborDirection.getCounterClockWise().getNormal())) {
                                        trunkPos = neighborPos;
                                        trunkMap.add(trunkPos);
                                        placed = true;
                                    }
                                }
                                if (placed && TreeFeature.validTreePos(level, trunkPos.below())) {
                                    trunkPos = trunkPos.below();
                                    trunkMap.add(trunkPos);
                                }
                                break;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < TrunkMap.SIZE.getX(); x++) {
                for (int y = 0; y < TrunkMap.SIZE.getY(); y++) {
                    for (int z = 0; z < TrunkMap.SIZE.getZ(); z++) {
                        boolean hasTrunk = trunkMap.map[x][y][z];
                        Vec3i indices = new Vec3i(x, y, z);
                        if (hasTrunk && !indices.equals(TrunkMap.START) && !indices.equals(TrunkMap.START.above()) && !indices.equals(TrunkMap.START.below())) {
                            Vec3i offset = indices.subtract(TrunkMap.START);
                            BlockPos worldPos = pos.offset(offset);

                            BlockState trunkState = this.trunkState.getState(random, worldPos);

                            for (Direction directionCheck : Direction.Plane.HORIZONTAL) {
                                if (trunkMap.get(worldPos, directionCheck.getNormal()) || checkBlock(context, worldPos.relative(directionCheck), BlockState::isSolid)) {
                                    WallSide wallSide = WallSide.LOW;
                                    if ((trunkMap.get(worldPos, Vec3i.ZERO.above()) || checkBlock(context, worldPos.above(), BlockState::isSolid))
                                            && checkBlock(context, worldPos.above().relative(directionCheck), BlockState::isSolid)) {
                                        wallSide = WallSide.TALL;
                                    }
                                    trunkState = trunkState.setValue(TrunkBlock.getPropertyForDirection(directionCheck), wallSide);
                                }
                            }
                            if (trunkMap.get(worldPos, Vec3i.ZERO.above()) || checkBlock(context, worldPos.above(), BlockState::isSolid)) {
                                trunkState = trunkState.setValue(TrunkBlock.TALL, true);
                            }

                            if (TreeFeature.validTreePos(level, worldPos)) {
                                context.setBlock(worldPos, trunkState);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.SIMPLE_TRUNK.get();
    }

    private static boolean checkBlock(Context context, BlockPos pos, Predicate<BlockState> predicate) {
        return context.level().isStateAtPosition(pos, predicate);
    }

    public static class TrunkMap {
        public static final Vec3i SIZE = new Vec3i(3, 4, 3);
        public static final Vec3i START = new Vec3i(1, 2, 1);
        public final boolean[][][] map = new boolean[SIZE.getX()][SIZE.getY()][SIZE.getZ()];
        public final BlockPos origin;

        public TrunkMap(BlockPos origin) {
            this.origin = origin;
            this.map[START.getX()][START.getY() - 1][START.getZ()] = true;
            this.map[START.getX()][START.getY()][START.getZ()] = true;
            this.map[START.getX()][START.getY() + 1][START.getZ()] = true;
        }

        public void add(BlockPos pos) {
            Vec3i arrayPos = this.getArrayPos(pos);
            if (this.isWithinBounds(arrayPos)) {
                this.map[arrayPos.getX()][arrayPos.getY()][arrayPos.getZ()] = true;
            }
        }

        public boolean get(Vec3i pos, Vec3i relative) {
            Vec3i arrayPos = this.getArrayPos(pos).offset(relative);
            if (this.isWithinBounds(arrayPos)) {
                return this.map[arrayPos.getX()][arrayPos.getY()][arrayPos.getZ()];
            }
            return false;
        }

        public Vec3i getLocalPos(Vec3i pos) {
            return new Vec3i(
                    this.origin.getX() - pos.getX(),
                    this.origin.getY() - pos.getY(),
                    this.origin.getZ() - pos.getZ()
            );
        }

        public Vec3i getArrayPos(Vec3i pos) {
            return START.subtract(this.getLocalPos(pos));
        }

        public boolean isWithinBounds(Vec3i pos) {
            if (pos.getX() >= 0 && pos.getX() < SIZE.getX()
                    && pos.getY() >= 0 && pos.getY() < SIZE.getY()
                    && pos.getZ() >= 0 && pos.getZ() < SIZE.getZ()) {
                return true;
            }
            return false;
        }
    }
}

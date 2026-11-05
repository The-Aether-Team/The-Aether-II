package com.aetherteam.aetherii.world.tree.decorator;

import com.aetherteam.aetherii.block.natural.TrunkBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.function.Function;

public class CrystalTrunkTreeDecorator extends TreeDecorator {
    public static final MapCodec<CrystalTrunkTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BlockStateProvider.CODEC.fieldOf("log_block_provider").forGetter(decorator -> decorator.logState),
                    BlockStateProvider.CODEC.fieldOf("wood_block_provider").forGetter(decorator -> decorator.woodState),
                    BlockStateProvider.CODEC.fieldOf("trunk_block_provider").forGetter(decorator -> decorator.trunkState)
            ).apply(instance, CrystalTrunkTreeDecorator::new));

    private final BlockStateProvider logState;
    private final BlockStateProvider woodState;
    private final BlockStateProvider trunkState;

    public CrystalTrunkTreeDecorator(BlockStateProvider logState, BlockStateProvider woodState, BlockStateProvider trunkState) {
        this.logState = logState;
        this.woodState = woodState;
        this.trunkState = trunkState;
    }

    @Override
    public void place(Context context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        if (context.logs().size() > 1) {
            BlockPos pos = context.logs().get(1);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos.MutableBlockPos mutableBlockPos = pos.relative(direction).mutable();
                if (random.nextFloat() > 0.33F) {
                    this.placeTrunk(level, random, mutableBlockPos, (state) -> state.setValue(TrunkBlock.getPropertyForDirection(direction.getOpposite()), WallSide.LOW));
                    this.placeWood(level, random, mutableBlockPos.move(Direction.DOWN));
                    if (level.getBlockState(mutableBlockPos.relative(direction).below()).isSolid()) {
                        this.placeWood(level, random, mutableBlockPos.move(direction));
                    }
                    this.placeWood(level, random, mutableBlockPos.move(Direction.DOWN));
                    if (random.nextFloat() > 0.15F) {
                        this.placeWood(level, random, mutableBlockPos.move(direction));

                        if (level.getBlockState(mutableBlockPos.below()).isSolid()) {
                            this.placeEnds(level, random, mutableBlockPos);
                        } else {
                            Direction offsetDirection = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
                            this.placeWood(level, random, mutableBlockPos.move(offsetDirection));

                            this.placeEnds(level, random, mutableBlockPos);
                        }
                    }
                } else {
                    this.placeWood(level, random, mutableBlockPos);
                    this.placeTrunk(level, random, mutableBlockPos.move(direction), (state) -> state.setValue(TrunkBlock.getPropertyForDirection(direction.getOpposite()), WallSide.LOW));
                    this.placeWood(level, random, mutableBlockPos.move(Direction.DOWN));
                    this.placeWood(level, random, mutableBlockPos.move(Direction.DOWN));
                    if (random.nextFloat() > 0.15F) {
                        this.placeWood(level, random, mutableBlockPos.move(direction));

                        if (level.getBlockState(mutableBlockPos.below()).isSolid()) {
                            this.placeEnds(level, random, mutableBlockPos);
                        } else {
                            Direction offsetDirection = random.nextBoolean() ? direction.getClockWise() : direction.getCounterClockWise();
                            this.placeWood(level, random, mutableBlockPos.move(offsetDirection));

                            this.placeEnds(level, random, mutableBlockPos);
                        }
                    }
                }
            }
        }
    }

    public void placeEnds(WorldGenLevel level, RandomSource random, BlockPos.MutableBlockPos mutableBlockPos) {
        for (int i = 0; i < random.nextInt(4) + 1; i++) {
            this.placeWood(level, random, mutableBlockPos.move(Direction.DOWN));
        }
        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            if (!level.getBlockState(mutableBlockPos.below()).isSolid()) {
                this.placeTrunk(level, random, mutableBlockPos.move(Direction.DOWN), (state) -> state.setValue(TrunkBlock.TALL, true));
            }
        }
    }

    private void placeWood(WorldGenLevel level, RandomSource random, BlockPos pos) {
        this.placeWood(level, random, pos, Function.identity());
    }

    private void placeWood(WorldGenLevel level, RandomSource random, BlockPos pos, Function<BlockState, BlockState> stateModifier) {
        level.setBlock(pos, stateModifier.apply(this.woodState.getState(level, random, pos)), 1 | 2);
    }

    private void placeLog(WorldGenLevel level, RandomSource random, BlockPos pos) {
        this.placeLog(level, random, pos, Function.identity());
    }

    private void placeLog(WorldGenLevel level, RandomSource random, BlockPos pos, Function<BlockState, BlockState> stateModifier) {
        level.setBlock(pos, stateModifier.apply(this.logState.getState(level, random, pos)), 1 | 2);
    }

    private void placeTrunk(WorldGenLevel level, RandomSource random, BlockPos pos) {
        this.placeTrunk(level, random, pos, Function.identity());
    }

    private void placeTrunk(WorldGenLevel level, RandomSource random, BlockPos pos, Function<BlockState, BlockState> stateModifier) {
        level.setBlock(pos, stateModifier.apply(this.trunkState.getState(level, random, pos)), 1 | 2);
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.CRYSTALROOT_TRUNK.get();
    }
}

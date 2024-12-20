package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.feature.configuration.PointedStoneConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Optional;
import java.util.function.Consumer;

public class PointedStoneFeature extends Feature<PointedStoneConfiguration> {
    public PointedStoneFeature(Codec<PointedStoneConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PointedStoneConfiguration> context) {
        LevelAccessor level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        PointedStoneConfiguration config = context.config();
        Optional<Direction> optional = getTipDirection(level, pos, random, config);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos relativePos = pos.relative(optional.get().getOpposite());
            createPatchOfDripstoneBlocks(level, random, relativePos, config);
            int i = 1 + (random.nextFloat() < config.chanceOfTallerDripstone() && DripstoneUtils.isEmptyOrWater(level.getBlockState(pos.relative(optional.get()))) ? random.nextInt(5) : random.nextInt(3));
            growPointedDripstone(level, pos, optional.get(), i, false, random, config);
            return true;
        }
    }

    private static Optional<Direction> getTipDirection(LevelAccessor level, BlockPos pos, RandomSource random, PointedStoneConfiguration config) {
        boolean isAboveBase = isDripstoneBase(level.getBlockState(pos.above()), random, pos, config);
        boolean isBelowBase = isDripstoneBase(level.getBlockState(pos.below()), random, pos, config);
        if (isAboveBase && isBelowBase) {
            return Optional.of(random.nextBoolean() ? Direction.DOWN : Direction.UP);
        } else if (isAboveBase) {
            return Optional.of(Direction.DOWN);
        } else {
            return isBelowBase ? Optional.of(Direction.UP) : Optional.empty();
        }
    }

    private static void createPatchOfDripstoneBlocks(LevelAccessor level, RandomSource random, BlockPos pos, PointedStoneConfiguration config) {
        placeDripstoneBlockIfPossible(level, random, pos, config);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!(random.nextFloat() > config.chanceOfDirectionalSpread())) {
                BlockPos firstRelativePos = pos.relative(direction);
                placeDripstoneBlockIfPossible(level, random, firstRelativePos, config);
                if (!(random.nextFloat() > config.chanceOfSpreadRadius2())) {
                    BlockPos secondRelativePos = firstRelativePos.relative(Direction.getRandom(random));
                    placeDripstoneBlockIfPossible(level, random, secondRelativePos, config);
                    if (!(random.nextFloat() > config.chanceOfSpreadRadius3())) {
                        BlockPos thirdRelativePos = secondRelativePos.relative(Direction.getRandom(random));
                        placeDripstoneBlockIfPossible(level, random, thirdRelativePos, config);
                    }
                }
            }
        }
    }

    protected static boolean placeDripstoneBlockIfPossible(LevelAccessor level, RandomSource random, BlockPos pos, PointedStoneConfiguration config) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS)) {
            level.setBlock(pos, config.stoneBlock().getState(random, pos), 2);
            return true;
        } else {
            return false;
        }
    }

    protected static void growPointedDripstone(LevelAccessor level, BlockPos pos, Direction direction, int height, boolean mergeTip, RandomSource random, PointedStoneConfiguration config) {
        if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())), random, pos, config)) {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            buildBaseToTipColumn(direction, height, mergeTip, (state) -> {
                BlockState stoneBlock = config.stoneBlock().getState(random, mutablePos);
                if (state.is(stoneBlock.getBlock())) {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, level.isWaterAt(mutablePos));
                }
                level.setBlock(mutablePos, state, 2);
                mutablePos.move(direction);
            }, random, mutablePos, config);
        }
    }

    public static boolean isDripstoneBase(BlockState state, RandomSource random, BlockPos pos, PointedStoneConfiguration config) {
        BlockState stoneBlock = config.stoneBlock().getState(random, pos);
        return state.is(stoneBlock.getBlock());
    }

    protected static void buildBaseToTipColumn(Direction direction, int height, boolean mergeTip, Consumer<BlockState> blockSetter, RandomSource random, BlockPos pos, PointedStoneConfiguration config) {
        if (height >= 3) {
            blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.BASE, random, pos, config));

            for (int i = 0; i < height - 3; ++i) {
                blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.MIDDLE, random, pos, config));
            }
        }

        if (height >= 2) {
            blockSetter.accept(createPointedDripstone(direction, DripstoneThickness.FRUSTUM, random, pos, config));
        }

        if (height >= 1) {
            blockSetter.accept(createPointedDripstone(direction, mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP, random, pos, config));
        }
    }

    private static BlockState createPointedDripstone(Direction direction, DripstoneThickness dripstoneThickness, RandomSource random, BlockPos pos, PointedStoneConfiguration config) {
        return config.pointedStoneBlock().getState(random, pos).setValue(PointedDripstoneBlock.TIP_DIRECTION, direction).setValue(PointedDripstoneBlock.THICKNESS, dripstoneThickness);
    }
}

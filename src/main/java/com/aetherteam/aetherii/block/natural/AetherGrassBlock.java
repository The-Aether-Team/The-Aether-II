package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.data.resources.registries.AetherIIBlockIds;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.Util;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.Optional;

/**
 * Based on {@link net.minecraft.world.level.block.GrassBlock}.
 */
public class AetherGrassBlock extends SpreadingSnowyBlock implements BonemealableBlock {
public AetherGrassBlock(BlockBehaviour.Properties properties) {
        super(properties, AetherIIBlockIds.AETHER_DIRT);
    }

    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return level.getBlockState(pos.above()).isAir() && !level.isOutsideBuildHeight(pos.above().getY());
    }

    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    /**
     * Based on {@link net.minecraft.world.level.block.SpreadingSnowyBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.<br><br>
     * Warning for "deprecation" is suppressed due to being copied from what Forge does.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            if (!level.isAreaLoaded(pos, 1)) {
                return;
            }

            level.setBlockAndUpdate(pos, AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
        } else {
            if (!level.isAreaLoaded(pos, 3)) {
                return;
            }

            if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState defaultState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos offsetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (level.getBlockState(offsetPos).is(AetherIIBlocks.AETHER_DIRT.get()) && canPropagate(defaultState, level, offsetPos)) {
                        level.setBlockAndUpdate(offsetPos, defaultState.setValue(SNOWY, isSnowySetting(level.getBlockState(offsetPos.above()))));
                    }
                }
            }
        }
    }

    private static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = levelReader.getBlockState(abovePos);
        if ((aboveState.is(AetherIIBlocks.ARCTIC_SNOW.get()) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) || plantIsSnowed(aboveState)) {
            return true;
        } else if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(levelReader, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(levelReader, abovePos));
            return i < 15;
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        return canBeGrass(state, level, pos) && !level.getFluidState(abovePos).is(FluidTags.WATER);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos above = pos.above();
        BlockState grass = AetherIIBlocks.SHORT_AETHER_GRASS.get().defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> grassFeature = level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE).get(HolyIslesPlacedFeatures.AETHER_GRASS_BONEMEAL);

        label47:
        for (int j = 0; j < 128; ++j) {
            BlockPos testPos = above;

            for (int i = 0; i < j / 16; ++i) {
                testPos = testPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(testPos.below()).is(this) || level.getBlockState(testPos).isCollisionShapeFullBlock(level, testPos)) {
                    continue label47;
                }
            }

            BlockState testState = level.getBlockState(testPos);
            if (testState.is(grass.getBlock()) && random.nextInt(10) == 0) {
                BonemealableBlock bonemealableBlock = (BonemealableBlock) grass.getBlock();
                if (bonemealableBlock.isValidBonemealTarget(level, testPos, testState, false)) {
                    bonemealableBlock.performBonemeal(level, random, testPos, testState);
                }
            }

            if (testState.isAir() && !level.isOutsideBuildHeight(testPos)) {
                if (grassFeature.isPresent()) {
                    grassFeature.get().value().place(level, level.getChunkSource().getGenerator(), random, testPos);
                }
            }
        }
    }

        @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor levelReader, BlockPos pos, BlockPos neighborPos) {
        LevelAccessor scheduledTickAccess = levelReader;
        return direction == Direction.UP ? state.setValue(SNOWY, isSnowySetting(neighborState)) : super.updateShape(state, direction, neighborState, levelReader, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos().above());
        return this.defaultBlockState().setValue(SNOWY, isSnowySetting(blockState));
    }

    public static boolean isSnowySetting(BlockState state) {
        return state.is(AetherIIBlocks.ARCTIC_SNOW.get()) || state.is(AetherIIBlocks.ARCTIC_SNOW_BLOCK.get()) || plantIsSnowed(state);
    }

    public static boolean plantIsSnowed(BlockState state) {
        return state.getBlock() instanceof Snowable snowable && snowable.isSnowy(state);
    }

    public static boolean plantNotSnowed(BlockState state) {
        return state.getBlock() instanceof Snowable snowable && !snowable.isSnowy(state);
    }

    public static boolean shouldSnow(Biome biome, LevelReader level, BlockPos pos) {
        if (!biome.warmEnoughToRain(pos)) {
            if (pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight() && level.getBrightness(LightLayer.BLOCK, pos) < 10) {
                BlockState blockState = level.getBlockState(pos);
                return ((blockState.isAir() || blockState.is(AetherIIBlocks.ARCTIC_SNOW.get())) && AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState().canSurvive(level, pos)) || AetherGrassBlock.plantNotSnowed(blockState);
            }
        }
        return false;
    }
}

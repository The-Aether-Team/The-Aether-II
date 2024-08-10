package com.aetherteam.aetherii.block.natural;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.highlands.HighlandsPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class AetherGrassBlock extends GrassBlock {
    public AetherGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource random, BlockPos pos, TreeConfiguration config) {
        placeFunction.accept(pos, AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
        return true;
    }

    /**
     * Based on {@link net.minecraft.world.level.block.SpreadingSnowyDirtBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.<br><br>
     * Warning for "deprecation" is suppressed due to being copied from what Forge does.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            if (!level.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            level.setBlockAndUpdate(pos, AetherIIBlocks.AETHER_DIRT.get().defaultBlockState());
        } else {
            if (!level.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
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

    private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState blockState = level.getBlockState(abovePos);
        if ((blockState.is(AetherIIBlocks.ARCTIC_SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) || plantIsSnowed(blockState)) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(level, state, pos, blockState, abovePos, Direction.UP, blockState.getLightBlock(level, abovePos));
            return i < level.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        return canBeGrass(state, level, pos) && !level.getFluidState(abovePos).is(FluidTags.WATER);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        Block grass = AetherIIBlocks.AETHER_GRASS_BLOCK.get();
        Optional<Holder.Reference<PlacedFeature>> grassFeatureOptional = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(HighlandsPlacedFeatures.AETHER_GRASS_BONEMEAL);

        start:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockPos = abovePos;

            for (int j = 0; j < i / 16; ++j) {
                blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockPos.below()).is(this) || level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
                    continue start;
                }
            }

            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(grass) && random.nextInt(10) == 0) {
                ((BonemealableBlock) grass).performBonemeal(level, random, blockPos, blockState);
            }

            if (blockState.isAir()) {
                Holder<PlacedFeature> featureHolder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockPos).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }
                    featureHolder = ((RandomPatchConfiguration) list.get(random.nextInt(list.size())).config()).feature();
                } else {
                    if (grassFeatureOptional.isEmpty()) {
                        continue;
                    }
                    featureHolder = grassFeatureOptional.get();
                }
                featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockPos);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.UP ? state.setValue(SNOWY, isSnowySetting(facingState)) : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos().above());
        return this.defaultBlockState().setValue(SNOWY, isSnowySetting(blockState));
    }

    public static boolean isSnowySetting(BlockState state) {
        return state.is(AetherIIBlocks.ARCTIC_SNOW) || state.is(AetherIIBlocks.ARCTIC_SNOW_BLOCK) || plantIsSnowed(state);
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
                return ((blockState.isAir() || blockState.is(AetherIIBlocks.ARCTIC_SNOW)) && AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState().canSurvive(level, pos)) || AetherGrassBlock.plantNotSnowed(blockState);
            }
        }
        return false;
    }
}

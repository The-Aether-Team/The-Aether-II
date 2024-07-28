package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.IceCrystalBlock;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIVegetationFeatures;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.Objects;

public class CoastFeature extends Feature<CoastConfiguration> {
    public CoastFeature(Codec<CoastConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CoastConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        CoastConfiguration config = context.config();

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());

        config.distanceNoise().mapAll(visitor);
        config.patternNoise().ifPresent(noise -> noise.mapAll(visitor));

        for (int x = pos.getX(); x < pos.getX() + 16; ++x) {
            for (int z = pos.getZ(); z < pos.getZ() + 16; ++z) {
                for (int y = config.yRange().getMinValue(); y < config.yRange().getMaxValue(); ++y) {
                    BlockPos placementPos = new BlockPos(x, y, z);
                    int distance = (int) config.distanceNoise().compute(new DensityFunction.SinglePointContext(x, y, z));

                        if (level.getBlockState(placementPos).isAir()
                                && level.getBlockState(placementPos.below(2)).isAir()
                                && level.getBlockState(placementPos.below(4)).isAir()
                                && level.getBlockState(placementPos.below(8)).isAir()
                                && level.getBlockState(placementPos.below(16)).isAir()
                                && level.getBlockState(placementPos.above()).is(config.validBlocks()) && level.getBlockState(placementPos.above(2)).isAir()) {
                            placeCoast(level, config.block(), placementPos, config.size(), random, distance, config.type());
                            placeCoast(level, config.block(), placementPos.below(), config.size(), random, (int) (distance / 1.75F), config.type());
                            break;
                        }
                }
            }
        }
        distributeVegetation(context, level, config, random);
        return true;
    }

    public static void placeCoast(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, int distance, Type type) {
        float radiusSq = radius * radius;
        placeCoastBlock(level, blockProvider, center, random, distance, type);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                placeCoastBlock(level, blockProvider, center.offset(x, 0, z), random, distance, type);
                placeCoastBlock(level, blockProvider, center.offset(-x, 0, -z), random, distance, type);
                placeCoastBlock(level, blockProvider, center.offset(-z, 0, x), random, distance, type);
                placeCoastBlock(level, blockProvider, center.offset(z, 0, -x), random, distance, type);
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeCoastBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, int distance, Type type) {
        if ((level.getBlockState(pos).isAir() || level.getBlockState(pos).getBlock() instanceof BushBlock)
                && (level.getBlockState(pos.north(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.east(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.south(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.west(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
        )) {
            BlockState state = provider.getState(random, pos);
            if (level.setBlock(pos, state, 2)) {
                if (type == Type.ARCTIC) { //todo data-driven
                    SimpleWeightedRandomList.Builder<BlockState> crystals = new SimpleWeightedRandomList.Builder<>();
                    crystals.add(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 3);
                    crystals.add(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 2);
                    crystals.add(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 1);
                    WeightedStateProvider weightedProvider = new WeightedStateProvider(crystals);
                    BlockState crystalState = weightedProvider.getState(random, pos.below());
                    if (random.nextInt(20) == 0 && level.getBlockState(pos.below()).isAir()) {
                        level.setBlock(pos.below(), crystalState, 2);
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected void distributeVegetation(FeaturePlaceContext<CoastConfiguration> context, WorldGenLevel level, CoastConfiguration config, RandomSource random) {
        if (config.brettlChance() > 0.0F && random.nextFloat() < config.brettlChance()) {
            ConfiguredFeature<?, ?> feature = Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(AetherIIVegetationFeatures.BRETTL_PLANT_PATCH).orElse(null)).value();
            feature.place(level, context.chunkGenerator(), random, context.origin());
        }
    }

    public enum Type implements StringRepresentable {
        HIGHFIELDS("highfields"),
        MAGNETIC("magnetic"),
        ARCTIC("arctic");

        public static final Codec<CoastFeature.Type> CODEC = StringRepresentable.fromEnum(CoastFeature.Type::values);
        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
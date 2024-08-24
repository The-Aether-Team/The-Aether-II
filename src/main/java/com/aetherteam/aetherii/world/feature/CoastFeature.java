package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashSet;
import java.util.Set;

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
        Set<BlockPos> set = new HashSet<>();

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
                            placeCoast(level, config.block(), placementPos, config.size(), random, distance, set);
                            placeCoast(level, config.block(), placementPos.below(), config.size(), random, (int) (distance / 1.75F), set);
                            break;
                        }
                }
            }
        }
        this.distributeVegetation(context, level, config, random, set);
        return true;
    }

    public static void placeCoast(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, int distance, Set<BlockPos> set) {
        float radiusSq = radius * radius;
        placeCoastBlock(level, blockProvider, center, random, distance, set);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                placeCoastBlock(level, blockProvider, center.offset(x, 0, z), random, distance, set);
                placeCoastBlock(level, blockProvider, center.offset(-x, 0, -z), random, distance, set);
                placeCoastBlock(level, blockProvider, center.offset(-z, 0, x), random, distance, set);
                placeCoastBlock(level, blockProvider, center.offset(z, 0, -x), random, distance, set);
            }
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeCoastBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, int distance, Set<BlockPos> set) {
        if (level.getBlockState(pos).canBeReplaced()
                && (level.getBlockState(pos.north(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.east(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.south(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
                || level.getBlockState(pos.west(distance)).is(AetherIITags.Blocks.SHAPES_COASTS)
        )) {
            BlockState state = provider.getState(random, pos);
            if (level.setBlock(pos, state, 2)) {
                set.add(pos);
                return true;
            }
        }
        return false;
    }

    protected void distributeVegetation(FeaturePlaceContext<CoastConfiguration> context, WorldGenLevel level, CoastConfiguration config, RandomSource random, Set<BlockPos> set) {
        for (BlockPos blockPos : set) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                config.vegetationFeature().ifPresent(placedFeatureHolder -> placedFeatureHolder.value().place(level, context.chunkGenerator(), random, blockPos));
            }
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
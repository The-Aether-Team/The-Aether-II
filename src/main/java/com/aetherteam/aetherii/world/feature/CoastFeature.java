package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.CoastConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashSet;
import java.util.LinkedHashSet;
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

        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed()); //todo remove distance noise
        config.distanceNoise().mapAll(visitor);

        ChunkPos chunkPos = ChunkPos.containing(pos);
        pos = chunkPos.getBlockAt(0, pos.getY(), 0);

        //todo
        //  fix chunk cascade issue
        //  restore bottom second layer of quicksoil
        //  more frequent generation and at more y levels
        //  this all doesnt apply to lakes

        BlockPos origin = null;

        for (int x = pos.getX(); x < pos.getX() + 16; ++x) {
            for (int z = pos.getZ(); z < pos.getZ() + 16; ++z) {
                BlockPos offset = new BlockPos(x, pos.getY(), z);
                if (level.getBlockState(offset).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && level.getBlockState(offset.above()).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && level.getBlockState(offset.below()).is(AetherIITags.Blocks.SHAPES_COASTS)
                        && (!level.getBlockState(offset.north()).isSolid()
                        || !level.getBlockState(offset.east()).isSolid()
                        || !level.getBlockState(offset.south()).isSolid()
                        || !level.getBlockState(offset.west()).isSolid())) {
                    origin = offset;
                    break;
                }
            }
        }

        if (origin != null) {
            Set<BlockPos> coastPositions = new LinkedHashSet<>();

            BlockPos pointer = origin;
            boolean start = false;
            for (int i = 0; i < 16; i++) {
                boolean end = true;
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockPos offset = pointer.relative(direction);
                    if (!coastPositions.contains(offset)
                            && level.getBlockState(offset).is(AetherIITags.Blocks.SHAPES_COASTS)
                            && (!level.getBlockState(offset.north()).isSolid()
                            || !level.getBlockState(offset.north().east()).isSolid()
                            || !level.getBlockState(offset.east()).isSolid()
                            || !level.getBlockState(offset.south().east()).isSolid()
                            || !level.getBlockState(offset.south()).isSolid()
                            || !level.getBlockState(offset.south().west()).isSolid()
                            || !level.getBlockState(offset.west()).isSolid()
                            || !level.getBlockState(offset.north().west()).isSolid())) {
                        coastPositions.add(offset);
                        pointer = offset;
                        start = true;
                        end = false;
                    }
                    if (start && end) {
                        break;
                    }
                }
            }

            //todo proper smoothing and also random subtraction if > 1
            //  can i maybe interpolate the values into more gradual decimals
            if (coastPositions.size() > 8) {
                int i = 0;
                int max = coastPositions.size() - 1;
                float half = max / 2.0F;
                for (BlockPos coastPos : coastPositions) {
                    int mainDistToCenter = Mth.ceil(Math.abs(i - half));
                    int scale = Mth.floor((coastPositions.size() / 2.0F) - mainDistToCenter);
                    float radius = Mth.floor(Math.pow(scale + 1, 0.65F)) + 0.25F;
                    placeCoast(level, config.block(), coastPos, radius, random, set);
//                    placeCoast(level, config.block(), coastPos.below(), radius - 1, random, set); //todo
                    i += 1;
                }
            }
        }
        this.distributeVegetation(context, level, config, random, set);
        return true;
    }

    public static void placeCoast(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, Set<BlockPos> set) {
        float radiusSq = radius * radius;
        placeCoastBlock(level, blockProvider, center, random, set);
        for (int z = 0; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                if (x * x + z * z <= radiusSq) {
                    placeCoastBlock(level, blockProvider, center.offset(x, 0, z), random, set);
                    placeCoastBlock(level, blockProvider, center.offset(-x, 0, -z), random, set);
                    placeCoastBlock(level, blockProvider, center.offset(-z, 0, x), random, set);
                    placeCoastBlock(level, blockProvider, center.offset(z, 0, -x), random, set);
                }
            }
        }
    }

    @SuppressWarnings({"UnusedReturnValue", "deprecation"})
    public static boolean placeCoastBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, Set<BlockPos> set) {
        if ((!level.getBlockState(pos).is(AetherIITags.Blocks.SHAPES_COASTS)
                || !level.getBlockState(pos.below()).is(AetherIITags.Blocks.SHAPES_COASTS)
                || !level.getBlockState(pos.above()).is(AetherIITags.Blocks.SHAPES_COASTS))
                && !level.getBlockState(pos).liquid()) {
            BlockState state = provider.getState(level, random, pos);
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
}
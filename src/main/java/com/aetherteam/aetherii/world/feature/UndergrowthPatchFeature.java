package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.feature.configuration.UndergrowthPatchConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class UndergrowthPatchFeature extends Feature<UndergrowthPatchConfiguration> {

    public UndergrowthPatchFeature(Codec<UndergrowthPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<UndergrowthPatchConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        UndergrowthPatchConfiguration config = context.config();
        ChunkGenerator chunkGenerator = context.chunkGenerator();

        placeDisk(level, config.block(), pos, config.radius().sample(random), random, config, chunkGenerator, 1);
        placeDisk(level, config.block(), pos.below(), config.radiusBelow().sample(random), random, config, chunkGenerator, 2);
        return true;
    }

    private static void placeDisk(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, UndergrowthPatchConfiguration config, ChunkGenerator chunkGenerator, int offset) {
        float radiusSq = radius * radius;
        placeProvidedBlock(level, blockProvider, center, random, config, chunkGenerator, offset);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                placeProvidedBlock(level, blockProvider, center.offset(x, 0, z), random, config, chunkGenerator, offset);
                placeProvidedBlock(level, blockProvider, center.offset(-x, 0, -z), random, config, chunkGenerator, offset);
                placeProvidedBlock(level, blockProvider, center.offset(-z, 0, x), random, config, chunkGenerator, offset);
                placeProvidedBlock(level, blockProvider, center.offset(z, 0, -x), random, config, chunkGenerator, offset);
            }
        }
    }

    public static void placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, UndergrowthPatchConfiguration config, ChunkGenerator chunkGenerator, int offset) {
            BlockPos.MutableBlockPos mutable = pos.mutable();
            for (int blockOffset = 0; level.isStateAtPosition(mutable.above(offset), BlockBehaviour.BlockStateBase::isAir) && blockOffset < 5; ++blockOffset) {
                mutable.move(Direction.UP);
            }

        level.setBlock(mutable.above(offset - 1), provider.getState(level, random, mutable.above(offset - 1)), 2);
                /*
                if (level.getBlockState(mutable.above(offset - 1)).isAir()) {
                    level.setBlock(mutable.above(offset - 1), provider.getState(level, random, mutable.above(offset - 1)), 2);
                }

                 */
        if (level.getBlockState(mutable.below()).isAir()) {
            if (config.vegetationChance() > 0.0F && random.nextFloat() < config.vegetationChance()) {
                config.vegetationFeature().value().place(level, chunkGenerator, random, mutable.below());
            }
        }
    }
}
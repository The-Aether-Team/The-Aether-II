package com.aetherteam.aetherii.world.feature;

import com.aetherteam.aetherii.world.density.PerlinNoiseFunction;
import com.aetherteam.aetherii.world.feature.configuration.LoganRockConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;

public class LoganRockFeature extends Feature<LoganRockConfiguration> {
    public LoganRockFeature(Codec<LoganRockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LoganRockConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        LoganRockConfiguration config = context.config();

        Set<BlockPos> positions = new HashSet<>();

        DensityFunction noise = config.noise();
        DensityFunction.Visitor visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed());
        noise.mapAll(visitor);
        float radius = config.radius().sample(random);

        BlockPos randomizedPos = new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-2, 2), pos.getY(), pos.getZ() + random.nextIntBetweenInclusive(-2, 2));

        this.placeRock(radius, level, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-2, 2), pos.getY(), pos.getZ() + random.nextIntBetweenInclusive(-2, 2)), context, config, noise, positions, random.nextBoolean());
        this.placeRock(radius, level, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-2, 2), pos.getY() + 2, pos.getZ() + random.nextIntBetweenInclusive(-2, 2)), context, config, noise, positions, random.nextBoolean());
        this.placeRock(radius, level, new BlockPos(pos.getX() + random.nextIntBetweenInclusive(-2, 2), pos.getY() + random.nextIntBetweenInclusive(3, 5), pos.getZ() + random.nextIntBetweenInclusive(-2, 2)), context, config, noise, positions, true);



        return true;
    }

    public void placeRock(float radius, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<LoganRockConfiguration> context, LoganRockConfiguration config, DensityFunction noise, Set<BlockPos> positions, boolean small) {
            float adjustedRadius = small ? radius * 0.5F : radius;
            int smallOffset = small ? 1 : 0;

            this.placeRockLayer(adjustedRadius * 0.6F, level, pos.below(), context, config, noise, positions);
            if (!small) {
                this.placeRockLayer(adjustedRadius, level, pos, context, config, noise, positions);
            }
            this.placeRockLayer(adjustedRadius * 0.7F, level, pos.above().below(smallOffset), context, config, noise, positions);
            this.placeRockLayer(adjustedRadius * 0.45F, level, pos.above(2).below(smallOffset), context, config, noise, positions);
    }

    public void placeRockLayer(float radius, WorldGenLevel level, BlockPos pos, FeaturePlaceContext<LoganRockConfiguration> context, LoganRockConfiguration config, DensityFunction noise, Set<BlockPos> positions) {
        this.placeDisk(config, level, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), radius, positions);

        //if (this.groundChecks(level, pos, positions)) {
            for (BlockPos position : positions) {
                double density = noise.compute(new DensityFunction.SinglePointContext(position.getX(), position.getY(), position.getZ()));
                if (position.getY() == pos.getY()) {
                    double densitySmoothed = density - Mth.clamp(position.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) * config.inclineFactor() - radius * config.scatterFactor(), 0, 10);
                    if (densitySmoothed > 0) {
                        if (radius > 1) {
                            level.setBlock(position, config.state().getState(level, context.random(), position), 2);
                        }
                    }
                }
            }
       // }
    }

    public void placeDisk(LoganRockConfiguration config, WorldGenLevel level, BlockPos center, float radius, Set<BlockPos> positions) {
        float radiusSq = radius * radius;
        this.placeProvidedBlock(config, level, center, positions);
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                this.placeProvidedBlock(config, level, center.offset(x, 0, z), positions);
                this.placeProvidedBlock(config, level, center.offset(-x, 0, -z), positions);
                this.placeProvidedBlock(config, level, center.offset(-z, 0, x), positions);
                this.placeProvidedBlock(config, level, center.offset(z, 0, -x), positions);
            }
        }
    }

    public void placeProvidedBlock(LoganRockConfiguration config, WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        BlockState state = level.getBlockState(pos);
        if (!state.isSolid() || state.is(config.validBlocks())) {
            positions.add(pos);
        }
    }

    public boolean groundChecks(WorldGenLevel level, BlockPos pos, Set<BlockPos> positions) {
        for (BlockPos position : positions) {
            if (position.getY() == pos.getY()) {
                if (!level.getBlockState(position.below(5)).isSolid()) {
                    return false;
                }
            }
        }
        return true;
    }
}